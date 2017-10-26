package adyd.web.org.tsr.beans;

import java.util.ArrayList;
import java.util.List;

public class GetFileInfoResponse {
	
	
	private List<FileInfo> listaResultados;



	private Resultado resultado;

	public GetFileInfoResponse() {
		resultado  = new Resultado();
		listaResultados = new ArrayList<FileInfo>();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetFileInfoResponse other = (GetFileInfoResponse) obj;
		if (listaResultados == null) {
			if (other.listaResultados != null)
				return false;
		} else if (!listaResultados.equals(other.listaResultados))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		return true;
	}
	
	public List<FileInfo> getListaResultados() {
		return listaResultados;
	}



	public Resultado getResultado() {
		return resultado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaResultados == null) ? 0 : listaResultados.hashCode());
		result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
		return result;
	}

	public void setListaResultados(List<FileInfo> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

}
