package modelo;

import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reportes {

	
	private JasperDesign disenio;
	private JasperPrint impreso;
	private JasperReport reporte;
	Conexion con;
	
	public Reportes(){
		disenio = new JasperDesign();
		impreso = new JasperPrint();
		con = Conexion.getInstancia();
	}

	public void cargarReporte(String ruta){
		try {
			con.conectar();
			File f = new File(ruta);
			disenio = JRXmlLoader.load(f.getAbsolutePath());
			reporte = JasperCompileManager.compileReport(disenio);
			impreso = JasperFillManager.fillReport(reporte, new HashMap(), con.getConexion());
			con.desconectar();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarReporte(){
		try{
			/*Esta parte del codigo sirve para poder visualizar el reporte
			* El false es para que solo cierre el reporte y no la aplicación
			*/
			JasperViewer.viewReport(impreso, false);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
