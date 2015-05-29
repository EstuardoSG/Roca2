package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeReparacion implements Initializable {

	@FXML Button btnCheckList, btnReparacion, btnServicios, btnRealizados, btnPartes;
	
	private controlador.ControladordeVentanas ventanas;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ventanas = ControladordeVentanas.getInstancia(); 
		
	}
	
	public void checkList(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/CheckList.fxml");
	}
	public void reparacion(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/RegistrarReparacion.fxml");
	}
	public void servicios(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/Servicios.fxml");
	}
	public void realizados(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/ServiciosRealizados.fxml");
	}
	public void partes(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/ReparacionPartes.fxml");
	}
}
