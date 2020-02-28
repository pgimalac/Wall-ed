package Main;

import interface_bdd.Edition_table;

public class Dechet {
	
	private final Session session;

	private int dechetID;
	private int sessionID;
	private int braceletID;
	private String type;
	private String typeEleve;
	private int heureRamassage;
	
	public Dechet(int dechetID, Session session, int braceletID, String type, String typeEleve, int heureRamassage) {
		this.dechetID = dechetID;
		this.sessionID = session.getSessionID();
		this.session = session;
		this.braceletID = braceletID;
		this.type = type;
		this.typeEleve = typeEleve;
		this.heureRamassage = heureRamassage;
		
		String[] values = {Integer.toString(this.dechetID), Integer.toString(this.sessionID), Integer.toString(this.braceletID), this.type, this.typeEleve, Integer.toString(this.heureRamassage)};
		
		Edition_table.addEnregistrement(session.getTable_ramassage(), values);
	}
	
	public int getDechetID() {
		return dechetID;
	}
	public void setDechetID(int dechetID) {
		this.dechetID = dechetID;
	}
	public int getSessionID() {
		return sessionID;
	}
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	public int getBraceletID() {
		return braceletID;
	}
	public void setBraceletID(int braceletID) {
		this.braceletID = braceletID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeEleve() {
		return typeEleve;
	}
	public void setTypeEleve(String typeEleve) {
		this.typeEleve = typeEleve;
	}
	public int getHeureRamassage() {
		return heureRamassage;
	}
	public void setHeureRamassage(int heureRamassage) {
		this.heureRamassage = heureRamassage;
	}

}
