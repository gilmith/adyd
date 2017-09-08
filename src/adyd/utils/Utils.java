package adyd.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import adyd.file.analizer.Miniatura;

public class Utils {
	
	private static Properties properties;
	private final static Logger logger = Logger.getLogger(Utils.class);

	private Utils() {
		properties = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("properties\\adyd.properties");
			properties.load(new InputStreamReader(fis));
			fis.close();
		} catch (FileNotFoundException e) {
			logger.error("Fichero de properties no encontrado", e);
			} catch (IOException e) {
			logger.error("Error al leer el fichero de properties", e);
		}

	}
	
	public static Properties getProperties() {
		if(properties == null) { 
			Utils util = new Utils();
		} 
		return properties;
	}

}
