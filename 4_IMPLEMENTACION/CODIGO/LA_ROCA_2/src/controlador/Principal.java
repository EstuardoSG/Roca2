package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;



public class Principal implements Initializable {
	
	private Errores er;
	private ControladorVentana ventana;
	
	@FXML Button btnCerrarSesion;
	
	@FXML public void cerrarSesion(){
		ventana = ControladorVentana.getInstancia();
		ventana.asignarEscenaLogin("../vista/fxml/IniciarSesion.fxml", "Iniciar Sesión");
	}

	public Principal() throws IOException {
		er = new Errores();
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	

	
	private static TreeItem<String> padre;
	
	 /*
	  * Paso 1# crear el TreeItem.
	  * Si el TreeItem el fxml tiene controlador añadir la interfaz
	  */

    private static TreeItem<String> tiRespaldo;
    
    private static TreeItem<String> tiAlmacen;
    
    private static TreeItem<String> tiProveedor;
    private TreeItem<String> tiRegistrarProveedorContacto;
    
    private static TreeItem<String> tiRegistrar;
    private TreeItem<String> tiRegistrarCliente;
    private TreeItem<String> tiRegistrarMotocicleta;
    private TreeItem<String> tiRegistrarEmpleado;
     
    private static TreeItem<String> tiReparacion;
    private TreeItem<String> tiCheckList;
    private TreeItem<String> tiRegistrarReparacion;
    
    
    private static TreeItem<String> tiRestaurarContacto;
     
    private static TreeItem<String> tiRegistrarServicios;
    
    
	 public static TreeView<String> tvMenu;

	 static ControladordeVentanas contenedor = new ControladordeVentanas();
	 static BorderPane plantilla =null;
	 
    /*
     * Paso 2# agregarle valores un nombre y la ruta del fxml.
     */

    public static String ventanaRespaldo = "Respaldo";
    public static String fxmlRespaldo = "../vista/fxml/Respaldo.fxml";
    
    public static String ventanaAlmacen = "Almacen";
    public static String fxmlAlamcen = "../vista/fxml/Almacen.fxml";
    /*
     * 
     */
    public static String ventanaProveedor = "Proveedor";
    public static String fxmlProveedor = "../vista/fxml/Proveedor.fxml";
    
    public static String ventanaContacto = "Contacto";
    public static String fxmlContacto = "../vista/fxml/Proveedor.fxml";
    
    
    /* 
     * ventana Registrar y fxmlRegistrar no busca ningun fxml solo es para poder crear una pestaña de esa categoria
     */
    public static String ventanaRegistrar = "Registrar";
    public static String fxmlRegistrar = "../vista/fxml/Registrar.fxml";
    
    public static String ventanaRegistrarCliente = "RegistrarCliente";
    public static String fxmlRegistrarCliente = "../vista/fxml/RegistrarCliente.fxml";
    
    public static String ventanaRegistrarMotocicleta = "RegistrarMotocicleta";
    public static String fxmlRegistrarMotocicleta = "../vista/fxml/RegistrarMotocicleta.fxml";
    
    public static String ventanaRegistrarEmpleado = "RegistrarEmpleado";
    public static String fxmlRegistrarEmpleado = "../vista/fxml/RegistrarEmpleado.fxml";
    /*
     * Reparacion no busca fxml solo es para poder crear una pestaña de esa categoria
     */
    public static String ventanaReparacion = "Reparacion";
    public static String fxmlReparacion = "../vista/fxml/Reparacion.fxml";
    
    public static String ventanaCheckList = "CheckList";
    public static String fxmlCheckList = "../vista/fxml/CheckList.fxml";
    
    public static String ventanaRegistrarReparacion = "RegistrarReparacion";
    public static String fxmlRegistrarReparacion = "../vista/fxml/RegistrarReparacion.fxml";
    
	public static String ventanaImagen = "Imagen";
	public static String fxmlImaggen = "../vista/fxml/Imagen.fxml";
	
	 public static String ventanaRestaurarContacto = "RestaurarContacto";
	 public static String fxmlRestaurarContacto = "../vista/fxml/PContacto.fxml";
	 
	 public static String ventanaRegistrarServicio = "Registrar Servicio";
	 public static String fxmlRegistrarServicio= "../vista/fxml/Servicios.fxml";
	 
	public static void start() throws IOException {
	 /*
	  * Paso 3# Inicializar  los fxml para poder traer las ventanas.
	  */
    contenedor.loadScreen(Principal.ventanaRespaldo, Principal.fxmlRespaldo);
    contenedor.loadScreen(Principal.ventanaAlmacen, Principal.fxmlAlamcen);
    contenedor.loadScreen(Principal.ventanaContacto, Principal.fxmlContacto);
    contenedor.loadScreen(Principal.ventanaRegistrarCliente, Principal.fxmlRegistrarCliente);
    contenedor.loadScreen(Principal.ventanaRegistrarMotocicleta, Principal.fxmlRegistrarMotocicleta);
    contenedor.loadScreen(Principal.ventanaRegistrarEmpleado, Principal.fxmlRegistrarEmpleado);
    contenedor.loadScreen(Principal.ventanaCheckList, Principal.fxmlCheckList);
    contenedor.loadScreen(Principal.ventanaRegistrarReparacion, Principal.fxmlRegistrarReparacion);
    contenedor.loadScreen(Principal.ventanaImagen, Principal.fxmlImaggen);
    contenedor.loadScreen(Principal.ventanaRestaurarContacto, Principal.fxmlRestaurarContacto);
    contenedor.loadScreen(Principal.ventanaRegistrarServicio, Principal.fxmlRegistrarServicio);
    
     contenedor.setScreen(Principal.ventanaImagen);
	}

	
	static VBox miMenuEmpleado(){
		VBox mnu = new VBox();
		mnu.setPrefWidth(150);
            mnu.setPrefHeight(150);
            
		 padre = new TreeItem<String> ("La Roca 2");
		 padre.setExpanded(true);
             
	  /*
	   * Paso 4# Aqui se Inicializa los TreeItem del TreeView Dandole un nombre el cual aparecerá en la vista final del TreeView
	   */
     tiAlmacen = new TreeItem<String>("Almacén");
     tiProveedor = new TreeItem<String> ("Proveedor");
     tiProveedor.getChildren().add(new TreeItem<String>("Contacto"));
     
     tiRegistrar = new TreeItem<String> ("Registrar");
     tiRegistrar.getChildren().add(new TreeItem<String>("Cliente"));
     tiRegistrar.getChildren().add(new TreeItem<String> ("Motocicleta"));
     
     tiReparacion = new TreeItem<String> ("Reparación");
     tiReparacion.getChildren().add(new TreeItem<String>("CheckList"));
     tiReparacion.getChildren().add(new TreeItem<String>("Reparacion"));
     tiReparacion.getChildren().add(new TreeItem<String>("Servicios"));

		 /*
		  * Paso 5# Agregamos el TreeItem Inicializado.
		  */
     		padre.getChildren().add(tiAlmacen);
             padre.getChildren().add(tiRegistrar);
             padre.getChildren().add(tiReparacion);
             padre.getChildren().add(tiProveedor);
		    
	     tvMenu = new TreeView<String> (padre);        
	 
	  
	     tvMenu.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
	    	 	    	 
	         @Override
	         public void changed(ObservableValue observable, Object oldValue,
	                 Object newValue) {

	             TreeItem<String> selectedItem = (TreeItem<String>) newValue;
	             String nodoPadre = selectedItem.getParent().getValue();
	             String pulsoSobre = selectedItem.getValue(); 
                    //System.out.println(nodoPadre);
                    //System.out.println(pulsoSobre);
	             switch(nodoPadre){
                        case "La Roca 2":
                               switch(pulsoSobre){
                               /*
                                *Paso 6# ponemos en el case exactamente el nombre del paso 5 y llamamos la vista.
                                *por TreeItem
                                *case;
                                *contenedor
                                *break;
                                */
                                   case "Almacén":
                                    contenedor.loadScreen(Principal.ventanaAlmacen, Principal.fxmlAlamcen);
                                   contenedor.setScreen(Principal.ventanaAlmacen);        
                                   break;
                            }
                        case "Proveedor":
                       	 switch(pulsoSobre){
                       	 case "Contacto":
                       		contenedor.loadScreen(Principal.ventanaContacto, Principal.fxmlContacto);
                            contenedor.setScreen(Principal.ventanaContacto);
                            break;
                       	 }
                       
                        case "Registrar":
                       	 switch(pulsoSobre){
                       	 case"Cliente":
                         contenedor.loadScreen(Principal.ventanaRegistrarCliente, Principal.fxmlRegistrarCliente);
                       	 contenedor.setScreen(Principal.ventanaRegistrarCliente);
                       	 break;
                       	 case"Motocicleta":
                       		contenedor.loadScreen(Principal.ventanaRegistrarMotocicleta, Principal.fxmlRegistrarMotocicleta);
                            contenedor.setScreen(Principal.ventanaRegistrarMotocicleta);
                            break;
                       	 }
                      
                        case "Reparación":
                       	 switch(pulsoSobre){
                       	 case "CheckList":
                       		contenedor.loadScreen(Principal.ventanaCheckList, Principal.fxmlCheckList);
                            contenedor.setScreen(Principal.ventanaCheckList);
                            break;
                       	 case "Reparacion":
                       		contenedor.loadScreen(Principal.ventanaRegistrarReparacion, Principal.fxmlRegistrarReparacion);
                       	 contenedor.setScreen(Principal.ventanaRegistrarReparacion);
                       	 break;
                       	 case "Servicios":
                       		contenedor.loadScreen(Principal.ventanaRegistrarServicio, Principal.fxmlRegistrarServicio);
                       	 contenedor.setScreen(Principal.ventanaRegistrarServicio);
                       	 break;
                       	 }
                       	ControladorVentana.contenedorDialog.setCenter(contenedor);
                        break;
	             }
                    
	         }

	       });
	     
	     mnu.getChildren().add(tvMenu);

		
		
		return mnu;
	     
	}
	
	
	static VBox miMenuAdministrador(){
		VBox mn = new VBox();
		mn.setPrefWidth(150);
            mn.setPrefHeight(150);
            
		 padre = new TreeItem<String> ("La Roca 2");
		 padre.setExpanded(true);
             
	  /*
	   * Paso 4# Aqui se Inicializa los TreeItem del TreeView Dandole un nombre el cual aparecerá en la vista final del TreeView
	   */
     tiRespaldo = new TreeItem<String>("Respaldo");
     tiAlmacen = new TreeItem<String>("Almacén");
     tiProveedor = new TreeItem<String> ("Proveedor");
     tiProveedor.getChildren().add(new TreeItem<String>("Contacto"));
     
     tiRegistrar = new TreeItem<String> ("Registrar");
     tiRegistrar.getChildren().add(new TreeItem<String>("Cliente"));
     tiRegistrar.getChildren().add(new TreeItem<String> ("Motocicleta"));
     tiRegistrar.getChildren().add(new TreeItem<String> ("Empleado"));
     
     tiReparacion = new TreeItem<String> ("Reparación");
     tiReparacion.getChildren().add(new TreeItem<String>("CheckList"));
     tiReparacion.getChildren().add(new TreeItem<String>("Reparacion"));
     tiReparacion.getChildren().add(new TreeItem<String>("Servicios"));
     
     tiRestaurarContacto = new TreeItem<String> ("Papelera");
     tiRestaurarContacto.getChildren().add(new TreeItem<String>("Contactos"));
		 /*
		  * Paso 5# Agregamos el TreeItem Inicializado.
		  */
     		padre.getChildren().add(tiAlmacen);
             padre.getChildren().add(tiRegistrar);
             padre.getChildren().add(tiReparacion);
             padre.getChildren().add(tiProveedor);
             padre.getChildren().add(tiRespaldo);
             padre.getChildren().add(tiRestaurarContacto);
		 
		    
	     tvMenu = new TreeView<String> (padre);        
	 
	  
	     tvMenu.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
	    	 	    	 
	         @Override
	         public void changed(ObservableValue observable, Object oldValue,
	                 Object newValue) {

	             TreeItem<String> selectedItem = (TreeItem<String>) newValue;
	             String nodoPadre = selectedItem.getParent().getValue();
	             String pulsoSobre = selectedItem.getValue(); 
                    //System.out.println(nodoPadre);
                    //System.out.println(pulsoSobre);
	             switch(nodoPadre){
                        case "La Roca 2":
                               switch(pulsoSobre){
                               /*
                                *Paso 6# ponemos en el case exactamente el nombre del paso 5 y llamamos la vista.
                                *por TreeItem
                                *case;
                                *contenedor
                                *break;
                                */
                               case "Almacén":
                               contenedor.loadScreen(Principal.ventanaAlmacen, Principal.fxmlAlamcen);
                               contenedor.setScreen(Principal.ventanaAlmacen);
                               break;
                               case "Respaldo":
                               contenedor.loadScreen(Principal.ventanaRespaldo, Principal.fxmlRespaldo);
                               contenedor.setScreen(Principal.ventanaRespaldo);
                               break;
                            }
                        case "Proveedor":
                       	 switch(pulsoSobre){
                       	 case "Contacto":
                       		 contenedor.loadScreen(Principal.ventanaContacto, Principal.fxmlContacto);
                            contenedor.setScreen(Principal.ventanaContacto);
                            break;
                       	 }
                       
                        case "Registrar":
                       	 switch(pulsoSobre){
                       	 case"Cliente":
                       	 contenedor.loadScreen(Principal.ventanaRegistrarCliente, Principal.fxmlRegistrarCliente);
                       	 contenedor.setScreen(Principal.ventanaRegistrarCliente);
                       	 break;
                       	case"Motocicleta":
                       		contenedor.loadScreen(Principal.ventanaRegistrarMotocicleta, Principal.fxmlRegistrarMotocicleta);
                            contenedor.setScreen(Principal.ventanaRegistrarMotocicleta);
                            break;
                       	 case"Empleado":
                       	 contenedor.loadScreen(Principal.ventanaRegistrarEmpleado, Principal.fxmlRegistrarEmpleado);
                       	 contenedor.setScreen(Principal.ventanaRegistrarEmpleado);
                       	 break;
                       	 }
                      
                        case "Reparación":
                       	 switch(pulsoSobre){
                       	 case "CheckList":
                       		contenedor.loadScreen(Principal.ventanaCheckList, Principal.fxmlCheckList);
                            contenedor.setScreen(Principal.ventanaCheckList);
                            break;
                       	 case "Reparacion":
                       		contenedor.loadScreen(Principal.ventanaRegistrarReparacion, Principal.fxmlRegistrarReparacion);
                       	 contenedor.setScreen(Principal.ventanaRegistrarReparacion);
                       	 break;
                    	 case "Servicios":
                    		 contenedor.loadScreen(Principal.ventanaRegistrarServicio, Principal.fxmlRegistrarServicio);
                           	 contenedor.setScreen(Principal.ventanaRegistrarServicio);
                           	 break;
                       	 }
                        case "Papelera":
                        	switch(pulsoSobre){
                        	case"Contactos":
                        		contenedor.loadScreen(Principal.ventanaRestaurarContacto, Principal.fxmlRestaurarContacto);
                        	contenedor.setScreen(Principal.ventanaRestaurarContacto);
                        	break;
                        	}
                       	 ControladorVentana.contenedorDialog.setCenter(contenedor);
                         break;
	             }
                    
	         }

	       });
	     
	     mn.getChildren().add(tvMenu);

		
		
		return mn;
	     
	}

}
