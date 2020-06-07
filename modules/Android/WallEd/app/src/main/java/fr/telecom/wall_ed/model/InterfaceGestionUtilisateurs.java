package fr.telecom.wall_ed.model;
import java.util.ArrayList;

import fr.telecom.wall_ed.controller.UtilisateurAdapter;
import fr.telecom.wall_ed.model.Utilisateur;

public interface InterfaceGestionUtilisateurs {

    public ArrayList<Utilisateur> getUser ();
    public void addUser (Utilisateur u);
    public ArrayList<Utilisateur> getSelectedUser ();
    public UtilisateurAdapter getUserAdaptateur();
    


}
