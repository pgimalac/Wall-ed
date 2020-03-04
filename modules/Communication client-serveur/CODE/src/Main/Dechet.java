package Main;

import interface_bdd.Connect_bdd;
import interface_bdd.Edition_table;

public class Dechet {
	
	private final Session session;

	private final int dechetID;
	private final int braceletID;
	private final String type;
	private final String typePropose;
	private final boolean reponseEleve;
	private final String heureRamassage;
	
	public Dechet(int braceletID, String type, String typePropose, boolean reponseEleve) {
		this.session = Initialisation.getSessionEnCours();
		
		String query = "SELECT * FROM " + this.session.getTable_ramassage();
		this.dechetID = Integer.parseInt(Connect_bdd.lastExecuteSQL(query, "dechetID")) + 1;
		
		this.braceletID = braceletID;
		this.type = type;
		this.typePropose = typePropose;
		this.heureRamassage = java.time.LocalTime.now().toString();
		this.reponseEleve = reponseEleve;
		
		String[] values = {Integer.toString(this.dechetID), Integer.toString(this.session.getSessionID()), Integer.toString(this.braceletID), this.type, this.typePropose, String.valueOf(this.reponseEleve), this.heureRamassage};
		
		Edition_table.addEnregistrement(session.getTable_ramassage(), values);
	}
	
	public int getDechetID() {
		return this.dechetID;
	}

	public Session getSession() {
		return this.session;
	}

	public int getBraceletID() {
		return this.braceletID;
	}

	public String getType() {
		return this.type;
	}

	public String getTypeEleve() {
		return this.typePropose;
	}

	public String getHeureRamassage() {
		return this.heureRamassage;
	}

}
