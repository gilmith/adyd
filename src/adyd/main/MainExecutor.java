package adyd.main;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import adyd.bbdd.CallProceduresADyD;
import adyd.bbdd.utils.Conector;
import adyd.file.analizer.FileAnalizer;
import adyd.file.analizer.FileDao;
import adyd.utils.Utils;

public class MainExecutor {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Error hay que pasar una ruta de la carpeta como argumento");
		} else { 
			FileAnalizer fileAnalizer = new FileAnalizer(args[0]);
			fileAnalizer.arrayFiles2txt(Utils.getProperties().getProperty("RutaFiles"));
			try {
				buclePrincipal(fileAnalizer.getArrayFiles());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	private static void buclePrincipal(File[] listaArchivos) throws SQLException {
		//me salto el primero que es el init
		long init = System.currentTimeMillis();
		int i = 0;
		Conector conn = new Conector(Utils.getProperties().getProperty("bbdd"));
		CallProceduresADyD callAdyD = new CallProceduresADyD(conn.getConection());
		int coleccion = 0;
		for(File file : listaArchivos) {
			
			if(i == 0) {
				System.out.println("skip");
				i++;
			} else {
				FileDao fileDao;
				try {
					fileDao = new FileDao(file);
					int coleccion1 = 0;
					if(fileDao.isDirectory()) {
						coleccion1 = callAdyD.getColeccion(Utils.getProperties().getProperty("getColeccion"),file.getName());
						if(coleccion1 == 0)  {
							callAdyD.insertModulo(Utils.getProperties().getProperty("insertModulo"),file.getName());
							System.out.println("modulo insertado");
						} else {
							coleccion = coleccion1;
						}
					} else {
//						System.out.println("saca la miniatura si es pdf o lo mete en extra tambien tiene que examinar el nombre");
						fileDao.setMini();
						callAdyD.insertFile(Utils.getProperties().getProperty("insertFile"), 
								fileDao.getName(), fileDao.getPath(), fileDao.getSize(), 
								fileDao.getTsrId(), fileDao.getTsrId2(), coleccion);
						//call ADYD_PKG.INSERTFILE(?name, ?path ,?size, ?tsr1 ,?tsr2, ?mini, ?coleccion, ?id, ?mensaje)
						System.out.println("CODIGO = " + callAdyD.getIdFile() + " MENSAJE " + callAdyD.getMensaje());
						
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
