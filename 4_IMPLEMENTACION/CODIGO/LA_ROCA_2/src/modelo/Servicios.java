package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.Errores;

import java.math.BigDecimal;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Servicios {
	//***************************************************************************************************
	//VARIABLES Y CONTROLES
	private IntegerProperty idServicio, idReparacion;
	private int  cantidad;
	private StringProperty nombreServicio;
	private FloatProperty precio1,precio2, subtotal;
	private ObservableList<Servicios> servicios;
	private Conexion con;
	private Errores er;
	
	public Servicios(){
		idServicio = new SimpleIntegerProperty();
		nombreServicio = new SimpleStringProperty();
		precio1 = precio2 = new SimpleFloatProperty();
		con = Conexion.getInstancia();
		er = new Errores();
	}
	//***************************************************************************************************
	//METODOS BOLEANOS
	public boolean guardarServicio(){
		BigDecimal precio01 = BigDecimal.valueOf(this.getPrecio1());
		BigDecimal precio02 = BigDecimal.valueOf(this.getPrecio2());
		try {
			String sql= "select fn_insertarservicio(?,?,?)";
			con.conectar();
			PreparedStatement servicio = con.getConexion().prepareStatement(sql);
			servicio.setString(1,this.getNombreServicio());
			servicio.setBigDecimal(2, precio01);
			servicio.setBigDecimal(3, precio02);
			servicio.execute();
			return true;
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		
	}
	
	public boolean actualizarServicio(){
		BigDecimal precio01 = BigDecimal.valueOf(this.getPrecio1());
		BigDecimal precio02 = BigDecimal.valueOf(this.getPrecio2());
		try {
			String sql= "select fn_actualizarservicio(?,?,?,?)";
			con.conectar();
			PreparedStatement servicio = con.getConexion().prepareStatement(sql);
			servicio.setInt(1,this.getIdServicio());
			servicio.setString(2,this.getNombreServicio());
			servicio.setBigDecimal(3, precio01);
			servicio.setBigDecimal(4, precio02);
			servicio.execute();
			return true;
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		
	}
	
	public boolean eliminarServicio(){
		try{
			String sql = "select fn_eliminaservicio(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdServicio());
			System.out.println(this.getIdServicio());
			comando.execute();
			return true;
			
			
		}catch(Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
			return  false;
		}
	}
	//***************************************************************************************************
	//OBSERVABLES
	public ObservableList<Servicios> getServicios(boolean activo) throws SQLException{
		ResultSet rs = null;
		try {
			String sql  ="select idservicio,nombreservicio,precio1,precio2 from servicios where activo = '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			servicios = FXCollections.observableArrayList();
			while (rs.next()) {
				Servicios s = new Servicios();
				s.idServicio = new SimpleIntegerProperty(rs.getInt("idservicio"));
				s.nombreServicio = new SimpleStringProperty(rs.getString("nombreservicio"));
				s.precio1 = new SimpleFloatProperty(rs.getFloat("precio1"));
				s.precio2 = new SimpleFloatProperty(rs.getFloat("precio2"));
				servicios.add(s);
				
			}
			
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return servicios;
		
	}

	//***************************************************************************************************
	//METODOS SET Y GET
	public Integer getIdServicio() {
		return idServicio.get();
	}

	public void setIdServicio(IntegerProperty idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio.get();
	}

	public void setNombreServicio(StringProperty nombreServicio) {
		this.nombreServicio = nombreServicio;
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

	public String toString() {
		return nombreServicio.get();
	}
	public Float getSubtotal() {
		return subtotal.get();
	}
	public void setSubtotal(FloatProperty subtotal) {
		this.subtotal = subtotal;
	}
	
	
	public void setIdReparacion(IntegerProperty idReparacion) {
		this.idReparacion = idReparacion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int i) {
		this.cantidad = i;
	}
	public Integer getIdReparacion() {
		return idReparacion.get();
	}


}
