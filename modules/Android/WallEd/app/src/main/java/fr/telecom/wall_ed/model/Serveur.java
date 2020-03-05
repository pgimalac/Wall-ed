package fr.telecom.wall_ed.model;

import java.util.ArrayList;
import fr.telecom.wall_ed.model.Utilisateur;

public class Serveur {

    public Serveur(){
        //TODO
    }

    /**
     * @return users saved from previous sessions
     */
    public ArrayList<Utilisateur> getUsers(){
        //TODO
        return new ArrayList<>();
    }

    /**
     * Asks the server to start a new session of the activity
     * @param users : users that will participate in the activity
     */
    public void startNewSession(ArrayList<Utilisateur> users){
        //TODO
    }

    /**
     * Asks the server to end the session
     */
    public void endSession(){
        //TODO
    }

}
