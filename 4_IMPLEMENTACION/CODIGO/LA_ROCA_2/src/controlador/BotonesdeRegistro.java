package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeRegistro implements Initializable {
	
	@FXML Button btnCliente, btnMotocileta, btnMarca;
	
	private ControladordeVentanas ventanas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ventanas = ControladordeVentanas.getInstancia();
	}
	
	
	public void cliente(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/RegistrarCliente.fxml");
	}
	public void motocicleta(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/RegistrarMotocicleta.fxml");
	}
	public void marca(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/RegistrarMarca.fxml");
	}
	
}
