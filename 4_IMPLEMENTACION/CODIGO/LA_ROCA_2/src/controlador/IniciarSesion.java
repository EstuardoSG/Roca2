package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import vista.MainRoca2;
import modelo.Conexion;
import modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class IniciarSesion implements Initializable {
	
	private Conexion con;
	private Usuario modeloUsuario;
	private ControladordeVentanas ventanas;
	
	@FXML TextField txtUsuario;
	@FXML PasswordField pwdContrasenia;
	@FXML Button btnIniciarSesion;
	@FXML Label lblMensaje;
	
	private vista.MainRoca2 mainRoca2;
	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void Logear(ActionEvent event){
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
				ventanas = ControladordeVentanas.getInstancia();
				ventanas.asignarEscena("../vista/fxml/Principal.fxml", "La Roca");
				System.out.println("Existe el usuario, es:"+ modeloUsuario.getPrivilegio());	
			}
			else{
				System.out.println("Usuario no valido");
			}
		}
	}
}
