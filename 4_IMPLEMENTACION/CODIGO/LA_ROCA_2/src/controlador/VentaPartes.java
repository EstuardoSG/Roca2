package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import modelo.Almacen;
import modelo.DetalleVenta;
import modelo.Repair;
import modelo.Servicios;
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
import javafx.scene.control.CheckBox;
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
	private ObservableList<DetalleVenta> detalle;
	private ObservableList<Servicios> elementosServicio;
	private ObservableList<Almacen> elementosRefaccion;
	private FilteredList<Almacen> elementosBusquedaR;
	private FilteredList<Servicios> elementosBusquedaS;
	private Almacen al;
	private Servicios se;
	private VentaRefaccion vr;
	private Repair re;
	private DetalleVenta dv;
	private Errores er;
	private int idrefaccion;
	private int idreparacion;

	@FXML TextField txtBuscadorRefacciones, txtBuscadorServicios, txtPrecio, txtIVA, txtCantidad, txtTotal,
		txtExistencia, txtRefaccion,txtMonto, txtCambio;
	@FXML ComboBox<Repair> cbReparacion;
	@FXML public ListView<Almacen> lvVentaR;
	@FXML ListView<Servicios> lvVentaS;
	@FXML Button btnGuardar, btnElimiar, btnNuevo, btnAgregar, btnBuscar;
	@FXML CheckBox chkPrecio;
	
	@FXML TableView<DetalleVenta> tvVentaR;
	@FXML TableView<Servicios> tvVentaS;
	@FXML TableColumn<DetalleVenta, String> tcRefaccion;
	@FXML TableColumn<DetalleVenta, Integer>   tcCantidad;
	@FXML TableColumn<DetalleVenta, Float> tcPrecio, tcSubtotal;
	@FXML TableColumn tcEliminar;
	
	
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
		elementosRefaccion = FXCollections.observableArrayList();
		elementosServicio = FXCollections.observableArrayList();
		detalle = FXCollections.observableArrayList();
		dv= new DetalleVenta();
		al = new Almacen();
		se = new Servicios();
		vr = new VentaRefaccion();
		re = new Repair();
		er = new Errores();
		System.out.println(getClass().getResource("../vista/images/delete.png").getPath());
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			btnGuardar.setDisable(true);
			cbReparacion.setItems(re.getRepair());
			elementosRefaccion = al.getAlmacens(true);
			elementosServicio = se.getServicios(true);
		
			lvVentaR.setItems(elementosRefaccion);
			lvVentaS.setItems(elementosServicio);
			insertarBotonR();
			tcRefaccion.setCellValueFactory(
					new PropertyValueFactory<DetalleVenta, String>("refaccion"));
			tcCantidad.setCellValueFactory(
					new PropertyValueFactory<DetalleVenta, Integer>("cantidad"));
			tcPrecio.setCellValueFactory(
					new PropertyValueFactory<DetalleVenta, Float>("precio"));
			tcSubtotal.setCellValueFactory(
					new PropertyValueFactory<DetalleVenta, Float>("subtotal"));
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void nuevo(){
		txtCantidad.clear();
		txtRefaccion.clear();
		txtExistencia.clear();
		txtIVA.clear();
		txtPrecio.clear();
		cbReparacion.setValue(null);
		txtTotal.clear();
		txtCambio.clear();
		tvVentaR.getItems().clear();

	}
	
	
	@FXML public void guardar(){
		try {	
			if(vr.guardar(detalle)){
				
				txtCantidad.clear();
				txtRefaccion.clear();
				txtExistencia.clear();
				txtIVA.clear();
				txtPrecio.clear();
				cbReparacion.setValue(null);
				txtTotal.clear();
				txtCambio.clear();
				tvVentaR.getItems().clear();
				lvVentaR.setItems(al.getAlmacens(true));
				
				System.out.println("La venta ha sido realizada.");
			}	
			
			else{
				//lblMensaje.setText("Se ha producido un error. Consulte a su administrador.");
			}
			 System.out.println(detalle.toString());
		} catch (Exception e) {
			//lblMensaje.setText("Se ha producido un error. Consulte a su administrador.");
			er.printLog(e.getMessage(), this.toString());
		}
	}
	
	@FXML public void habilitarGuardar(){
		try {
			double rs;
			rs = vr.getTotalR();
			//txtTotal.setText(String.valueOf(vr.getTotalR()));
			Double efectivo = Double.valueOf(txtMonto.getText());
			if(efectivo>vr.getTotalR()){
				Double cambio = efectivo - rs;
				txtCambio.setText(cambio.toString());
				btnGuardar.setDisable(false);
			}
			else{
				btnGuardar.setDisable(true);
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
				vr.setServicio(lvVentaS.getSelectionModel().getSelectedItem());
				vr.setAlmacen(lvVentaR.getSelectionModel().getSelectedItem());

				
				if(vr.getAlmacen() !=null){
					vr.setExistencia(Integer.parseInt(txtCantidad.getText()));
					vr.setPrecio(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText()));
					vr.setIdrefaccionalmacen(getIdrefaccion());
					re.setRep(cbReparacion.getSelectionModel().getSelectedItem());	
				}
				if(vr.getServicio() != null){
					vr.setExistencia(Integer.parseInt(txtCantidad.getText()));
					vr.setPrecio(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText()));
					vr.setIdrefaccionalmacen(getIdrefaccion());
					re.setRep(cbReparacion.getSelectionModel().getSelectedItem());
				}

				if(vr.agregarDetalle()==true){
					System.out.println("Se agrego el producto.");			
					DetalleVenta dr = new DetalleVenta();
					dr.setCantidad(new SimpleIntegerProperty(Integer.parseInt(txtCantidad.getText())));
					dr.setIdrefaccionalmacen(new SimpleIntegerProperty(getIdrefaccion()));
					dr.setPrecio(new  SimpleFloatProperty(Float.parseFloat(txtPrecio.getText()) ));
					dr.setSubtotal(new SimpleFloatProperty(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText())));
				    dr.setIdReparacion(new SimpleIntegerProperty(cbReparacion.getSelectionModel().getSelectedItem().getIdreparacion()));
				    detalle.add(dr);
				   
				}
				
			/*	if(vr.agregarDetalle() ==true){
					System.out.println("Se agrego el producto.");			
					DetalleVenta dr = new DetalleVenta();
					dr.setIdServicio(new SimpleIntegerProperty(Integer.parseInt(txtCantidad.getText())));
					dr.setIdServicio(new SimpleIntegerProperty(dr.getIdServicio()));
					dr.setPrecio1(new  SimpleFloatProperty(Float.parseFloat(txtPrecio.getText()) ));
					dr.setSubtotal(new SimpleFloatProperty(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText())));
					dr.setIdReparacion(new SimpleIntegerProperty(cbReparacion.getSelectionModel().getSelectedItem().getIdreparacion()));	
			   
			    detalle.add(dr);
				}*/
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
	

	@FXML public void buscadorRefacciones(){
		try {
			if(!txtBuscadorRefacciones.getText().trim().isEmpty()){
				elementosBusquedaR = new FilteredList<Almacen>(elementosRefaccion);
				elementosBusquedaR.setPredicate(Almacen->Almacen.getNombre().toLowerCase().
		 					contains(txtBuscadorRefacciones.getText().toLowerCase()));
				lvVentaR.setItems(elementosBusquedaR);
			}
			else{
				lvVentaR.setItems(elementosRefaccion);
			}
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void buscadorServicios(){
		try {
			if(!txtBuscadorServicios.getText().trim().isEmpty()){
				elementosBusquedaS = new FilteredList<Servicios>(elementosServicio);
				elementosBusquedaS.setPredicate(Servicios->Servicios.getNombreServicio().toLowerCase().
		 					contains(txtBuscadorServicios.getText().toLowerCase()));
				lvVentaS.setItems(elementosBusquedaS);
			}
			else{
				lvVentaS.setItems(elementosServicio);
			}
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void buscar(){
		
	}
	
	@FXML public void seleccionarRefaccion(){ 
		try {
			Almacen a = lvVentaR.getSelectionModel().getSelectedItem();
			if(a!=null){
				double iva;
				iva = a.getPrecio1() * 0.16;
				txtRefaccion.setText(a.getNombre() + " " + a.getModelo());
				txtExistencia.setText(a.getExistencia().toString());
				txtPrecio.setText(a.getPrecio1().toString());
				txtIVA.setText(String.valueOf(iva));
				txtCantidad.setText("1");
				setIdrefaccion(a.getIdrefaccionalmacen());
			}
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
// Falta agregar codigo	
	@FXML public void seleccionarServicio(){ 
		try {
			Servicios s = lvVentaS.getSelectionModel().getSelectedItem();
			if(s!=null){
				double iva;
				iva = s.getPrecio1() * 0.16;
				s.setCantidad(12);
				txtRefaccion.setText(s.getNombreServicio());
				txtExistencia.setText(String.valueOf(s.getCantidad()));
				txtPrecio.setText(s.getPrecio1().toString());
				txtIVA.setText(String.valueOf(iva));
				txtCantidad.setText("1");
				setIdrefaccion(s.getIdServicio());
	                                          
			}
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	
	
	public void actualizarDetalle(){
		try {
/*	
 * Debe traer el iva de la base de datos y no tener un valor estatico.
 */
			tvVentaR.setItems(vr.obtenerDetalleR());
			//tvVentaS.setItems(vr.obtenerDetalleS());
			
			insertarBotonR();
			txtTotal.setText("$" +String.valueOf(vr.getTotalR() * 1.16));
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
	}
	

	@SuppressWarnings("unchecked")
	public void insertarBotonR(){
		try{
		
        tcEliminar.setSortable(false);         
        tcEliminar.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DetalleVenta, Boolean>, 
                ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DetalleVenta, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
 
        tcEliminar.setCellFactory(
                new Callback<TableColumn<DetalleVenta, Boolean>, TableCell<DetalleVenta, Boolean>>() {
 
            @Override
            public TableCell<DetalleVenta, Boolean> call(TableColumn<DetalleVenta, Boolean> p) {
                return new ButtonCell1();
            }
         
        });
		}
		catch(Exception ex){
			er.printLog(ex.getMessage(), this.getClass().toString());
		}
	}
	
	
	// #region Clase privada
	
	private class ButtonCell1 extends TableCell<DetalleVenta, Boolean> {
	       
        Image eliminarImagen;
        ImageView iv;
        final Button cellButtons;
        
        ButtonCell1(){
        	eliminarImagen = new Image(getClass().getResourceAsStream("/vista/images/delete.png"),30,30,false,false);
        	iv = new ImageView(eliminarImagen);
        	cellButtons = new Button("", new ImageView(eliminarImagen));
      	
            cellButtons.setOnAction(new EventHandler<ActionEvent>(){
            	/*
            	 * ELIMINAR EL PRODUCTO
            	 */
            	
				@Override
				public void handle(ActionEvent arg0) {
					DetalleVenta dr =(DetalleVenta)ButtonCell1.this.getTableView().getItems().get(ButtonCell1.this.getIndex());
					vr.eliminarDetalleR(dr);
					//vr.eliminarDetalleS(se);
					vr.getTotalR();//Actualizar el total
					actualizarDetalle(); //Actualizar el TableView
				}
            });
        }
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButtons);
            }
        }
    }	
	
	
	
	@Override
	public void setVentanaPrincipal(ControladordeVentanas screenParent) {
		 ventanas = screenParent;
		
	}
}
