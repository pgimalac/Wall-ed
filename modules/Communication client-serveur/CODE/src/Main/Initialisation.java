package Main;
import interface_bdd.*;

public class Initialisation {
	
	// récupération de la liste des enfants avec leurs ID de bracelets
	// --> on stocke ça dans les tableaux suivants
	
	
	/* SI ON UTILISE UN CONSTRUCTEUR AU LIEU D'UNE METHODE STATIQUE
	 * 
	private static final String[] noms;
	private static final String[] prenoms;
	private static final int[] ids;
	private static final String date, heureDebut;
	private static final int[] bracelets;
	*/
	
	public static Session initialisation(String[] noms, String[] prenoms, int[] bracelets) {
		/* IDEM
		
		this.noms = noms;
		this.prenoms = prenoms;
		this.ids = ids;
		this.bracelets = bracelets;
		this.date = date;
		this.heureDebut = heureDebut;
		*/
		
		int[] elevesID = AjoutEleve.addEleves(noms, prenoms);
		
		Eleve[] listeEleves = new Eleve[noms.length];
		
		for (int i = 0; i < noms.length; i++) {
			Eleve suivant = new Eleve(elevesID[i], prenoms[i], noms[i]);
			listeEleves[i] = suivant;
		}
		
		Main_bdd.initSessionID();
		int sessionID = Main_bdd.setSessionID();
		
		Session session = new Session(sessionID, listeEleves, bracelets);
		
		return session;
		
	}

}
