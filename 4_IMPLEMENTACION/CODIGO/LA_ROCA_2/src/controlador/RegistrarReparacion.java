package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import javafx.fxml.Initializable;

public class RegistrarReparacion implements Initializable, IControladorVentanas {

	private ControladordeVentanas ventanas;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}

}
