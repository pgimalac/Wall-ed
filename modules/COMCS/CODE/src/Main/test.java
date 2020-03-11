package Main;

import java.io.IOException;

import interface_bdd.*;
import interface_externe.Main_appli;

public class test {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println(Main.executePythonScriptForAI("/home/adrien/Images/test.jpg"));
		
		/*
		String name = "GLOBALE";
		String[] argus = {"sessionID", "date", "heureDebut", "heureFin", "nbEleves"};
		Creation_table.createTable(name, argus);
		
		
		String query0 = "INSERT INTO GLOBALE VALUES ('0', 'NULL', 'NULL', 'NULL', '0')";
		Connect_bdd.executeSQL(query0);
		*/
		
		/*
		AjoutEleve.addEleve("bla", "alb");
		String query = "SELECT * FROM ELEVES";
		String resp = Connect_bdd.lastExecuteSQL(query, "nom");
		
		System.out.println(resp);
		*/
		
		/*
		System.out.println("creating socket, connecting to server");
		Main_appli mn = new Main_appli("192.168.2.6", 2345);
		System.out.println("connected");
		Thread t = new Thread(new Runnable() {
			public void run() {
				mn.run();
			}
		});
		t.start();
		String[] noms = {"AA", "BB"};
		String[] prenoms = {"aa", "bb"};
		Thread.sleep(2000);
		int[] braceletsID = {1,2};
		mn.initSession(noms, prenoms, braceletsID);
		*/
		/*
		String query = "SELECT * FROM ELEVES where nom = 'ozeifj'";
		System.out.println(Connect_bdd.lastExecuteSQL(query, "eleveID"));
		*/
	}

}
