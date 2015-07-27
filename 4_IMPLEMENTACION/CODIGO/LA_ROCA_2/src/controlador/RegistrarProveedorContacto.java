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
	@FXML TextField txtNEmpresa, txtDomicilio, txtNI, txtNE, txtCalle, txtLocalidad, txtCiudad,
	txtEstado, txtCP, txtTelefono, txtNombre, txtAP, txtAM, txtCelular, txtCorreo, txtBuscar;
	@FXML Label lblMensaje, lblRegistros;
	@FXML CheckBox ckbEliminados;

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
			tcEmpresa.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("p"));
			tcNombre.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("nombre"));
			tcAP.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("apellidoPaterno"));
			tcAM.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("apellidoMaterno"));
			tcCelular.setCellValueFactory(new PropertyValueFactory<ProveedorContacto, String>("celular"));
			llenarTableView(true);
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
		if(tvProveedor.getSelectionModel().getSelectedItem()!=null){
			c = tvProveedor.getSelectionModel().getSelectedItem();
			//TextField
			idc = c.getIdProveedorContacto();
			ide = c. getIdproveedor();
			txtNEmpresa.setText(c.getNombreEmpresa());
			txtDomicilio.setText(c.getDomicilio());
			txtNI.setText(c.getNumeroInterior());
			txtNE.setText(c.getNumeroExterior());
			txtCalle.setText(c.getCalle());
			txtLocalidad.setText(c.getLocalidad());
			txtCiudad.setText(c.getCiudad());
			txtEstado.setText(c.getEstado());
			txtCP.setText(c.getCodigoPostal());
			txtTelefono.setText(c.getTelefonoEmpresa());
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
		try{
			if(txtNEmpresa.getText().trim().isEmpty() ||
					txtDomicilio.getText().trim().isEmpty() ||
					txtNI.getText().trim().isEmpty() ||
					txtNE.getText().trim().isEmpty() ||
					txtCalle.getText().trim().isEmpty() ||
					txtLocalidad.getText().trim().isEmpty() ||
					txtCiudad.getText().trim().isEmpty() ||
					txtEstado.getText().trim().isEmpty() ||
					txtCP.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() );
			else
				c = new ProveedorContacto();
				c.setNombreEmpresa(new SimpleStringProperty(txtNEmpresa.getText()));
				c.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				c.setNumeroInterior(new SimpleStringProperty(txtNI.getText()));
				c.setNumeroExterior(new SimpleStringProperty(txtNE.getText()));
				c.setCalle(new SimpleStringProperty(txtCalle.getText()));
				c.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				c.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				c.setEstado(new SimpleStringProperty(txtEstado.getText()));
				c.setCodigoPostal(new SimpleStringProperty(txtCP.getText()));
				c.setTelefonoEmpresa(new SimpleStringProperty(txtTelefono.getText()));
				if(c. insertp()){
					lblMensaje.setText("Datos insertados con éxito");
					cbEmpresa.setItems(p.getCompany());
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se producio un problema en el servidor.");
			
			if(txtNombre.getText().trim().isEmpty() ||
					txtAP.getText().trim().isEmpty() ||
					txtAM.getText().trim().isEmpty() ||
					txtCelular.getText().trim().isEmpty() ||
					txtCorreo.getText().trim().isEmpty()){
			}
			else {
				c = new ProveedorContacto();
				c.setNombre(new SimpleStringProperty(txtNombre.getText()));
				c.setApellidoPaterno(new SimpleStringProperty(txtAP.getText()));
				c.setApellidoMaterno(new SimpleStringProperty(txtAM.getText()));
				c.setCelular(new SimpleStringProperty(txtCelular.getText()));
				c.setCorreo(new SimpleStringProperty(txtCorreo.getText()));
				c.setP(cbEmpresa.getSelectionModel().getSelectedItem());
				if(c.insertc()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se producio un problema en el servidor.");
			}
		} catch (Exception e){
			
		}
	}
	
	@FXML public void click_actualizarp(){
		try{
			if(txtNEmpresa.getText().trim().isEmpty() ||
					txtDomicilio.getText().trim().isEmpty() ||
					txtNI.getText().trim().isEmpty() ||
					txtNE.getText().trim().isEmpty() ||
					txtCalle.getText().trim().isEmpty() ||
					txtLocalidad.getText().trim().isEmpty() ||
					txtCiudad.getText().trim().isEmpty() ||
					txtEstado.getText().trim().isEmpty() ||
					txtCP.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() );
			else
				c = new ProveedorContacto();
				c.setIdproveedor(new SimpleIntegerProperty(getIde()));
				c.setNombreEmpresa(new SimpleStringProperty(txtNEmpresa.getText()));
				c.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				c.setNumeroInterior(new SimpleStringProperty(txtNI.getText()));
				c.setNumeroExterior(new SimpleStringProperty(txtNE.getText()));
				c.setCalle(new SimpleStringProperty(txtCalle.getText()));
				c.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				c.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				c.setEstado(new SimpleStringProperty(txtEstado.getText()));
				c.setCodigoPostal(new SimpleStringProperty(txtCP.getText()));
				c.setTelefonoEmpresa(new SimpleStringProperty(txtTelefono.getText()));
				if(c.actualizarp()){
					lblMensaje.setText("Datos insertados con éxito");
					cbEmpresa.setItems(p.getCompany());
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se producio un problema en el servidor.");
			
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
	
	public void limpiar(){
		txtNEmpresa.clear();
		txtDomicilio.clear();
		txtNI.clear();
		txtNE.clear();
		txtCalle.clear();
		txtLocalidad.clear();
		txtCiudad.clear();
		txtEstado.clear();
		txtCP.clear();
		txtTelefono.clear();
		txtNombre.clear();
		txtAP.clear();
		txtAM.clear();
		txtCelular.clear();
		txtCorreo.clear();
		cbEmpresa.getSelectionModel().select(-1);
	}
	
	@FXML public void click_inactivos(){
		if(ckbEliminados.isSelected())
			llenarTableView(false);
		else
			llenarTableView(true);
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
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
