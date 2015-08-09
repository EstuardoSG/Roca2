package controlador;

import modelo.Almacen;
import modelo.MarcaCombo;
import modelo.RestaurarAlmacen;

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

public class CRestaurarAlmacen implements Initializable, IControladorVentanas {
	private MarcaCombo mc;
	private RestaurarAlmacen ra;
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

	@FXML TableColumn<RestaurarAlmacen, String> tcNombre, tcModelo, tcIVA, tcMarca;
	@FXML TableColumn<RestaurarAlmacen, Float> tcPrecio1;
	@FXML TableView<RestaurarAlmacen> tvPAlmacen;
	@FXML TextField txtBuscar;
	@FXML Label lblMensaje, lblRegistros;


	// #region Variables_Paginacion
	private int filasXPagina;
	private ObservableList<RestaurarAlmacen> datos;
	private FilteredList<RestaurarAlmacen> datosB;
	@FXML Pagination paginador;
	// #endregion
	
	// #region Constructor
	public CRestaurarAlmacen() {
		mc = new MarcaCombo();
		ra = new RestaurarAlmacen();
		filasXPagina = 10;
		datos = FXCollections.observableArrayList();
	}
	// #endregion
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			llenarTableView(true);
	}
	
	// #region metodos_paginacion
	private Node createPage(int pageIndex){
		if(filasXPagina>0){
			int fromIndex = pageIndex * filasXPagina;
			int toIndex = Math.min(fromIndex + filasXPagina, datosB.size());
			tvPAlmacen.setItems(FXCollections.observableArrayList(datosB.subList(fromIndex, toIndex)));
		}
		else {
			tvPAlmacen.setItems(null);
			paginador.setPageCount(0);
		}
		return new BorderPane(tvPAlmacen);
	}
	// #endregion
	
	//Método para llenar el tableView
	public void llenarTableView(Boolean estatus){
		tcMarca.setCellValueFactory(new PropertyValueFactory<RestaurarAlmacen, String>("l"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<RestaurarAlmacen, String>("nombre"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<RestaurarAlmacen, String>("modelo"));
		tcPrecio1.setCellValueFactory(new PropertyValueFactory<RestaurarAlmacen, Float>("precio1"));
		tcIVA.setCellValueFactory(new PropertyValueFactory<RestaurarAlmacen, String>("iva"));
		try{
			datos= ra.getAlmacen(estatus);
			datosB = new FilteredList<>(datos);
			paginador.setPageCount(datosB.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistros.setText(datos.size() + " registros encontrados.");
		}catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Se ha producido un error al recuperar los datos.");
		}
	}
	
	
	//Método para eliminar el registro seleccionado en el TableView.
	@FXML public void click_restaurarA(){
		try{
			if(txtBuscar.getText().isEmpty())
				lblMensaje.setText("Debe seleccionar el contacto que desea restaurar.");
			else
				if(ra.restaurar()==true){
					ra = new RestaurarAlmacen();
					ra.setIdrefaccionalmacen(new SimpleIntegerProperty(getIdc()));
					llenarTableView(true);
					lblMensaje.setText("Contacto restaurado.");
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
		if(tvPAlmacen.getSelectionModel().getSelectedItem()!=null){
			ra = tvPAlmacen.getSelectionModel().getSelectedItem();
			//TextField
			idc = ra.getIdrefaccionalmacen();
			txtBuscar.setText(ra.getNombre());
			//ComboBox
		}
	}
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}