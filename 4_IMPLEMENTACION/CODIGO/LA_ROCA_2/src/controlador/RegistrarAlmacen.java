package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import vista.ControladordeVentanas;
import vista.IControladorVentanas;
import modelo.MarcaCombo;
import modelo.Almacen;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class RegistrarAlmacen implements Initializable, IControladorVentanas{
	
	private ControladordeVentanas ventanas;
	private MarcaCombo l;
	private Almacen f;	
	@FXML ComboBox<MarcaCombo> cbMarca;
	@FXML TableColumn<Almacen, Integer> tcMarca;
	@FXML TableColumn<Almacen, String> tcNombre, tcModelo, tcIva;
	@FXML TableColumn<Almacen, Float> tcPrecio1;
	@FXML TableView<Almacen> tvAlmacen;
	
	@FXML TextField txtBuscador, txtNombre, txtModelo, txtPrecio1, txtPrecio2, txtStockmin, txtStockmax, txtId;
	@FXML Label lblMensaje;
	@FXML CheckBox ckbInactivos;
	@FXML Button btnGuardar, btnEliminar, btnEditar, btnBuscar, btnMas, btnNuevo;
	
	private ControladorVentana ventana;
	
	//variables de paginacion
	private int filasXPagina;
	private ObservableList<Almacen> datos;
	private FilteredList<Almacen> datosBusqueda;
	@FXML Pagination paginador;
	
	//constructor
	 public RegistrarAlmacen() {
		l = new MarcaCombo();
		f = new Almacen();
		filasXPagina  = 10;
		datos = FXCollections.observableArrayList();
		
	}
	 
	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		 try{
			cbMarca.setItems(l.getMarcaCombo());
			//Enlazar columnas
			//tenia l
			tcMarca.setCellValueFactory(new PropertyValueFactory<Almacen, Integer>("l"));
			tcNombre.setCellValueFactory(new PropertyValueFactory<Almacen, String>("nombre"));
			tcModelo.setCellValueFactory(new PropertyValueFactory<Almacen, String>("modelo"));
			tcPrecio1.setCellValueFactory(new PropertyValueFactory<Almacen, Float>("precio1"));
			tcIva.setCellValueFactory(new PropertyValueFactory<Almacen, String>("iva"));
			//Llenar TableView
			llenarTableView(true);
			btnEditar.setDisable(true);
			btnEliminar.setDisable(true);
	 } catch (SQLException e) {
			e.printStackTrace();
		};
	}
	 
	
	 
	 //metodo para subir los datos del tableview a los textfield
	 @FXML public void  click_TableView(){
		 if(ckbInactivos.isSelected()){
				btnGuardar.setDisable(true);
				btnEliminar.setDisable(true);
				btnEditar.setDisable(true);
			}else{
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(false);
			btnEditar.setDisable(false);
			}
		 
		 if (tvAlmacen.getSelectionModel().getSelectedItem()!= null) {
			f = tvAlmacen.getSelectionModel().getSelectedItem();
			//Textfield
			txtId.setText(f.getIdrefaccionalmacen().toString());
			txtNombre.setText(f.getNombre().toString());
			txtModelo.setText(f.getModelo().toString());
			txtPrecio1.setText(f.getPrecio1().toString());
			txtPrecio2.setText(f.getPrecio2().toString());
			txtStockmin.setText(f.getStockminimo().toString());
			txtStockmax.setText(f.getStockmaximo().toString());
			//ComboBox
			cbMarca.getSelectionModel().select(f.getL());
		 }
	 }
	 	//metodo paginacion
	 
		private Node createPage(int pageIndex) {
				if(filasXPagina>0){
				   int fromIndex = pageIndex * filasXPagina;
				   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
				   tvAlmacen.setItems(FXCollections.observableArrayList(
						   datosBusqueda.subList(fromIndex, toIndex)));
				}
				else{
					tvAlmacen.setItems(null);
					paginador.setPageCount(0);
				}
			   return new BorderPane(tvAlmacen);
			}	
		 
		 
		 	@FXML public void buscarTexto(){
		 		if(txtBuscador.getText().trim().isEmpty()){
		 			//Llenar TableView
		 			datosBusqueda= new FilteredList<>(datos);
		 			filasXPagina=10;
					paginador.setPageCount(datosBusqueda.size()/filasXPagina);
					paginador.setPageFactory((Integer pagina) -> createPage(pagina));
					lblMensaje.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
		 		}
		 		else{
		 			try{
			 			datosBusqueda.setPredicate(Almacen->Almacen.getNombre().toLowerCase().
			 					contains(txtBuscador.getText().toLowerCase()));
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
		 
		 
	 
	 //Metodo para insertar registros
	 @FXML public void click_insertar(){
			try{
				if(	txtNombre.getText().trim().isEmpty() ||
					txtModelo.getText().trim().isEmpty() ||
					txtPrecio1.getText().trim().isEmpty() ||
					txtPrecio2.getText().trim().isEmpty() ||
					txtStockmin.getText().trim().isEmpty() ||
					txtStockmax.getText().trim().isEmpty() ||
					cbMarca.getSelectionModel().getSelectedItem()==null) {
					lblMensaje.setText("Faltan datos por ingresar");
			}
				else{
					Float precio01 = Float.valueOf(txtPrecio1.getText());
					Float precio02 = Float.valueOf(txtPrecio2.getText());
					f= new Almacen();
					f.setL(cbMarca.getSelectionModel().getSelectedItem());
					f.setNombre(new SimpleStringProperty(txtNombre.getText()));
					f.setModelo(new SimpleStringProperty(txtModelo.getText()));
					f.setPrecio1(new SimpleFloatProperty(precio01));
					f.setPrecio2(new SimpleFloatProperty(precio02));
					f.setStockminimo(new SimpleIntegerProperty(Integer.valueOf(txtStockmin.getText())));
					f.setStockmaximo(new SimpleIntegerProperty(Integer.valueOf(txtStockmax.getText())));
					boolean res = f.insertar();
					if (res){
						lblMensaje.setText("Datos insertados");
					}
					else
						lblMensaje.setText("Se ha producido un error en el servidor");	
				}
		}catch (Exception ex){
			ex.printStackTrace();
			lblMensaje.setText("Se ha producido un error en el servidor");
		}
	 }
	 
	 //metodo para eliminar el registro seleccionado del tableview
	 @FXML public void click_eliminar(){
			try {
				if(txtId.getText().isEmpty())
					lblMensaje.setText("Debe seleccionar el registro a dar de baja");
				else
					f = new Almacen();
					f.setIdrefaccionalmacen(new SimpleIntegerProperty(Integer.valueOf(txtId.getText())));
					if(f.eliminar()==true){
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
		
		//modificar para actualizar
		@FXML public void click_actualizar(){
			try{
			if (txtId.getText().isEmpty() ||
				txtNombre.getText().isEmpty()) {
				lblMensaje.setText("Debe selecionar un registro");
			}
			else{
			Float precio01 = Float.valueOf(txtPrecio1.getText());
			Float precio02 = Float.valueOf(txtPrecio2.getText());
			f = new Almacen();
			f.setIdrefaccionalmacen(new SimpleIntegerProperty(Integer.valueOf(txtId.getText())));
			f.setL(cbMarca.getSelectionModel().getSelectedItem());
			f.setNombre(new SimpleStringProperty(txtNombre.getText()));
			f.setModelo(new SimpleStringProperty(txtModelo.getText()));
			f.setPrecio1(new SimpleFloatProperty(precio01));
			f.setPrecio2(new SimpleFloatProperty(precio02));
			f.setStockminimo(new SimpleIntegerProperty(Integer.valueOf(txtStockmin.getText())));
			f.setStockmaximo(new SimpleIntegerProperty(Integer.valueOf(txtStockmax.getText())));

			if(f.actualizar()){
				llenarTableView(true);
				limpiar();
				lblMensaje.setText("Datos actualizados con éxito");					
			}
			else
				lblMensaje.setText("Se ha producido un problema en el servidor.");
			
			}	
				} catch (Exception e) {
			}
		}
		
		@FXML public void nuevo(){
			btnGuardar.setDisable(false);
			btnEditar.setDisable(true);
			btnEliminar.setDisable(true);
			limpiar();
			ckbInactivos.setSelected(false);
			llenarTableView(true);
		}
		
		//Método para mostrar los registros dados de baja.
		@FXML public void click_inactivos(){
			if(ckbInactivos.isSelected()){
				llenarTableView(false);
				btnEditar.setDisable(true);
				btnEliminar.setDisable(true);
				btnGuardar.setDisable(true);
				limpiar();
			}
			else
			{
				llenarTableView(true);
				btnEditar.setDisable(true);
				btnEliminar.setDisable(true);
				btnGuardar.setDisable(false);
				limpiar();
			}
		}
		
		public void limpiar(){
			cbMarca.getSelectionModel().select(-1);
			txtBuscador.clear();
			txtId.clear();
			txtNombre.clear();
			txtModelo.clear();
			txtPrecio1.clear();
			txtPrecio2.clear();
			txtStockmin.clear();
			txtStockmax.clear();
			cbMarca.setValue(null);
			
		}
		
		
		public void llenarTableView(Boolean activo){
			try {
				//Refrescar y volver a cargar los datos en el TableView
				datos=f.getAlmacens(activo);
//agregue almacen
				datosBusqueda = new FilteredList<Almacen>(datos);
				paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory(( pagina) -> createPage(pagina));
				lblMensaje.setText(datos.size() + " registros encontrados.");
			} catch (Exception e) {
				e.printStackTrace();
				lblMensaje.setText("Se ha producido un error al recuperar los datos.");
			}
				
		}
		
		
		//Método para incorporar otra marca.
		@FXML public void click_mas(ActionEvent event){
			ventana = ControladorVentana.getInstancia();
			ventana.modal("../vista/fxml/RegistrarMarca.fxml", "Registrar marca");
		}
		
		@Override
		public void setVentanaPrincipal(ControladordeVentanas screenParent) {
			 ventanas = screenParent;	
		}
}
