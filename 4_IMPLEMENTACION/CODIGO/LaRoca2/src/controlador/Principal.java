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
	@FXML Button btnAlmacen, btnRegistro, btnReparacion, btnClientes, btnCerrarSesion;
	
	@FXML Label lblMensaje;
	
	@FXML MenuItem miConfiguracion, miRegistrarEmpleado;

	
	private vista.MainRoca2 mainRoca2;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ventanas = ControladordeVentanas.getInstancia(); 
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
	public void clientes(ActionEvent event){
		//mainRoca2.showConsultaCliente();
	}
	public void cerrarsesion(ActionEvent event){
		System.exit(0);
	}
	
	/*
	 * Configuracion de Menu bar
	 */
	
	public void configuracion(ActionEvent event){
		//mainRoca2.showConfiguraciondeConexion();
	}
	
	public void registrarEmpleado(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/RegistrarEmpleado.fxml");
		
	}

}
