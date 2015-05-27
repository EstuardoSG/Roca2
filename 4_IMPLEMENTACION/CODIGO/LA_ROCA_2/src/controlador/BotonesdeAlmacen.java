package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeAlmacen implements Initializable {

	@FXML Button btnRefacciones, btnAlmacen;
	
	private controlador.ControladordeVentanas ventanas;
	
	public void refacciones(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/RegistrarAlmacen.fxml");
	}
	public void almacen(ActionEvent event){
		//mainRoca2.showRegistrarAlmacen();
		ventanas.asignarCentro("../vista/fxml/RegistrarRefaccion.fxml");
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ventanas = ControladordeVentanas.getInstancia(); 
		
	}

}
