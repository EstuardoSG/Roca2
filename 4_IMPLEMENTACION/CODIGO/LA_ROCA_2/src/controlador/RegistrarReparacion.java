package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import modelo.Reparacion;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class RegistrarReparacion implements Initializable, IControladorVentanas {

	private ControladordeVentanas ventanas;
	private ControladorVentana ventana;
	private Reparacion re;
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private int filasXPagina;
	private ObservableList<Reparacion> datos;
	private FilteredList<Reparacion> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	@FXML TextField txtAnticipo, txtEntrega;
	@FXML TextArea txtaDescripcionLlaves, txtaDescripcionRefaccion;
	@FXML Button btnGuardar, btnActualizar, btnEliminar,btnNuevo;
	@FXML ComboBox cbCheckList;
	@FXML CheckBox chbLlaves, chbRefaccion, chbRegistrosEliminados;
	@FXML TableView<Reparacion> tvReparacion;
	@FXML TableColumn<Reparacion, String> tcCheckList, tcDescripcionRefaccion, tcFecha;
	@FXML TableColumn<Reparacion, Boolean> tcRefaccion;
	@FXML TableColumn<Reparacion, Float> tcAnticipo;
	
	public RegistrarReparacion(){
		re = new Reparacion();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	public void initialize(URL location, ResourceBundle resources) {

			//	cbCheckList.setItems(re.getReparacion(true));
				llenarTableView(true);
				btnActualizar.setDisable(true);
				btnEliminar.setDisable(true);

	}

	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvReparacion.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvReparacion.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvReparacion);
	}
	
	public void llenarTableView(Boolean estatus){
		tcCheckList.setCellValueFactory(new PropertyValueFactory<Reparacion, String>("nombre1"));
		tcRefaccion.setCellValueFactory(new PropertyValueFactory<Reparacion, Boolean>("dejorefaccion"));
		tcDescripcionRefaccion.setCellValueFactory(new PropertyValueFactory<Reparacion, String>("descripcionrefaccion"));
		tcAnticipo.setCellValueFactory(new PropertyValueFactory<Reparacion, Float>("anticipo"));
		tcFecha.setCellValueFactory(new PropertyValueFactory<Reparacion, String>("fechaentrega"));
		try {
			//Refrescar y volver a cargar los datos en el TableView
			datos= re.getReparacion(true);
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
	@FXML public void seleccionar(){
		if(chbRegistrosEliminados.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
			btnActualizar.setDisable(true);
		}else{
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		btnActualizar.setDisable(false);
		}
		if(tvReparacion.getSelectionModel().getSelectedItem()!=null){
			re =  tvReparacion.getSelectionModel().getSelectedItem();
			id = re.getIdreparacion();
			txtaDescripcionLlaves.setText(re.getDescripcionllaves().toString());
			txtaDescripcionRefaccion.setText(re.getDescripcionrefaccion().toString());
			txtAnticipo.setText(re.getAnticipo().toString());
			txtEntrega.setText(re.getFechaentrega().toString());
			// cbCheckList.getSelectionModel().select(re.getNombre1());
			 //limpiarLbl();
			if(re.getDejallaves().equals(true))
				chbLlaves.setSelected(true);
			else
				chbLlaves.setSelected(false);
			
			if(re.getDejarefaccion().equals(true))
				chbRefaccion.setSelected(true);
			else
				chbRefaccion.setSelected(false);
		}
	}
	
	/*public void limpiarLbl(){
		lbl1.setText("");
		lbl2.setText("");
		lbl3.setText("");
		lbl4.setText("");
		lbl5.setText("");
		lbl6.setText("");
		lbl8.setText("");
		lblMensaje.setText("");
	}
	*/
	@FXML public void guardar(){
	//	limpiarLbl();
	/*	try {
			if(cbEmpleado.getSelectionModel().getSelectedItem() == null){
				lbl1.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(cbMarca.getSelectionModel().getSelectedItem() == null){
				lbl2.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtModelo.getText().trim().isEmpty()){
				lbl3.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtMotor.getText().trim().isEmpty()){
				lbl4.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtColor.getText().trim().isEmpty()) {
				lbl5.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(chkPlacas.isSelected() == false){
				lbl6.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtaDescripciondelaMotocicleta.getText().trim().isEmpty()){
				lbl8.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				m = new Motocicleta();
				m.setEm(cbEmpleado.getSelectionModel().getSelectedItem());
				m.setBr(cbMarca.getSelectionModel().getSelectedItem());
				m.setModelo(new SimpleStringProperty(txtModelo.getText()));
				m.setColor(new SimpleStringProperty(txtColor.getText()));
				m.setMotor(new SimpleStringProperty(txtMotor.getText()));
				m.setDescripcionMotocicleta(new SimpleStringProperty(txtaDescripciondelaMotocicleta.getText()));
				
				}if(m.guardar()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
				}
				else{
					lblMensaje.setText("Se producido un problema en el servidor.");
				
				}	
			} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@FXML public void actualizar(){
	/*	try {
			if(txtModelo.getText().trim().isEmpty() ||
					txtMotor.getText().trim().isEmpty() ||
					txtColor.getText().trim().isEmpty() ||
					txtaDescripciondelaMotocicleta.getText().trim().isEmpty()){
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				m = new Motocicleta();
				m.setIdmotocicleta(new SimpleIntegerProperty(getId()));
				m.setEm(cbEmpleado.getSelectionModel().getSelectedItem());
				m.setBr(cbMarca.getSelectionModel().getSelectedItem());
				m.setModelo(new SimpleStringProperty(txtModelo.getText()));
				m.setColor(new SimpleStringProperty(txtColor.getText()));
				m.setMotor(new SimpleStringProperty(txtMotor.getText()));
				m.setDescripcionMotocicleta(new SimpleStringProperty(txtaDescripciondelaMotocicleta.getText()));
				
				if(m.actualizar()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se producido un problema en el servidor.");
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@FXML public void eliminar(){
/*		try {
			if(m.getIdmotocicleta().equals(""))
				lblMensaje.setText("Debe seleccionar el film a dar de baja");
			else
				
				if(m.eliminar()==true){
					m = new Motocicleta();
			    	m.setIdmotocicleta(new SimpleIntegerProperty(getId()));
					llenarTableView(true);
					limpiar();
					lblMensaje.setText("Registro dado de baja satisfactoriamente.");
				}
				else{
					lblMensaje.setText("Se ha presentado un error con el servidor");
				}
					
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@FXML public void nuevo(){
	/*	limpiar();
		btnGuardar.setDisable(false);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		chkMotocicletasEliminadas.setSelected(false);
		llenarTableView(true);*/
	}


	public void limpiar(){
	/*	txtModelo.clear();
		txtColor.clear();
		txtMotor.clear();
		txtFecha.clear();
		txtaDescripciondelaMotocicleta.clear();
		txtFiltro.clear();
		//.clearSelection no agarra si el valor fue llenado al darle click a la tabla
		//cbEmpleado.getSelectionModel().clearSelection();
		cbEmpleado.setValue(null);
		cbMarca.setValue(null);
		chkPlacas.setSelected(false);*/
	}
	
	@FXML public void eliminados(){
	/*	if(chkMotocicletasEliminadas.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
			btnEditar.setDisable(true);
			llenarTableView(false);
			limpiarLbl();
		}else{
			llenarTableView(true);
		lblMensaje.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		btnEditar.setDisable(false);
		limpiarLbl();
		}*/
	}
	@FXML public void buscador(){
	/*	if(txtFiltro.getText().trim().isEmpty()){
				//Llenar TableView
				datosBusqueda= new FilteredList<>(datos);
				filasXPagina=10;
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblMensaje.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
			}
			else{
				try{
	 			datosBusqueda.setPredicate(Motocicleta->Motocicleta.getModelo().toLowerCase().
	 					contains(txtFiltro.getText().toLowerCase()));
	 			if(datosBusqueda.size()<10)
	 				filasXPagina= datosBusqueda.size();
	 			else
	 				filasXPagina=10;
	 			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensaje.setText("Se encontraron " + datosBusqueda.size() + " coincidencias.");
				}
				catch(Exception ex){
					//Enviar mensaje
					lblMensaje.setText("No se encontraron resultados");
					filasXPagina=0;
					paginador.setPageCount(filasXPagina); 				
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				}
			}*/
		}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
