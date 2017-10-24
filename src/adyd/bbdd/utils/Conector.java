package adyd.bbdd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import adyd.bbdd.CallProceduresADyD;
import adyd.web.org.tsr.beans.Resultado;

public class Conector {
	
	public Connection conection;
	public DataSource ds;
	private final static Logger logger = Logger.getLogger(Conector.class);

	public Conector() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/xe");			
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public Conector(String url) throws SQLException {
		conection = DriverManager.getConnection(url);
	}

	public Connection getConection() {
		return conection;
	}
	
	public Connection getContextConection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error("Error al obtener la conexion del tomcat", e);
		}
		return null;
	}

	public void setConection(Connection conection) {
		this.conection = conection;
	}
	


}
