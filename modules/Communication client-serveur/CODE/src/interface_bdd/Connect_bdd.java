package interface_bdd;
import java.sql.DriverManager;
import java.sql.Connection;

public class Connect_bdd {
	
	public static void main(String[] args) {
	
		try {
	      Class.forName("org.mariadb.jdbc.Driver");
	      
	      System.out.println("Driver OK");
	         
	      String url = "jdbc:mariadb://localhost:3306/pact";
	      String user = "pact";
	      String passwd = "pactpact";
	         
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	      
	      System.out.println("connection ok");
	      
	      /*
	      //Création d'un objet Statement
	      Statement state = conn.createStatement();
	      //L'objet ResultSet contient le résultat de la requête SQL
	      ResultSet result = state.executeQuery("SELECT * FROM classe");
	      //On récupère les MetaData
	      ResultSetMetaData resultMeta = result.getMetaData();
	      

	      result.close();
	      state.close();
	      */
	         
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}