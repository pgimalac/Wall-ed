package fr.telecom.pact32.wall_ed.model;

import java.io.Serializable;



public class Utilisateur implements Serializable {

    private String nom;
    private String sexe;

    public Utilisateur(String nom, String sexe) {
        this.nom = nom;
        this.sexe = sexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return nom + " (" + sexe + ")";
    }

}