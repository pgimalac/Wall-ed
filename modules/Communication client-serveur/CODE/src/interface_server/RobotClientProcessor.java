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
   private final Session session;
   
   public RobotClientProcessor(Socket pSock, Session session){
      sock = pSock;
      this.session = session;
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
            	   // encoder 
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
}