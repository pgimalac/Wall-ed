package interface_bdd;
import Main.*;
import interface_bdd.Connect_bdd;

public class Main_bdd {

    private static int sessionID;

    public static int setSessionID() {
        sessionID = sessionID + 1;
        return sessionID;
    }

    public static void initSessionID() {
        String query = "SELECT * FROM GLOBALE";
        int id =
            Integer.parseInt(Connect_bdd.lastExecuteSQL(query, "sessionID"));
        sessionID = id;
    }

    public static int getSessionID() { return sessionID; }

    public static Eleve getEleveByID(int id) {
        String query =
            "SELECT * FROM ELEVES WHERE eleveID = " + Integer.toString(id);
        String prenom = Connect_bdd.lastExecuteSQL(query, "prenom");
        String nom = Connect_bdd.lastExecuteSQL(query, "nom");
        return new Eleve(id, prenom, nom);
    }
}
