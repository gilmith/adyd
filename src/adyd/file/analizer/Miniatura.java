package adyd.file.analizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Miniatura {
	
	private BufferedImage bim;
	private String nombre;

	public Miniatura(File fichero) throws InvalidPasswordException, IOException {
		nombre = fichero.getName();
		System.out.println("extrayendo el fichero " + fichero.getAbsolutePath());
        PDDocument document = PDDocument.load(fichero);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        bim = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
        document.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Miniatura other = (Miniatura) obj;
		if (bim == null) {
			if (other.bim != null)
				return false;
		} else if (!bim.equals(other.bim))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public BufferedImage getBim() {
		return bim;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bim == null) ? 0 : bim.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	public void saveMiniatura(String path) {
		nombre = path + nombre.replaceFirst(".pdf", ".png");
		try {
			ImageIOUtil.writeImage(bim, nombre, 100);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setBim(BufferedImage bim) {
		this.bim = bim;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Miniatura [bim=" + bim + ", nombre=" + nombre + "]";
	}
	
}
