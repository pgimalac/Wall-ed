package fr.telecom.pact32.wall_ed.model;

import java.io.Serializable;



public class Utilisateur implements Serializable {

    private String prenom;
    private String nom;
    private String classe;

    public Utilisateur(String prenom, String nom, String classe) {
        this.prenom = prenom;
        this.nom = nom;
        this.classe = classe;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + classe + ")";
    }
}