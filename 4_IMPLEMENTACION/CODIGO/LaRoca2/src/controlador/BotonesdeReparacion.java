package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BotonesdeReparacion implements Initializable {

	@FXML Button btnPendientes, btnEntregar, btnEntregadas, btn30dias;
	
	private vista.MainRoca2 mainRoca2;
	
	public void pendientes(ActionEvent event){
		
	}
	public void entregar(ActionEvent event){
		
	}
	public void entregadas(ActionEvent event){
		
	}
	public void diasmas(ActionEvent event){
		
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void setMainRoca2(vista.MainRoca2 mainRoca2){
		this.mainRoca2 = mainRoca2;
	}
}
