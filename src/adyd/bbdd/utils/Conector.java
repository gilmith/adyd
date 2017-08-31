package adyd.bbdd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conector {
	
	public Connection conection;
	public String user, password;
	
	public Conector(String user, String password) throws SQLException {
		this.user = user;
		this.password = password;
		conection = DriverManager.getConnection("jdbc:oracle:thin:" + user + "/" + password + "@localhost:1521:xe");
	}

	public Connection getConection() {
		return conection;
	}

	public void setConection(Connection conection) {
		this.conection = conection;
	}
	

}
