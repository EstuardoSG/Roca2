package modelo;

import java.sql.PreparedStatement;

import controlador.Errores;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class DetalleVenta {

	private Errores er;
	private VentaRefaccion vr;
	private Conexion con;
	/*
	 * Refacciones
	 */
	private IntegerProperty cantidad, idrefaccionalmacen, idReparacion;
	private FloatProperty precio, subtotal;
	private StringProperty refaccion, modelo;
	private BooleanProperty activoR;
	/*
	 * Servicios
	 */
	private IntegerProperty idServicio;
	private FloatProperty precio1, precio2;
	private StringProperty nombreservicio;
	private BooleanProperty activoS;
		private float iva;
	
	public DetalleVenta(){
		/*
		 * Refacciones
		 */
		cantidad = idrefaccionalmacen = new SimpleIntegerProperty();
		precio = subtotal  = new SimpleFloatProperty();
		refaccion = modelo= new SimpleStringProperty();
		activoR = new SimpleBooleanProperty();
		/*
		 * Servicios
		 */
		idServicio = new SimpleIntegerProperty();
		precio1 = precio2 = new SimpleFloatProperty();
		nombreservicio = new SimpleStringProperty();
		activoS = new SimpleBooleanProperty();
		con = Conexion.getInstancia();
		setIva(16);
		er = new Errores();
		vr = new VentaRefaccion();
	}
	
	public float getIva() {
		return iva;
	}

	public void setIva(float iva) {
		this.iva = iva;
	}

	public String getModelo() {
		return modelo.get();
	}


	public void setModelo(StringProperty modelo) {
		this.modelo = modelo;
	}


	public Integer getCantidad() {
		return cantidad.get();
	}

	public void setCantidad(IntegerProperty cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIdrefaccionalmacen() {
		return idrefaccionalmacen.get();
	}

	public void setIdrefaccionalmacen(IntegerProperty idrefaccionalmacen) {
		this.idrefaccionalmacen = idrefaccionalmacen;
	}

	public Float getPrecio() {
		return precio.get();
	}

	public void setPrecio(FloatProperty precio) {
		this.precio = precio;
	}

	public Float getSubtotal() {
		return subtotal.get();
	}

	public void setSubtotal(FloatProperty subtotal) {
		this.subtotal = subtotal;
	}

	public String getRefaccion() {
		return refaccion.get();
	}

	public void setRefaccion(StringProperty refaccion) {
		this.refaccion = refaccion;
	}

	
	public Integer getIdReparacion() {
		return idReparacion.get();
	}

	public void setIdReparacion(IntegerProperty idReparacion) {
		this.idReparacion = idReparacion;
	}
	


	
	
	
	public Boolean getActivoR() {
		return activoR.get();
	}

	public void setActivoR(BooleanProperty activoR) {
		this.activoR = activoR;
	}

	public Integer getIdServicio() {
		return idServicio.get();
	}

	public void setIdServicio(IntegerProperty idServicio) {
		this.idServicio = idServicio;
	}

	public Float getPrecio1() {
		return precio1.get();
	}

	public void setPrecio1(FloatProperty precio1) {
		this.precio1 = precio1;
	}

	public Float getPrecio2() {
		return precio2.get();
	}

	public void setPrecio2(FloatProperty precio2) {
		this.precio2 = precio2;
	}

	public String getNombreservicio() {
		return nombreservicio.get();
	}

	public void setNombreservicio(StringProperty nombreservicio) {
		this.nombreservicio = nombreservicio;
	}

	public Boolean getActivoS() {
		return activoS.get();
	}

	public void setActivoS(BooleanProperty activoS) {
		this.activoS = activoS;
	}
	
	public boolean guardar(ObservableList<DetalleVenta> detalle){
		try {
			con.conectar();
			String sql="select fn_agregarpartes(default,?,?,?,?,?)";
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			con.getConexion().setAutoCommit(false);
			for(DetalleVenta d: vr.listaDetalleR){
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
