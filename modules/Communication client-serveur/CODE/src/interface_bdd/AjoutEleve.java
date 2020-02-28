package interface_bdd;

public class AjoutEleve {
	
	private static int lastID;
	
	public static int[] addEleves(String[] noms, String[] prenoms) {
		int[] elevesID = new int[noms.length];
		lastID = getLastEleveID();
		for (int i = 0; i < noms.length; i++) {
			int id = lastID + 1 + i;
			String[] champs = {Integer.toString(id), noms[i], prenoms[i]};
			Edition_table.addEnregistrement("ELEVES", champs);
			elevesID[i] = id;
		}
		
		return elevesID;
	}
	
	public static void addEleve(String nom, String prenom) {
		lastID = getLastEleveID();
		String[] champs = {Integer.toString(lastID + 1), nom, prenom};
		Edition_table.addEnregistrement("ELEVES", champs);
	}
	
	public static int getLastEleveID() {
		String query = "SELECT * FROM ELEVES";
		return Integer.parseInt(Connect_bdd.lastExecuteSQL(query, "eleveID"));
	}

}
