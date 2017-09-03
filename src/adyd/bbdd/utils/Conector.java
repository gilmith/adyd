package adyd.bbdd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conector {
	
	public Connection conection;
	
	public Conector(String url) throws SQLException {
		conection = DriverManager.getConnection(url);
	}

	public Connection getConection() {
		return conection;
	}

	public void setConection(Connection conection) {
		this.conection = conection;
	}
	

}
