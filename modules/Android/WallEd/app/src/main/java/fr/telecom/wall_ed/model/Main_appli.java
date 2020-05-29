package fr.telecom.wall_ed.model;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Calendar;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.telecom.wall_ed.model.Dechet;

public class Main_appli implements Runnable{

    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private static int count = 0;
    private String name = "Client-";
    private String command = "none";
    private JSONObject data;
    private int sessionID = -1;
    private Eleve[] eleves = {};
    private boolean initEleve = false;
    private Queue<Dechet> dechets = new LinkedList<>();
    private Date lastUpdate;
    private boolean isReadyToGetStats = false;

    public Main_appli(String host, int port){
        lastUpdate = Calendar.getInstance().getTime();
        name += ++count;
        try {
            connexion = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("socket created on port");
    }

    @Override
    public void run(){

        while(!connexion.isClosed()){
            try {

                //Si les stats datent d'il y a plus de 5s, qu'aucune commande n'est en attente et que le serveur est prêt, on les met à jour
                if (sessionID>-1 && command.equals("none") && (Calendar.getInstance().getTime().getTime()-lastUpdate.getTime())/1000 > 5){
                    if (isReadyToGetStats){
                        Log.i("PACT32_DEBUG", "(Main_appli) getStats command sent");
                        command = "getStats";
                    }else{
                        Log.i("PACT32_DEBUG", "(Main_appli) isReadyToGetStats set to true");
                        isReadyToGetStats = true;
                    }
                    lastUpdate = Calendar.getInstance().getTime();
                }

                writer = new PrintWriter(connexion.getOutputStream(), true);
                reader = new BufferedInputStream(connexion.getInputStream());

                switch(command){
                    case "initSession":
                        System.out.println("sending command to server");
                        writer.write(command);
                        writer.flush();
                        String resp = read();
                        System.out.println("receiving request");
                        if (resp.equals("send")) {
                            System.out.println("sending init info");
                            Thread.sleep(2000);
                            data.writeJSONString(writer);
                            writer.flush();
                            System.out.println("init info sent");
                            sessionID = Integer.parseInt(read());
                            System.out.println("this session ID is : " + Integer.toString(sessionID));
                        }
                        else {System.out.println("no matching");}
                        command = "none";
                        break;
                    case "getStats":
                        writer.write(command);
                        writer.flush();
                        String receiv;
                        while (!(receiv = read()).equals("nomore")) {
                            JSONObject dechetJSON = this.decode(receiv);
                            Dechet dechet = new Dechet(
                                    (int)dechetJSON.get("dechetID"),
                                    (int)dechetJSON.get("braceletID"),
                                    (String)dechetJSON.get("type"),
                                    (String)dechetJSON.get("typePropose"),
                                    (boolean)dechetJSON.get("reponseEleve"),
                                    (String)dechetJSON.get("heureRamassage"));
                            dechets.add(dechet);
                        }
                        command = "none";
                        Log.i("PACT32_DEBUG", "(Main_appli) size of dechets: " + dechets.size());
                        break;
                    case "getEleves":
                        writer.write(command);
                        writer.flush();
                        String res = read();
                        JSONObject eleves = this.decode(res);
                        this.eleves = this.jsonToEleve(eleves);
                        this.initEleve = true;
                        command = "none";
                        break;

                    case "close":
                        writer.write("close");
                        writer.flush();
                        writer.close();
                        break;
                    default :
                        break;
                }

            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void initSession(String[] noms, String[] prenoms, String[] braceletsID) {
        command = "initSession";
        int nb = noms.length;
        JSONObject lastNames = new JSONObject();
        JSONObject firstNames = new JSONObject();
        JSONObject IDs = new JSONObject();
        for(int i = 0; i<nb;i++) {
            lastNames.put(((Integer)i).toString(),noms[i] );
        }
        for(int i = 0; i<nb;i++) {
            firstNames.put(((Integer)i).toString(),prenoms[i] );
        }for(int i = 0; i<nb;i++) {
            IDs.put(((Integer)i).toString(),braceletsID[i] );
        }
        data = new JSONObject();
        data.put("numberOfStudents", nb);
        data.put("lastNames", lastNames);
        data.put("firstNames", firstNames);
        data.put("IDs", IDs);
    }

    public void recupEleves() {
        command = "getEleves";
    }

    public void getStats() {
        command = "getStats";
    }

    public void stop() {
        command = "close";
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
        JSONParser parser;
        JSONObject json = null;
        try {
            parser = new JSONParser();
            json = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Eleve[] jsonToEleve(JSONObject json) {
        long nbtemp = (long)json.get("numberOfStudents");
        int nb = (int)nbtemp;
        String nom;
        String prenom;
        int ID;
        JSONObject lastNames = (JSONObject)json.get("lastNames");
        JSONObject firstNames = (JSONObject)json.get("firstNames");
        JSONObject IDs = (JSONObject)json.get("IDs");
        Eleve[] res = new Eleve[nb];
        for (int i =0; i<nb; i++) {
            String strI = Integer.toString(i);
            nom = (String)lastNames.get(strI);
            prenom = (String)firstNames.get(strI);
            long temp = (long)IDs.get(strI);
            ID = (int) temp;
            Eleve elev = new Eleve(ID, nom, prenom);
            res[i] = elev;
        }
        return res;
    }

    //---------- Code Android ----------


    public Eleve[] getEleves() {
        recupEleves();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eleves;
    }

    public ArrayList<Dechet> getDechets() {
        ArrayList list = new ArrayList(dechets);
        dechets.clear();
        return list;
    }
}
