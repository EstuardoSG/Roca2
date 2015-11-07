package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import vista.MainRoca2;
import modelo.Conexion;
import modelo.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

public class IniciarSesion implements Initializable, IControladorVentanas {
	
	private Conexion con;
	private Usuario modeloUsuario;
	private ControladorVentana ventana;
	private ControladordeVentanas ventanas;
	
	private MainRoca2 main;

	private GridPane grid = new GridPane();
	private String usernameResult,passwordResult;
	private TextField username = new TextField();
	private PasswordField password = new PasswordField(); 
	private Callback myCallback;
	private Stage stage;
	



	@FXML TitledPane tbIniciarSesion;
	@FXML Accordion acVista;
	
	@FXML TextField txtUsuario;
	@FXML PasswordField pwdContrasenia;
	@FXML Button btnIniciarSesion;
	@FXML Label lblMensaje;
	

	@FXML TextField txtIP, txtPuerto, txtBasedeDatos, txtUsuarioConfi;
	@FXML PasswordField pwdContraseniaConfi;
	@FXML Button btnConectar, btnDesconectar;
	
	
	@FXML TitledPane tpConfiguracion;
	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 * acVista y tbIniciar es para mostrar el TitledPane de IniciarSesión.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		acVista.setExpandedPane(tbIniciarSesion);
	}

	

	@FXML public void  iniciarSesion() throws IOException{
		if(txtUsuario.getText().isEmpty() && pwdContrasenia.getText().isEmpty()){
			lblMensaje.setText("Faltan datos por ingresar");
		}else{
			//Validar si existe el usuario.
			if(modeloUsuario == null){
				modeloUsuario = new Usuario();
			}
			//Asignamos los datos
			modeloUsuario.setNombre(txtUsuario.getText());
			modeloUsuario.setContrasenia(pwdContrasenia.getText());
			//Verificamos si existe en la Base de datos.
			boolean resultado = modeloUsuario.Existe();
			if(resultado){
				if(modeloUsuario.getPrivilegio().equals("Administrador")){
					Principal.start();
			    	ventana = ControladorVentana.getInstancia();
					ventana.asignarEscenaAdministrador("../vista/fxml/Principal.fxml", "Administrador");
					System.out.println("Existe el usuario, es:"+ modeloUsuario.getPrivilegio());
					
				}else{
					Principal.start();
			    	ventana = ControladorVentana.getInstancia();
					ventana.asignarEscenaEmpleado("../vista/fxml/Principal.fxml", "Empleado");
					System.out.println("cargo empleado");
					System.out.println("Existe el usuario, es:"+ modeloUsuario.getPrivilegio());

				}
			}
			else{
				lblMensaje.setText("Usuario no valido");
				System.out.println("Usuario no valido");
			}
		}
		
	}
	
	@FXML public void conectar(){
		con = Conexion.getInstancia();
		if(txtIP.getText().isEmpty()==false &
				txtPuerto.getText().isEmpty()==false &
				txtBasedeDatos.getText().isEmpty()==false & 
				txtUsuarioConfi.getText().isEmpty()==false &
				pwdContraseniaConfi.getText().isEmpty()==false){
			
			con.setIp(txtIP.getText().trim());
			con.setPuerto(txtPuerto.getText().trim());
			con.setBd(txtBasedeDatos.getText().trim());
			con.setUsuario(txtUsuarioConfi.getText().trim());
			con.setContrasenia(pwdContraseniaConfi.getText().trim());
			String mensaje = con.conectar();
			lblMensaje.setText(mensaje);
		}else{
			lblMensaje.setText("Ingresa los datos Correctos");
		}
	}
	
	public void desconectar(ActionEvent event){
		con.desconectar();
		lblMensaje.setText("Se ha desconectado del servidor");
	}
	
	/*
	 * Metodos para el dialog
	 */
	
	@FXML
	public void click_Accion(){
	//	stage=MainRoca2.getPrimaryStage();	
		dialogAcceso();
		if(ButtonData.OK_DONE != null){
			if(username.getText().isEmpty()==false & password.getText().isEmpty()==false){
				if(modeloUsuario == null){
					modeloUsuario = new Usuario();
				}
				modeloUsuario.setNombre(username.getText());
				modeloUsuario.setContrasenia(password.getText());
				boolean resultado = modeloUsuario.Existe();
				if(resultado){
					if(modeloUsuario.getPrivilegio().equals("Administrador")){
					tpConfiguracion.setExpanded(true);
					System.out.println("Existe el usuario, es:"+ modeloUsuario.getPrivilegio());	
					lblMensaje.setText("");
					}else{
						lblMensaje.setText("Acceso Denegodo");
					}
				}
				else{
					lblMensaje.setText("Acceso denegado");
					System.out.println("Acceso denegado");
				}
			}
		}
		username.setText("");
		password.setText("");
	}
		
	
	public void dialogAcceso(){
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Validar");
		dialog.setHeaderText("¿Eres Administrador?");
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		
		username.setPromptText("Usuario");
	
		password.setPromptText("Contraseña");

		grid.add(new Label("Usuario:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Contraseña:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), password.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
		    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	
	}



	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
	}
}
