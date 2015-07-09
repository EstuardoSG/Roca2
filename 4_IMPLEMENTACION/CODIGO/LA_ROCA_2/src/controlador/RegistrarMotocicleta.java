package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import modelo.Brand;
import modelo.Employee;
import modelo.Motocicleta;
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

public class RegistrarMotocicleta implements Initializable{
	
	private Motocicleta m;
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
	private ObservableList<Motocicleta> datos;
	private FilteredList<Motocicleta> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	@FXML TextField txtModelo, txtMotor, txtColor, txtFiltro, txtFecha;
	@FXML TextArea txtaDescripciondelaMotocicleta;
	@FXML Button btnGuardar, btnEditar, btnEliminar, btnBuscar;
	@FXML ComboBox<Employee> cbEmpleado;
	@FXML ComboBox<Brand>  cbMarca;
	@FXML CheckBox chkPlacas, chkMotocicletasEliminadas;
	@FXML TableView<Motocicleta> tvMotocicletas;
	@FXML TableColumn<Motocicleta, String> tcMarca, tcModelo, tcColor, tcFecha, tcEmpleado;
	
	public RegistrarMotocicleta(){
		m = new Motocicleta();
		em = new Employee();
		br = new Brand();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
	}
	public void initialize(URL location, ResourceBundle resources) {
			try {
				cbMarca.setItems(br.getBrand());
				cbEmpleado.setItems(em.getEmployee());
				llenarTableView(true);
			} catch (SQLException e) {
				e.printStackTrace();
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
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
	@FXML public void click_TablaClientes(){
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
			if(m.getPlaca().equals(true))
				chkPlacas.setSelected(true);
			else
				chkPlacas.setSelected(false);
		}
	}
	@FXML public void guardar(){
		try {
			if(txtModelo.getText().trim().isEmpty() ||
					txtMotor.getText().trim().isEmpty() ||
					txtColor.getText().trim().isEmpty() ||
					txtaDescripciondelaMotocicleta.getText().trim().isEmpty()){
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
				
				if(m.guardar()){
					lblMensaje.setText("Datos insertados con éxito");
					llenarTableView(true);
				}
				else
					lblMensaje.setText("Se producido un problema en el servidor.");
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}
	
	public void limpiar(){
		txtModelo.setText("");
		txtColor.setText("");
		txtMotor.setText("");
		txtFecha.setText("");
		txtaDescripciondelaMotocicleta.setText("");
		cbEmpleado.getSelectionModel().select(-1);
		cbMarca.getSelectionModel().select(-1);
		chkPlacas.setSelected(false);
	}
	
	@FXML public void click_inactivos(){
		if(chkMotocicletasEliminadas.isSelected())
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
			}
		}
}
