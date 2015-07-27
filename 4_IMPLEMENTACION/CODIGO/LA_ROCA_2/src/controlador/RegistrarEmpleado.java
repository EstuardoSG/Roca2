package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Empleado;
import modelo.Notificaciones;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistrarEmpleado implements Initializable, IControladorVentanas {
	//***************************************************************************************************
	//VARIABLES, OBJETOS Y CONTROLES
	private ControladordeVentanas ventanas;
	private ObservableList<Empleado> datosEmpleado;
	private int identificador;
	private Empleado e;
	@FXML TextField txtNombre, txtNombre2, txtApellidoPaterno, txtApellidoMaterno, txtTelefono, txtTelefono2,
	txtCelular, txtCelular2, txtDomicilio, txtNumeroInterior, txtNumeroExterior, txtCalle, txtLocalidad, txtCiudad, 
	txtEstado, txtCodigoPostal, txtCorreo, txtUsuario, txtPrivilegio;
	@FXML PasswordField pwdContrasenia;
	@FXML DatePicker dateFecha;
	@FXML Button btnGuardar, btnEliminar,btnNuevoRegistroEmpleado,btnEliminarEmpleado,btnActualizarEmpleado;
	@FXML Label lblFechaRegistro,lblMensajeEmpleado;
	@FXML CheckBox chbEmpleados;
	
	@FXML TableView<Empleado> TableEmpleado;
	@FXML TableColumn<Empleado, String> tcNombre, tcApellidoPaterno, tcApellidoMaterno,tcCelular,tcCorreo,tcDireccion;
	
	Notificaciones notificacion = new Notificaciones();
	
	public RegistrarEmpleado(){
		e = new Empleado();
		datosEmpleado = FXCollections.observableArrayList();
	}
	//***************************************************************************************************
	//CONTROLES-BOTONES
	
	public void registrarNuevoEmpleado(){
		TableEmpleado.getSelectionModel().clearSelection();
		limpiarFormulario();

	}

	public void guardarEmpleado(){
		if(txtNombre.getText().isEmpty()  || 
				txtApellidoPaterno.getText().isEmpty() || txtApellidoMaterno.getText().isEmpty() ||
				txtDomicilio.getText().isEmpty() || txtNumeroExterior.getText().isEmpty() || 
				txtCalle.getText().isEmpty() || txtLocalidad.getText().isEmpty() ||
				txtCiudad.getText().isEmpty() || txtEstado.getText().isEmpty() ||
				txtCodigoPostal.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
				txtUsuario.getText().isEmpty() || txtPrivilegio.getText().isEmpty()
				){
			notificacion.faltanDatos();
		}else{
			try{
					e.setNombre1(new SimpleStringProperty(txtNombre.getText()));
					e.setNombre2(new SimpleStringProperty(txtNombre2.getText()));
					e.setApellidopaterno(new SimpleStringProperty(txtApellidoPaterno.getText()));
					e.setApellidomaterno(new SimpleStringProperty(txtApellidoMaterno.getText()));
					e.setTelefono1(new SimpleStringProperty(txtTelefono.getText()));
					e.setTelefono2(new SimpleStringProperty(txtTelefono2.getText()));
					e.setCelular1(new SimpleStringProperty(txtCelular.getText()));
					e.setCelular2(new SimpleStringProperty(txtCelular2.getText()));
					e.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
					e.setNumerointerior(new SimpleStringProperty(txtNumeroInterior.getText()));
					e.setNumeroexterior(new SimpleStringProperty(txtNumeroExterior.getText()));
					e.setCalle(new SimpleStringProperty(txtCalle.getText()));
					e.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
					e.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
					e.setEstado(new SimpleStringProperty(txtEstado.getText()));
					e.setCodigopostal(new SimpleStringProperty(txtCodigoPostal.getText()));
					e.setCorreo(new SimpleStringProperty(txtCorreo.getText()));
					e.setUsuario(new SimpleStringProperty(txtUsuario.getText()));
					e.setContrasenia(new SimpleStringProperty(pwdContrasenia.getText()));
					e.setPrivilegio(new SimpleStringProperty(txtPrivilegio.getText()));
					//*********

					if(dateFecha.getEditor().getText().isEmpty()){
						e.setFechadesalida(new SimpleStringProperty("0000-00-00"));
					}else{
						e.setFechadesalida(new SimpleStringProperty(dateFecha.getValue().toString()));
						
					}
						/*PORCION DE CÓDIGO REUTILIZABLE
					String estatus;
					if(chbEstatus.isSelected())
						estatus="TRUE";
					else
						estatus="FALSE";
					e.setEstatus(new SimpleBooleanProperty(Boolean.valueOf(estatus)));
					*/
				
					boolean res = e.guardarEmpleado();
					if (res){
						dateFecha.getEditor().clear();
						filtrarEmpleados();
						notificacion.datosGuardados();
					}
					else
						notificacion.datosNoGuardados();
	
		
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void actualizarEmpleado(){
		
		identificador = Integer.valueOf(e.getIdempleado());
		try{
			
			if (TableEmpleado.getSelectionModel().isEmpty()) {
				notificacion.faltanDatos();
			}else{
			
				e.setIdempleado(new SimpleIntegerProperty(identificador));
				e.setNombre1(new SimpleStringProperty(txtNombre.getText()));
				e.setNombre2(new SimpleStringProperty(txtNombre2.getText()));
				e.setApellidopaterno(new SimpleStringProperty(txtApellidoPaterno.getText()));
				e.setApellidomaterno(new SimpleStringProperty(txtApellidoMaterno.getText()));
				e.setTelefono1(new SimpleStringProperty(txtTelefono.getText()));
				e.setTelefono2(new SimpleStringProperty(txtTelefono2.getText()));
				e.setCelular1(new SimpleStringProperty(txtCelular.getText()));
				e.setCelular2(new SimpleStringProperty(txtCelular2.getText()));
				e.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				e.setNumerointerior(new SimpleStringProperty(txtNumeroInterior.getText()));
				e.setNumeroexterior(new SimpleStringProperty(txtNumeroExterior.getText()));
				e.setCalle(new SimpleStringProperty(txtCalle.getText()));
				e.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				e.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				e.setEstado(new SimpleStringProperty(txtEstado.getText()));
				e.setCodigopostal(new SimpleStringProperty(txtCodigoPostal.getText()));
				e.setCorreo(new SimpleStringProperty(txtCorreo.getText()));
				e.setUsuario(new SimpleStringProperty(txtUsuario.getText()));
				e.setContrasenia(new SimpleStringProperty(pwdContrasenia.getText()));
				e.setPrivilegio(new SimpleStringProperty(txtPrivilegio.getText()));
				e.setFechadesalida(new SimpleStringProperty(dateFecha.getValue().toString()));

				/*PORCION DE CÓDIGO REUTILIZABLE
				String estatus;
				if(chbEstatus.isSelected())
					estatus="TRUE";
				else
					estatus="FALSE";
				e.setEstatus(new SimpleBooleanProperty(Boolean.valueOf(estatus)));
*/
				boolean res = e.actualizarEmpleado();
				if (res){
					dateFecha.getEditor().clear();
					filtrarEmpleados();
					notificacion.datosGuardados();
				}
				else
					notificacion.datosNoGuardados();
				
				
				
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

		
		@FXML public void eliminarEmpleado(){
			identificador = Integer.valueOf(e.getIdempleado());
			if (TableEmpleado.getSelectionModel().isEmpty()) {
				notificacion.seleccionarRegistro();
				
			}else{
				e = new Empleado();
				e.setIdempleado(new SimpleIntegerProperty(Integer.valueOf(identificador)));
				if (e.eEmpleado() == true ) {
					limpiarFormulario();
					filtrarEmpleados();
					notificacion.datosEliminados();
									
				}else{
					notificacion.falloEliminar();
				}
			}
		}
		
		
	
	//***************************************************************************************************
	//FORMULARIOS Y TABLAS
	@FXML public void click_TablaEmpleados(){
		if(TableEmpleado.getSelectionModel().getSelectedItem()!=null){
			e = TableEmpleado.getSelectionModel().getSelectedItem();
			txtNombre.setText(e.getNombre1().toString());
			txtNombre2.setText(e.getNombre2().toString());
			txtApellidoPaterno.setText(e.getApellidopaterno().toString());
			txtApellidoMaterno.setText(e.getApellidomaterno().toString());
			txtTelefono.setText(e.getTelefono1().toString());
			txtTelefono2.setText(e.getTelefono2().toString());
			txtCelular.setText(e.getCelular1().toString());
			txtCelular2.setText(e.getCelular2().toString());
			txtDomicilio.setText(e.getDomicilio().toString());
			txtNumeroInterior.setText(e.getNumerointerior().toString());
			txtNumeroExterior.setText(e.getNumeroexterior().toString());
			txtCalle.setText(e.getCalle().toString());
			txtLocalidad.setText(e.getLocalidad().toString());
			txtCiudad.setText(e.getCiudad().toString());
			txtEstado.setText(e.getEstado().toString());
			txtCodigoPostal.setText(e.getCodigopostal().toString());
			txtCorreo.setText(e.getCorreo().toString());
			txtUsuario.setText(e.getUsuario().toString());
			txtPrivilegio.setText(e.getPrivilegio().toString());
			pwdContrasenia.setText(e.getContrasenia().toString());
			dateFecha.setPromptText(e.getFechadesalida());
			lblFechaRegistro.setText("Fecha de ingreso: "+e.getFechaingreso());
		}

	}
	
	
	public void filtrarEmpleados(){
		if (chbEmpleados.isSelected()) {
			asignarDatosTabla();
			try {
				TableEmpleado.setItems(e.getEmpleadoInactivo());
				datosEmpleado = e.getEmpleadoInactivo();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String cantidadEmpleados = String.valueOf(datosEmpleado.size());
			lblMensajeEmpleado.setText(cantidadEmpleados+" Registros encontrados");
			limpiarFormulario();
			btnEliminarEmpleado.setText("Restaurar empleado");
		}else{
			asignarDatosTabla();
			try {
				TableEmpleado.setItems(e.getEmpleado());
				datosEmpleado = e.getEmpleado();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			String cantidadEmpleados = String.valueOf(datosEmpleado.size());
			lblMensajeEmpleado.setText(cantidadEmpleados+" Registros encontrados");
			limpiarFormulario();
			btnEliminarEmpleado.setText("Eliminar empleado");
		}
		
	}
	//***************************************************************************************************
	//OTROS 
	public void asignarDatosTabla(){
		tcNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre1"));
		tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidopaterno"));
		tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidomaterno"));
		tcCorreo.setCellValueFactory(new PropertyValueFactory<Empleado,String>("correo"));
		tcDireccion.setCellValueFactory(new PropertyValueFactory<Empleado,String>("domicilio"));
		
	}
	
	public void limpiarFormulario(){
		txtNombre.clear();
		txtNombre2.clear();
		txtApellidoPaterno.clear();
		txtApellidoMaterno.clear(); 
		txtTelefono.clear(); 
		txtTelefono2.clear();
		txtCelular.clear(); 
		txtCelular2.clear(); 
		txtDomicilio.clear();
		txtNumeroInterior.clear();
		txtNumeroExterior.clear();
		txtCalle.clear();
		txtLocalidad.clear();
		txtCiudad.clear();
		txtEstado.clear(); 
		txtCodigoPostal.clear(); 
		txtCorreo.clear();
		txtUsuario.clear(); 
		txtPrivilegio.clear();
		pwdContrasenia.clear();
		dateFecha.setPromptText(null);
	}
	
	//***************************************************************************************************
	/*Inicialización de la clase
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filtrarEmpleados();
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
