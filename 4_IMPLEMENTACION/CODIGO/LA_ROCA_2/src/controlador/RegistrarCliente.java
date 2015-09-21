package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Clientes;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class RegistrarCliente implements Initializable, IControladorVentanas {
	
	private Clientes c;
	private Errores er;
	ControladordeVentanas ventanas;
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	private int filasXPagina;
	private ObservableList<Clientes> datos;
	private FilteredList<Clientes> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	
	@FXML TextField txtNombre, txtNombre2, txtApellidoPaterno, txtApellidoMaterno, txtTelefono, txtTelefono2, txtCelular, txtCelular2,
	txtDomicilio, txtNumeroInterior, txtNumeroExterior, txtCalle, txtLocalidad, txtCiudad, txtEstado, txtCodigoPostal, 
	txtNumerodeCliente, txtFiltro;
	
	@FXML Button btnGuardar, btnEditar, btnEliminar, btnBuscar, btnNuevo;
	
	@FXML CheckBox chkClienteFrecuente, chkClientesEliminados;
	
	@FXML TableView<Clientes> tvClientes;
	@FXML TableColumn<Clientes, String> tcNombre, tcApellidoPaterno, tcApellidoMaterno, tcTelefono, tcCelular;
		
	
	public RegistrarCliente(){
		c = new Clientes();
		er = new Errores();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			btnEditar.setDisable(true);
			btnEliminar.setDisable(true);
			llenarTableView(true);
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		
	}
	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvClientes.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvClientes.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvClientes);
	}
	
	public void llenarTableView(Boolean estatus){
		tcNombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre1"));
		tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidopaterno"));
		tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidomaterno"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono1"));
		tcCelular.setCellValueFactory(new PropertyValueFactory<Clientes, String>("celular1"));
		try {
			//Refrescar y volver a cargar los datos en el TableView
			datos=c.getCliente(estatus);
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
		if(tvClientes.getSelectionModel().getSelectedItem()!=null){
			c = tvClientes.getSelectionModel().getSelectedItem();
			id = c.getIdcliente();
			txtNombre.setText(c.getNombre1().toString());
			txtNombre2.setText(c.getNombre2().toString());
			txtApellidoPaterno.setText(c.getApellidopaterno().toString());
			txtApellidoMaterno.setText(c.getApellidomaterno().toString());
			txtTelefono.setText(c.getTelefono1().toString());
			txtTelefono2.setText(c.getTelefono2().toString());
			txtCelular.setText(c.getCelular1().toString());
			txtCelular2.setText(c.getCelular2().toString());
			txtDomicilio.setText(c.getDomicilio().toString());
			txtNumeroInterior.setText(c.getNumerointerior().toString());
			txtNumeroExterior.setText(c.getNumeroexterior().toString());
			txtCalle.setText(c.getCalle().toString());
			txtLocalidad.setText(c.getLocalidad().toString());
			txtCiudad.setText(c.getCiudad().toString());
			txtEstado.setText(c.getEstado().toString());
			txtCodigoPostal.setText(c.getCodigopostal().toString());
			txtNumerodeCliente.setText(c.getNumerocliente().toString());
			
			if(c.getActivo().equals(true))
				chkClienteFrecuente.setSelected(true);
			else
				chkClienteFrecuente.setSelected(false);
		}
		btnGuardar.setDisable(true);
		btnEditar.setDisable(false);
		btnEliminar.setDisable(false);
	}
	public void limpiar(){
		txtNombre.setText("");
		txtNombre2.setText("");
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtTelefono.setText("");
		txtTelefono2.setText("");
		txtCelular.setText("");
		txtCelular2.setText("");
		txtDomicilio.setText("");
		txtNumeroInterior.setText("");
		txtNumeroExterior.setText("");
		txtCalle.setText("");
		txtLocalidad.setText("");
		txtCiudad.setText("");
		txtEstado.setText("");
		txtCodigoPostal.setText("");
		txtNumerodeCliente.setText("");
		txtFiltro.setText("");
		chkClienteFrecuente.setSelected(false);
	}
	
	@FXML public void click_inactivos(){
		if(chkClientesEliminados.isSelected())
			llenarTableView(false);
		else
			llenarTableView(true);
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
	 			datosBusqueda.setPredicate(Clientes->Clientes.getNombre1().toLowerCase().
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
	
	@FXML public void guardar(){
		try {
			if(txtNombre.getText().trim().isEmpty() ||
					txtNombre2.getText().trim().isEmpty() ||
					txtApellidoPaterno.getText().trim().isEmpty() ||
					txtApellidoMaterno.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() ||
					txtTelefono2.getText().trim().isEmpty() ||
					txtCelular.getText().trim().isEmpty() ||
					txtCelular2.getText().trim().isEmpty() ||
					txtDomicilio.getText().trim().isEmpty()||
					txtNumeroInterior.getText().trim().isEmpty()||
					txtNumeroExterior.getText().trim().isEmpty()||
					txtCalle.getText().trim().isEmpty()||
					txtLocalidad.getText().trim().isEmpty()||
					txtCiudad.getText().trim().isEmpty()||
					txtEstado.getText().trim().isEmpty()||
					txtCodigoPostal.getText().trim().isEmpty()||
					txtNumerodeCliente.getText().trim().isEmpty()){
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				c = new Clientes();
				c.setNombre1(new SimpleStringProperty(txtNombre.getText()));
				c.setNombre2(new SimpleStringProperty(txtNombre2.getText()));
				c.setApellidopaterno(new SimpleStringProperty(txtApellidoPaterno.getText()));
				c.setApellidomaterno(new SimpleStringProperty(txtApellidoMaterno.getText()));
				c.setTelefono1(new SimpleStringProperty(txtTelefono.getText()));
				c.setTelefono2(new SimpleStringProperty(txtTelefono2.getText()));
				c.setCelular1(new SimpleStringProperty(txtCelular.getText()));
				c.setCelular2(new SimpleStringProperty(txtCelular2.getText()));
				c.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				c.setNumerointerior(new SimpleStringProperty(txtNumeroInterior.getText()));
				c.setNumeroexterior(new SimpleStringProperty(txtNumeroExterior.getText()));
				c.setCalle(new SimpleStringProperty(txtCalle.getText()));
				c.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				c.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				c.setEstado(new SimpleStringProperty(txtEstado.getText()));
				c.setCodigopostal(new SimpleStringProperty(txtCodigoPostal.getText()));
				c.setNumerocliente(new SimpleIntegerProperty(Integer.valueOf(txtNumerodeCliente.getText())));
				String especial ="{";
				if(chkClienteFrecuente.isSelected())
					especial+=chkClienteFrecuente.getText() + ": ";
				especial += "}";
				if(c.guardar()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
					limpiar();
				}
				else
					lblMensaje.setText("Se producido un problema en el servidor.");
				
			}	
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void editar(){
		try {
			if(txtNombre.getText().trim().isEmpty() ||
					txtNombre2.getText().trim().isEmpty() ||
					txtApellidoPaterno.getText().trim().isEmpty() ||
					txtApellidoMaterno.getText().trim().isEmpty() ||
					txtTelefono.getText().trim().isEmpty() ||
					txtTelefono2.getText().trim().isEmpty() ||
					txtCelular.getText().trim().isEmpty() ||
					txtCelular2.getText().trim().isEmpty() ||
					txtDomicilio.getText().trim().isEmpty()||
					txtNumeroInterior.getText().trim().isEmpty()||
					txtNumeroExterior.getText().trim().isEmpty()||
					txtCalle.getText().trim().isEmpty()||
					txtLocalidad.getText().trim().isEmpty()||
					txtCiudad.getText().trim().isEmpty()||
					txtEstado.getText().trim().isEmpty()||
					txtCodigoPostal.getText().trim().isEmpty()||
					txtNumerodeCliente.getText().trim().isEmpty()){
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				c = new Clientes();
				c.setIdcliente(new SimpleIntegerProperty(getId()));
				c.setNombre1(new SimpleStringProperty(txtNombre.getText()));
				c.setNombre2(new SimpleStringProperty(txtNombre2.getText()));
				c.setApellidopaterno(new SimpleStringProperty(txtApellidoPaterno.getText()));
				c.setApellidomaterno(new SimpleStringProperty(txtApellidoMaterno.getText()));
				c.setTelefono1(new SimpleStringProperty(txtTelefono.getText()));
				c.setTelefono2(new SimpleStringProperty(txtTelefono2.getText()));
				c.setCelular1(new SimpleStringProperty(txtCelular.getText()));
				c.setCelular2(new SimpleStringProperty(txtCelular2.getText()));
				c.setDomicilio(new SimpleStringProperty(txtDomicilio.getText()));
				c.setNumerointerior(new SimpleStringProperty(txtNumeroInterior.getText()));
				c.setNumeroexterior(new SimpleStringProperty(txtNumeroExterior.getText()));
				c.setCalle(new SimpleStringProperty(txtCalle.getText()));
				c.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
				c.setCiudad(new SimpleStringProperty(txtCiudad.getText()));
				c.setEstado(new SimpleStringProperty(txtEstado.getText()));
				c.setCodigopostal(new SimpleStringProperty(txtCodigoPostal.getText()));
				c.setNumerocliente(new SimpleIntegerProperty(Integer.valueOf(txtNumerodeCliente.getText())));
		
			
					
				if(c.actualizar()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
					limpiar();
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
			if(c.getIdcliente().equals(""))
				lblMensaje.setText("Debe seleccionar el film a dar de baja");
			else
				c = new Clientes();
	    		c.setIdcliente(new SimpleIntegerProperty(getId()));
				if(c.eliminar()==true){
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
		btnGuardar.setDisable(false);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
	}
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
