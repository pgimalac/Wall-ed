package Main;

import interface_server.*;

public class Activite {
	
	private String mode;
	private final Session session;
	private final AppClientProcessor clientApp;
	private final RobotClientProcessor clientRobot;
	
	
	public Activite(String[] noms, String[] prenoms, int[] braceletsID, AppClientProcessor clientApp) {
		this.session = Initialisation.initialisation(noms, prenoms, braceletsID);
		this.clientRobot = Main.inform(this.session, this.session.getEleves());
		this.mode = null;
		this.clientApp = clientApp;
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
			this.changeToVERIF();
			break;
		default:
			this.mode = "RECHERCHE";
			this.changeToRECHERCHE();
			break;
		}
			
	}
	
	private void changeToVERIF() {
		// send to the robot the change of state
		// send to the app that a trash has been found
		// --> envoyer au robot les infos du déchet
		// --> on récupère du robot les infos et on crée l'objet déchet :
		
		Dechet dechet = new Dechet(braceletID, type, typePropose, reponseEleve); //paramètres à remplir
		this.changeMode();
	}
	
	private void changeToRECHERCHE() {
		// send to the robot the change of state
		// on reçoit des photos du robot, que l'on passe dans l'IA, et que l'on retransmet au robot
		this.clientRobot.modeRecherche();
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
