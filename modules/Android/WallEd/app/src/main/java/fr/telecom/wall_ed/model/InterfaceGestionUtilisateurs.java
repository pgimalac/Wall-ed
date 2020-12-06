package fr.telecom.wall_ed.model;
import fr.telecom.wall_ed.controller.UtilisateurAdapter;
import fr.telecom.wall_ed.model.Utilisateur;
import java.util.ArrayList;

public interface InterfaceGestionUtilisateurs {

    public ArrayList<Utilisateur> getUser();
    public void addUser(Utilisateur u);
    public ArrayList<Utilisateur> getSelectedUser();
    public UtilisateurAdapter getUserAdaptateur();
}
