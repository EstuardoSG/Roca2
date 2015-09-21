package vista;

import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControladordeVentanas extends StackPane {
	
    private HashMap<String, Node> screens = new HashMap<>();
    
    public ControladordeVentanas() {
        super();
    }

    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {
            //System.out.println(name);
            //System.out.println(resource);
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            //System.out.println("paso1");
            Parent loadScreen = (Parent) myLoader.load();
            //System.out.println("paso2");
            IControladorVentanas myScreenControler = ((IControladorVentanas) myLoader.getController());
            //System.out.println("paso3");
            //myScreenControler.setVentanaMainRoca2(this);
            //System.out.println("paso4");
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()+"==="+name+"====="+resource);
            return false;
        }
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded. Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) { //screen loaded
        	
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) { //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(100), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0); //remove the displayed screen
                        getChildren().add(0, screens.get(name)); //add the screen
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(80), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name)); //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(250), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            //System.out.println("No se ha podido cargar el archivo \n");
            return false;
        }
    }

    //This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            //System.out.println("La ventana no existe");
            return false;
        } else {
            return true;
        }
    }
}
