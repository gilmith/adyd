package adyd.main;

import java.io.File;
import java.io.IOException;

import adyd.file.analizer.FileAnalizer;
import adyd.file.analizer.FileDao;

public class MainExecutor {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Error hay que pasar una ruta de la carpeta como argumento");
		} else { 
			FileAnalizer fileAnalizer = new FileAnalizer(args[0]);
			fileAnalizer.arrayFiles2txt("C:\\Users\\Jacobo\\dungeons.txt");
			buclePrincipal(fileAnalizer.getArrayFiles());
		}

	}
	
	
	
	private static void buclePrincipal(File[] listaArchivos) {
		//me salto el primero que es el init
		long init = System.currentTimeMillis();
		int i = 0;
		for(File file : listaArchivos) {
			if(i == 0) {
				System.out.println("skip");
				i++;
			} else {
				FileDao fileDao;
				try {
					fileDao = new FileDao(file);
					if(fileDao.isDirectory()) {
//						System.out.println("lo mete en modulos o se lo salta y guarda el valor del id");
					} else {
//						System.out.println("saca la miniatura si es pdf o lo mete en extra tambien tiene que examinar el nombre");
						fileDao.setMini();
//						System.out.println("terminada la miniatura");
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		System.out.println("ha tardado " + (System.currentTimeMillis() - init) );
	}

}
