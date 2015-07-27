package vista;

import java.io.IOException;
import java.net.URL;

import controlador.IniciarSesion;
import controlador.Principal;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainRoca2 extends Application {
		
	 
	 public static Stage getPrimaryStage(){
	 		return primaryStage;
	 	}
    
     public static Stage primaryStage;
 	
 	public void start(Stage primaryStage) throws IOException{
  		try{
 			Parent root = FXMLLoader.load(getClass().getResource("fxml/IniciarSesion.fxml"));
 			Scene scene = new Scene(root);
 			this.primaryStage = primaryStage;
 			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
 			primaryStage.setTitle("Iniciar Sesión");
 			primaryStage.setScene(scene);
 			primaryStage.show();
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 	}
 	
 	public static void main(String [] args){
 		launch(args);
 	}

}
