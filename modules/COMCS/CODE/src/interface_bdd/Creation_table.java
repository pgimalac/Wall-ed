package interface_bdd;
import interface_bdd.Connect_bdd;

public class Creation_table {

    public static void createTable(String name, String[] args) {
        String query = "CREATE TABLE " + name + "( ";
        int i;
        for (i = 0; i < args.length; i++) {
            query += args[i] + " "
                     + "VARCHAR(100)";
            if (i == 0) {
                query += " PRIMARY KEY";
            }
            if (i < args.length - 1) {
                query += ", ";
            }
        }
        query += " )";
        Connect_bdd.executeSQL(query);
    }
}
