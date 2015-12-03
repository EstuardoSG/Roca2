package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Brand;
import modelo.Empleado;
import modelo.Motocicleta;
import modelo.Reportes;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class RegistrarMotocicleta implements Initializable, IControladorVentanas{
	
	private Reportes re;
	private ControladordeVentanas ventanas;
	private ControladorVentana ventana;
	private Motocicleta m;
	private Empleado em;
	private Brand br;
	private Errores er;
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private int filasXPagina;
	private ObservableList<Motocicleta> datos;
	private FilteredList<Motocicleta> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8;
	@FXML TextField txtModelo, txtMotor, txtColor, txtFiltro, txtFecha;
	@FXML TextArea txtaDescripciondelaMotocicleta;
	@FXML Button btnGuardar, btnEditar, btnEliminar, btnBuscar, btnNuevo, btnMarca, btnReporte;
	@FXML ComboBox<Empleado> cbEmpleado;
	@FXML ComboBox<Brand>  cbMarca;
	@FXML CheckBox chkPlacas, chkMotocicletasEliminadas, chkActivos, chkEliminados;
	@FXML TableView<Motocicleta> tvMotocicletas;
	@FXML TableColumn<Motocicleta, String> tcMarca, tcModelo, tcColor, tcFecha, tcEmpleado;
	
	public RegistrarMotocicleta(){
		m = new Motocicleta();
		em = new Empleado();
		br = new Brand();
		er = new Errores();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
		re = new Reportes();
	}
	
	public void initialize(URL location, ResourceBundle resources) {
			try {
				cbMarca.setItems(br.getBrand());
				cbEmpleado.setItems(em.getEmpleado());
				llenarTableView(true);
				btnEditar.setDisable(true);
				btnEliminar.setDisable(true);
			} catch (SQLException e) {
				er.printLog(e.getMessage(), this.getClass().toString());
			}
	}

	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvMotocicletas.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvMotocicletas.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvMotocicletas);
	}
	
	public void llenarTableView(Boolean estatus){
		tcMarca.setCellValueFactory(new PropertyValueFactory<Motocicleta, String>("br"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<Motocicleta, String>("modelo"));
		tcColor.setCellValueFactory(new PropertyValueFactory<Motocicleta, String>("color"));
		tcFecha.setCellValueFactory(new PropertyValueFactory<Motocicleta, String>("fecha"));
		tcEmpleado.setCellValueFactory(new PropertyValueFactory<Motocicleta, String>("em"));
		try {
			//Refrescar y volver a cargar los datos en el TableView
			datos=m.getMotocicleta(estatus);
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
	@FXML public void click_TablaClientes(){
		if(chkMotocicletasEliminadas.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
			btnEditar.setDisable(true);
		}else{
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		btnEditar.setDisable(false);
		}
		if(tvMotocicletas.getSelectionModel().getSelectedItem()!=null){
			m =  tvMotocicletas.getSelectionModel().getSelectedItem();
			id = m.getIdmotocicleta();
			txtModelo.setText(m.getModelo().toString());
			txtMotor.setText(m.getMotor().toString());
			txtColor.setText(m.getColor().toString());
			txtFecha.setText(m.getFecha());
			txtaDescripciondelaMotocicleta.setText(m.getDescripcionMotocicleta().toString());
			 cbEmpleado.getSelectionModel().select(m.getEm());
			 cbMarca.getSelectionModel().select(m.getBr());
			 limpiarLbl();
			if(m.getPlaca().equals(true))
				chkPlacas.setSelected(true);
			else
				chkPlacas.setSelected(false);
		}
	}
	
	public void limpiarLbl(){
		lbl1.setText("");
		lbl2.setText("");
		lbl3.setText("");
		lbl4.setText("");
		lbl5.setText("");
		lbl6.setText("");
		lbl8.setText("");
		lblMensaje.setText("");
	}
	@FXML public void guardar(){
		limpiarLbl();
		try {
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
				er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	@FXML public void editar(){
		try {
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
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	@FXML public void eliminar(){
		try {
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
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void nuevo(){
		limpiar();
		limpiarLbl();
		btnGuardar.setDisable(false);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		chkMotocicletasEliminadas.setSelected(false);
		llenarTableView(true);
	}

	@FXML public void marca(){
		ventana = ControladorVentana.getInstancia();
		ventana.modal("../vista/fxml/RegistrarMarca.fxml", "Registrar marca");
	}
	public void limpiar(){
		txtModelo.clear();
		txtColor.clear();
		txtMotor.clear();
		txtFecha.clear();
		txtaDescripciondelaMotocicleta.clear();
		txtFiltro.clear();
		//.clearSelection no agarra si el valor fue llenado al darle click a la tabla
		//cbEmpleado.getSelectionModel().clearSelection();
		cbEmpleado.setValue(null);
		cbMarca.setValue(null);
		chkPlacas.setSelected(false);
	}
	
	@FXML public void click_inactivos(){
		if(chkMotocicletasEliminadas.isSelected()){
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
		}
	}
	@FXML public void buscarTexto(){
		if(txtFiltro.getText().trim().isEmpty()){
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
				er.printLog(ex.getMessage(), this.getClass().toString());
				}
			}
		}
	
	@FXML public void reporte(){
		if(chkActivos.isSelected()){
			re.cargarReporte("src/vista/reportes/MotocicletasActivo.jrxml");
			re.mostrarReporte();
		}
		if(chkEliminados.isSelected()){
			re.cargarReporte("src/vista/reportes/MotocicletasInactivo.jrxml");
			re.mostrarReporte();
		}
		else{
			lblMensaje.setText("Selecciona una opción");
		}
		chkEliminados.setSelected(false);
		chkActivos.setSelected(false);
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
