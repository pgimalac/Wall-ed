package Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import interface_bdd.*;
import interface_server.*;

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
	   
	   public static int initSession() {
		   //todo (renvoie l'identifiant de la session créée)
	   }
	   
	   public static void addPupil(String lastName, String firstName, String braceletID) {
		   //todo
	   }
	   
	   public static void getStats(int sessionID) {
		   //todo (renvoie //todo)
	   }
}