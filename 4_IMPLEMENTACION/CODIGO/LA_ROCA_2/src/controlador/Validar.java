package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import modelo.Conexion;
import modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Validar implements Initializable{

	private Conexion con;
	private Usuario modeloUsuario;
	private ControladordeVentanas ventanas;
	
	@FXML Label lblMensaje;
	@FXML TextField txtUsuarioValidar;
	@FXML PasswordField pwdContraseniaValidar;
	@FXML Button btnValidar;
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ventanas = ControladordeVentanas.getInstancia();
		
	}
	
	public void validar(ActionEvent event){
		if(txtUsuarioValidar.getText().isEmpty() && pwdContraseniaValidar.getText().isEmpty()){
			lblMensaje.setText("Faltan datos por ingresar");
		}else{
			//Validar si existe el usuario.
			if(modeloUsuario == null){
				modeloUsuario = new Usuario();
			}
			//Asignamos los datos
			modeloUsuario.setNombre(txtUsuarioValidar.getText());
			modeloUsuario.setContrasenia(pwdContraseniaValidar.getText());
			//Verificamos si existe en la Base de datos.
			boolean resultado = modeloUsuario.Existe();
			if(resultado){
				if(modeloUsuario.getPrivilegio().equals("admin")){
				ventanas = ControladordeVentanas.getInstancia();
				ventanas.asignarEscena("../vista/fxml/Principal.fxml", "La Roca");
				System.out.println("Existe el usuario, es:"+ modeloUsuario.getPrivilegio());
				
					ventanas.asignarCentroI("../vista/fxml/ConfiguraciondeConexion.fxml");
					
				}else{
					lblMensaje.setText("Usuario no valido");
					System.out.println("Usuario no valido");
				}
			}else{
				lblMensaje.setText("Usuario no valido");
				System.out.println("Usuario no valido");
			}
		}
	}
}
