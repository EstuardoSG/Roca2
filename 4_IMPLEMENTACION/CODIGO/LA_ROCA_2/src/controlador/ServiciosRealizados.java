package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;

public class ServiciosRealizados implements Initializable, IControladorVentanas  {

	private ControladordeVentanas ventanas;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
	}

}
