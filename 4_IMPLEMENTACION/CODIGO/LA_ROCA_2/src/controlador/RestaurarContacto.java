package controlador;

import modelo.Company;
import modelo.RestaurarC;

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
//import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class RestaurarContacto implements Initializable, IControladorVentanas {
	private Company p;
	private RestaurarC c;
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

	//@FXML ComboBox<Company> cbEmpresa;
	@FXML TableColumn<RestaurarC, String> tcEmpresa, tcNombre, tcAp, tcAm, tcCelular;
	@FXML TableView<RestaurarC> tvPC;
	@FXML TextField txtBuscar;
	@FXML Label lblMensaje, lblRegistros;
	@FXML CheckBox ckbEliminados;
	//@FXML ComboBox<Company> cbEmpresa;


	// #region Variables_Paginacion
	private int filasXPagina;
	private ObservableList<RestaurarC> datos;
	private FilteredList<RestaurarC> datosB;
	@FXML Pagination paginador;
	// #endregion
	
	// #region Constructor
	public RestaurarContacto() {
		p = new Company();
		c = new RestaurarC();
		filasXPagina = 10;
		datos = FXCollections.observableArrayList();
	}
	// #endregion
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//try{
			//cbEmpresa.setItems(p.getCompany());
			//Enlazar Columnas
			tcEmpresa.setCellValueFactory(new PropertyValueFactory<RestaurarC, String>("p"));
			tcNombre.setCellValueFactory(new PropertyValueFactory<RestaurarC, String>("nombre"));
			tcAp.setCellValueFactory(new PropertyValueFactory<RestaurarC, String>("apellidoPaterno"));
			tcAm.setCellValueFactory(new PropertyValueFactory<RestaurarC, String>("apellidoMaterno"));
			tcCelular.setCellValueFactory(new PropertyValueFactory<RestaurarC, String>("celular"));
			llenarTableView(true);
		//}catch (SQLException e){
			//e.printStackTrace();
		//};
	}
	
	// #region metodos_paginacion
	private Node createPage(int pageIndex){
		if(filasXPagina>0){
			int fromIndex = pageIndex * filasXPagina;
			int toIndex = Math.min(fromIndex + filasXPagina, datosB.size());
			tvPC.setItems(FXCollections.observableArrayList(datosB.subList(fromIndex, toIndex)));
		}
		else {
			tvPC.setItems(null);
			paginador.setPageCount(0);
		}
		return new BorderPane(tvPC);
	}
	// #endregion
	
	//M�todo para llenar el tableView
	public void llenarTableView(Boolean estatus){
		try{
			datos= c.getRestaurarC(estatus);
			datosB = new FilteredList<>(datos);
			paginador.setPageCount(datosB.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		}catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
	}
	
	
	//M�todo para eliminar el registro seleccionado en el TableView.
	@FXML public void click_restaurarC(){
		try{
			if(txtBuscar.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar el contacto que desea restaurar.");
			else
				if(c.restaurarc()==true){
					c = new RestaurarC();
					c.setIdProveedorContacto(new SimpleIntegerProperty(getIdc()));
					llenarTableView(true);
					lblMensaje.setText("Contacto restaurado.");
				}
				else {
					lblMensaje.setText("Se ha presentado un errorcon el servido.");
				}
		}catch (Exception e){
			e.printStackTrace();
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
				datosB.setPredicate(RestaurarC -> RestaurarC.getNombre().toLowerCase().
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
	
	@FXML public void click_TableView(){
		if(tvPC.getSelectionModel().getSelectedItem()!=null){
			c = tvPC.getSelectionModel().getSelectedItem();
			//TextField
			idc = c.getIdProveedorContacto();
			ide = c. getIdproveedor();
			txtBuscar.setText(c.getNombre());
			//ComboBox
		}
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}