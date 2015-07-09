package controlador;


import java.net.URL;
import java.util.ResourceBundle;

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
import modelo.Marca;

public class MarcaMotocicleta implements Initializable{
	private Marca f;
	@FXML TableColumn<Marca, String> tcMarca;
	@FXML TableView<Marca> tvMarca;
	
	@FXML TextField txtMarca,txtBuscador, txtId;
	@FXML Label lblMensaje;
	@FXML Button btnGuardar, eliminar, btnEditar, btnBuscar;
	@FXML CheckBox ckbInactivos;
	
	
	private int filasXPagina;
	private ObservableList<Marca> datos;
	private FilteredList< Marca> datosBusqueda;
	@FXML Pagination paginador;
	
	 public MarcaMotocicleta() {
		// TODO Auto-generated constructor stub
		f = new Marca();
		
		filasXPagina  = 10;
		datos = FXCollections.observableArrayList();
		
	}
	
	 
	 
	 @FXML public void  click_TableView(){
		 if (tvMarca.getSelectionModel().getSelectedItem()!= null) {
			f = tvMarca.getSelectionModel().getSelectedItem();
			txtId.setText(f.getIdmarca().toString());
			txtMarca.setText(f.getNombre().toString());

		 }
	 }
	 
 
	 private Node createPage(int pageIndex){
		 if(filasXPagina>0){
		 int fromIndex = pageIndex *filasXPagina;
		 int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		 tvMarca.setItems(FXCollections.observableArrayList(
				 datosBusqueda.subList(fromIndex, toIndex)));
		 }
			else{
				tvMarca.setItems(null);
				paginador.setPageCount(0);
			}
		   return new BorderPane(tvMarca);
		}	

	 
	 @FXML public void click_buscarTexto(){
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
		 			datosBusqueda.setPredicate(Marca->Marca.getNombre().toLowerCase().
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
	 
	 @FXML public void click_insertar(){
			try{
				if(txtMarca.getText().trim().isEmpty()){
					lblMensaje.setText("Faltan datos por ingresar");
			}
				else
					f= new Marca();
					f.setNombre(new SimpleStringProperty(txtMarca.getText()));
										
					boolean res = f.insertar();
					if (res){
						lblMensaje.setText("Datos insertados");
					}
					else
						lblMensaje.setText("Se ha producido un error en el servidor");					
		}catch (Exception ex){
			ex.printStackTrace();
			lblMensaje.setText("Se ha producido un error en el servidor");
		}
}
	 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Enlazar columnas
		tcMarca.setCellValueFactory(new PropertyValueFactory<Marca, String>("nombre"));
		//Llenar TableView
		llenarTableview(true);
	}
		
		
		@FXML public void click_eliminar(){
			try {
				if(txtId.getText().isEmpty())
					lblMensaje.setText("Debe seleccionar el registro a dar de baja");
				else
					f = new Marca();
					f.setIdmarca(new SimpleIntegerProperty(Integer.valueOf(txtId.getText())));
					if(f.eliminar()==true){
						llenarTableview(true);
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
				txtMarca.getText().isEmpty()) {
				lblMensaje.setText("Debe selecionar un registro");
			}
			else{
			f = new Marca();
			f.setIdmarca(new SimpleIntegerProperty(Integer.valueOf(txtId.getText())));
			f.setNombre(new SimpleStringProperty(txtMarca.getText()));
			if(f.actualizar()){
				llenarTableview(true);
				limpiar();
				lblMensaje.setText("Datos actualizados con éxito");					
			}
			else
				lblMensaje.setText("Se ha producido un problema en el servidor.");
			
		}	
	} catch (Exception e) {
		// TODO: handle exception
	}
}
		
		
		//Método para mostrar los registros dados de baja.
		@FXML public void click_inactivos(){
			if(ckbInactivos.isSelected())
				llenarTableview(false);
			else
				llenarTableview(true);
		}
		
		public void limpiar(){
			txtMarca.clear();
			txtBuscador.clear();
		}
		
	
		
		public void llenarTableview(Boolean activo){
			try {
				//Refrescar y volver a cargar los datos en el TableView
				datos=f.getMarcas(activo);
				datosBusqueda = new FilteredList<>(datos);
				paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory(( pagina) -> createPage(pagina));
				lblMensaje.setText(datos.size() + " registros encontrados.");
			} catch (Exception e) {
				e.printStackTrace();
				lblMensaje.setText("Se ha producido un error al recuperar los datos.");
			}
				
		}
		
		
}