package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainRoca2 extends Application {

	private static Stage primaryStage;
	
	public void start(Stage primaryStage){
		try{
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
			Scene scene = new Scene(root);
			this.primaryStage = primaryStage;
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Stage getPrimaryStage(){
		return primaryStage;
	}
	public static void main(String [] args){
		launch(args);
	}
		
/*	private Stage primaryStage; 
	
	private static AnchorPane rootLayout; //Login
	private static BorderPane rootLayout1;
	
	private IniciarSesion controlleroot;
	private Principal controlleroot1; 

	
	public MainRoca2() {
		
	}
	*/
	
	/*public void start(Stage primaryStage) {
		try {
			FXMLLoader loader1 = new FXMLLoader(MainRoca2.class.getResource("fxml/Login.fxml"));
			rootLayout = (AnchorPane) loader1.load();
			Scene scene = new Scene(rootLayout);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			controlleroot = loader1.getController();
			controlleroot.setMainRoca2(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
/*      	@Override
	public void start(Stage primaryStage){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/Principal.fxml"));
		
		rootLayout1 = (BorderPane) loader.load();
		Scene scene = new Scene(rootLayout1);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		controlleroot1 = loader.getController();
		controlleroot1.setMainRoca2(this);
	}catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/*public void showPrincipal(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/Principal.fxml"));
			rootLayout1 = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout1);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			controlleroot1 = loader.getController();
			controlleroot1.setMainRoca2(this);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
	/*¨
	 * Ventanas de Menubar
	 */
/*	
	public void showConfiguraciondeConexion(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/ConfiguraciondeConexion.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			rootLayout1.setLeft(null);
			
			ConfiguraciondeConexion controller = loader.getController();
			controller.setMainRoca2(this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Ventanas de los botones
	 */
/*	public void showBotonesdeAlmacen(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/BotonesdeAlmacen.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setLeft(overviewPage);
			rootLayout1.setCenter(null);
			
			BotonesdeAlmacen controller = loader.getController();
			controller.setMainRoca2(this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void showBotonesdeRegistro(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/BotonesdeRegistro.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setLeft(overviewPage);
			rootLayout1.setCenter(null);
			
			BotonesdeRegistro controller = loader.getController();
			controller.setMainRoca2(this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	
	public void showBotonesdeReparacion(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/BotonesdeReparacion.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setLeft(overviewPage);
			rootLayout1.setCenter(null);
			
			BotonesdeReparacion controller = loader.getController();
			controller.setMainRoca2(this);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	/*
	 * Ventanas de Botones de Almacen
	 */
/*	public void showRegistrarRefaccion(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/RegistrarRefaccion.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			
			RegistrarRefaccion controller = loader.getController();
			controller.setMainRoca2(this);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showRegistrarAlmacen(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/RegistrarAlmacen.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			
			RegistrarAlmacen controller = loader.getController();
			controller.setMainRoca2(this);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Ventanas de Botones de Registro
	 */
/*	public void showRegistrarCliente(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/RegistrarCliente.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			
			RegistrarCliente controller = loader.getController();
			controller.setMainRoca2(this);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showRegistrarMotocicleta(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/RegistrarMotocicleta.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			
			RegistrarMotocicleta controller = loader.getController();
			controller.setMainRoca2(this);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showRegistrarReparacion(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/RegistrarReparacion.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			
			RegistrarReparacion controller = loader.getController();
			controller.setMainRoca2(this);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showRegistrarProveedor(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/RegistrarProveedor.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			
			RegistrarProveedor controller = loader.getController();
			controller.setMainRoca2(this);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showConsultaCliente(){
		try{
			FXMLLoader loader = new FXMLLoader(MainRoca2.class.getResource("fxml/ConsultaCliente.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout1.setCenter(overviewPage);
			rootLayout1.setLeft(null);
			
			ConsultaCliente controller = loader.getController();
			controller.setMainRoca2(this);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);

	}
*/
}
