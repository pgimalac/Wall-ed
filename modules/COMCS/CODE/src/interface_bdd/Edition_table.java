package interface_bdd;
import interface_bdd.Connect_bdd;

public class Edition_table {
	
	public static void addEnregistrement(String table, String[] values) {
		String valeurs = "(";
		for (int i = 0; i < values.length ; i++) {
			valeurs += "'" + values[i] + "'";
			if (i < values.length - 1) {
				valeurs += ", ";
			}
		}
		valeurs += ")";
		String query = "INSERT INTO " + table + " VALUES " + valeurs;
		Connect_bdd.executeSQL(query);
	}
	
	public static void deleteEnregistrement(String table, String idPrimaryKey, String primaryKey) {
		String query = "DELETE FROM " + table + " WHERE " + idPrimaryKey + " = " + primaryKey;
		Connect_bdd.executeSQL(query);
	}
	
	public static void modifyEnregistrement(String table, String[] args, String[] values) {
		String valeurs = "";
		for (int i = 1; i < values.length ; i++) {
			valeurs += args[i] + " = '" + values[i] + "'";
			if (i < values.length - 1) {
				valeurs += ", ";
			}
		}
		String query = "UPDATE " + table + "SET " + valeurs + " WHERE " + args[0] + " = " + values[0];
		Connect_bdd.executeSQL(query);
	}
	
}
