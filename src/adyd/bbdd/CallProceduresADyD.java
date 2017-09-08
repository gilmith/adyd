package adyd.bbdd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import adyd.utils.Utils;

public class CallProceduresADyD {
	
	private Connection conn;
	private int idFile;
	private String mensaje;
	private final static Logger logger = Logger.getLogger(CallProceduresADyD.class);

	
	public CallProceduresADyD(Connection conn) {
		this.conn = conn;	
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CallProceduresADyD other = (CallProceduresADyD) obj;
		if (conn == null) {
			if (other.conn != null)
				return false;
		} else if (!conn.equals(other.conn))
			return false;
		if (idFile != other.idFile)
			return false;
		if (mensaje == null) {
			if (other.mensaje != null)
				return false;
		} else if (!mensaje.equals(other.mensaje))
			return false;
		return true;
	}
	
	public int getColeccion(String call, String modulo) {
		int salida = 0;
		try {
			logger.info("Ejecutando el getColeccion");
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, modulo);
			cstmt.execute();
			salida = cstmt.getInt(1);
			cstmt.close();
		} catch (SQLException e) {
			logger.error("Error de BBDD ", e);
		}
		return salida;
	}
	
	public int getIdFile() {
		return idFile;
	}

	public String getMensaje() {
		return mensaje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conn == null) ? 0 : conn.hashCode());
		result = prime * result + idFile;
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		return result;
	}

	public void insertFile(String call, String name, String ruta, long tamanho,
			String tsrid, String tsrid2, int coleccion) {
		CallableStatement cstmtFile;
		try {
			logger.info("ejecutando la inserccion de fichero");
			cstmtFile = conn.prepareCall(call);
			//call ADYD_PKG.INSERTFILE(?name1, ?path2 ,?size3, ?tsr14 ,?tsr25, ?mini6, ?coleccion7, ?id8, ?mensaje9)

			cstmtFile.setString(1, name);
			cstmtFile.setString(2, ruta);
			cstmtFile.setString(3, String.valueOf(tamanho));
			cstmtFile.setString(4, tsrid);
			if (tsrid2 ==  null ) {
				cstmtFile.setString(5, "NULL");
			} else {
				cstmtFile.setString(5, tsrid2);

			}
			cstmtFile.setString(6, Utils.getProperties().getProperty("Rutamini") + "\\" + name.replace(".pdf", ".png"));
			cstmtFile.setInt(7, coleccion);
			cstmtFile.registerOutParameter(8, Types.INTEGER);
			cstmtFile.registerOutParameter(9, Types.VARCHAR);
			cstmtFile.execute();
			idFile = cstmtFile.getInt(8);
			mensaje = cstmtFile.getString(9);
			cstmtFile.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error de BBDD ", e);
		}
		
	}

	public int insertModulo(String call, String modulo) {
		int salida = 0;
		try {
			logger.info("Ejecutando la inserccion de modulo");
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, modulo);
			cstmt.execute();
			salida = cstmt.getInt(1);
			cstmt.close();
		} catch (SQLException e) {
			logger.error("Error de BBDD ", e);
		}
		return salida;
	}
	
	public int getFile(String call, String name) {
		int salida = -1;
		try {
			logger.info("Ejecutando la busqueda de modulo");
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, name);
			cstmt.execute();
			salida = cstmt.getInt(1);
			cstmt.close();
		} catch (SQLException e) {
			logger.error("Error de BBDD ", e);
		}
		return salida;
	}

	public void setIdFile(int idFile) {
		this.idFile = idFile;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
