package vista;

import java.io.IOException;

import controlador.Errores;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainRoca2 extends Application {
		
	static  private Errores er;
	 public static Stage getPrimaryStage(){
	 		return primaryStage;
	 	}
    
     public static Stage primaryStage;
 	
 	public void start(Stage primaryStage) throws IOException{
  		try{
 			Parent root = FXMLLoader.load(getClass().getResource("fxml/IniciarSesion.fxml"));
 			Scene scene = new Scene(root);
 			MainRoca2.primaryStage = primaryStage;
 			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 			primaryStage.setTitle("Iniciar Sesión");
 			primaryStage.setScene(scene);
 			primaryStage.show();
 		}catch(Exception e){
 			er.printLog(e.getMessage(), this.getClass().toString());
 		}
 	}
 	
 	public static void main(String [] args){
 		launch(args);
 		er = new Errores();
 	}

}
