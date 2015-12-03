package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controlador.Errores;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VentaRefaccion {

	private Conexion con;
	private Almacen almacen;
	private Errores er;
	private Servicios servicio;

	private Integer idrefaccionalmacen,  existencia, venta_id;

	private Float precio, total;
	private String refaccion, modelo;
	ObservableList<DetalleVenta> listaDetalleR;

	public VentaRefaccion(){
		venta_id = idrefaccionalmacen = existencia = 0;
		listaDetalleR = FXCollections.observableArrayList();
		con = Conexion.getInstancia();
		almacen = new Almacen();
		servicio = new Servicios();
		er = new Errores();
	}

	public Almacen getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	public Servicios getServicio() {
		return servicio;
	}
	public void setServicio(Servicios servicio) {
		this.servicio = servicio;
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
	
	public Integer getVenta_id() {
		return venta_id;
	}

	public void setVenta_id(Integer venta_id) {
		this.venta_id = venta_id;
	}
	
	
	
	public boolean agregarDetalle(){
		boolean existe=false;
		try {		
			/*if(listaDetalleR.isEmpty()==false){
				for(int i=0; i<listaDetalleR.size();i++){
					if(listaDetalleR.get(i).getIdrefaccionalmacen()== servicio.getIdServicio()){
						//La pelicula ya existe
						DetalleVenta d = listaDetalleR.get(i);
						int nuevacantidad = d.getCantidad() 
								+ existencia;
						if(nuevacantidad <= almacen.getExistencia())
						{
						d.setCantidad(new SimpleIntegerProperty(nuevacantidad));
						Float nuevoSubtotal = d.getPrecio() 
								* nuevacantidad; 
						d.setSubtotal(new SimpleFloatProperty(nuevoSubtotal));
						listaDetalleR.set(i,d);
						}
						existe=true;
					}
				}
			}	*/
			if(listaDetalleR.isEmpty()==false){
				for(int i=0; i<listaDetalleR.size();i++){
					if(listaDetalleR.get(i).getIdrefaccionalmacen()== almacen.getIdrefaccionalmacen()){
						//La pelicula ya existe
						DetalleVenta d = listaDetalleR.get(i);
						int nuevacantidad = d.getCantidad() 
								+ existencia;
						if(nuevacantidad <= almacen.getExistencia())
						{
						d.setCantidad(new SimpleIntegerProperty(nuevacantidad));
						Float nuevoSubtotal = d.getPrecio() 
								* nuevacantidad; 
						d.setSubtotal(new SimpleFloatProperty(nuevoSubtotal));
						listaDetalleR.set(i,d);
						}
						existe=true;
					}
				}
			}			
			if(listaDetalleR.isEmpty() || existe ==false){
				DetalleVenta dr = new DetalleVenta();
				dr.setIdrefaccionalmacen(new SimpleIntegerProperty(almacen.getIdrefaccionalmacen()));
				dr.setCantidad(new SimpleIntegerProperty(existencia));
				dr.setPrecio(new SimpleFloatProperty(almacen.getPrecio1()));
				dr.setSubtotal(new SimpleFloatProperty(existencia*almacen.getPrecio1()));
				dr.setRefaccion(new SimpleStringProperty(almacen.getNombre() + " " + almacen.getModelo()));		

				listaDetalleR.add(dr);
			}
			
			/*if(listaDetalleR.isEmpty() || existe ==false){
				DetalleVenta dr = new DetalleVenta();
				dr.setIdrefaccionalmacen(new SimpleIntegerProperty(servicio.getIdServicio()));
				dr.setCantidad(new SimpleIntegerProperty(existencia));
				dr.setPrecio(new SimpleFloatProperty(servicio.getPrecio1()));
				dr.setSubtotal(new SimpleFloatProperty(existencia*servicio.getPrecio1()));
				dr.setRefaccion(new SimpleStringProperty(servicio.getNombreServicio() + " " + almacen.getModelo()));		

				listaDetalleR.add(dr);
			}*/
			return true;
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
	}
	
	public float getTotalR(){
		total = (float) 0;
		for(DetalleVenta d: listaDetalleR){
			total+=d.getSubtotal();
		}
		return total;
	}
	
	
	public boolean eliminarDetalleR(DetalleVenta dr){
		if(dr !=null){
			listaDetalleR.remove(dr);
			return true;
		}
		else{
			return false;
		}
		
	}
		
	public ObservableList<DetalleVenta> obtenerDetalleR(){
		return listaDetalleR;
	}
	
	
	
	
	public boolean guardar(ObservableList<DetalleVenta> detalle){
		try {
			con.conectar();
			String sql="select fn_agregar_reparacion_partes(?,?,?,?,?)";
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			con.getConexion().setAutoCommit(false);
			for(DetalleVenta d: detalle){
				comando.setInt(1, d.getIdrefaccionalmacen());
				comando.setInt(2, d.getIdReparacion());
				comando.setFloat(3, d.getPrecio());
				comando.setFloat(4, d.getIva());
				comando.setInt(5, d.getCantidad());
				System.out.println(comando.toString());
				comando.execute();
			}
			con.getConexion().commit();
			con.getConexion().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		finally{
			con.desconectar();
		}
	}
}
