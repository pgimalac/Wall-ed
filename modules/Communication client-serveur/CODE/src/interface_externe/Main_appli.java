package interface_externe;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import Main.Main;

public class Main_appli implements Runnable{

   private Socket connexion = null;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;
   private static int count = 0;
   private String name = "Client-";
   private String command = "none";
   
   public Main_appli(String host, int port){
      name += ++count;
      try {
         connexion = new Socket(host, port);
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   @Override
   public void run(){

      while(!connexion.isClosed()){
         try {

            
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());
            
            switch(command){
            case "initSession":
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
            writer.write(command);
            writer.flush();
            
            System.out.println("Commande " + command + " envoyée au serveur");
            
            String response = read();
            System.out.println("\t * " + name + " : Réponse reçue " + response);
            
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
   }
   
   public void initSession() {
	   command = "initSession";
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