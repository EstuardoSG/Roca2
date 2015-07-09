package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import modelo.Notificaciones;
import modelo.Servicios;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistrarServicios implements Initializable{
	//***************************************************************************************************
	//VARIABLES, OBJETOS Y CONTROLES
	private int identificador;
	private Servicios serviciosModelo;
	private ObservableList<Servicios> datosServicio;
	@FXML private TextField txtNombreServicio,txtPrecio1Servicio,txtPrecio2Servicio;
	@FXML private CheckBox chbFiltrarServicios;
	@FXML private Button btnEstatusServicio,btnGuardarServicio,btnNuevoServicio,btnActualizarServicio;
	@FXML private Label lblMensajeServicios;
	@FXML TableView<Servicios> tableServicios;
	@FXML TableColumn<Servicios, String> tcNombreServicio;
	@FXML TableColumn<Servicios, Float> tcPrecio1Servicio,tcPrecio2Servicio;
	
	
	Notificaciones notificacion = new Notificaciones();
	
	public RegistrarServicios(){
		serviciosModelo = new Servicios();
		datosServicio = FXCollections.observableArrayList();
	}
 	
	//***************************************************************************************************
	//BOTONES
	public void nuevoServicio(){
		tableServicios.getSelectionModel().clearSelection();
		limpiarFormulario();
		
	}
	
	public void guardarServicio(){
		tableServicios.getSelectionModel().clearSelection();
		try {
			if (txtNombreServicio.getText().isEmpty() || txtPrecio1Servicio.getText().isEmpty() || txtPrecio2Servicio.getText().isEmpty()) {
				notificacion.faltanDatos();
			}else{
				Float precio01 = Float.valueOf(txtPrecio1Servicio.getText());
				Float precio02 = Float.valueOf(txtPrecio1Servicio.getText());
				serviciosModelo = new Servicios();
				serviciosModelo.setNombreServicio(new SimpleStringProperty(txtNombreServicio.getText()));
				serviciosModelo.setPrecio1(new SimpleFloatProperty(precio01));
				serviciosModelo.setPrecio2(new SimpleFloatProperty(precio02));
				boolean res = serviciosModelo.guardarServicio();
				if (res){
					filtrarServicios();
					notificacion.datosGuardados();
				}
				else
					notificacion.datosNoGuardados();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void actualizarServicio(){
		tableServicios.getSelectionModel().clearSelection();
		identificador = Integer.valueOf(serviciosModelo.getIdServicio());
		try {
			if (txtNombreServicio.getText().isEmpty()) {
				notificacion.faltanDatos();
			}else{
				Float precio01 = Float.valueOf(txtPrecio1Servicio.getText());
				Float precio02 = Float.valueOf(txtPrecio1Servicio.getText());
				serviciosModelo = new Servicios();
				serviciosModelo.setIdServicio(new SimpleIntegerProperty (identificador));
				serviciosModelo.setNombreServicio(new SimpleStringProperty(txtNombreServicio.getText()));
				serviciosModelo.setPrecio1(new SimpleFloatProperty(precio01));
				serviciosModelo.setPrecio2(new SimpleFloatProperty(precio02));
				boolean res = serviciosModelo.actualizarServicio();	
				System.out.println(serviciosModelo.getIdServicio());
				if (res){
					filtrarServicios();
					notificacion.datosGuardados();
				}
				else
					notificacion.datosNoGuardados();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void eliminarServicio(){
		identificador = Integer.valueOf(serviciosModelo.getIdServicio());
		if (tableServicios.getSelectionModel().isEmpty()) {
			notificacion.seleccionarRegistro();
			
		}else{
			serviciosModelo = new Servicios();
			
			serviciosModelo.setIdServicio(new SimpleIntegerProperty(Integer.valueOf(identificador)));
			if (serviciosModelo.eliminarServicio() == true ) {
				limpiarFormulario();
				filtrarServicios();
				notificacion.datosEliminados();
								
			}else{
				notificacion.falloEliminar();
			}
		}
	}
	
	//***************************************************************************************************
	//FORMULARIOS Y TABLAS
	@FXML public void  click_TablaServicios(){
		 if (tableServicios.getSelectionModel().getSelectedItem()!= null) {
			serviciosModelo = tableServicios.getSelectionModel().getSelectedItem();
			txtNombreServicio.setText(serviciosModelo.getNombreServicio().toString());
			txtPrecio1Servicio.setText(serviciosModelo.getPrecio1().toString());
			txtPrecio2Servicio.setText(serviciosModelo.getPrecio2().toString());
		 }
	 }
	
	public void filtrarServicios(){
		if (chbFiltrarServicios.isSelected()) {
			inicializarTabla();
			try {
				tableServicios.setItems(serviciosModelo.getServiciosInactivos());
				datosServicio = serviciosModelo.getServiciosInactivos();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String cantidadServicios = String.valueOf(datosServicio.size());
			lblMensajeServicios.setText(cantidadServicios+" Registros encontrados");
			limpiarFormulario();
			btnEstatusServicio.setText("Restaurar servicio");
		}else{
			inicializarTabla();
			try {
				tableServicios.setItems(serviciosModelo.getServicios());
				datosServicio = serviciosModelo.getServicios();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String cantidadServicios = String.valueOf(datosServicio.size());
			lblMensajeServicios.setText(cantidadServicios+" Registros encontrados");
			limpiarFormulario();
			btnEstatusServicio.setText("Eliminar servicio");
		}
	}
	
	//***************************************************************************************************
	//OTROS
	

	public void inicializarTabla(){
		tcNombreServicio.setCellValueFactory(new PropertyValueFactory<Servicios,String>("nombreServicio"));
		tcPrecio1Servicio.setCellValueFactory(new PropertyValueFactory<Servicios, Float>("precio1"));
		tcPrecio2Servicio.setCellValueFactory(new PropertyValueFactory<Servicios, Float>("precio2"));
	}
	
	public void limpiarFormulario(){
		txtNombreServicio.clear();	
		txtPrecio1Servicio.clear();
		txtPrecio2Servicio.clear();
	}
	
	//***************************************************************************************************
	//INICIALIZACION DE LA CLASE
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		filtrarServicios();
		
	}

}
