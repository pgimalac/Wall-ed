package Main;

import interface_bdd.*;
import interface_externe.Main_appli;
import java.io.IOException;

public class test {

    private static String imageStoringPath = "/home/adrien/Images/pactImages/";

    public static void main(String[] args)
        throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        int numberOfImages = 1;
        String imagePath =
            imageStoringPath + Integer.toString(numberOfImages) + ".jpg";
        System.out.println("starting AI");
        String bal = Main.executePythonScriptForAI(imagePath);
        System.out.println(bal);

        /*
        String name = "GLOBALE";
        String[] argus = {"sessionID", "date", "heureDebut", "heureFin",
        "nbEleves"}; Creation_table.createTable(name, argus);


        String query0 = "INSERT INTO GLOBALE VALUES ('0', 'NULL', 'NULL',
        'NULL', '0')"; Connect_bdd.executeSQL(query0);
        */

        /*
        AjoutEleve.addEleve("bla", "alb");
        String query = "SELECT * FROM ELEVES";
        String resp = Connect_bdd.lastExecuteSQL(query, "nom");

        System.out.println(resp);
        */

        /*
        System.out.println("creating socket, connecting to server");
        Main_appli mn = new Main_appli("192.168.2.4", 22345);
        System.out.println("connected");
        Thread t = new Thread(new Runnable() {
                public void run() {
                        mn.run();
                }
        });
        t.start();
        String[] noms = {"AA", "BB"};
        String[] prenoms = {"aa", "bb"};
        Thread.sleep(2000);
        int[] braceletsID = {1,2};
        mn.initSession(noms, prenoms, braceletsID);
        */
        /*
        String query = "SELECT * FROM ELEVES where nom = 'ozeifj'";
        System.out.println(Connect_bdd.lastExecuteSQL(query, "eleveID"));
        */
    }
}
