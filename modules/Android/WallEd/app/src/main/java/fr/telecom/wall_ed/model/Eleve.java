package fr.telecom.wall_ed.model;

/**
 * Classe utilisateur (aspect serveur)
 */

public class Eleve {
    private int eleveID;
    private String prenom;
    private String nom;
    private final String[] possible =
        new String[] {"V", "R", "C", "J", "M", "B"};
    private String braceletID = "ab";

    public Eleve(int eleveID, String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
        this.eleveID = eleveID;
        this.braceletID = possible[eleveID] + possible[eleveID + 1];
    }

    public String getPrenom() { return this.prenom; }

    public String getNom() { return this.nom; }

    public int getEleveID() { return this.eleveID; }

    public String getBraceletID() { return braceletID; }
}
