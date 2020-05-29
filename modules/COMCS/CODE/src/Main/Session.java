package Main;
import interface_bdd.*;
import java.time.*;

public class Session {
	
	private int sessionID;
	private Eleve[] eleves;
	private String date;
	private String heureDebut;
	private String heureFin;
	private int nbEleves;
	
	private String table_ramassage;
	private String table_eleves;
	
	public Session(int id, Eleve[] eleves, String[] bracelets) {
		this.sessionID = id;
		this.eleves = eleves;
		this.nbEleves = this.eleves.length;
		
		table_ramassage = "RAMA_" + Integer.toString(this.sessionID);
		table_eleves = "ELEV_" + Integer.toString(this.sessionID);
		
		String[] args = {"dechetID", "sessionID", "braceletID", "type", "typePropose", "reponseEleve", "heureRamassage"};
		String[] args2 = {"braceletID", "eleveID"};
		
		Creation_table.createTable(table_ramassage, args);
		Creation_table.createTable(table_eleves, args2);
		
		String[] blank_dechet = {"0", "0", "0", "none", "none", "none", "none"};
		Edition_table.addEnregistrement(table_ramassage, blank_dechet);
		
		for (int i = 0; i < this.nbEleves; i++) {
			String[] braceletEleve = {bracelets[i], Integer.toString(eleves[i].getEleveID())};
			Edition_table.addEnregistrement(table_eleves, braceletEleve);
		}
		System.out.println("[Session] tables in database created");
		
	}
	
	public void debutSession() {
		LocalDate now = java.time.LocalDate.now();
		this.date = now.toString();
		LocalTime now2 = java.time.LocalTime.now();
		this.heureDebut = now2.toString();
	}
	
	public void finSession() {
		LocalTime now3 = java.time.LocalTime.now();
		this.heureFin = now3.toString();
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
				break;
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

	public Eleve[] getEleves() {
		return this.eleves;
	}

}
