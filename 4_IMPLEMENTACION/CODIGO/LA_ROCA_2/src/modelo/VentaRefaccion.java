package modelo;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VentaRefaccion {

	private Conexion con;
	private Almacen almacen;
	private Integer idrefaccionalmacen,  existencia;
	private Float precio, total;
	private String refaccion, modelo;
	private ObservableList<DetalleRefaccion> listaDetalleR;

	public VentaRefaccion(){
		idrefaccionalmacen = existencia = 0;
		listaDetalleR = FXCollections.observableArrayList();
		con = Conexion.getInstancia();
		almacen = new Almacen();
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public Integer getIdrefaccionalmacen() {
		return idrefaccionalmacen;
	}

	public void setIdrefaccionalmacen(Integer idrefaccionalmacen) {
		this.idrefaccionalmacen = idrefaccionalmacen;
	}

	public Integer getExistencia() {
		return existencia;
	}

	public void setExistencia(Integer existencia) {
		this.existencia = existencia;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public String getRefaccion() {
		return refaccion;
	}

	public void setRefaccion(String refaccion) {
		this.refaccion = refaccion;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	
	public boolean agregarDetalle(){
		boolean existe=false;
		try {
			if(listaDetalleR.isEmpty()==false){
				for(int i=0; i<listaDetalleR.size();i++){
					if(listaDetalleR.get(i).getIdrefaccionalmacen()== almacen.getIdrefaccionalmacen()){
						//La pelicula ya existe
						DetalleRefaccion d = listaDetalleR.get(i);
						int nuevacantidad = d.getCantidad() 
								+ existencia;
						d.setCantidad(new SimpleIntegerProperty(nuevacantidad));
						Float nuevoSubtotal = d.getPrecio() 
								* nuevacantidad; 
						d.setSubtotal(new SimpleFloatProperty(nuevoSubtotal));
						listaDetalleR.set(i,d);
						existe=true;
					}
				}
			}			
			if(listaDetalleR.isEmpty() || existe ==false){
				DetalleRefaccion dr = new DetalleRefaccion();
				dr.setIdrefaccionalmacen(new SimpleIntegerProperty(almacen.getIdrefaccionalmacen()));
				dr.setCantidad(new SimpleIntegerProperty(existencia));
				dr.setPrecio(new SimpleFloatProperty(almacen.getPrecio1()));
				dr.setSubtotal(new SimpleFloatProperty(existencia*almacen.getPrecio1()));
				dr.setRefaccion(new SimpleStringProperty(almacen.getNombre() + " " + almacen.getModelo()));			
				listaDetalleR.add(dr);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public float getTotal(){
		total = (float) 0;
		for(DetalleRefaccion d: listaDetalleR){
			total+=d.getSubtotal();
		}
		return total;
	}
	
	public ObservableList<DetalleRefaccion> obtenerDetalle(){
		return listaDetalleR;
	}
	
	
}
