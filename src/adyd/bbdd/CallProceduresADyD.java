package adyd.bbdd;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import adyd.utils.Utils;
import adyd.web.org.tsr.beans.FileInfo;
import adyd.web.org.tsr.beans.GetFileInfoResponse;
import adyd.web.org.tsr.beans.Resultado;

public class CallProceduresADyD {
	
	private final static Logger logger = Logger.getLogger(CallProceduresADyD.class);
	private Connection conn;
	private int idFile;
	private String mensaje;

	
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
	
	public GetFileInfoResponse getAllColecciones(String nombre) {
		logger.info("Ejecutando la busqueda del webservice por nombre de colecciones " + nombre);
		String call = "{ call ADYD_PKG.GET_ALL_COLECCION(?, ?, ?, ? ,?, ? ,?, ?, ?, ?, ?) }";
		GetFileInfoResponse gfir = new GetFileInfoResponse();
		try {
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.setString(1, nombre);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(5, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(6, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(7, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(8, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(9, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(10, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(11, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.execute();
			Resultado res = new Resultado();
			res.setCodigo(cstmt.getInt(2));
			res.setMensaje(cstmt.getString(3));
			gfir = new GetFileInfoResponse();
			gfir.setResultado(res);
			gfir.setListaResultados(setFileInfo(cstmt.getArray(4), 
					cstmt.getArray(5), cstmt.getArray(6),
					cstmt.getArray(7), cstmt.getArray(8), cstmt.getArray(9),
					cstmt.getArray(10), cstmt.getArray(11)));
		} catch (SQLException e) {
			logger.error("Error en el SQL", e);
		}
		
		return gfir;
	}
	
	public GetFileInfoResponse getAllModulos(String nombre) {
		logger.info("Ejecutando la busqueda del webservice por nombre de modulo " + nombre);
		String call = "{ call ADYD_PKG.GET_ALL_MODULOS(?, ?, ?, ? ,?, ? ,?, ?, ?, ?, ?) }";
		GetFileInfoResponse gfir = new GetFileInfoResponse();
		try {
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.setString(1, nombre);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(5, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(6, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(7, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(8, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(9, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(10, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(11, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.execute();
			Resultado res = new Resultado();
			res.setCodigo(cstmt.getInt(2));
			res.setMensaje(cstmt.getString(3));
			gfir = new GetFileInfoResponse();
			gfir.setResultado(res);
			gfir.setListaResultados(setFileInfo(cstmt.getArray(4), 
					cstmt.getArray(5), cstmt.getArray(6),
					cstmt.getArray(7), cstmt.getArray(8), cstmt.getArray(9),
					cstmt.getArray(10), cstmt.getArray(11)));
		} catch (SQLException e) {
			logger.error("Error en el SQL", e);
		}
		
		return gfir;
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
	
	public int getFile(String call, String name) {
		int salida = -1;
		try {
			logger.info("Ejecutando la busqueda de aventura ");
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
	
	//	   PROCEDURE GET_FILES_INFO (V_NOMBRE    IN     ADYD_FILES.NOMBRE%TYPE,
//               CODIGO         OUT NUMBER, 
//               RESULTADO      OUT VARCHAR2,
//               ID             OUT TBL_OUTPUT,
//               TSR_ID         OUT TBL_OUTPUT,
//               TSR_ID2        OUT TBL_OUTPUT,
//               NOMBRE         OUT TBL_OUTPUT,
//               COLECCION      OUT TBL_OUTPUT,
//               MODULO         OUT TBL_OUTPUT,
//               RUTA           OUT TBL_OUTPUT,
//               TAMANHO        OUT TBL_OUTPUT)
	public GetFileInfoResponse getFileInfo(String nombre){
		logger.info("Ejecutando la busqueda del webservice por nombre del archivo " + nombre);
		String call = "{ call ADYD_PKG.GET_FILES_INFO(?, ?, ?, ? ,?, ? ,?, ?, ?, ?, ?) }";
		GetFileInfoResponse gfir = new GetFileInfoResponse();
		try {
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.setString(1, nombre);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(5, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(6, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(7, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(8, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(9, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(10, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.registerOutParameter(11, Types.ARRAY, "MASTER.TBL_OUTPUT");
			cstmt.execute();
			Resultado res = new Resultado();
			res.setCodigo(cstmt.getInt(2));
			res.setMensaje(cstmt.getString(3));
			gfir = new GetFileInfoResponse();
			gfir.setResultado(res);
			gfir.setListaResultados(setFileInfo(cstmt.getArray(4), 
					cstmt.getArray(5), cstmt.getArray(6),
					cstmt.getArray(7), cstmt.getArray(8), cstmt.getArray(9),
					cstmt.getArray(10), cstmt.getArray(11)));
		} catch (SQLException e) {
			logger.error("Error en el SQL", e);
		}
		
		return gfir;
		
	}
	
	

	public GetFileInfoResponse getFileInfoObj(String nombre) {
		logger.info("Ejecutando la busqueda del webservice por nombre del archivo " + nombre);
		String call = "{ call ADYD_PKG.GET_FILE_INFO_OBJ(?, ?, ?, ?) }";
		GetFileInfoResponse gfir = new GetFileInfoResponse();
		try {
			CallableStatement cstmt = conn.prepareCall(call);
			cstmt.setString(1, nombre);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.ARRAY, "MASTER.ARRAY_RESPUESTA");
			cstmt.execute();
			Resultado res = new Resultado();
			res.setCodigo(cstmt.getInt(2));
			res.setMensaje(cstmt.getString(3));
			gfir = new GetFileInfoResponse();
			gfir.setResultado(res);
			gfir.setListaResultados(setFileInfo(cstmt.getArray(4)));
		} catch (SQLException e) {
			logger.error("Error en el SQL", e);
		}
		return gfir;
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

	private List<FileInfo> setFileInfo(Array id, Array tsr_id, Array tsr_id2,
			Array nombre, Array coleccion, Array modulo,
			Array ruta, Array tamanho){
		logger.info("Construye la respuseta");
		List<FileInfo> lista = new ArrayList<FileInfo>();
		try {
			Object[] valuesId = (Object[])id.getArray();
			Object[] valuesTsr_id = (Object[])tsr_id.getArray();
			Object[] valuesTsr_id2 = (Object[])tsr_id2.getArray();
			Object[] valuesNombre = (Object[])nombre.getArray();
			Object[] valuesColeccion = (Object[])coleccion.getArray();
			Object[] valuesModulo = (Object[])modulo.getArray();
			Object[] valuesRuta = (Object[]) ruta.getArray();
			Object[] valuesTamanho = (Object[]) tamanho.getArray();
			for(int i = 0; i < valuesId.length; i++) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setId((String) valuesId[i]);
				fileInfo.setTsr_id((String) valuesTsr_id[i]);
				fileInfo.setTsr_id2((String)valuesTsr_id2[i]);
				fileInfo.setNombre((String)valuesNombre[i]);
				fileInfo.setColeccion((String)valuesColeccion[i]);
				fileInfo.setModulo((String) valuesModulo[i]);
				fileInfo.setRuta((String) valuesRuta[i]);
				fileInfo.setTamanho((String)valuesTamanho[i]);
				lista.add(fileInfo);
			}
			
		} catch (SQLException e) {
			logger.error("Error de SQL", e);
		}
		
		return lista;		
		  
	}

	private List<FileInfo> setFileInfo(Array array){
		logger.info("Construye la respuseta");
		List<FileInfo> lista = new ArrayList<FileInfo>();
		try {
			Object[] estructuras = (Object[])array.getArray();
			for (int i = 0; i < estructuras.length; i++) {
				Struct estruct = (Struct) estructuras[i];
				Object[] atributos = estruct.getAttributes();
				FileInfo fileInfo = new FileInfo();
				for(int j = 0; j<= atributos.length; j++) {
					fileInfo.setId((String) atributos[j++]);
					fileInfo.setTsr_id((String) atributos[j++]);
					fileInfo.setTsr_id2((String)atributos[j++]);
					fileInfo.setNombre((String)atributos[j++]);
					fileInfo.setColeccion((String)atributos[j++]);
					fileInfo.setModulo((String) atributos[j++]);
					fileInfo.setRuta((String) atributos[j++]);
					fileInfo.setTamanho((String)atributos[j++]);
					lista.add(fileInfo);
				}
			}
		} catch (SQLException e) {
			logger.error("Error en el array" , e);
		}
		return lista;
	}
	
	public void setIdFile(int idFile) {
		this.idFile = idFile;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
