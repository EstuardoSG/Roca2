package controlador;



import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vista.MainRoca2;

public class ControladorVentana {

	/*
	 * Atributos.
	 */
	private Errores er;
	private IniciarSesion is;
	private Principal pr;
	private static ControladorVentana ventanas;
	private Stage primaryStage,modalEscenario, escenarioAdministrador, escenarioEmpleado;
	private Scene escena;
	private BorderPane contenedor;
	static BorderPane contenedorDialog, contenedord;
	private AnchorPane subContenedorDialog;
	
	/*
	 * Constructor privado.
	 */
	
	ControladorVentana(){
		er = new Errores();
		try {
			pr = new Principal();
		} catch (IOException e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	/*
	 * Recuperar la Instancia de la clase.
	 */
	
	public static  ControladorVentana getInstancia(){
		if(ventanas == null){
			ventanas = new ControladorVentana();
		}
		return ventanas;
	}
	
	/*
	 * Establecer Escenario Principal.
	 */
	
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	

	
	public void asignarEscenaAdministrador(String ruta, String titulo){
		try{
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedorDialog = (BorderPane)interfaz.load();
			contenedorDialog.setLeft(pr.miMenuAdministrador());
		    contenedorDialog.setCenter(pr.contenedor);
			 escenarioAdministrador = MainRoca2.getPrimaryStage();
			escenarioAdministrador.setTitle(titulo);
			escena = new Scene(contenedorDialog);
			escenarioAdministrador.setScene(escena);
			escenarioAdministrador.centerOnScreen();
			escenarioAdministrador.show();
			
		}catch(Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	public void asignarEscenaEmpleado(String ruta, String titulo){
		try{
			
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedorDialog = (BorderPane)interfaz.load();
			contenedorDialog.setLeft(pr.miMenuEmpleado());
		    contenedorDialog.setCenter(pr.contenedor);
		    escenarioEmpleado= MainRoca2.getPrimaryStage();
		    escenarioEmpleado.setTitle(titulo);
			escena = new Scene(contenedorDialog);
			escenarioEmpleado.setScene(escena);
			escenarioEmpleado.centerOnScreen();
			escenarioEmpleado.show();
			
		}catch(Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	public void asignarEscenaLogin(String ruta, String titulo){
		try{
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedorDialog = (BorderPane)interfaz.load();
			Stage dialogEscenario = MainRoca2.getPrimaryStage();
			dialogEscenario.setTitle(titulo);
			escena = new Scene(contenedorDialog);
			dialogEscenario.setScene(escena);
			dialogEscenario.centerOnScreen();
			dialogEscenario.show();
			
		}catch(Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	public void modal(String ruta, String titulo){
		try {
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			subContenedorDialog = (AnchorPane)interfaz.load();			
			modalEscenario = new Stage();
			modalEscenario.setTitle(titulo);
			modalEscenario.initModality(Modality.WINDOW_MODAL);
			modalEscenario.initOwner(escenarioEmpleado);
			escena = new Scene(subContenedorDialog);			
			modalEscenario.setScene(escena);	
			modalEscenario.centerOnScreen();
			modalEscenario.show();
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
}