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

public class Main {

	   public static void main(String[] args) {
		   
		   // init de deux Main_server (un robot et un appli)
	    
	      String host = "127.0.0.1";
	      int port = 2345;
	      
	      Main_server ts = new Main_server(host, port);
	      ts.open();
	      
	      System.out.println("Serveur initialisé.");
	      
	      
	      // TODO
	      
	      
	   }
	   
	   public static int initSession(String[] noms, String[] prenoms, int[] braceletsID) {
		   Session sessionEnCours = Initialisation.initialisation(noms, prenoms, braceletsID);
		   return sessionEnCours.getSessionID();
	   }
	   
	   public static void getStats(int sessionID) {
		   //todo (renvoie //todo)
	   }
	   
	   public static String executePythonScriptForAI(String image) throws IOException {
		   // arguments : nom de la fonction puis paramètres (comme en C)
		   
		   String pythonScriptPath = "/home/adrien/Documents/test.py"; // à définir
		   String[] cmd = new String[3];
		   cmd[0] = "python"; // check version of installed python: python -V
		   cmd[1] = pythonScriptPath;
		   cmd[2] = image;
		    
		   // create runtime to execute external command
		   Runtime rt = Runtime.getRuntime();
		   Process pr = rt.exec(cmd);
		    
		   // retrieve output from python script
		   BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		   String line = bfr.readLine();
		   return line;
	   }
}