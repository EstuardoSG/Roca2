package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Check;
import modelo.Customer;
import modelo.Motorcycle;
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

public class CheckList implements Initializable, IControladorVentanas {

	private Check ch;
	private Customer cus;
	private Motorcycle mc;
	private Errores er;
	private Reportes re;
	private ControladordeVentanas ventanas;
	
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private int filasXPagina;
	private ObservableList<Check> datos;
	private FilteredList<Check> datosBusqueda;
	@FXML Pagination paginador;
	
	@FXML Label lblRegistros, lblMensaje;
	@FXML TextField txtGasolina, txtLuces, txtEspejoIzquierdo, txtEspejoDerecho, txtLlantaDelantera, txtLlantaTrasera, txtFiltro, txtFecha;
	@FXML TextArea txtaFallas, txtaDiagnostico;
	@FXML Button btnGuardar, btnEditar, btnEliminar, btnBuscar, btnAgregarC, btnReporte;
	@FXML ComboBox<Customer> cbCliente;
	@FXML ComboBox<Motorcycle> cbMotocicleta;
	@FXML CheckBox chkListadeComprobacionesEliminadas, chkActivo, chkEliminados;
	@FXML TableView<Check> tvCheckList;
	@FXML TableColumn<Check, String> tcCliente, tcMotocicleta, tcFallas, tcDiagnostico;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			cbCliente.setItems(cus.getCustomer());
			cbMotocicleta.setItems(mc.getMotorCycle());
			llenarTableView(true);
		} catch (SQLException e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	public CheckList(){
		ch = new Check();
		cus = new Customer();
		mc = new Motorcycle();
		er = new Errores();
		datos = FXCollections.observableArrayList();
		filasXPagina=10;
		re = new Reportes();
	}
	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tvCheckList.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tvCheckList.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tvCheckList);
	}
	
	public void llenarTableView(Boolean estatus){

		tcCliente.setCellValueFactory(new PropertyValueFactory<Check, String>("cus"));
		tcMotocicleta.setCellValueFactory(new PropertyValueFactory<Check, String>("mc"));
		tcFallas.setCellValueFactory(new PropertyValueFactory<Check, String>("fallas"));
		tcDiagnostico.setCellValueFactory(new PropertyValueFactory<Check, String>("diagnostico"));
		try {
			//Refrescar y volver a cargar los datos en el TableView
			datos=ch.getCheck(estatus);
			datosBusqueda = new FilteredList<>(datos);
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
			
	}
	
	@FXML public void click_TablaCheckList(){
		if(tvCheckList.getSelectionModel().getSelectedItem()!=null){
			ch =  tvCheckList.getSelectionModel().getSelectedItem();
			id = ch.getIdchecklist();
			txtGasolina.setText(ch.getGasolina().toString());
			txtLuces.setText(ch.getLuces().toString());
			txtEspejoIzquierdo.setText(ch.getEspejoizquierdo().toString());
			txtEspejoDerecho.setText(ch.getEspejoderecho().toString());
			txtLlantaDelantera.setText(ch.getLlantadelantera().toString());
			txtLlantaTrasera.setText(ch.getLlantatrasera().toString());
			txtaFallas.setText(ch.getFallas().toString());
			txtaDiagnostico.setText(ch.getDiagnostico().toString());
			txtFecha.setText(ch.getFecha());
			
			 cbCliente.getSelectionModel().select(ch.getCus());
			 cbMotocicleta.getSelectionModel().select(ch.getMc());
		}
	}

	
	@FXML public void guardar(){
		try {
			if(txtGasolina.getText().trim().isEmpty() ||
					txtLuces.getText().trim().isEmpty() ||
					txtEspejoIzquierdo.getText().trim().isEmpty() ||
					txtEspejoDerecho.getText().trim().isEmpty() ||
					txtLlantaDelantera.getText().trim().isEmpty() ||
					txtLlantaTrasera.getText().trim().isEmpty() ||
					txtaFallas.getText().trim().isEmpty()){
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				ch = new Check();
				ch.setCus(cbCliente.getSelectionModel().getSelectedItem());
				ch.setMc(cbMotocicleta.getSelectionModel().getSelectedItem());
				ch.setGasolina(new SimpleStringProperty(txtGasolina.getText()));
				ch.setLuces(new SimpleStringProperty(txtLuces.getText()));
				ch.setEspejoizquierdo(new SimpleStringProperty(txtEspejoIzquierdo.getText()));
				ch.setEspejoderecho(new SimpleStringProperty(txtEspejoDerecho.getText()));
				ch.setLlantadelantera(new SimpleStringProperty(txtLlantaDelantera.getText()));
				ch.setLlantatrasera(new SimpleStringProperty(txtLlantaTrasera.getText()));
				ch.setFallas(new SimpleStringProperty(txtaFallas.getText()));
				ch.setDiagnostico(new SimpleStringProperty(txtaDiagnostico.getText()));
				if(ch.guardar()){
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
	@FXML public void editar(){
		try {
			if(txtGasolina.getText().trim().isEmpty() ||
					txtLuces.getText().trim().isEmpty() ||
					txtEspejoIzquierdo.getText().trim().isEmpty() ||
					txtEspejoDerecho.getText().trim().isEmpty() ||
					txtLlantaDelantera.getText().trim().isEmpty() ||
					txtLlantaTrasera.getText().trim().isEmpty() ||
					txtaFallas.getText().trim().isEmpty()){
				lblMensaje.setText("Faltan datos por llenar");
			}
			else{
				ch = new Check();
				ch.setIdchecklist(new SimpleIntegerProperty(getId()));
				ch.setCus(cbCliente.getSelectionModel().getSelectedItem());
				ch.setMc(cbMotocicleta.getSelectionModel().getSelectedItem());
				ch.setGasolina(new SimpleStringProperty(txtGasolina.getText()));
				ch.setLuces(new SimpleStringProperty(txtLuces.getText()));
				ch.setEspejoizquierdo(new SimpleStringProperty(txtEspejoIzquierdo.getText()));
				ch.setEspejoderecho(new SimpleStringProperty(txtEspejoDerecho.getText()));
				ch.setLlantadelantera(new SimpleStringProperty(txtLlantaDelantera.getText()));
				ch.setLlantatrasera(new SimpleStringProperty(txtLlantaTrasera.getText()));
				ch.setFallas(new SimpleStringProperty(txtaFallas.getText()));
				ch.setDiagnostico(new SimpleStringProperty(txtaDiagnostico.getText()));
				if(ch.actualizar()){
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
			if(txtGasolina.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar el un registro a dar de baja");
			else
				if(ch.eliminar()==true){
					ch = new Check();
			    	ch.setIdchecklist(new SimpleIntegerProperty(getId()));
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

	public void limpiar(){
		txtGasolina.setText("");
		txtLuces.setText("");
		txtEspejoIzquierdo.setText("");
		txtEspejoDerecho.setText("");
		txtLlantaDelantera.setText("");
		txtLlantaTrasera.setText("");
		txtFecha.setText("");
		txtaFallas.setText("");
		txtaDiagnostico.setText("");
		cbCliente.getSelectionModel().select(-1);
		cbMotocicleta.getSelectionModel().select(-1);
			
	}
	
	@FXML public void click_inactivos(){
		if(chkListadeComprobacionesEliminadas.isSelected())
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
 			datosBusqueda.setPredicate(Customer->Customer.cus.getNombre1().toLowerCase().
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
		
	@FXML public void clickCliente(){
	/*	try {
			ventanas.modal("../vista/fxml/RegistrarCliente.fxml", "Clientes");
			cbCliente.setItems(cus.getCustomer());
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	@FXML public void reporte(){
		if(chkActivo.isSelected()){
			re.cargarReporte("src/vista/reportes/CheckListActivo.jrxml");
			re.mostrarReporte();
		}
		if(chkEliminados.isSelected()){
			re.cargarReporte("src/vista/reportes/CheckListInactivo.jrxml");
			re.mostrarReporte();
		}
		else{
			lblMensaje.setText("Selecciona una opción");
		}
		chkEliminados.setSelected(false);
		chkActivo.setSelected(false);
	}
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
