package adyd.file.analizer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileAnalizer {
	
	private File[] arrayFiles;

	private File file;
	private String path;
	
	/**
	 * Constructor con la ruta a analizar 
	 * @param path
	 */
	
	public FileAnalizer(String path) {
		this.path = path;
		file = new File(path);
		arrayFiles = FileUtils.convertFileCollectionToFileArray(
				FileUtils.listFilesAndDirs(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE));
	}
	
	public String arrayFiles2String() {
		StringBuilder sb = new StringBuilder();
		for(File file : arrayFiles) {
			sb.append(file.getAbsolutePath() + "\n");
		}
		return sb.toString();
	}
	
	@SuppressWarnings("deprecation")
	public void arrayFiles2txt(String path) { 
		StringBuilder sb = new StringBuilder();
		for(File file : arrayFiles) {
			sb.append(file.getName() + "\n");
		}
		try {
			FileUtils.write(new File(path), sb.toString(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileAnalizer other = (FileAnalizer) obj;
		if (!Arrays.equals(arrayFiles, other.arrayFiles))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	public File[] getArrayFiles() {
		return arrayFiles;
	}

	public File getFile() {
		return file;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(arrayFiles);
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	public void setArrayFiles(File[] arrayFiles) {
		this.arrayFiles = arrayFiles;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FileAnalizer [path=" + path + ", file=" + file + ", arrayFiles=" + Arrays.toString(arrayFiles) + "]";
	}
	
	

}
