package Main;

import java.io.IOException;

import interface_bdd.*;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println(Main.executePythonScriptForAI("blabla"));
		System.out.println("ozeiforfij");
		
		String name = "ELEVES";
		String[] argus = {"eleveID", "nom", "prenom"};
		Creation_table.createTable(name, argus);
		
		
		AjoutEleve.addEleve("bla", "alb");
		String query = "SELECT * FROM ELEVES";
		String resp = Connect_bdd.lastExecuteSQL(query, "nom");
		
		System.out.println(resp);
	}

}
