package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Compra;
import modelo.Empleado;
import modelo.Provider;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class RegistrarCompra implements Initializable, IControladorVentanas{

	private ControladordeVentanas ventanas;
	private Compra co;
	private Provider pr;
	private Empleado em;
	public int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {

		this.id = id;
	}
	private int filasXPagina;
	private ObservableList<Compra> datos;
	private FilteredList<Compra> datosBusqueda;
	@FXML Pagination paginador;
	
	
	@FXML Label lblMensaje, lblRegistros, lblFecha, lbl1, lbl2, lbl3, lbl4;
	@FXML TextField txtRefaccion, txtCantidad, txtFecha, txtFiltro;
	@FXML ComboBox<Empleado> cbEmpleado;
	@FXML ComboBox<Provider> cbProveedor;
	@FXML CheckBox chbPedidosRealizados, chbPedidosCancelados;
	
	@FXML Button btnNuevo, btnGuardar,  btnEliminar, btnRecibido;
	@FXML TableView<Compra> tvCompra;
	@FXML TableColumn<Compra, String> tcEmpleado, tcProveedor, tcRefaccion;
	@FXML TableColumn<Compra, Integer> tcCantidad;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			cbProveedor.setItems(pr.getProvider());
			cbEmpleado.setItems(em.getEmpleado());
			llenarTableViewActual(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public RegistrarCompra(){
		co = new Compra();
		pr = new Provider();
		em = new Empleado();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	

	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvCompra.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvCompra.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvCompra);
	}
	
	public void llenarTableViewActual(Boolean estatus){
		
		tcEmpleado.setCellValueFactory(new PropertyValueFactory<Compra, String>("em"));
		tcProveedor.setCellValueFactory(new PropertyValueFactory<Compra, String>("pr"));
		tcRefaccion.setCellValueFactory(new PropertyValueFactory<Compra, String>("refaccion"));
		tcCantidad.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("cantidad"));
		
		try {
			//Refrescar y volver a cargar los datos en el TableView

				datos = co.getCompraActual(estatus);
			
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
public void llenarTableViewActivo(Boolean estatus){
		
		tcEmpleado.setCellValueFactory(new PropertyValueFactory<Compra, String>("em"));
		tcProveedor.setCellValueFactory(new PropertyValueFactory<Compra, String>("pr"));
		tcRefaccion.setCellValueFactory(new PropertyValueFactory<Compra, String>("refaccion"));
		tcCantidad.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("cantidad"));
		
		try {
			//Refrescar y volver a cargar los datos en el TableView

				datos = co.getCompraActivo(estatus);
			
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
public void llenarTableViewEstatus(Boolean estatus){
		
		tcEmpleado.setCellValueFactory(new PropertyValueFactory<Compra, String>("em"));
		tcProveedor.setCellValueFactory(new PropertyValueFactory<Compra, String>("pr"));
		tcRefaccion.setCellValueFactory(new PropertyValueFactory<Compra, String>("refaccion"));
		tcCantidad.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("cantidad"));
		
		try {
			//Refrescar y volver a cargar los datos en el TableView

				datos = co.getCompraEstatus(estatus);
			
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}


	@FXML public void click_TablaCompra(){
		if(chbPedidosRealizados.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
		}else{
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		}
		if(chbPedidosCancelados.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
		}else{
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		}
		if(tvCompra.getSelectionModel().getSelectedItem()!=null){
			co =  tvCompra.getSelectionModel().getSelectedItem();
			id = co.getFolioCompra();
			txtRefaccion.setText(co.getRefaccion().toString());
			txtCantidad.setText(co.getCantidad().toString());
			txtFecha.setText(co.getFecha().toString());
			cbEmpleado.getSelectionModel().select(co.getEm());
			cbProveedor.getSelectionModel().select(co.getPr());
			 limpiarLbl();
		}
	}
	
	@FXML public void nuevo(){
		chbPedidosCancelados.setSelected(false);
		chbPedidosRealizados.setSelected(false);
	}
	@FXML public void guardar(){
		limpiarLbl();
		try {
			if(cbEmpleado.getSelectionModel().getSelectedItem() == null){
				lbl1.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(cbProveedor.getSelectionModel().getSelectedItem() == null){
				lbl2.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtRefaccion.getText().trim().isEmpty()){
				lbl3.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtCantidad.getText().trim().isEmpty()){
				lbl4.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				co = new Compra();
				//co.setEm(cbEmpleado.getSelectionModel().getSelectedItem());
				co.setPr(cbProveedor.getSelectionModel().getSelectedItem());
				co.setRefaccion(new SimpleStringProperty(txtRefaccion.getText()));
				co.setCantidad(new SimpleIntegerProperty(Integer.valueOf(txtCantidad.getText())));
				
				}if(co.guardar()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableViewActual(true);
				}
				else{
					lblMensaje.setText("Se producido un problema en el servidor.");
				
				}	
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML public void eliminar(){
		try {
			if(co.getFolioCompra().equals(""))
				lblMensaje.setText("Debe seleccionar el film a dar de baja");
			else
				
				if(co.eliminar()==true){
					co = new Compra();
			    	co.setFolioCompra(new SimpleIntegerProperty(getId()));
					llenarTableViewActual(true);
					limpiar();
					lblMensaje.setText("Registro ha sido cancelado.");
				}
				else{
					lblMensaje.setText("Se ha presentado un error con el servidor");
				}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML public void recibido(){
		try {
			if(co.getFolioCompra().equals(""))
				lblMensaje.setText("Debe seleccionar el film a dar de baja");
			else
				
				if(co.recibido()==true){
					co = new Compra();
			    	co.setFolioCompra(new SimpleIntegerProperty(getId()));
					llenarTableViewActual(true);
					limpiar();
					lblMensaje.setText("Registro ha sido recibido.");
				}
				else{
					lblMensaje.setText("Se ha presentado un error con el servidor");
				}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void click_pedidosRealizados() throws SQLException{
		if(chbPedidosRealizados.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
			llenarTableViewEstatus(false);
			limpiarLbl();
			chbPedidosCancelados.setSelected(false);
		}else{
			llenarTableViewActual(true);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		limpiarLbl();
		}
	}
		
		@FXML public void click_pedidosCancelados() throws SQLException{
			if(chbPedidosCancelados.isSelected()){
				btnGuardar.setDisable(true);
				btnEliminar.setDisable(true);
				llenarTableViewActivo(false);
				limpiarLbl();
				chbPedidosRealizados.setSelected(false);
			}else{
				llenarTableViewActual(true);
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(false);
			limpiarLbl();
			}
		}
	public void limpiarLbl(){
		lbl1.setText("");
		lbl2.setText("");
		lbl3.setText("");
		lbl4.setText("");
	}
	public void  limpiar(){
		txtRefaccion.clear();
		txtCantidad.clear();
		txtFecha.clear();
		txtFiltro.clear();
		//.clearSelection no agarra si el valor fue llenado al darle click a la tabla
		//cbEmpleado.getSelectionModel().clearSelection();
		cbEmpleado.setValue(null);
		cbProveedor.setValue(null);
	}

	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
	
}
