package Main;

import interface_server.*;

public class Activite {

    private String mode;
    private final Session session;
    public final AppClientProcessor clientApp;
    public final RobotClientProcessor clientRobot;

    public Activite(String[] noms, String[] prenoms, String[] braceletsID,
                    AppClientProcessor clientApp) throws InterruptedException {
        this.clientApp = clientApp;
        this.session =
            Initialisation.initialisation(noms, prenoms, braceletsID);
        System.out.println("[ACTIVITE] initialisation completed");
        this.clientRobot = Main.inform(this, this.session.getEleves());
        System.out.println("[ACTIVITE] informed Main, RobotClientProcessor ok");
        this.mode = null;
    }

    public Session getSession() { return this.session; }

    public String getMode() { return this.mode; }

    public void changeMode() {
        switch (mode) {
        case "FIN":
            // DIRE QUE C'EST FINI (renvoyer une erreur par ex, d'ailleurs il
            // doit y a voir une erreur quelquepart)
        case "RECHERCHE":
            this.mode = "VERIFICATION";
            this.changeMode();
            break;
        default:
            this.mode = "RECHERCHE";
            this.clientRobot.modeRecherche();
            break;
        }
    }

    public void start() {
        this.session.debutSession();
        this.mode = "INIT";
        this.changeMode();
    }

    public void stop() {
        this.session.finSession();
        this.mode = "FIN";
        this.clientRobot.closeConnexion();
        // --> terminer les connexions et le thread !!
    }
}
