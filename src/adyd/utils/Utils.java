package adyd.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Utils {
	
	private static Properties properties;
	
	private Utils() {
		properties = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("properties\\adyd.properties");
			properties.load(new InputStreamReader(fis));
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Properties getProperties() {
		if(properties == null) { 
			Utils util = new Utils();
		} 
		return properties;
	}

}
