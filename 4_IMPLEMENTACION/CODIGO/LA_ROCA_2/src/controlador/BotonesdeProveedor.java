package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeProveedor implements Initializable {

	@FXML Button btnProveedor, btnContacto, btnActivo;
	
	private ControladordeVentanas ventanas;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ventanas = ControladordeVentanas.getInstancia();
	}

	public void proveedor(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/Proveedor.fxml");
	}
	public void contacto(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/Contacto.fxml");
	}
	public void activo(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/ProveedorContacto.fxml");
	}
}
