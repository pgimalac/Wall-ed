package fr.telecom.wall_ed.model;

import java.io.Serializable;

/**
 * Classe utilisateur (aspect Android)
 */

public class Utilisateur implements Serializable {

    private String prenom;
    private String nom;
    private String classe;
    private String id;
    private boolean selected;
    private String braceletID = "ab";
    private final String[] possible =
        new String[] {"V", "R", "C", "J", "M", "B"};

    public Utilisateur(String prenom, String nom, String classe, String id) {
        this.prenom = prenom;
        this.nom = nom;
        this.classe = classe;
        this.id = id;
        this.selected = false;
        this.braceletID =
            possible[Integer.parseInt(id)] + possible[Integer.parseInt(id)];
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + classe + ")";
    }

    public String getPrenom() { return prenom; }

    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getClasse() { return classe; }

    public void setClasse(String classe) { this.classe = classe; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public boolean isSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }

    public String getBraceletID() { return braceletID; }
}