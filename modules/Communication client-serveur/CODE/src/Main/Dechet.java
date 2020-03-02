package Main;

import interface_bdd.Edition_table;

public class Dechet {
	
	private final Session session;

	private final int dechetID;
	private final int sessionID;
	private final int braceletID;
	private final String type;
	private final String typePropose;
	private final boolean reponseEleve;
	private final int heureRamassage;
	
	public Dechet(int dechetID, Session session, int braceletID, String type, String typePropose, boolean reponseEleve, int heureRamassage) {
		this.dechetID = dechetID;
		this.sessionID = session.getSessionID();
		this.session = session;
		this.braceletID = braceletID;
		this.type = type;
		this.typePropose = typePropose;
		this.heureRamassage = heureRamassage;
		this.reponseEleve = reponseEleve;
		
		String[] values = {Integer.toString(this.dechetID), Integer.toString(this.sessionID), Integer.toString(this.braceletID), this.type, this.typePropose, String.valueOf(this.reponseEleve), Integer.toString(this.heureRamassage)};
		
		Edition_table.addEnregistrement(session.getTable_ramassage(), values);
	}
	
	public int getDechetID() {
		return dechetID;
	}

	public int getSessionID() {
		return sessionID;
	}

	public int getBraceletID() {
		return braceletID;
	}

	public String getType() {
		return type;
	}

	public String getTypeEleve() {
		return typePropose;
	}

	public int getHeureRamassage() {
		return heureRamassage;
	}

}
