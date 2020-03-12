package interface_bdd;

public class AjoutEleve {
	
	private static int lastID;
	
	public static int[] addEleves(String[] noms, String[] prenoms) {
		int[] elevesID = new int[noms.length];
		lastID = getLastEleveID();
		String nom = "";
		String prenom = "";
		String decidingID;
		for (int i = 0; i < noms.length; i++) {
			nom = noms[i];
			prenom = prenoms[i];
			String query = "SELECT * FROM ELEVES WHERE nom = '" + nom + "' AND prenom = '" + prenom + "'";
			decidingID = Connect_bdd.lastExecuteSQL(query, "eleveID");
			if (decidingID == "none") {
				lastID++;
				String[] champs = {Integer.toString(lastID), noms[i], prenoms[i]};
				Edition_table.addEnregistrement("ELEVES", champs);
				elevesID[i] = lastID;
			}
			else {
				elevesID[i] = Integer.parseInt(decidingID);
			}
		}
		
		return elevesID;
	}
	
	public static int addEleve(String nom, String prenom) {
		String[] noms = {nom};
		String[] prenoms = {prenom};
		int[] res = addEleves(noms, prenoms);
		return res[0];
	}
	
	public static int getLastEleveID() {
		String query = "SELECT * FROM ELEVES";
		return Integer.parseInt(Connect_bdd.lastExecuteSQL(query, "eleveID"));
	}

}
