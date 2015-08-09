package modelo;

import java.sql.PreparedStatement;

import controlador.VentaPartes;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class DetalleRefaccion {


	private Conexion con;
	private IntegerProperty cantidad, idrefaccionalmacen, idReparacion;
	private FloatProperty precio, subtotal;
	private StringProperty refaccion, modelo;
	private float iva;
	
	public DetalleRefaccion(){
		cantidad = idrefaccionalmacen = new SimpleIntegerProperty();
		precio = subtotal  = new SimpleFloatProperty();
		refaccion = modelo= new SimpleStringProperty();
		con = Conexion.getInstancia();
		setIva(16);
		
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

	public boolean guardar(ObservableList<DetalleRefaccion>  detalle){
		try {
			
			String sql="select fn_agregarpartes(?,?,?,?,?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
		   for (DetalleRefaccion dr : detalle){
			comando.setInt(1, dr.getIdReparacion());
			comando.setInt(2,dr.getIdrefaccionalmacen());
			comando.setFloat(3, dr.getPrecio());
			comando.setFloat(4, dr.getIva());
			comando.setInt(5, dr.getCantidad());
			System.out.println(comando.toString());
			comando.execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
}
