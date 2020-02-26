package Main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import interface_appli.Main_appli;
import interface_bdd.*;

public class Main {

	   public static void main(String[] args) {
	    
	      String host = "127.0.0.1";
	      int port = 2345;
	      
	      Main_bdd ts = new Main_bdd(host, port);
	      ts.open();
	      
	      System.out.println("Serveur initialis�.");
	      
	      for(int i = 0; i < 5; i++){
	         Thread t = new Thread(new Main_appli(host, port));
	         t.start();
	      }
	   }
	   
	   // CE QUE TU AVAIS MIS SUR MAIN_BDD
	   
	   private int port = 2345;
	   private String host = "127.0.0.1";
	   private ServerSocket server = null;
	   private boolean isRunning = true;
	   
	   public Main_bdd(){
	      try {
	         server = new ServerSocket(port, 10, InetAddress.getByName(host));
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   public Main_bdd(String pHost, int pPort){
	      host = pHost;
	      port = pPort;
	      try {
	         server = new ServerSocket(port, 100, InetAddress.getByName(host));
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   public void open(){
	      
	      Thread t = new Thread(new Runnable(){
	         public void run(){
	            while(isRunning == true){
	               
	               try {
	                  Socket client = server.accept();
	                  System.out.println("Connexion cliente re�ue.");                  
	                  Thread t = new Thread(new ClientProcessor(client));
	                  t.start();
	                  
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
	            }
	            
	            try {
	               server.close();
	            } catch (IOException e) {
	               e.printStackTrace();
	               server = null;
	            }
	         }
	      });
	      
	      t.start();
	   }
	   
	   public void close(){
	      isRunning = false;
	   }
	}