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
	
	private static String host = "192.168.1.15";
    private static int port = 2345;

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
		   
		   String pythonScriptPath = "/home/adrien/Documents/test.py"; // à définir
		   String[] cmd = new String[3];
		   cmd[0] = "python";
		   cmd[1] = pythonScriptPath;
		   cmd[2] = image;
		    
		   // create runtime to execute external command
		   Runtime rt = Runtime.getRuntime();
		   Process pr = rt.exec(cmd);
		    
		   // retrieve output from python script
		   BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		   String line = bfr.readLine();
		   bfr.close();
		   return line;
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
}