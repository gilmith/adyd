package adyd.main;

import adyd.file.analizer.FileAnalizer;

public class MainExecutor {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Error hay que pasar una ruta de la carpeta como argumento");
		} else { 
			FileAnalizer fileAnalizer = new FileAnalizer(args[0]);
		}

	}

}
