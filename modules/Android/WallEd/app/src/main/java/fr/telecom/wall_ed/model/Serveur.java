package fr.telecom.wall_ed.model;

import android.util.Log;

import java.util.ArrayList;

public class Serveur {

    private Main_appli mn;

    public Serveur(){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                mn = new Main_appli("pact32.ml", 22345);
                mn.run();
            }
        });
        t1.start();
    }

    /**
     * @return users saved from previous sessions
     */
    public ArrayList<Utilisateur> getUsers(){
        ArrayList<Utilisateur> users = new ArrayList<>();
        Eleve[] eleves = mn.getEleves();
        for (int i=0 ; i<eleves.length ; i++){
            Utilisateur user = new Utilisateur(eleves[i].getPrenom(), eleves[i].getNom(), "PlaceHolder", ""+eleves[i].getEleveID());
            users.add(user);
        }
        return users;
    }

    public ArrayList<Dechet> getDechets() {
        Log.i("PACT32_DEBUG", "CheckPoint (Server) : MAJ des stats");
        return mn.getDechets();
    }

    /**
     * Asks the server to start a new session of the activity
     * @param users : users that will participate in the activity
     */
    public void startNewSession(ArrayList<Utilisateur> users){
        String[] noms = new String[users.size()];
        String[] prenoms = new String[users.size()];
        String[] braceletsID = new String[users.size()];
        for (int i=0 ; i<users.size() ; i++){
            noms[i] = users.get(i).getNom();
            prenoms[i] = users.get(i).getPrenom();
            braceletsID[i] = users.get(i).getBraceletID();
        }
        mn.initSession(noms, prenoms, braceletsID);
    }

    /**
     * Asks the server to end the session
     */
    public void endSession(){
        mn.stop();
    }

}
