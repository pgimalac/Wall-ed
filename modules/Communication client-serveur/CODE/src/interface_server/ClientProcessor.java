package interface_server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;
import Main.*;

public class ClientProcessor implements Runnable{

   private Socket sock;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;
   
   public ClientProcessor(Socket pSock){
      sock = pSock;
   }
   
   @Override
   public void run(){
      System.err.println("Lancement du traitement de la connexion cliente");

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
            
            String toSend = "";
            
            switch(response){
               case "initSession":
            	   Main.initSession();
            	   toSend = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM).format(new Date());
            	   break;
               case "getStats":
            	   Main.getStats(sessionID);
            	   toSend = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date());
            	   break;
               case "close":
            	   toSend = "Communication terminée"; 
            	   closeConnexion = true;
            	   break;
               default : 
            	   toSend = "Commande inconnue !";                     
            	   break;
            }
            
            writer.write(toSend);
            writer.flush();
            
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
   
}