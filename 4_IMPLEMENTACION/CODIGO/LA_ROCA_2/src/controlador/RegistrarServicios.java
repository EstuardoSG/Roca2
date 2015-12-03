package controlador;

import java.net.URL;
import java.util.ResourceBundle;
//import javafx.geometry.Pos;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Servicios;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistrarServicios implements Initializable, IControladorVentanas{
	//***************************************************************************************************
	//VARIABLES, OBJETOS Y CONTROLES
	private ControladordeVentanas ventanas; 
	private int identificador;
	private Servicios serviciosModelo;
	private Errores er;
	private ObservableList<Servicios> datosServicio;
	@FXML private TextField txtNombreServicio,txtPrecio1Servicio,txtPrecio2Servicio;
	@FXML private Button btnEstatusServicio,btnGuardarServicio,btnNuevoServicio,btnActualizarServicio;
	@FXML private Label lblMensajeServicios,lblNotificacionesServicios;
	@FXML TableView<Servicios> tableServicios;
	@FXML TableColumn<Servicios, String> tcNombreServicio;
	@FXML TableColumn<Servicios, Float> tcPrecio1Servicio,tcPrecio2Servicio;
	
	
	public RegistrarServicios(){
		serviciosModelo = new Servicios();
		datosServicio = FXCollections.observableArrayList();
		er = new Errores();
	}
 	
	//***************************************************************************************************
	//BOTONES
	public void nuevoServicio(){
		tableServicios.getSelectionModel().clearSelection();
		limpiarFormulario();
		btnGuardarServicio.setDisable(false);
		btnActualizarServicio.setDisable(true);
		btnEstatusServicio.setDisable(true);
		
	}
	
	public void guardarServicio(){
		tableServicios.getSelectionModel().clearSelection();
		try {
			if (txtNombreServicio.getText().isEmpty() || txtPrecio1Servicio.getText().isEmpty() || txtPrecio2Servicio.getText().isEmpty()) {
				lblNotificacionesServicios.setText("Faltan datos por ingresar");
			}else{
				Float precio01 = Float.valueOf(txtPrecio1Servicio.getText());
				Float precio02 = Float.valueOf(txtPrecio2Servicio.getText());
				serviciosModelo = new Servicios();
				serviciosModelo.setNombreServicio(new SimpleStringProperty(txtNombreServicio.getText()));
				serviciosModelo.setPrecio1(new SimpleFloatProperty(precio01));
				serviciosModelo.setPrecio2(new SimpleFloatProperty(precio02));
				boolean res = serviciosModelo.guardarServicio();
				if (res){
					filtrarServicios();
					lblNotificacionesServicios.setText("Los datos han sido guardados correctamente");
				}
				else
					lblNotificacionesServicios.setText("Los datos no se han podido guardar");
			}
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		
	}
	
	public void actualizarServicio(){
		tableServicios.getSelectionModel().clearSelection();
		identificador = Integer.valueOf(serviciosModelo.getIdServicio());
		try {
			if (txtNombreServicio.getText().isEmpty()) {
				lblNotificacionesServicios.setText("Faltan datos por ingresar");;
			}else{
				Float precio01 = Float.valueOf(txtPrecio1Servicio.getText());
				Float precio02 = Float.valueOf(txtPrecio2Servicio.getText());
				serviciosModelo = new Servicios();
				serviciosModelo.setIdServicio(new SimpleIntegerProperty (identificador));
				serviciosModelo.setNombreServicio(new SimpleStringProperty(txtNombreServicio.getText()));
				serviciosModelo.setPrecio1(new SimpleFloatProperty(precio01));
				serviciosModelo.setPrecio2(new SimpleFloatProperty(precio02));
				boolean res = serviciosModelo.actualizarServicio();	
				if (res){
					filtrarServicios();
					lblNotificacionesServicios.setText("Datos actualizados correctamente");
				}
				else
					lblNotificacionesServicios.setText("Datos no actualizados");
			}
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		
	}
	
	public void eliminarServicio(){
		identificador = Integer.valueOf(serviciosModelo.getIdServicio());
		if (tableServicios.getSelectionModel().isEmpty()) {
			lblNotificacionesServicios.setText("Debe selecionar un registro");
			
		}else{
			serviciosModelo = new Servicios();
			
			serviciosModelo.setIdServicio(new SimpleIntegerProperty(Integer.valueOf(identificador)));
			if (serviciosModelo.eliminarServicio() == true ) {
				limpiarFormulario();
				filtrarServicios();
						lblNotificacionesServicios.setText("Operación no válida para registros eliminados");
				}else{
				lblNotificacionesServicios.setText("No se ha podido eliminar el registro");
			}
		}
	}
	
	//***************************************************************************************************
	//FORMULARIOS Y TABLAS
	@FXML public void  click_TablaServicios(){
		btnGuardarServicio.setDisable(true);
		btnActualizarServicio.setDisable(false);
		btnEstatusServicio.setDisable(false);
		 if (tableServicios.getSelectionModel().getSelectedItem()!= null) {
			serviciosModelo = tableServicios.getSelectionModel().getSelectedItem();
			//txtPrecio1Servicio.setAlignment(Pos.CENTER_RIGHT);
			txtNombreServicio.setText(serviciosModelo.getNombreServicio().toString());
			txtPrecio1Servicio.setText(serviciosModelo.getPrecio1().toString());
			txtPrecio2Servicio.setText(serviciosModelo.getPrecio2().toString());
		 }
	}
	
	public void filtrarServicios(){
		inicializarTabla();
		try {
			tableServicios.setItems(serviciosModelo.getServicios(true));
			datosServicio = serviciosModelo.getServicios(true);
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		String cantidadServicios = String.valueOf(datosServicio.size());
		lblMensajeServicios.setText("Total de registros: "+cantidadServicios);
		limpiarFormulario();
		
	}
	
	//***************************************************************************************************
	//OTROS
	

	public void inicializarTabla(){
		btnActualizarServicio.setDisable(true);
		btnEstatusServicio.setDisable(true);
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
		filtrarServicios();
		
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}

}
