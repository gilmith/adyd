package adyd.web.org.tsr.beans;

public class FileInfo {
	
	private String id, tsr_id, tsr_id2, nombre, modulo, coleccion, ruta, tamanho;

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (coleccion == null) {
			if (other.coleccion != null)
				return false;
		} else if (!coleccion.equals(other.coleccion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		if (tamanho == null) {
			if (other.tamanho != null)
				return false;
		} else if (!tamanho.equals(other.tamanho))
			return false;
		if (tsr_id == null) {
			if (other.tsr_id != null)
				return false;
		} else if (!tsr_id.equals(other.tsr_id))
			return false;
		if (tsr_id2 == null) {
			if (other.tsr_id2 != null)
				return false;
		} else if (!tsr_id2.equals(other.tsr_id2))
			return false;
		return true;
	}

	public String getColeccion() {
		return coleccion;
	}

	public String getId() {
		return id;
	}

	public String getModulo() {
		return modulo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTsr_id() {
		return tsr_id;
	}

	public String getTsr_id2() {
		return tsr_id2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coleccion == null) ? 0 : coleccion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
		result = prime * result + ((tsr_id == null) ? 0 : tsr_id.hashCode());
		result = prime * result + ((tsr_id2 == null) ? 0 : tsr_id2.hashCode());
		return result;
	}

	public void setColeccion(String coleccion) {
		this.coleccion = coleccion;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTsr_id(String tsr_id) {
		this.tsr_id = tsr_id;
	}

	public void setTsr_id2(String tsr_id2) {
		this.tsr_id2 = tsr_id2;
	}

	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", tsr_id=" + tsr_id + ", tsr_id2=" + tsr_id2 + ", nombre=" + nombre + ", modulo="
				+ modulo + ", coleccion=" + coleccion + "]";
	}

}
