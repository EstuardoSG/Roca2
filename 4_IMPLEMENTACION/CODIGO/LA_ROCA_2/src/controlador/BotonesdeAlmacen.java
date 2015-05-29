package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeAlmacen implements Initializable {

	@FXML Button btnMarca, btnCompra, btnAlmacen, btnDetalle, btnAjuste;
		
	private controlador.ControladordeVentanas ventanas;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ventanas = ControladordeVentanas.getInstancia(); 
		
	}
	public void marca(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/MarcaRefaccion.fxml");
	}
	public void compra(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/CompraRefaccion.fxml");
	}
	
	public void almacen(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/Almacen.fxml");
	}
	public void detalle(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/CompraDetalle.fxml");
	}
	public void ajuste(ActionEvent event){
		ventanas.asignarCentro("../vista/fxml/AjusteAlmacen.fxml");
	}
	

}
