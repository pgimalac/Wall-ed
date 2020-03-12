package Main;

/*
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import interface_bdd.*;
*/
import interface_server.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;

import interface_bdd.AjoutEleve;
import interface_bdd.Connect_bdd;

public class Main {
	
	private static String host = "192.168.2.4";
    private static int port = 22345;

	   public static void main(String[] args) {
		   
		   // init de deux Main_server (un robot et un appli)
	      
	      Main_server ts = new Main_server(host, port, "app");
	      ts.open();
	      
	      System.out.println("[MAIN] Serveur initialisé.");
	      
	      
	      // TODO
	      
	      
	   }
	   
	   public static void getStats(int sessionID) {
		   //todo (renvoie //todo)
	   }
	   
	   public static String executePythonScriptForAI(String image) throws IOException {
		   // image : nom de l'image
		   
		   String pythonScriptPath = "/home/adrien/Documents/pact/pact32/modules/COMCS/AI.py"; // à définir
		   String[] cmd = new String[3];
		   cmd[0] = "python";
		   cmd[1] = pythonScriptPath;
		   cmd[2] = image;
		    
		   // create runtime to execute external command
		   Runtime rt = Runtime.getRuntime();
		   Process pr = rt.exec(cmd);
		    
		   // retrieve output from python script
		   BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		   String res = "";
		   String temp;
		   while ((temp = bfr.readLine()) != null) {
			   System.out.println(temp);
			   res = temp;
		   }
		   bfr.close();
		   return res;
	   }
	   
	   public static RobotClientProcessor inform(Activite act, Eleve[] eleves) throws InterruptedException {
		   Main_server rb = new Main_server(host, port+1, "robot");
		   rb.setActivite(act);
		   rb.open();
		   System.out.println("[MAIN] Waiting for a robot connexion");
		   while (!rb.robotConnected) {
			   Thread.sleep(5000);
			   System.out.println("[MAIN] Robot still not connected");
		   }
		   System.out.println("[MAIN] robot connected, finishing activity creation");
		   return rb.getRobotApp();
	   }
	   
	   public static Eleve[] jsonToEleve(JSONObject json) {
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
	   
	   public static Eleve[] getElevesInBDD() {
		   int lastID = AjoutEleve.getLastEleveID();
		   Eleve[] liste = new Eleve[lastID];
		   for (int i=1; i < lastID+1;i++) {
			   String query = "SELEC * FROM ELEVES WHERE eleveID = " + Integer.toString(i);
			   Eleve temp = new Eleve(i, Connect_bdd.lastExecuteSQL(query, "prenom"),Connect_bdd.lastExecuteSQL(query, "nom"));
			   liste[i] = temp;
		   }
		   return liste;
	   }
}