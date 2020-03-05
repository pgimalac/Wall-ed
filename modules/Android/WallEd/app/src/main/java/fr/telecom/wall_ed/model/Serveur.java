package fr.telecom.wall_ed.model;

import java.util.ArrayList;
import fr.telecom.wall_ed.model.Utilisateur;

public class Serveur {

    public Serveur(){

    }

    /**
     * @return users saved from previous sessions
     */
    public ArrayList<Utilisateur> getUsers(){
        return new ArrayList<>();
    }

}
