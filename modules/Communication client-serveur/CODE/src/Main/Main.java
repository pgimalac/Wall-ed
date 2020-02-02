package Main;

import interface_appli.Main_appli;
import interface_bdd.*;

public class Main {

	   public static void main(String[] args) {
	    
	      String host = "127.0.0.1";
	      int port = 2345;
	      
	      Main_bdd ts = new Main_bdd(host, port);
	      ts.open();
	      
	      System.out.println("Serveur initialisé.");
	      
	      for(int i = 0; i < 5; i++){
	         Thread t = new Thread(new Main_appli(host, port));
	         t.start();
	      }
	   }
	}