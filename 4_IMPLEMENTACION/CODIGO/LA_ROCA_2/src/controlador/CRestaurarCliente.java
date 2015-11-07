package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import controlador.Errores;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.RestaurarC;
import modelo.RestaurarCliente;
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

public class CRestaurarCliente<Clientes> implements Initializable, IControladorVentanas {
	
	private Errores er;
	private RestaurarCliente c;
	ControladordeVentanas ventanas;
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	private int filasXPagina;
	private ObservableList<RestaurarCliente> datos;
	private FilteredList<RestaurarCliente> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	
	@FXML TextField txtBuscar;
	
	@FXML Button btnRestaurar;
	
	@FXML TableView<RestaurarCliente> tvPCliente;
	@FXML TableColumn<RestaurarCliente, String> tcNombre, tcApellidoPaterno, tcApellidoMaterno, tcTelefono, tcCelular;
		
	
	public CRestaurarCliente(){
		er = new Errores();
		c = new RestaurarCliente();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			llenarTableView(true);
			btnRestaurar.setDisable(true);
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		
	}
	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvPCliente.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvPCliente.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvPCliente);
	}
	
	public void llenarTableView(Boolean estatus){
		tcNombre.setCellValueFactory(new PropertyValueFactory<RestaurarCliente, String>("nombre1"));
		tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<RestaurarCliente, String>("apellidopaterno"));
		tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory<RestaurarCliente, String>("apellidomaterno"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<RestaurarCliente, String>("telefono1"));
		tcCelular.setCellValueFactory(new PropertyValueFactory<RestaurarCliente, String>("celular1"));
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
		if(tvPCliente.getSelectionModel().getSelectedItem()!=null){
			c = tvPCliente.getSelectionModel().getSelectedItem();
			id = c.getIdcliente();
			txtBuscar.setText(c.getNombre1().toString());
			btnRestaurar.setDisable(false);
		}
	}

	

	
	@FXML public void buscarTexto(){
 		if(txtBuscar.getText().trim().isEmpty()){
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
	 					contains(txtBuscar.getText().toLowerCase()));
	 			if(datosBusqueda.size()<10)
	 				filasXPagina= datosBusqueda.size();
	 			else
	 				filasXPagina=10;
	 			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensaje.setText("Se encontraron " + datosBusqueda.size() + " coincidencias.");
 			}
 			catch(Exception e){
 				er.printLog(e.getMessage(), this.getClass().toString());
 				//Enviar mensaje
 				lblMensaje.setText("No se encontraron resultados");
 				filasXPagina=0;
 				paginador.setPageCount(filasXPagina); 				
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
 			}
 		}
 	}
	

	
	@FXML public void click_restaurarC(){
		try{
			if(txtBuscar.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar el contacto que desea restaurar.");
			else
				if(c.rcliente()==true){
					c = new RestaurarCliente();
					c.setIdcliente(new SimpleIntegerProperty(getId()));
					llenarTableView(true);
					lblMensaje.setText("Contacto restaurado.");
				}
				else {
					lblMensaje.setText("Se ha presentado un errorcon el servido.");
				}
		}catch (Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
