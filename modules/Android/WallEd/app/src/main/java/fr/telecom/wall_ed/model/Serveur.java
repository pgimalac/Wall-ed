package fr.telecom.wall_ed.model;

import java.util.ArrayList;
import fr.telecom.wall_ed.model.Utilisateur;

public class Serveur {

    private Main_appli mn;

    public Serveur(){
        mn = new Main_appli("192.168.2.4", 2345);
        Thread t = new Thread(new Runnable() {
            public void run() {
                mn.run();
            }
        });
        t.start();
    }

    /**
     * @return users saved from previous sessions
     */
    public ArrayList<Utilisateur> getUsers(){
        //TODO (on attend le code d'Adrien)
        return new ArrayList<>();
    }

    /**
     * Asks the server to start a new session of the activity
     * @param users : users that will participate in the activity
     */
    public void startNewSession(ArrayList<Utilisateur> users){
        String[] noms = new String[users.size()];
        String[] prenoms = new String[users.size()];
        int[] braceletsID = new int[users.size()];
        for (int i=0 ; i<users.size() ; i++){
            noms[i] = users.get(i).getNom();
            prenoms[i] = users.get(i).getPrenom();
            braceletsID[i] = Integer.parseInt(users.get(i).getId());
        }
        mn.initSession(noms, prenoms, braceletsID);
    }

    /**
     * Asks the server to end the session
     */
    public void endSession(){
        //TODO
    }

}
