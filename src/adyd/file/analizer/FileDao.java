package adyd.file.analizer;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import adyd.bbdd.CallProceduresADyD;
import adyd.utils.Utils;

public class FileDao {
	
	private File file;
	private boolean isDirectory, isExtra, isPdf;
	private Miniatura mini;
	private String tsrId, tsrId2, name;
	private long size;
	private final static Logger logger = Logger.getLogger(FileDao.class);
	
	
	public long getSize() {
		return size;
	}



	public FileDao(File file) throws InvalidPasswordException, IOException {
		this.file = file;
		this.size = file.length();
		setTipo();
		mini = null;
		
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileDao other = (FileDao) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (isDirectory != other.isDirectory)
			return false;
		if (isExtra != other.isExtra)
			return false;
		if (isPdf != other.isPdf)
			return false;
		return true;
	}
	
	public File getFile() {
		return file;
	}


	public Miniatura getMini() {
		return mini;
	}


	public String getName() {
		return name;
	}


	public String getPath() {
		return file.getAbsolutePath();
	}


	public String getTsrId() {
		return tsrId;
	}
	
	public String getTsrId2() {
		return tsrId2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + (isDirectory ? 1231 : 1237);
		result = prime * result + (isExtra ? 1231 : 1237);
		result = prime * result + (isPdf ? 1231 : 1237);
		return result;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public boolean isExtra() {
		return isExtra;
	}

	public boolean isPdf() {
		return isPdf;
	}

	private void parseName(String name) {
		StringTokenizer st = new StringTokenizer(name, " ");
		StringBuilder sb = new StringBuilder();
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			if(token.startsWith("TSR")) {
				logger.info("skip TSR");
			} else if (token.matches("[0-9]{4}")) {
				this.tsrId = token;
			} else if(token.matches("[0-9,A-Z]{5}")) {
				this.tsrId = token;
			}else if(token.matches("[0-9,A-Z,-]{6}")) {
				this.tsrId2 = token;
			} else if(token.matches("[A-Z,0-9]{2}")) {
				this.tsrId2 = token;
			} else if(token.matches("[A-Z,0-9]{3}")) {
					this.tsrId2 = token;
			}else if(token.matches("[A-Z,0-9,-]{4}")) {
				this.tsrId2 = token;
			} else if(token.matches("[A-Z,0-9]{7}")) {
				this.tsrId2 = token;
			} else {
				sb.append(token + " "); 
			}
		}
		this.name = sb.toString();
		logger.info("TSRID = " + tsrId);
		logger.info("TSRID2 = " + tsrId2);
		logger.info("NAME = " + this.name);
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public void setExtra(boolean isExtra) {
		this.isExtra = isExtra;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setMini() throws InvalidPasswordException, IOException {
		mini = new Miniatura(this.file);
		mini.saveMiniatura(Utils.getProperties().getProperty("Rutamini"));
	}
	
	public void setName(String name) {
		this.name = name;
	}



	public void setPdf(boolean isPdf) {
		this.isPdf = isPdf;
	}



	private void setTipo() {
		if(file.getName().endsWith("pdf") && (file.isFile()) ){
			isPdf = true;
			parseName(file.getName());
		} else if (file.getName().endsWith("mp3") && (file.isFile())) {
			isExtra = true;
		} else if (file.isDirectory()){
			isDirectory = true;
		}
		
	}



	public void setTsrId(String tsrId) {
		this.tsrId = tsrId;
	}



	public void setTsrId2(String tsrId2) {
		this.tsrId2 = tsrId2;
	}



	@Override
	public String toString() {
		return "FileDao [file=" + file + ", isDirectory=" + isDirectory + ", isExtra=" + isExtra + ", isPdf=" + isPdf
				+ "]";
	}

}
