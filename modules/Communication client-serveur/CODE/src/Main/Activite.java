package Main;

public class Activite {
	
	private String mode;
	private final Session session;
	
	// ajouter un attribut privé final thread ??
	
	public Activite(String[] noms, String[] prenoms, int[] braceletsID) {
		this.session = Initialisation.initialisation(noms, prenoms, braceletsID);
		this.mode = null;
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public String getMode() {
		return this.mode;
	}
	
	public void changeMode() {
		switch (mode) {
		case "FIN":
			// DIRE QUE C'EST FINI (renvoyer une erreur par ex, d'ailleurs il doit y a voir une erreur quelquepart)
		case "RECHERCHE":
			this.mode = "VERIFICATION";
			// --> envoyer au robot les infos du déchet
			break;
		default:
			this.mode = "RECHERCHE";
			break;
		}
		
		// send to robot the change of state !!!! And the information !!
			
	}
	
	public void start() {
		this.session.debutSession();
		this.changeMode();
	}
	
	public void stop() {
		this.session.finSession();
		this.mode = "FIN";
		// --> terminer les connexions et le thread !!
	}

}
