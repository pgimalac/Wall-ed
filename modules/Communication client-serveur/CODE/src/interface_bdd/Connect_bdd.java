package interface_bdd;
import org.mariadb.jdbc.*;

public class Connect_bdd {
	
	public int connexion() {
	
		try {
	      Class.forName("Driver");
	         
	      String url = "jdbc:postgresql://localhost:5432/Ecole";
	      String user = "postgres";
	      String passwd = "postgres";
	         
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	         
	      //Création d'un objet Statement
	      Statement state = conn.createStatement();
	      //L'objet ResultSet contient le résultat de la requête SQL
	      ResultSet result = state.executeQuery("SELECT * FROM classe");
	      //On récupère les MetaData
	      ResultSetMetaData resultMeta = result.getMetaData();
	      

	      result.close();
	      state.close();
	         
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}