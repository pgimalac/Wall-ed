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

public class AppClientProcessor implements Runnable{

   private Socket sock;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;
   private int sessionID;
   
   public AppClientProcessor(Socket pSock){
      sock = pSock;
   }
   
   @Override
   public void run(){
      System.err.println("Lancement du traitement de la connexion appli");

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
               case "initSession":
            	   writer.write("send");
            	   writer.flush();
            	   String stringData = read();
            	   JSONObject data = decode(stringData);
            	   int nb = (int) data.get("numberOfStudents");
            	   String[] noms = new String[nb];
            	   String[] prenoms = new String[nb];
            	   int[] braceletsID = new int[nb];
            	   JSONObject lastNames = (JSONObject)data.get("lastNames");
            	   JSONObject firstNames = (JSONObject)data.get("firstNames");
            	   JSONObject IDs = (JSONObject)data.get("IDs");
            	   for (int i =0; i<nb; i++) {
            		   String strI = Integer.toString(i);
            		   noms[i] = (String)lastNames.get(strI);
            		   prenoms[i] = (String)firstNames.get(strI);
            		   braceletsID[i] = (int)IDs.get(strI);
            	   }
            	   Activite act = new Activite(noms, prenoms, braceletsID, this);
            	   sessionID = act.getSession().getSessionID();
            	   writer.write(Integer.toString(sessionID));
            	   writer.flush();
               	   break;
               case "getStats":
            	   Main.getStats(sessionID);
               	   break;
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return jSONData;
   }
}