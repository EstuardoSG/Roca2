package controlador;


import vista.MainRoca2;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladordeVentanas {

	/*
	 * Atributos.
	 */
	
	private static ControladordeVentanas ventanas;
	private Stage primaryStage, dialogEscenario;
	private Scene escena;
	private BorderPane contenedor;
	private BorderPane contenedorDialog;
	private AnchorPane subContenedorDialog;
	
	/*
	 * Constructor privado.
	 */
	
	private ControladordeVentanas(){
	}
	
	/*
	 * Recuperar la Instancia de la clase.
	 */
	
	public static ControladordeVentanas getInstancia(){
		if(ventanas == null){
			ventanas = new ControladordeVentanas();
		}
		return ventanas;
	}
	
	/*
	 * Establecer Escenario Principal.
	 */
	
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	public void asignarMenu(String ruta, String titulo){
		try{
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedor = (BorderPane)interfaz.load();
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			escena = new Scene(contenedor, screenBounds.getWidth(), screenBounds.getHeight());
			primaryStage.setScene(escena);
			primaryStage.setTitle(titulo);
			primaryStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void asignarEscena(String ruta, String titulo){
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
			e.printStackTrace();
		}
	}
	public void asignarIzquierda(String ruta){
		try{

			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			subContenedorDialog = (AnchorPane)interfaz.load();
			contenedorDialog.setLeft(subContenedorDialog);
			contenedorDialog.setCenter(null);
			Stage dialogEscenario =  MainRoca2.getPrimaryStage();
			dialogEscenario.setScene(escena);
			dialogEscenario.show();			
			

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void asignarCentro(String ruta){
		try{
			
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			subContenedorDialog = (AnchorPane)interfaz.load();
			contenedorDialog.setCenter(subContenedorDialog);
			Stage dialogEscenario = MainRoca2.getPrimaryStage();
			dialogEscenario.setScene(escena);
			dialogEscenario.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void asignarCentroI(String ruta){
		try{
			
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			subContenedorDialog = (AnchorPane)interfaz.load();
			contenedorDialog.setCenter(subContenedorDialog);
			contenedorDialog.setLeft(null);
			Stage dialogEscenario = MainRoca2.getPrimaryStage();
			dialogEscenario.setScene(escena);
			dialogEscenario.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void modal(String ruta, String titulo){
		try {
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedorDialog = (BorderPane)interfaz.load();			
			dialogEscenario = new Stage();
			dialogEscenario.setTitle(titulo);
			dialogEscenario.initModality(Modality.WINDOW_MODAL);
			dialogEscenario.initOwner(primaryStage);
			escena = new Scene(contenedorDialog);			
			dialogEscenario.setScene(escena);	
			dialogEscenario.centerOnScreen();
			dialogEscenario.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
