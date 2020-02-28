package Main;
import interface_bdd.*;

public class Initialisation {
	
	// récupération de la liste des enfants avec leurs ID de bracelets
	// --> on stocke ça dans les tableaux suivants
	
	private final String[] noms;
	private final String[] prenoms;
	private final int[] ids;
	
	public Initialisation(String[] noms, String[] prenoms, int[] ids) {
		this.noms = noms;
		this.prenoms = prenoms;
		this.ids = ids;
		
		int[] elevesID = AjoutEleve.addEleves(noms, prenoms);
		
		Eleve[] listeEleves = new Eleve[noms.length];
		
		for (int i = 0; i < noms.length; i++) {
			Eleve suivant = new Eleve(elevesID[i], prenoms[i], noms[i]);
			listeEleves[i] = suivant;
		}
		
		// on récupère les infos de la session (dates et heures) et des bracelets
		
		String date, heureDebut, heureFin;
		int[] bracelets;
		
		Main_bdd.initSessionID();
		int sessionID = Main_bdd.setSessionID();
		
		Session session = new Session(sessionID, listeEleves, bracelets, date, heureDebut, heureFin);
		
	}

}
