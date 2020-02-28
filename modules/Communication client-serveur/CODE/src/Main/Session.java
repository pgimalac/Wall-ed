package Main;
import interface_bdd.*;

public class Session {
	
	private int sessionID;
	private Eleve[] eleves;
	private String date;
	private String heureDebut;
	private String heureFin;
	private int nbEleves;
	
	private String table_ramassage;
	private String table_eleves;
	
	public Session(int id, Eleve[] eleves, int[] bracelets, String date, String heureDebut, String heureFin) {
		this.sessionID = id;
		this.eleves = eleves;
		this.nbEleves = this.eleves.length;
		this.date = date;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		
		int lastID = AjoutEleve.getLastEleveID();
		int tempID;
		table_ramassage = "RAMA_" + Integer.toString(this.sessionID);
		table_eleves = "ELEV_" + Integer.toString(this.sessionID);
		
		String[] args = {"dechetID", "sessionID", "braceletID", "typeDechet", "reponseEleve"};
		String[] args2 = {"braceletID", "eleveID"};
		
		Creation_table.createTable(table_ramassage, args);
		Creation_table.createTable(table_eleves, args2);
		
		for (int i = 0; i < this.nbEleves; i++) {
			tempID = this.eleves[i].getEleveID();
			if (tempID > lastID) {
				AjoutEleve.addEleve(eleves[i].getNom(), eleves[i].getPrenom());
				String[] braceletEleve = {Integer.toString(bracelets[i]), Integer.toString(eleves[i].getEleveID())};
				Edition_table.addEnregistrement(table_eleves, braceletEleve);
			}
		}
		
		String[] values = {Integer.toString(this.sessionID), this.date, this.heureDebut, this.heureFin, Integer.toString(this.nbEleves)};
		Edition_table.addEnregistrement("GLOBALE", values);
		
	}
	
	public int getSessionID() {
		return this.sessionID;
	}
	
	public Eleve getEleveInSessionByID(int id) {
		if (this.isEleveInSession(id)) {
			return Main_bdd.getEleveByID(id);
		}
		else return null;
	}
	
	public String getSessionDate() {
		return this.date;
	}
	
	public String getSessionHeureDebut() {
		return this.heureDebut;
	}
	
	public String getSessionHeureFin() {
		return this.heureFin;
	}
	
	public boolean isEleveInSession(int id) {
		boolean result = false;
		for (int i = 0; i < this.eleves.length; i++) {
			if (this.eleves[i].getEleveID() == id) {
				result = true;
			}
		}
		return result;
	}

	public String getTable_ramassage() {
		return table_ramassage;
	}

	public String getTable_eleves() {
		return table_eleves;
	}


}
