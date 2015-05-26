package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeRegistro implements Initializable {
	
	@FXML Button btnCliente, btnMotocileta, btnReparacion, btnProveedor;
	
	private vista.MainRoca2 mainRoca2;
	
	public void cliente(ActionEvent event){
		//mainRoca2.showRegistrarCliente();
	}
	public void motocicleta(ActionEvent event){
		//mainRoca2.showRegistrarMotocicleta();
	}
	public void reparacion(ActionEvent event){
		//mainRoca2.showRegistrarReparacion();
	}
	public void proveedor(ActionEvent event){
		//mainRoca2.showRegistrarProveedor();
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	public void setMainRoca2(vista.MainRoca2 mainRoca2){
		this.mainRoca2 = mainRoca2;
	}
}
