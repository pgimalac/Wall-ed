package fr.telecom.wall_ed.model;


public class Eleve {
    private final int eleveID;
    private final String prenom;
    private final String nom;

    public Eleve(int eleveID, String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
        this.eleveID = eleveID;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public int getEleveID() {
        return this.eleveID;
    }
}
