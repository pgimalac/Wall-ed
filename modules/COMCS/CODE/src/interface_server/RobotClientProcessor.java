package interface_server;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

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
   private boolean closeConnexion = false;
   private boolean changeMode = false;
   private boolean initDone = false;
   private String action = "none";
   private String command;
   private String imageStoringPath = "/home/adrien/Images/pactImages/";
   private int numberOfImages = 0;
   
   public RobotClientProcessor(Socket pSock, Activite act){
      sock = pSock;
      this.act = act;
   }
   
   @Override
   public void run(){
      System.out.println("[RobotCP] Lancement du traitement de la connexion robot");

      while(!sock.isClosed()){
         
         try {
            
            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedInputStream(sock.getInputStream());
            
            if (!this.act.clientApp.getInitSate()) {System.err.println("[RobotCP] Waiting app initialisation to be finished");}
            while (!this.act.clientApp.getInitSate()) {
            	Thread.sleep(3000);
            	System.out.println("[RobotCP] Still not finished");
            }
            
            if (this.initDone) {
            	
            	System.err.println("[RobotCP] init done, waiting to see if change mode needed");
            	Thread.sleep(3000);
            	if (this.changeMode) {
            		System.out.println("[RobotCP] NOT reading socket because of specific action");
            		action = command;
            	}
            	else {
                	System.err.println("[RobotCP] reading socket");
                	Thread.sleep(3000);
                	action = read();
                }
            }
            else {
            	System.err.println("[RobotCP] reading socket");
            	Thread.sleep(3000);
            	action = read();
            }
            
            System.out.println("[RobotCP] action required with robot : " + action);
            
            switch(action){
               case "init":
            	   System.out.print("[RobotCP] sending students to robot : ");
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
            	   System.out.println("done");
            	   this.initDone = true;
               	   break;
               case "newImage":
            	   byte[] b= new byte[20000];
            	   writer.write("sendImage");
            	   writer.flush();
            	   this.numberOfImages++;
            	   System.out.println("receiving image");
            	   String imagePath = this.imageStoringPath + Integer.toString(this.numberOfImages) + ".jpg";
            	   
            	   FileOutputStream image = new FileOutputStream(imagePath);
            	   int n;
            	   while((n=reader.read(b,0,b.length))>=20000){
   	            		image.write(b,0,n);                                        
            	   }
            	   image.write(b,0,n);
            	   System.out.println("image stored, launching IA");
            	   String AIresult = Main.executePythonScriptForAI(imagePath);
            	   System.out.println("ai done");
            	   // then send results to the robot
            	   writer.write(AIresult);
            	   writer.flush();
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
            		   Dechet dechet = new Dechet(this.act.getSession(), braceletID, type, typePropose, reponseEleve);
            		   this.act.changeMode();
            		   // send this dechet info to the app ?
            	   }
            	   break;
               case "close":
            	   this.closeConnexion = true;
            	   break;
               case "RECHERCHE":
            	   writer.write(action);
            	   writer.flush();
            	   this.changeMode = false;
            	   break;
               default : 
            	   System.out.println("[RobotCP] Commande inconnue de la part du robot");
            	   //writer.write("Commande inconnue !");
            	   //writer.flush();
            	   break;
            }

            if(closeConnexion){
               System.err.println("[RobotCP] COMMANDE CLOSE DETECTEE ! ");
               writer = null;
               reader = null;
               sock.close();
               break;
            }
         }catch(SocketException e){
            System.err.println("[RobotCP] LA CONNEXION A ETE INTERROMPUE ! ");
            break;
         } catch (IOException e) {
            e.printStackTrace();
         } catch (InterruptedException e) {
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
	   this.changeMode = true;
	   this.command = "RECHERCHE";
   }
   
   public void closeConnexion() {
	   this.changeMode = true;
	   this.command = "close";
   }
}