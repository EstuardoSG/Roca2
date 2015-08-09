package controlador;

import modelo.Company;
import modelo.ProveedorContacto;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
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

public class RegistrarProveedorContacto implements Initializable, IControladorVentanas {
	private Company p;
	private ProveedorContacto c;
	private ControladordeVentanas ventanas;
	private ControladorVentana ventana;
	
	private int idc;
	private int ide;
	public int getIdc() {
		return idc;
	}
	public void setIdc(int idc) {
		this.idc = idc;
	}
	public int getIde() {
		return ide;
	}
	public void setIde(int ide) {
		this.ide = ide;
	}

	@FXML ComboBox<Company> cbEmpresa;
	@FXML TableColumn<ProveedorContacto, String> tcEmpresa, tcNombre, tcAP, tcAM, tcCelular;
	@FXML TableView<ProveedorContacto> tvProveedor;
	@FXML TextField txtNombre, txtAP, txtAM, txtCelular, txtCorreo, txtBuscar;
	@FXML Label lblMensaje, lblRegistros;
	@FXML Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, lbl13, lbl14,lbl15, lbl16;
	@FXML CheckBox ckbEliminados;
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnEliminar, btnProveedor;

	// #region Variables_Paginacion
	private int filasXPagina;
	private ObservableList<ProveedorContacto> datos;
	private FilteredList<ProveedorContacto> datosB;
	@FXML Pagination paginador;
	// #endregion
	
	// #region Constructor
	public RegistrarProveedorContacto() {
		p = new Company();
		c = new ProveedorContacto();
		filasXPagina = 10;
		datos = FXCollections.observableArrayList();
	}
	// #endregion
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			cbEmpresa.setItems(p.getCompany());
			//Enlazar Columnas
			llenarTableView(true);
			
			btnEliminar.setDisable(true);
			btnEditar.setDisable(true);
		}catch (SQLException e){
			e.printStackTrace();
		};
	}
	
	// #region metodos_paginacion
	private Node createPage(int pageIndex){
		if(filasXPagina>0){
			int fromIndex = pageIndex * filasXPagina;
			int toIndex = Math.min(fromIndex + filasXPagina, datosB.size());
			tvProveedor.setItems(FXCollections.observableArrayList(datosB.subList(fromIndex, toIndex)));
		}
		else {
			tvProveedor.setItems(null);
			paginador.setPageCount(0);
		}
		return new BorderPane(tvProveedor);
	}
	// #endregion
	
	//Método para llenar el tableView
	public void llenarTableView(Boolean estatus){
		tcEmpresa.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("p"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("nombre"));
		tcAP.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("apellidoPaterno"));
		tcAM.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("apellidoMaterno"));
		tcCelular.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("celular"));
		try{
			datos= c.getProveedorContacto(estatus);
			datosB = new FilteredList<>(datos);
			paginador.setPageCount(datosB.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		}catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
	}
	
	//Método para subir los datos del TableView a los Textfield
	@FXML public void click_TableView(){
		if(ckbEliminados.isSelected()){
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
			btnEditar.setDisable(true);
		}else{
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(false);
		btnEditar.setDisable(false);
		}
		if(tvProveedor.getSelectionModel().getSelectedItem()!=null){
			c = tvProveedor.getSelectionModel().getSelectedItem();
			//TextField
			idc = c.getIdProveedorContacto();
			ide = c. getIdproveedor();
			txtNombre.setText(c.getNombre());
			txtAP.setText(c.getApellidoPaterno());
			txtAM.setText(c.getApellidoMaterno());
			txtCelular.setText(c.getCelular());
			txtCorreo.setText(c.getCorreo());
			//ComboBox
			cbEmpresa.getSelectionModel().select(c.getP());
		}
	}
	
	//Método para insertar registros proveedor.
	@FXML public void click_insertarp(){
		limpiarlbl();
		try{
			if(cbEmpresa.getSelectionModel().getSelectedItem() == null){
				lbl11.setText("*");
			}
			if(txtNombre.getText().trim().isEmpty()){
				lbl12.setText("*");
			}
			if(txtAP.getText().trim().isEmpty()){
				lbl13.setText("*");
			}
			if(txtAM.getText().trim().isEmpty()){
				lbl14.setText("*");
			}
			if(txtCelular.getText().trim().isEmpty()){
				lbl15.setText("*");
			}
			if(txtCorreo.getText().trim().isEmpty()){
				lbl16.setText("*");
			}else {
				c = new ProveedorContacto();
				c.setNombre(new SimpleStringProperty(txtNombre.getText()));
				c.setApellidoPaterno(new SimpleStringProperty(txtAP.getText()));
				c.setApellidoMaterno(new SimpleStringProperty(txtAM.getText()));
				c.setCelular(new SimpleStringProperty(txtCelular.getText()));
				c.setCorreo(new SimpleStringProperty(txtCorreo.getText()));
				c.setP(cbEmpresa.getSelectionModel().getSelectedItem());
			}
			if(c.insertc()){
				lblMensaje.setText("Datos insertados con éxito");
				llenarTableView(true);
			}else{
					lblMensaje.setText("Se producio un problema en el servidor.");
			}
		} catch (Exception e){
			
		}
	}
	
	@FXML public void click_actualizarp(){
		try{
			
			if(txtNombre.getText().trim().isEmpty() ||
					txtAP.getText().trim().isEmpty() ||
					txtAM.getText().trim().isEmpty() ||
					txtCelular.getText().trim().isEmpty() ||
					txtCorreo.getText().trim().isEmpty()){
			}
			else {
				c = new ProveedorContacto();
				c.setIdProveedorContacto(new SimpleIntegerProperty(getIdc()));
				c.setNombre(new SimpleStringProperty(txtNombre.getText()));
				c.setApellidoPaterno(new SimpleStringProperty(txtAP.getText()));
				c.setApellidoMaterno(new SimpleStringProperty(txtAM.getText()));
				c.setCelular(new SimpleStringProperty(txtCelular.getText()));
				c.setCorreo(new SimpleStringProperty(txtCorreo.getText()));
				c.setP(cbEmpresa.getSelectionModel().getSelectedItem());
				if(c.actualizarc()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se producio un problema en el servidor.");
			}
		} catch (Exception e){
			
		}
	}
	
	//Método para eliminar el registro seleccionado en el TableView.
	@FXML public void click_eliminarp(){
		try{
			if(txtNombre.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar la empresa que desea eliminar");
			else
				if(c.eliminarc()==true){
					c = new ProveedorContacto();
					c.setIdProveedorContacto(new SimpleIntegerProperty(getIdc()));
					llenarTableView(true);
					limpiar();
					lblMensaje.setText("Registro dado de baja.");
				}
				else {
					lblMensaje.setText("Se ha presentado un errorcon el servido.");
				}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void nuevo(){
		limpiar();
		limpiarlbl();
		btnGuardar.setDisable(false);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		ckbEliminados.setSelected(false);
		llenarTableView(true);
	}
	
	public void limpiar(){

		txtNombre.clear();
		txtAP.clear();
		txtAM.clear();
		txtCelular.clear();
		txtCorreo.clear();
		cbEmpresa.setValue(null);
	}
	public void limpiarlbl(){
		lbl1.setText("");
		lbl2.setText("");
		lbl3.setText("");
		lbl4.setText("");
		lbl5.setText("");
		lbl6.setText("");
		lbl7.setText("");
		lbl8.setText("");
		lbl9.setText("");
		lbl10.setText("");
		lbl11.setText("");
		lbl12.setText("");
		lbl13.setText("");
		lbl14.setText("");
		lbl15.setText("");
		lbl16.setText("");
	}
	@FXML public void click_inactivos(){
		if(ckbEliminados.isSelected()){
			llenarTableView(false);
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
			btnEditar.setDisable(true);
		}else{
			llenarTableView(true);
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(false);
			btnEditar.setDisable(false);
		}
	}
	
	@FXML public void buscarTexto(){
		if(txtBuscar.getText().trim().isEmpty()){
			datosB = new FilteredList<>(datos);
			filasXPagina = 10;
			paginador.setPageCount(datosB.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblMensaje.setText(datosB.size() + " registros encontrados.");
		}
		else{
			try{
				datosB.setPredicate(ProveedorContacto -> ProveedorContacto.getNombre().toLowerCase().
						contains(txtBuscar.getText().toLowerCase()));
				if(datosB.size()<10)
					filasXPagina = datosB.size();
				else
					filasXPagina=10;
				paginador.setPageCount(datosB.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensaje.setText("Se encontraron " + datosB.size() + " coincidencias.");
			} catch (Exception e){
				lblMensaje.setText("No se encontraron resultados");
				filasXPagina=0;
				paginador.setPageCount(filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			}
		}
	}
	
	@FXML public void proveedor(){
		ventana = ControladorVentana.getInstancia();
		ventana.modal("../vista/fxml/RegistrarProveedor.fxml", "Registrar proveedor");
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
