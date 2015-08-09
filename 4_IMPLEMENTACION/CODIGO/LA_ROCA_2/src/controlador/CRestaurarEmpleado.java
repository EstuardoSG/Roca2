package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.Company;
import modelo.RestaurarC;
import modelo.RestaurarEmpleado;
import modelo.Empleado;
import modelo.Notificaciones;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class CRestaurarEmpleado implements Initializable, IControladorVentanas {

	private ControladordeVentanas ventanas;
	private ObservableList<RestaurarEmpleado> datosEmpleado;
	private int identificador;
	private RestaurarEmpleado re;
	private int filasXPagina;
	private FilteredList<RestaurarEmpleado> datosE;

	@FXML TextField txtBuscar;
	@FXML Button btnRestaurar;
	@FXML Label lblRegistros,lblMensaje;
	
	@FXML TableView<RestaurarEmpleado> tvPEmpleado;
	@FXML TableColumn<RestaurarEmpleado, String> tcNombre, tcApellidoPaterno, tcApellidoMaterno,tcCelular,tcTelefono;
	
	@FXML Pagination paginador;
	
	Notificaciones notificacion = new Notificaciones();
	
	public CRestaurarEmpleado(){
		re = new RestaurarEmpleado();
		datosEmpleado = FXCollections.observableArrayList();
	}
	
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

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			llenarTableView(true);
	}
	
	// #region metodos_paginacion
	private Node createPage(int pageIndex){
		if(filasXPagina>0){
			int fromIndex = pageIndex * filasXPagina;
			int toIndex = Math.min(fromIndex + filasXPagina, datosEmpleado.size());
			tvPEmpleado.setItems(FXCollections.observableArrayList(datosEmpleado.subList(fromIndex, toIndex)));
		}
		else {
			tvPEmpleado.setItems(null);
			paginador.setPageCount(0);
		}
		return new BorderPane(tvPEmpleado);
	}
	// #endregion
	
	//Método para llenar el tableView
	public void llenarTableView(Boolean estatus){
		tcNombre.setCellValueFactory(new PropertyValueFactory<RestaurarEmpleado, String>("nombre1"));
		tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<RestaurarEmpleado, String>("apellidopaterno"));
		tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory<RestaurarEmpleado, String>("apellidomaterno"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<RestaurarEmpleado, String>("telefono1"));
		tcCelular.setCellValueFactory(new PropertyValueFactory<RestaurarEmpleado, String>("celular1"));
		try{
			datosEmpleado= re.getEmpleado();
			datosE = new FilteredList<>(datosEmpleado);
			paginador.setPageCount(datosE.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datosEmpleado.size() + " registros encontrados.");
		}catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
	}
	
	
	//Método para eliminar el registro seleccionado en el TableView.
	@FXML public void click_restaurarE(){
		try{
			if(txtBuscar.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar el empleado que desea restaurar.");
			else
				if(re.restaurar()==true){
					re = new RestaurarEmpleado();
					re.setIdempleado(new SimpleIntegerProperty(getIdc()));
					llenarTableView(true);
					lblMensaje.setText("Empleado restaurado.");
				}
				else {
					lblMensaje.setText("Se ha presentado un error.");
				}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	@FXML public void buscarTexto(){
		if(txtBuscar.getText().trim().isEmpty()){
			datosE = new FilteredList<>(datosEmpleado);
			filasXPagina = 10;
			paginador.setPageCount(datosE.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblMensaje.setText(datosE.size() + " registros encontrados.");
		}
		else{
			try{
				datosE.setPredicate(RestaurarC -> RestaurarC.getNombre1().toLowerCase().
						contains(txtBuscar.getText().toLowerCase()));
				if(datosE.size()<10)
					filasXPagina = datosE.size();
				else
					filasXPagina=10;
				paginador.setPageCount(datosE.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensaje.setText("Se encontraron " + datosE.size() + " coincidencias.");
			} catch (Exception e){
				lblMensaje.setText("No se encontraron resultados");
				filasXPagina=0;
				paginador.setPageCount(filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			}
		}
	}
	
	@FXML public void click_TableView(){
		if(tvPEmpleado.getSelectionModel().getSelectedItem()!=null){
			re = tvPEmpleado.getSelectionModel().getSelectedItem();
			//TextField
			idc = re.getIdempleado();
			txtBuscar.setText(re.getNombre1());
			//ComboBox
		}
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
	
}