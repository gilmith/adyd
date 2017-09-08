package adyd.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import adyd.bbdd.CallProceduresADyD;
import adyd.bbdd.utils.Conector;
import adyd.file.analizer.FileAnalizer;
import adyd.file.analizer.FileDao;
import adyd.utils.Utils;

public class MainExecutor {
	
	private final static Logger logger = Logger.getLogger(MainExecutor.class);
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("properties\\log4j.properties");
		logger.setLevel(Level.DEBUG);
		if(args.length == 0) {
			logger.error("Error hay que pasar una ruta de la carpeta como argumento");
			System.exit(-1);
		} else { 
			FileAnalizer fileAnalizer = new FileAnalizer(args[0]);
			fileAnalizer.arrayFiles2txt(Utils.getProperties().getProperty("RutaFiles"));
			logger.info("Cargado el listado de archivos");
			try {
				buclePrincipal(fileAnalizer.getArrayFiles());
			} catch (SQLException e) {
				logger.error("excepcion general de BBDD, comprobar conexion", e);
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
				logger.info("skip el directorio raiz");
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
							logger.info("Modulo " + file.getName() + " insertado");
						} else {
							coleccion = coleccion1;
						}
					} else {
						int codigoAventura = callAdyD.getFile(Utils.getProperties().getProperty("getFile"), fileDao.getName()) ;
						if(codigoAventura != -1) {
							fileDao.setMini();
							callAdyD.insertFile(Utils.getProperties().getProperty("insertFile"), 
									fileDao.getName(), fileDao.getPath(), fileDao.getSize(), 
									fileDao.getTsrId(), fileDao.getTsrId2(), coleccion);
							logger.info("CODIGO = " + callAdyD.getIdFile() + " MENSAJE " + callAdyD.getMensaje());		
						} else {
							logger.info("La aventura " + fileDao.getName() + " ya ha sido insertada");
						}
					}
				} catch (IOException e) {
					logger.error("Error de entrada salida comprobar disco duro", e);
				}
				
			}
			
		}
		logger.info("tiempo de ejecuccion = " +  (System.currentTimeMillis() - init) );
	}

}
