package fr.telecom.wall_ed.model;

import java.util.ArrayList;

public interface InterfaceServeur {

    public void startNewSession(ArrayList<Utilisateur> users);
    public void endSession();
}
