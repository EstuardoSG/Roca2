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
	
	}
	public void almacen(ActionEvent event){
		//mainRoca2.showRegistrarAlmacen();
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setControladordeVentanas(controlador.ControladordeVentanas ventanas){
		this.ventanas = ventanas;
	}

}
