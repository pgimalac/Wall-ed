package interface_server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Main.*;

public class RobotClientProcessor implements Runnable{

   private Socket sock;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;
   private final Activite act;
   
   public RobotClientProcessor(Socket pSock, Activite act){
      sock = pSock;
      this.act = act;
   }
   
   @Override
   public void run(){
      System.err.println("Lancement du traitement de la connexion robot");

      boolean closeConnexion = false;
      while(!sock.isClosed()){
         
         try {
            
            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedInputStream(sock.getInputStream());
                        
            String response = read();
            
            InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();
            String debug = "";
            debug = "Thread : " + Thread.currentThread().getName() + ". ";
            debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
            debug += " Sur le port : " + remote.getPort() + ".\n";
            debug += "\t -> Commande reçue : " + response + "\n";
            System.err.println("\n" + debug);
            
            switch(response){
               case "init":
            	   Eleve[] elvs = this.act.getSession().getEleves();
            	   int nb = elvs.length;
            	   String[] noms = new String[nb];
            	   String[] prenoms = new String[nb];
            	   int[] ids = new int[nb];
            	   for (int i = 0; i < nb; i++) {
            		   noms[i] = elvs[i].getNom();
            		   prenoms[i] = elvs[i].getPrenom();
            		   ids[i] = elvs[i].getEleveID();
            	   }
            	   JSONObject toSend = this.encode(noms, prenoms, ids);
            	   toSend.writeJSONString(writer);
            	   writer.flush();
               	   break;
               case "newImage":
            	   // TODO
            	   String stringData1 = read();
            	   // receive the image, store it in a specific folder created for this session and launch IA
            	   // then send results to the robot
            	   // receive the answer of the robot :
            	   String stringData = read();
            	   JSONObject data = decode(stringData);
            	   boolean trashFound = (boolean) data.get("trashFound");
            	   // --> if trash found then we get the trash info and continue in "RECHERCHE" mode
            	   // --> if no trash found nothing is done more
            	   if (trashFound) {
            		   int braceletID = (int) data.get("braceletID");
            		   String type = (String) data.get("type");
            		   String typePropose = (String) data.get("typePropose");
            		   boolean reponseEleve = (boolean) data.get("reponseEleve");
            		   this.act.changeMode();
            		   Dechet dechet = new Dechet(this.act.getSession(), braceletID, type, typePropose, reponseEleve);
            		   // send this dechet info to the app ?
            	   }
               case "close":
            	   writer.write("Communication terminée");
            	   writer.flush();
            	   closeConnexion = true;
            	   break;
               default : 
            	   writer.write("Commande inconnue !");
            	   writer.flush();
            	   break;
            }

            if(closeConnexion){
               System.err.println("COMMANDE CLOSE DETECTEE ! ");
               writer = null;
               reader = null;
               sock.close();
               break;
            }
         }catch(SocketException e){
            System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
            break;
         } catch (IOException e) {
            e.printStackTrace();
         }         
      }
   }
   
   private String read() throws IOException{      
      String response = "";
      int stream;
      byte[] b = new byte[4096];
      stream = reader.read(b);
      response = new String(b, 0, stream);
      return response;
   }
   
   private JSONObject decode(String input){
	   JSONParser parser = new JSONParser();
	   Object ObjData;
	   JSONObject jSONData = new JSONObject();
	try {
		ObjData = parser.parse(input);
		   jSONData = (JSONObject)(((JSONArray)ObjData).get(1));
	} catch (ParseException e) {
		e.printStackTrace();
	}
	   return jSONData;
   }
   
   private JSONObject encode(String[] noms, String[] prenoms, int[] ids) {
	   int nb = noms.length;
	   JSONObject lastNames = new JSONObject();
	   JSONObject firstNames = new JSONObject();
	   JSONObject IDs = new JSONObject();
	   for(int i = 0; i<nb;i++) {
		   lastNames.put(((Integer)i).toString(), noms[i]);
	   }
	   for(int i = 0; i<nb;i++) {
		   firstNames.put(((Integer)i).toString(), prenoms[i]);
	   }for(int i = 0; i<nb;i++) {
		   IDs.put(((Integer)i).toString(),ids[i] );
	   }
	   JSONObject data = new JSONObject();
	   data.put("numberOfStudents", nb);
	   data.put("lastNames", lastNames);
	   data.put("firstNames", firstNames);
	   data.put("IDs", IDs);
	   
	   return data;
   }
   
   public void modeRecherche() {
	   // send "RECHERCHE" to the robot
   }
}