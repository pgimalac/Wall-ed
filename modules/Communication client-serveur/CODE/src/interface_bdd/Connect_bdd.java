package interface_bdd;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.sql.Statement;
import java.sql.Connection;

public class Connect_bdd {
	
	private static String base = "pact";
	private static String user = "pact";
	private static String passwd = "pactpact";
	
	public void setDataBase(String base) {
		Connect_bdd.base = base;
	}
	
	public void setUser(String user, String passwd) {
		Connect_bdd.user = user;
		Connect_bdd.passwd = passwd;
	}
	
	public String getConnectionBase() {
		return Connect_bdd.base;
	}
	
	public String getConnectionUser() {
		return Connect_bdd.user;
	}
	
	public static ResultSetMetaData executeSQL(String commandeSQL) {
		
		ResultSetMetaData resultMeta = null;
		
		System.out.println(commandeSQL);
	
		try {
	      Class.forName("org.mariadb.jdbc.Driver");
	      
	      System.out.println("Driver OK");
	         
	      Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + Connect_bdd.base, Connect_bdd.user, Connect_bdd.passwd);
	      
	      System.out.println("Connexion ok");
	      
	      //Création d'un objet Statement
	      Statement state = conn.createStatement();
	      //L'objet ResultSet contient le résultat de la requête SQL
	      ResultSet result = state.executeQuery(commandeSQL);
	      //On récupère les MetaData
	      resultMeta = result.getMetaData();

	      result.close();
	      state.close();
	      
	      System.out.println("Commande exécutée");
	         
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		return resultMeta;
	}
	
	
	public static String lastExecuteSQL(String commandeSQL, String enregistrement) {
		
		String res = "";
		
		 System.out.println(commandeSQL);
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		      
		    System.out.println("Driver OK");
		         
		    Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + Connect_bdd.base, Connect_bdd.user, Connect_bdd.passwd);
		      
		    System.out.println("Connexion ok");
		      
		    //Création d'un objet Statement
		    Statement state = conn.createStatement();
		    //L'objet ResultSet contient le résultat de la requête SQL
		    ResultSet result = state.executeQuery(commandeSQL);
		    
		    result.last();
		    
		    res = result.getString(enregistrement);

		    result.close();
		    state.close();
		    
		    System.out.println("Commande exécutée");
		         
		} catch (SQLDataException e) {
			return "none";
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return res;
	}
}