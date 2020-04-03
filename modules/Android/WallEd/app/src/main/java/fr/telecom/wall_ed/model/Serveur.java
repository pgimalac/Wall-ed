package fr.telecom.wall_ed.model;

import java.util.ArrayList;

public class Serveur {

    private Main_appli mn;

    public Serveur(){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                mn = new Main_appli("127.0.0.1", 22345);
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
        mn.stop();
    }

}
