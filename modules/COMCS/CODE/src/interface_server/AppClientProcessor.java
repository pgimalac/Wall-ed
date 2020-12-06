package interface_server;
import Main.*;
import interface_bdd.AjoutEleve;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AppClientProcessor implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private int sessionID;
    private Activite act;
    private boolean initDone = false;
    private Queue<Dechet> dechetQueue = new LinkedList<Dechet>();

    public AppClientProcessor(Socket pSock) { sock = pSock; }

    @Override
    public void run() {
        System.err.println(
            "[AppCP] Lancement du traitement de la connexion appli");

        boolean closeConnexion = false;
        while (!sock.isClosed()) {

            try {

                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());
                String response = read();
                InetSocketAddress remote =
                    (InetSocketAddress)sock.getRemoteSocketAddress();

                String debug = "";
                debug = "[AppCP] " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " +
                         remote.getAddress().getHostAddress() + ".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "\t -> Commande reçue : " + response + "\n";
                System.err.println("\n" + debug);

                switch (response) {
                case "initSession":
                    writer.write("send");
                    writer.flush();
                    System.out.println("[AppCP] asked app to send init info");
                    String stringData = read();
                    System.out.print("[AppCP] info received, decoding ...");
                    Thread.sleep(1000);
                    JSONObject data = decode(stringData);
                    long nbtemp = (long)data.get("numberOfStudents");
                    int nb = (int)nbtemp;
                    String[] noms = new String[nb];
                    String[] prenoms = new String[nb];
                    String[] braceletsID = new String[nb];
                    JSONObject lastNames = (JSONObject)data.get("lastNames");
                    JSONObject firstNames = (JSONObject)data.get("firstNames");
                    JSONObject IDs = (JSONObject)data.get("IDs");
                    for (int i = 0; i < nb; i++) {
                        String strI = Integer.toString(i);
                        noms[i] = (String)lastNames.get(strI);
                        prenoms[i] = (String)firstNames.get(strI);
                        braceletsID[i] = (String)IDs.get(strI);
                    }
                    System.out.println("decoded !");
                    Thread.sleep(1000);
                    System.out.println("[AppCP] creating activity");
                    Activite act =
                        new Activite(noms, prenoms, braceletsID, this);
                    System.out.println("[AppCP] activity created");
                    this.act = act;
                    this.sessionID = act.getSession().getSessionID();
                    this.act.start();
                    writer.write(Integer.toString(sessionID));
                    writer.flush();
                    this.initDone = true;
                    break;
                case "getStats":
                    // récupération des statistiques en temps réel
                    System.out.println("[AppCP] app asked stats");
                    Dechet dechet;
                    while (dechetQueue.peek() != null) {
                        dechet = dechetQueue.poll();
                        JSONObject dechetJSON = new JSONObject();
                        dechetJSON.put("dechetID", dechet.getDechetID());
                        dechetJSON.put("braceletID", dechet.getBraceletID());
                        dechetJSON.put("type", dechet.getType());
                        dechetJSON.put("typePropose", dechet.getTypeEleve());
                        dechetJSON.put("reponseEleve",
                                       dechet.getReponseEleve());
                        dechetJSON.put("heureRamassage",
                                       dechet.getHeureRamassage());
                        dechetJSON.writeJSONString(writer);
                        writer.flush();
                    }
                    writer.write("nomore");
                    writer.flush();
                    break;
                case "close":
                    writer.write("[AppCP] Communication terminée");
                    writer.flush();
                    this.act.stop();
                    closeConnexion = true;
                    break;
                case "getEleves":
                    Eleve[] liste = Main.getElevesInBDD();
                    int len = liste.length;
                    JSONObject nims = new JSONObject();
                    JSONObject prenims = new JSONObject();
                    JSONObject ids = new JSONObject();
                    for (int i = 0; i < len; i++) {
                        nims.put(((Integer)i).toString(), liste[i].getNom());
                    }
                    for (int i = 0; i < len; i++) {
                        prenims.put(((Integer)i).toString(),
                                    liste[i].getPrenom());
                    }
                    for (int i = 0; i < len; i++) {
                        ids.put(((Integer)i).toString(), liste[i].getEleveID());
                    }
                    data = new JSONObject();
                    data.put("numberOfStudents", len);
                    data.put("lastNames", nims);
                    data.put("firstNames", prenims);
                    data.put("IDs", ids);
                    data.writeJSONString(writer);
                    writer.flush();
                    System.out.println("[AppCP] info sent");
                    break;
                case "recupNewEleve":
                    String nom = read();
                    String prenom = read();
                    AjoutEleve.addEleve(nom, prenom);
                    break;
                default:
                    writer.write("[AppCP] Commande inconnue !");
                    writer.flush();
                    break;
                }

                if (closeConnexion) {
                    System.err.println("[AppCP] COMMANDE CLOSE DETECTEE ! ");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
            } catch (SocketException e) {
                System.err.println("[AppCP] LA CONNEXION A ETE INTERROMPUE ! ");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDechet(Dechet dechet) { dechetQueue.add(dechet); }

    private String read() throws IOException {
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    private JSONObject decode(String input) {
        JSONParser parser;
        JSONObject json = null;
        try {
            parser = new JSONParser();
            json = (JSONObject)parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    public boolean getInitSate() { return this.initDone; }
}