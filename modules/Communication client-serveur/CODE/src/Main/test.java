package Main;

import java.io.IOException;

import interface_bdd.*;
import interface_externe.Main_appli;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println(Main.executePythonScriptForAI("blabla"));
		
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
		
		Main_appli mn = new Main_appli("127.0.0.1", 2345);
		mn.run();
		String[] noms = {"AA", "BB"};
		String[] prenoms = {"aa", "bb"};
		int[] braceletsID = {1,2};
		mn.initSession(noms, prenoms, braceletsID);
	}

}
