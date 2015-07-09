package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class Principal implements Initializable {

	private ControladordeVentanas ventanas;
	@FXML Button btnProveedor, btnAlmacen, btnRegistro, btnReparacion;
	
	@FXML Label lblMensaje;
	
	@FXML MenuItem miConfiguracion, miRegistrarEmpleado, miRespaldo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ventanas = ControladordeVentanas.getInstancia(); 
	}
	
	public void proveedor(ActionEvent event){
		ventanas.asignarIzquierda("../vista/fxml/BotonesdeProveedor.fxml");
	}
	public void almacen(ActionEvent event){
		//mainRoca2.showBotonesdeAlmacen();
		ventanas.asignarIzquierda("../vista/fxml/BotonesdeAlmacen.fxml");
	}
	public void registro(ActionEvent event){
		ventanas.asignarIzquierda("../vista/fxml/BotonesdeRegistro.fxml");
	}
	
	public void reparacion(ActionEvent event){
		ventanas.asignarIzquierda("../vista/fxml/BotonesdeReparacion.fxml");
	}
	

	/*
	 * Configuracion de Menu bar
	 */


	public void configuracion(ActionEvent event){
		ventanas.asignarCentroI("../vista/fxml/Validar.fxml");
	}
	
	public void registrarEmpleado(ActionEvent event){
		ventanas.asignarCentroI("../vista/fxml/RegistrarEmpleado.fxml");
		
	}
	public void respaldo(ActionEvent event){
		/*try {
	        String path = "C:/Users/stuart/Desktop/ana";
	        Runtime r = Runtime.getRuntime();
	 
	        //PostgreSQL variables            
	        String user = "postgres";
	        String dbase = "Roca2";
	        String password = "root";
	        Process p;
	        ProcessBuilder pb;
	 
	        
	          //Ejecucion del proceso de respaldo
	         
	        r = Runtime.getRuntime();        
	        pb = new ProcessBuilder("pg_dump", "-v", "-D", "-f", path, "-U", user, dbase);
	        pb.environment().put("PGPASSWORD", password);
	        pb.redirectErrorStream(true);
	        p = pb.start();        
	        System.out.println("se hizo con exito el respaldo");
	 
	    } catch (Exception e) {
	    }*/

	}

}
