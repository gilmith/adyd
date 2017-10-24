package adyd.web.org.tsr.impl;

import java.sql.Connection;

import javax.jws.WebService;

import adyd.bbdd.CallProceduresADyD;
import adyd.bbdd.utils.Conector;
import adyd.web.org.tsr.beans.GetFileInfoResponse;

@WebService(endpointInterface = "adyd.web.org.tsr.impl.GetFileInfo")
public class GetFileInfoImpl implements GetFileInfo {

	public GetFileInfoResponse getFileInfo(String nombre) {
		GetFileInfoResponse getFileInfoResponse = new GetFileInfoResponse();
		Conector conector = new Conector();
		Connection conn =  conector.getContextConection();
		CallProceduresADyD procedure = new CallProceduresADyD(conn);
		procedure.getFileInfo(nombre);
		
		return getFileInfoResponse;
	}

}
