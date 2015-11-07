package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import controlador.Errores;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Brand;
import modelo.Employee;
import modelo.Motocicleta;
import modelo.RestaurarMotocicleta;
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

public class CRestaurarMotocicleta implements Initializable, IControladorVentanas{

	private Errores er;
	private ControladordeVentanas ventanas;
	private ControladorVentana ventana;
	private RestaurarMotocicleta rm;
	private Employee em;
	private Brand br;
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private int filasXPagina;
	private ObservableList<RestaurarMotocicleta> datos;
	private FilteredList<RestaurarMotocicleta> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	@FXML TextField txtBuscar;
	@FXML Button btnRestaurar, btnBuscar;
	@FXML TableView<RestaurarMotocicleta> tvPMotocicleta;
	@FXML TableColumn<RestaurarMotocicleta, String> tcMarca, tcModelo, tcColor, tcFecha, tcEmpleado;
	
	public CRestaurarMotocicleta(){
		er = new Errores();
		rm = new RestaurarMotocicleta();
		em = new Employee();
		br = new Brand();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	public void initialize(URL location, ResourceBundle resources) {
			//try {
				llenarTableView(true);
				btnRestaurar.setDisable(true);
			//} catch (SQLException e) {
				//e.printStackTrace();
			//}
	}

	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvPMotocicleta.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvPMotocicleta.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvPMotocicleta);
	}
	
	public void llenarTableView(Boolean estatus){
		tcMarca.setCellValueFactory(new PropertyValueFactory<RestaurarMotocicleta, String>("br"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<RestaurarMotocicleta, String>("modelo"));
		tcColor.setCellValueFactory(new PropertyValueFactory<RestaurarMotocicleta, String>("color"));
		tcFecha.setCellValueFactory(new PropertyValueFactory<RestaurarMotocicleta, String>("fecha"));
		tcEmpleado.setCellValueFactory(new PropertyValueFactory<RestaurarMotocicleta, String>("em"));
		try {
			//Refrescar y volver a cargar los datos en el TableView
			datos=rm.getMotocicleta(estatus);
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
	@FXML public void click_TablaMotos(){
		if(tvPMotocicleta.getSelectionModel().getSelectedItem()!=null){
			rm =  tvPMotocicleta.getSelectionModel().getSelectedItem();
			id = rm.getIdmotocicleta();
			txtBuscar.setText(rm.getModelo().toString());
			btnRestaurar.setDisable(false);
		}
	}
	
	@FXML public void click_restaurarM(){
		try {
			if(txtBuscar.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar la motocicleta que desea restaurar.");
			else
				
				if(rm.restaurar()==true){
					rm = new RestaurarMotocicleta();
			    	rm.setIdmotocicleta(new SimpleIntegerProperty(getId()));
					llenarTableView(true);
					lblMensaje.setText("Registro restaurado.");
				}
				else{
					lblMensaje.setText("Se ha presentado un error.r");
				}
					
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
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
	 			datosBusqueda.setPredicate(Motocicleta->Motocicleta.getModelo().toLowerCase().
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
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}