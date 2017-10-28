package adyd.web.org.tsr.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import adyd.web.org.tsr.beans.GetFileInfoResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface GetAllColeccion {
	
	@WebMethod
	public GetFileInfoResponse getAllColeccion(String nombre);

}
