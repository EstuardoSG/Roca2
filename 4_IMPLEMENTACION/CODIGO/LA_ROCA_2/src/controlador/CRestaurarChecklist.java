package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.RestaurarChecklist;
//import modelo.Check;
import modelo.Customer;
import modelo.Motorcycle;
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

public class CRestaurarChecklist implements Initializable, IControladorVentanas {

	private RestaurarChecklist ch;
	private Customer cus;
	private Motorcycle mc;
	
	private ControladordeVentanas ventanas;
	
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private int filasXPagina;
	private ObservableList<RestaurarChecklist> datos;
	private FilteredList<RestaurarChecklist> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	@FXML TextField txtBuscar;
	@FXML Button btnRestaurar;
	@FXML TableView<RestaurarChecklist> tvPChecklist;
	@FXML TableColumn<RestaurarChecklist, String> tcCliente, tcMotocicleta, tcFallas, tcDiagnostico;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			llenarTableView(true);
	}
	
	public CRestaurarChecklist(){
		ch = new RestaurarChecklist();
		cus = new Customer();
		mc = new Motorcycle();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvPChecklist.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvPChecklist.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvPChecklist);
	}
	
	public void llenarTableView(Boolean estatus){

		tcCliente.setCellValueFactory(new PropertyValueFactory<RestaurarChecklist, String>("cus"));
		tcMotocicleta.setCellValueFactory(new PropertyValueFactory<RestaurarChecklist, String>("mc"));
		tcFallas.setCellValueFactory(new PropertyValueFactory<RestaurarChecklist, String>("fallas"));
		tcDiagnostico.setCellValueFactory(new PropertyValueFactory<RestaurarChecklist, String>("diagnostico"));
		try {
			//Refrescar y volver a cargar los datos en el TableView
			datos=ch.getCheck(estatus);
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
	@FXML public void click_TablaCheckList(){
		if(tvPChecklist.getSelectionModel().getSelectedItem()!=null){
			ch =  tvPChecklist.getSelectionModel().getSelectedItem();
			id = ch.getIdchecklist();
			txtBuscar.setText(ch.getCus().toString());
		}
	}

	@FXML public void click_restaurarC(){
		try {
			if(txtBuscar.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar el registro que desea restaurar");
			else
				if(ch.restaurar()==true){
					ch = new RestaurarChecklist();
			    	ch.setIdchecklist(new SimpleIntegerProperty(getId()));
					llenarTableView(true);
					lblMensaje.setText("Registro restaurado.");
				}
				else{
					lblMensaje.setText("Se ha presentado un error.");
				}
					
		} catch (Exception e) {
			e.printStackTrace();
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
 			datosBusqueda.setPredicate(Customer->Customer.cus.getNombre1().toLowerCase().
 					contains(txtBuscar.getText().toLowerCase()));
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
		}
	}
		
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
