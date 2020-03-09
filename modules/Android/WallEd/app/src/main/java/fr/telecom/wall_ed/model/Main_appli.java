package fr.telecom.wall_ed.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import org.json.simple.JSONObject;

public class Main_appli implements Runnable{

    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private static int count = 0;
    private String name = "Client-";
    private String command = "none";
    private JSONObject data;
    private int sessionID;


    public Main_appli(String host, int port){
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

    public void initSession(String[] noms, String[] prenoms, int[] braceletsID) {
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

    public void getStats(int sessionID) {
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
}
