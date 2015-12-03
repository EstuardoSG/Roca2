package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import modelo.Backup;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;

public class Respaldo implements Initializable, IControladorVentanas {

	private ControladordeVentanas ventanas;
	private Errores er;
	@FXML Label lblMensaje;
	@FXML TextField txtRuta, txtIp, txtPuerto, txtUsuario, txtBasedeDatos;
	@FXML PasswordField pwdContrasenia;
	@FXML Button btnRespaldo, btnRestaurar, btnBuscar;
	@FXML ListView lvRespaldo;



	@FXML public void respaldar() throws Exception{
		try {
			if(txtIp.getText().isEmpty() || txtPuerto.getText().isEmpty() || txtUsuario.getText().isEmpty() || 
					pwdContrasenia.getText().isEmpty() || txtBasedeDatos.getText().isEmpty()){
				lblMensaje.setText("Debes Ingresar los datos");
			}else{	
			        ProcessBuilder pbuilder;
			        //Realiza la construccion del comando
			        //			 
			        // otra forma de hacerlo sin  tener variables de entorno
			        //pbuilder = new ProcessBuilder("C://Program Files//PostgreSQL//9.4//bin//pg_dump ", "-h", txtIp.getText(), "-p", txtPuerto.getText(), "-U", txtUsuario.getText(), "-C",  "-d", txtBasedeDatos.getText(), "-f", "src/backup/Roca.sql");
			        //En caso de no poner toda la ruta configurar variables de entorno de postgresql
			        pbuilder = new ProcessBuilder("pg_dump ", "-h", txtIp.getText(), "-p", txtPuerto.getText(), "-U", txtUsuario.getText(), "-C",  "-d", txtBasedeDatos.getText(), "-f", "src/backup/Roca.sql");
			        //Se ingresa el valor del password a la variable de entorno de postgres
			        pbuilder.environment().put( "PGPASSWORD", pwdContrasenia.getText() );
			        pbuilder.redirectErrorStream(true);
			        //Ejecuta el proceso
			        
			        Process p = pbuilder.start();
			        lblMensaje.setText("Respaldando");
			        p.waitFor();
			        
		 
			        lblMensaje.setText("Respaldo Exitoso");
			        
			        txtRuta.setText( "src/backup/Roca.sql");
				    new Backup("DES/ECB/PKCS5Padding",  "src/backup/Roca.sql").encriptar();
			        
				      
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void restaurar(){
		try {
			if(txtRuta.getText().isEmpty() || txtIp.getText().isEmpty() || txtPuerto.getText().isEmpty() || txtUsuario.getText().isEmpty() || 
					pwdContrasenia.getText().isEmpty()){
				lblMensaje.setText("Debes Ingresar los datos");
			}else{
					new Backup("DES/ECB/PKCS5Padding",txtRuta.getText()).desencriptar();
			        ProcessBuilder pbuilder;
			        //Realiza la construccion del comando
			        pbuilder = new ProcessBuilder( "psql", "-h", txtIp.getText(), "-p", txtPuerto.getText(), "-U", txtUsuario.getText(),  "-f", txtRuta.getText()+ ".sql");
			        //Se ingresa el valor del password a la variable de entorno de postgres
			        pbuilder.environment().put( "PGPASSWORD", pwdContrasenia.getText() );
			        pbuilder.redirectErrorStream( true );
			        //Ejecuta el proceso y con Process espera a termine de hacer el respaldo.
			        Process p =pbuilder.start();
			        lblMensaje.setText("Restaurando base de datos espere un momento por favor");
			        p.waitFor();
			        new Backup("DES/ECB/PKCS5Padding", txtRuta.getText()+ ".sql").encriptar();
			        lblMensaje.setText("Restauración Exitosa");

			}
		} catch (Exception e) {
		}
	}
	
	@FXML public void buscar(){
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Buscar archivo");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.*"));
		 fileChooser.setInitialDirectory(new File("src/backup"));
		 File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			txtRuta.setText(selectedFile.getAbsolutePath());
		}
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtIp.setText("127.0.0.1");
		txtPuerto.setText("5432");
		txtUsuario.setText("postgres");
		txtBasedeDatos.setText("Roca2");
	}
	
}
