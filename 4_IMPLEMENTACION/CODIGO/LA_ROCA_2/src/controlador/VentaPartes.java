package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import modelo.Almacen;
import modelo.DetalleRefaccion;
import modelo.Repair;
import modelo.VentaRefaccion;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import vista.ControladordeVentanas;
import vista.IControladorVentanas;

public class VentaPartes implements Initializable, IControladorVentanas {

	private ControladordeVentanas ventanas;
	
	@FXML TextField txtBuscador, txtPrecio, txtIVA, txtCantidad, txtTotal, txtExistencia, txtRefaccion;
	@FXML ComboBox<Repair> cbReparacion;
	@FXML ListView<Almacen> lvVentaR;
	@FXML Button btnGuardar, btnElimiar, btnNuevo, btnAgregar;
	
	@FXML TableView<DetalleRefaccion> tvVentaR;
	@FXML TableColumn<DetalleRefaccion, String> tcRefaccion;
	@FXML TableColumn<DetalleRefaccion, Integer>   tcCantidad;
	@FXML TableColumn<DetalleRefaccion, Float> tcPrecio, tcSubtotal;
	@FXML TableColumn tcEliminar;
	
	
/*	ArrayList<Integer> alIdreparacion  = new ArrayList<Integer>();
	ArrayList<String> alRefaccion = new ArrayList<String>();
	ArrayList<Integer> alCantidad = new ArrayList<Integer>();
	ArrayList<Float> alPrecio = new ArrayList<Float>();
	ArrayList<Integer> alIdrefaccionalmacen = new ArrayList<Integer>();
	
	
	public ArrayList<String> getAlRefaccion() {
		return alRefaccion;
	}
	public void setAlRefaccion(ArrayList<String> alRefaccion) {
		this.alRefaccion = alRefaccion;
	}
	public ArrayList<Integer> getAlCantidad() {
		return alCantidad;
	}
	public void setAlCantidad(ArrayList<Integer> alCantidad) {
		this.alCantidad = alCantidad;
	}
	public ArrayList<Float> getAlPrecio() {
		return alPrecio;
	}
	public void setAlPrecio(ArrayList<Float> alPrecio) {
		this.alPrecio = alPrecio;
	}
	public ArrayList<Integer> getAlIdrefaccionalmacen() {
		return alIdrefaccionalmacen;
	}
	public void setAlIdrefaccionalmacen(ArrayList<Integer> alIdrefaccionalmacen) {
		this.alIdrefaccionalmacen = alIdrefaccionalmacen;
	}
	public ArrayList<Integer> getAlIdreparacion() {
		return alIdreparacion;
	}
	public void setAlIdreparacion(ArrayList<Integer> alIdreparacion) {
		this.alIdreparacion = alIdreparacion;
	}
	*/
	//Atributos
	private ObservableList<DetalleRefaccion> detalle;
	private ObservableList<Almacen> elementos;
	private FilteredList<Almacen> elementosBusqueda;
	private Almacen al;
	private VentaRefaccion vr;
	private Repair re;
	private DetalleRefaccion dp;
	private Errores er;
	private int idrefaccion;
	private int idreparacion;
	
	public int getIdrefaccion() {
		return idrefaccion;
	}
	public void setIdrefaccion(int idrefaccion) {
		this.idrefaccion = idrefaccion;
	}

	public int getIdreparacion() {
		return idreparacion;
	}

	public void setIdreparacion(int idreparacion) {
		this.idreparacion = idreparacion;
	}
	

	public VentaPartes() {
		elementos = FXCollections.observableArrayList();
		detalle= FXCollections.observableArrayList();
		dp= new DetalleRefaccion();
		al = new Almacen();
		vr = new VentaRefaccion();
		re = new Repair();
		er = new Errores();
		System.out.println(getClass().getResource("../vista/images/delete.png").getPath());
		
	}
	
	
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbReparacion.setItems(re.getRepair());
			elementos = al.getAlmacens(true);
		
			lvVentaR.setItems(elementos);
			insertarBoton();
			tcRefaccion.setCellValueFactory(
					new PropertyValueFactory<DetalleRefaccion, String>("refaccion"));
			tcCantidad.setCellValueFactory(
					new PropertyValueFactory<DetalleRefaccion, Integer>("cantidad"));
			tcPrecio.setCellValueFactory(
					new PropertyValueFactory<DetalleRefaccion, Float>("precio"));
			tcSubtotal.setCellValueFactory(
					new PropertyValueFactory<DetalleRefaccion, Float>("subtotal"));
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void nuevo(){
		
	}
	
	
	@FXML public void guardar(){
		try {
			if(cbReparacion.getSelectionModel().getSelectedItem() == null){
				System.out.println("no hay datos");
			} 

				if(dp.guardar(detalle)){
						System.out.println("guardo");
				}
				else{
				
				}	
			} catch (Exception e) {
				er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	


	@FXML public void agregar(){
		try {
			int cantidad = Integer.parseInt(txtCantidad.getText());
			int existencias = Integer.parseInt(txtExistencia.getText());
			
			if(cantidad > 0 && cantidad <= existencias){
				vr.setAlmacen(lvVentaR.getSelectionModel().getSelectedItem());
				vr.setExistencia(Integer.parseInt(txtCantidad.getText()));
				vr.setPrecio(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText()));
				vr.setIdrefaccionalmacen(getIdrefaccion());
				re.setRep(cbReparacion.getSelectionModel().getSelectedItem());
				if(vr.agregarDetalle()==true){
					System.out.println("Se agrego el producto.");			
					DetalleRefaccion dr = new DetalleRefaccion();
					dr.setCantidad(new SimpleIntegerProperty(Integer.parseInt(txtCantidad.getText())));
					dr.setIdrefaccionalmacen(new SimpleIntegerProperty(getIdrefaccion()));
					dr.setPrecio(new  SimpleFloatProperty(Float.parseFloat(txtPrecio.getText()) ));
					dr.setSubtotal(new SimpleFloatProperty(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText())));
				    dr.setIdReparacion(new SimpleIntegerProperty(cbReparacion.getSelectionModel().getSelectedItem().getIdreparacion()));
					detalle.add(dr);				
				}
				else
					System.out.println("Ha ocurrido un error.");
			}
			actualizarDetalle();
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	@FXML public void eliminar(){
		
	}
	

	@FXML public void buscador(){
		try {
			if(!txtBuscador.getText().trim().isEmpty()){
				elementosBusqueda = new FilteredList<Almacen>(elementos);
				elementosBusqueda.setPredicate(Almacen->Almacen.getNombre().toLowerCase().
		 					contains(txtBuscador.getText().toLowerCase()));
				lvVentaR.setItems(elementosBusqueda);
			}
			else{
				lvVentaR.setItems(elementos);
			}
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	
	@FXML public void seleccionar(){ 
		try {
			Almacen a = lvVentaR.getSelectionModel().getSelectedItem();
			if(a!=null){
				txtRefaccion.setText(a.getNombre() + " " + a.getModelo());
				txtExistencia.setText(a.getExistencia().toString());
				txtPrecio.setText(a.getPrecio1().toString());
				txtIVA.setText("16");
				txtCantidad.setText("1");
				setIdrefaccion(a.getIdrefaccionalmacen());
	
			}
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	
	public void actualizarDetalle(){
		try {
			
			tvVentaR.setItems(vr.obtenerDetalle());
			insertarBoton();
			txtTotal.setText("$" +String.valueOf(vr.getTotal()));
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	@SuppressWarnings("unchecked")
	public void insertarBoton(){
		try{
		
        tcEliminar.setSortable(false);         
        tcEliminar.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DetalleRefaccion, Boolean>, 
                ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DetalleRefaccion, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
        tcEliminar.setCellFactory(
                new Callback<TableColumn<DetalleRefaccion, Boolean>, TableCell<DetalleRefaccion, Boolean>>() {
 
            @Override
            public TableCell<DetalleRefaccion, Boolean> call(TableColumn<DetalleRefaccion, Boolean> p) {
                return new ButtonCell();
            }
         
        });
		}
		catch(Exception ex){
			er.printLog(ex.getMessage(), this.getClass().toString());
		}
	}
	
	
	// #region Clase privada
	
	private class ButtonCell extends TableCell<DetalleRefaccion, Boolean> {
	       
        Image eliminarImagen;
        ImageView iv;
        final Button cellButton;
        
        ButtonCell(){
        	eliminarImagen = new Image(getClass().getResourceAsStream("/vista/images/delete.png"),30,30,false,false);
        	iv = new ImageView(eliminarImagen);
        	cellButton = new Button("", new ImageView(eliminarImagen));
      	
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
            	/*
            	 * ELIMINAR EL PRODUCTO
            	 */
            	
				@Override
				public void handle(ActionEvent arg0) {
					DetalleRefaccion dr =(DetalleRefaccion)ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
					vr.eliminarDetalle(dr);
					vr.getTotal();//Actualizar el total
					actualizarDetalle(); //Actualizar el TableView
				}
            });
        }
 

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
