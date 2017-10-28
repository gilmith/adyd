package adyd.web.org.tsr.impl;

import java.sql.Connection;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import adyd.bbdd.CallProceduresADyD;
import adyd.bbdd.utils.Conector;
import adyd.web.org.tsr.beans.GetFileInfoResponse;

@WebService(endpointInterface = "adyd.web.org.tsr.impl.GetFileInfo")

public class GetFileInfoImpl implements GetFileInfo {
	
	
	private final static Logger logger = Logger.getLogger(GetFileInfoImpl.class);

	public GetFileInfoResponse getFileInfo(String nombre) {
		PropertyConfigurator.configure("C:\\Users\\Jacobo\\apache-tomcat-8.5.23\\conf\\log4j.properties");		
		
		GetFileInfoResponse getFileInfoResponse = new GetFileInfoResponse();
		Conector conector = new Conector();
		Connection conn =  conector.getContextConection();
		CallProceduresADyD procedure = new CallProceduresADyD(conn);
		getFileInfoResponse = procedure.getFileInfo(nombre);
		
		return getFileInfoResponse;
	}

	@Override
	public GetFileInfoResponse getAllColeccion(String nombre) {
		PropertyConfigurator.configure("C:\\Users\\Jacobo\\apache-tomcat-8.5.23\\conf\\log4j.properties");		
		
		GetFileInfoResponse getFileInfoResponse = new GetFileInfoResponse();
		Conector conector = new Conector();
		Connection conn =  conector.getContextConection();
		CallProceduresADyD procedure = new CallProceduresADyD(conn);
		getFileInfoResponse = procedure.getAllColecciones(nombre);
		
		return getFileInfoResponse;
	}

	@Override
	public GetFileInfoResponse getAllModulos(String nombre) {
		PropertyConfigurator.configure("C:\\Users\\Jacobo\\apache-tomcat-8.5.23\\conf\\log4j.properties");		
		
		GetFileInfoResponse getFileInfoResponse = new GetFileInfoResponse();
		Conector conector = new Conector();
		Connection conn =  conector.getContextConection();
		CallProceduresADyD procedure = new CallProceduresADyD(conn);
		getFileInfoResponse = procedure.getAllModulos(nombre);
		
		return getFileInfoResponse;
	}


}
