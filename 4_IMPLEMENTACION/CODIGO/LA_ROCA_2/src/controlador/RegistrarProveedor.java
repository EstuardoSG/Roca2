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

public class RegistrarProveedor implements Initializable {
	private Company p;
	private ProveedorContacto c;
	
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

	//@FXML ComboBox<Company> cbEmpresa;
	@FXML TableColumn<ProveedorContacto, String> tcEmpresa, tcDomicilio, tcNumeroExterior, tcCalle, tcTelefono;
	@FXML TableView<ProveedorContacto> tvProveedor;
	@FXML TextField txtEmpresa, txtDomicilio, txtNumeroInterior, txtNumeroExterior, txtCalle, txtLocalidad, txtCiudad,
	txtEstado, txtCodigoPostal, txtTelefono, txtBuscar;
	@FXML Label lblMensaje;
	@FXML Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10;
	@FXML CheckBox ckbInactivos;
	@FXML Button btnGuardar, btnEditar, btnEliminar;

	// #region Variables_Paginacion
	private int filasXPagina;
	private ObservableList<ProveedorContacto> datos;
	private FilteredList<ProveedorContacto> datosB;
	@FXML Pagination paginador;
	// #endregion
	
	// #region Constructor
	public RegistrarProveedor() {
		p = new Company();
		c = new ProveedorContacto();
		filasXPagina = 10;
		datos = FXCollections.observableArrayList();
	}
	// #endregion
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//try{
			//cbEmpresa.setItems(p.getCompany());
			//Enlazar Columnas
			llenarTableView(true);
			
			btnEliminar.setDisable(true);
			btnEditar.setDisable(true);
		//}catch (SQLException e){
			//e.printStackTrace();
		//};
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
		tcEmpresa.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("nombreEmpresa"));
		tcDomicilio.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("domicilio"));
		tcNumeroExterior.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("numeroExterior"));
		tcCalle.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("calle"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("telefonoEmpresa"));
		try{
			datos= c.getProveedorContacto(estatus);
			datosB = new FilteredList<>(datos);
			paginador.setPageCount(datosB.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			//lblRegistros.setText(datos.size() + " registros encontrados.");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Método para subir los datos del TableView a los Textfield
	@FXML public void click_TableView(){
		if(ckbInactivos.isSelected()){
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
			txtEmpresa.setText(c.getNombreEmpresa());
			txtDomicilio.setText(c.getDomicilio());
			txtNumeroInterior.setText(c.getNumeroInterior());
			txtNumeroExterior.setText(c.getNumeroExterior());
			txtCalle.setText(c.getCalle());
			txtLocalidad.setText(c.getLocalidad());
			txtCiudad.setText(c.getCiudad());
			txtEstado.setText(c.getEstado());
			txtCodigoPostal.setText(c.getCodigoPostal());
			txtTelefono.setText(c.getTelefonoEmpresa());
		}
	}
	
	//Método para insertar registros proveedor.
	@FXML public void click_insertarp(){
		limpiarlbl();
		try{
			if(txtEmpresa.getText().trim().isEmpty()){
				lbl1.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtDomicilio.getText().trim().isEmpty()){
				lbl2.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtNumeroInterior.getText().trim().isEmpty()){
				lbl3.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtNumeroExterior.getText().trim().isEmpty()){
				lbl4.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtCalle.getText().trim().isEmpty()) {
				lbl5.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtLocalidad.getText().trim().isEmpty()){
				lbl6.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtCiudad.getText().trim().isEmpty()){
				lbl7.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtEstado.getText().trim().isEmpty()){
				lbl8.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtCodigoPostal.getText().trim().isEmpty()){
				lbl9.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}
			if(txtTelefono.getText().trim().isEmpty() ){
				lbl10.setText("*");
				lblMensaje.setText("Faltan datos por llenar");
			}else{
				c = new ProveedorContacto();
				c.setNombreEmpresa(new SimpleStringProperty(txtEmpresa.getText()));
				c.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				c.setNumeroInterior(new SimpleStringProperty(txtNumeroInterior.getText()));
				c.setNumeroExterior(new SimpleStringProperty(txtNumeroExterior.getText()));
				c.setCalle(new SimpleStringProperty(txtCalle.getText()));
				c.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				c.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				c.setEstado(new SimpleStringProperty(txtEstado.getText()));
				c.setCodigoPostal(new SimpleStringProperty(txtCodigoPostal.getText()));
				c.setTelefonoEmpresa(new SimpleStringProperty(txtTelefono.getText()));
			}
			if(c. insertp()){
				lblMensaje.setText("Datos insertados con éxito");
				//cbEmpresa.setItems(p.getCompany());
				llenarTableView(true);
			}else{
				lblMensaje.setText("Se ha producido un error.");
			}
			
		} catch (Exception e){
			
		}
	}
	
	@FXML public void click_actualizarp(){
		try{
			if(txtEmpresa.getText().trim().isEmpty() ||
					txtDomicilio.getText().trim().isEmpty() ||
					txtNumeroInterior.getText().trim().isEmpty() ||
					txtNumeroExterior.getText().trim().isEmpty() ||
					txtCalle.getText().trim().isEmpty() ||
					txtLocalidad.getText().trim().isEmpty() ||
					txtCiudad.getText().trim().isEmpty() ||
					txtEstado.getText().trim().isEmpty() ||
					txtCodigoPostal.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() );
			else
				c = new ProveedorContacto();
				c.setIdproveedor(new SimpleIntegerProperty(getIde()));
				c.setNombreEmpresa(new SimpleStringProperty(txtEmpresa.getText()));
				c.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				c.setNumeroInterior(new SimpleStringProperty(txtNumeroInterior.getText()));
				c.setNumeroExterior(new SimpleStringProperty(txtNumeroExterior.getText()));
				c.setCalle(new SimpleStringProperty(txtCalle.getText()));
				c.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				c.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				c.setEstado(new SimpleStringProperty(txtEstado.getText()));
				c.setCodigoPostal(new SimpleStringProperty(txtCodigoPostal.getText()));
				c.setTelefonoEmpresa(new SimpleStringProperty(txtTelefono.getText()));
				if(c.actualizarp()){
					lblMensaje.setText("Datos insertados con éxito");
					//cbEmpresa.setItems(p.getCompany());
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se ha producido un problema.");
		} catch (Exception e){
			
		}
	}
	
	//Método para eliminar el registro seleccionado en el TableView.
	@FXML public void click_eliminarp(){
		try{
			if(txtEmpresa.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar la empresa que desea eliminar");
			else
				if(c.eliminarp()==true){
					c = new ProveedorContacto();
					c.setIdProveedorContacto(new SimpleIntegerProperty(getIdc()));
					llenarTableView(true);
					limpiar();
					lblMensaje.setText("Registro dado de baja.");
				}
				else {
					lblMensaje.setText("Se ha presentado un error.");
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
		ckbInactivos.setSelected(false);
		llenarTableView(true);
	}
	
	public void limpiar(){
		txtEmpresa.clear();
		txtDomicilio.clear();
		txtNumeroInterior.clear();
		txtNumeroInterior.clear();
		txtCalle.clear();
		txtLocalidad.clear();
		txtCiudad.clear();
		txtEstado.clear();
		txtCodigoPostal.clear();
		txtTelefono.clear();
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
	}
	
	@FXML public void click_inactivos(){
		if(ckbInactivos.isSelected()){
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

}
