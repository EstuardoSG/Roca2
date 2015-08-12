package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.print.attribute.standard.PDLOverrideSupported;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Empleado;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	private Errores er;
	private Empleado e;
	@FXML TextField txtNombre, txtNombre2, txtApellidoPaterno, txtApellidoMaterno, txtTelefono, txtTelefono2,
	txtCelular, txtCelular2, txtDomicilio, txtNumeroInterior, txtNumeroExterior, txtCalle, txtLocalidad, txtCiudad, 
	txtEstado, txtCodigoPostal, txtCorreo, txtUsuario, txtPrivilegio;
	@FXML PasswordField pwdContrasenia;
	@FXML Button btnGuardar, btnEliminar,btnNuevoRegistroEmpleado,btnEliminarEmpleado,btnActualizarEmpleado;
	@FXML Label lblFechaRegistro,lblMensajeEmpleado,lblNotificacionesEmpleados;
	@FXML CheckBox chbEmpleados;
	
	@FXML TableView<Empleado> TableEmpleado;
	@FXML TableColumn<Empleado, String> tcNombre, tcApellidoPaterno, tcApellidoMaterno,tcCelular,tcCorreo,tcDireccion;

	
	public RegistrarEmpleado(){
		e = new Empleado();
		datosEmpleado = FXCollections.observableArrayList();
		er = new Errores();
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
				txtUsuario.getText().isEmpty() || txtPrivilegio.getText().isEmpty() ||
				pwdContrasenia.getText().isEmpty()
				){
				lblNotificacionesEmpleados.setText("Faltan datos por ingresar");
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

					boolean res = e.guardarEmpleado();
					if (res){
						limpiarFormulario();
						filtrarEmpleados();
						lblNotificacionesEmpleados.setText("Los datos han sido guardados correctamente");
					}
					else
						lblNotificacionesEmpleados.setText("No se ha podido guardar el registro");
			}catch(Exception e){
				er.printLog(e.getMessage(), this.getClass().toString());
			}
		}
	}
	
	public void actualizarEmpleado(){
		
		identificador = Integer.valueOf(e.getIdempleado());
		try{
			
			if (TableEmpleado.getSelectionModel().isEmpty()) {
				lblNotificacionesEmpleados.setText("Faltan datos por ingresar");
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

				boolean res = e.actualizarEmpleado();
				if (res){
					filtrarEmpleados();
					lblNotificacionesEmpleados.setText("Los datos han sido actualizados correctamente");
				}
				else
					lblNotificacionesEmpleados.setText("Los datos no han sido actualizados");
				
				
				
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
		@FXML public void eliminarEmpleado(){
			identificador = Integer.valueOf(e.getIdempleado());
			if (TableEmpleado.getSelectionModel().isEmpty()) {
				lblNotificacionesEmpleados.setText("Debe seleccionar un registro");
				
			}else{
				e = new Empleado();
				e.setIdempleado(new SimpleIntegerProperty(Integer.valueOf(identificador)));
				if (e.eEmpleado() == true ) {
					limpiarFormulario();
					filtrarEmpleados();
					if (chbEmpleados.isSelected()) {
						lblNotificacionesEmpleados.setText("Operación no válida para registros eliminados");
					}else{
						lblNotificacionesEmpleados.setText("Los datos han sido eliminados correctamente");
					}
									
				}else{
					lblNotificacionesEmpleados.setText("Ha ocurrido un fallo al eliminar");
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
			lblFechaRegistro.setText("Fecha de ingreso: "+e.getFechaingreso());
		}

	}
	
	
	public void filtrarEmpleados(){
		asignarDatosTabla();
		try {
			TableEmpleado.setItems(e.getEmpleado());
			datosEmpleado = e.getEmpleado();
		} catch (SQLException e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	
		String cantidadEmpleados = String.valueOf(datosEmpleado.size());
		lblMensajeEmpleado.setText("Total de registros: "+cantidadEmpleados);
		limpiarFormulario();
			
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
