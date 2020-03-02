package interface_externe;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Main_appli implements Runnable{

   private Socket connexion = null;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;
   private String[] listCommands = {"FULL", "DATE", "HOUR", "NONE"};
   private static int count = 0;
   private String name = "Client-";   
   
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

      for(int i =0; i < 10; i++){
         try {
            Thread.currentThread().sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         try {

            
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());
            
            String commande = getCommand();
            writer.write(commande);
            writer.flush();
            
            System.out.println("Commande " + commande + " envoy�e au serveur");
            
            String response = read();
            System.out.println("\t * " + name + " : R�ponse re�ue " + response);
            
         } catch (IOException e1) {
            e1.printStackTrace();
         }
         
         try {
            Thread.currentThread().sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      
      writer.write("CLOSE");
      writer.flush();
      writer.close();
   }
   
   public void initBDD() {
	   //todo
   }
   
   public void addPupil(String lastName, String firstName, String braceletID) {
	   //todo
   }
   
   public void getStats(int sessionID) {
	   //todo
   }
   
   private String getCommand(){
      Random rand = new Random();
      return listCommands[rand.nextInt(listCommands.length)];
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