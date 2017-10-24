package adyd.web.org.tsr.beans;

public class GetFileInfoResponse {
	
	
	private FileInfo fileInfo;
	private Resultado resultado;
	
	public GetFileInfoResponse() {
		resultado  = new Resultado();
		resultado.setCodigo(0);
		resultado.setMensaje("OK");
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
		if (fileInfo == null) {
			if (other.fileInfo != null)
				return false;
		} else if (!fileInfo.equals(other.fileInfo))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		return true;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public Resultado getResultado() {
		return resultado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileInfo == null) ? 0 : fileInfo.hashCode());
		result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
		return result;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

}
