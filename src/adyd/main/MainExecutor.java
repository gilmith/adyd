package adyd.main;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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
		CallableStatement cstmtInsertModulo = conn.getConection().prepareCall("{ ? = call ADYD_PKG.INSERTMODULO(?) }");
		CallableStatement cstmtGetColeccion = conn.getConection().prepareCall("{ ? = call ADYD_PKG.GETCOLECCION(?) }");
		int coleccion = 0;
		for(File file : listaArchivos) {
			
			if(i == 0) {
				System.out.println("skip");
				i++;
			} else {
				FileDao fileDao;
				try {
					fileDao = new FileDao(file);
					if(fileDao.isDirectory()) {
						cstmtGetColeccion.registerOutParameter(1, Types.INTEGER);
						cstmtGetColeccion.setString(2, fileDao.getName());
						cstmtGetColeccion.execute();
						if(cstmtGetColeccion.getInt(1) == 0) {
							cstmtInsertModulo.registerOutParameter(1, Types.INTEGER);
							cstmtInsertModulo.setString(2, fileDao.getName());
							cstmtInsertModulo.execute();
							System.out.println(cstmtInsertModulo.getInt(1));
						} else {
							System.out.println("es una coleccion");
							coleccion = cstmtGetColeccion.getInt(1);
						}
					} else {
//						System.out.println("saca la miniatura si es pdf o lo mete en extra tambien tiene que examinar el nombre");
						fileDao.setMini();
						CallableStatement cstmtFile = conn.getConection().prepareCall("{ call ADYD_PKG.INSERTFILE(?, ? ,?, ? ,?, ?, ?, ?, ?) }");
						cstmtFile.setString(1, fileDao.getName());
						cstmtFile.setString(2, fileDao.getPath());
						cstmtFile.setLong(3, fileDao.getSize());
						cstmtFile.setString(4, fileDao.getTsrId());
						if (fileDao.getTsrId2() ==  null ) {
							cstmtFile.setString(5, "NULL");
						} else {
							cstmtFile.setString(5, fileDao.getTsrId2());

						}
						cstmtFile.setString(6, Utils.getProperties().getProperty("Rutamini"+ "\\" + fileDao.getName().replace(".pdf", ".png")));
						cstmtFile.setInt(7, coleccion);
						cstmtFile.registerOutParameter(8, Types.INTEGER);
						cstmtFile.registerOutParameter(9, Types.VARCHAR);
						cstmtFile.execute();
						System.out.println("CODIGO = " + cstmtFile.getInt(8) + " MENSAJE " + cstmtFile.getString(9));
						
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
