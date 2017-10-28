package adyd.web.org.tsr.impl;

import java.sql.Connection;

import org.apache.log4j.PropertyConfigurator;

import adyd.bbdd.CallProceduresADyD;
import adyd.bbdd.utils.Conector;
import adyd.web.org.tsr.beans.GetFileInfoResponse;

public class GetAllColeccionImpl implements GetAllColeccion {

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

}
