package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clientes {
	
	private Conexion con;
	private ObservableList<Clientes> informacion;
	private StringProperty nombre1, nombre2, apellidopaterno, apellidomaterno, telefono1, telefono2,
	celular1, celular2, domicilio, numerointerior, numeroexterior, calle, localidad, ciudad, estado,
	codigopostal;
	
	private IntegerProperty idcliente, numerocliente;
	private BooleanProperty clientefrecuente, activo;
	public String getNombre1() {
		return nombre1.get();
	}
	public void setNombre1(StringProperty nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return nombre2.get();
	}
	public void setNombre2(StringProperty nombre2) {
		this.nombre2 = nombre2;
	}
	public String getApellidopaterno() {
		return apellidopaterno.get();
	}
	public void setApellidopaterno(StringProperty apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}
	public String getApellidomaterno() {
		return apellidomaterno.get();
	}
	public void setApellidomaterno(StringProperty apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}
	public String getTelefono1() {
		return telefono1.get();
	}
	public void setTelefono1(StringProperty telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2.get();
	}
	public void setTelefono2(StringProperty telefono2) {
		this.telefono2 = telefono2;
	}
	public String getCelular1() {
		return celular1.get();
	}
	public void setCelular1(StringProperty celular1) {
		this.celular1 = celular1;
	}
	public String getCelular2() {
		return celular2.get();
	}
	public void setCelular2(StringProperty celular2) {
		this.celular2 = celular2;
	}
	public String getDomicilio() {
		return domicilio.get();
	}
	public void setDomicilio(StringProperty domicilio) {
		this.domicilio = domicilio;
	}
	public String getNumerointerior() {
		return numerointerior.get();
	}
	public void setNumerointerior(StringProperty numerointerior) {
		this.numerointerior = numerointerior;
	}
	public String getNumeroexterior() {
		return numeroexterior.get();
	}
	public void setNumeroexterior(StringProperty numeroexterior) {
		this.numeroexterior = numeroexterior;
	}
	public String getCalle() {
		return calle.get();
	}
	public void setCalle(StringProperty calle) {
		this.calle = calle;
	}
	public String getLocalidad() {
		return localidad.get();
	}
	public void setLocalidad(StringProperty localidad) {
		this.localidad = localidad;
	}
	public String getCiudad() {
		return ciudad.get();
	}
	public void setCiudad(StringProperty ciudad) {
		this.ciudad = ciudad;
	}
	public String getEstado() {
		return estado.get();
	}
	public void setEstado(StringProperty estado) {
		this.estado = estado;
	}
	public String getCodigopostal() {
		return codigopostal.get();
	}
	public void setCodigopostal(StringProperty codigopostal) {
		this.codigopostal = codigopostal;
	}
	public Integer getIdcliente() {
		return idcliente.get();
	}
	public void setIdcliente(IntegerProperty idcliente) {
		this.idcliente = idcliente;
	}
	public Integer getNumerocliente() {
		return numerocliente.get();
	}
	public void setNumerocliente(IntegerProperty numerocliente) {
		this.numerocliente = numerocliente;
	}
	public Boolean getClientefrecuente() {
		return clientefrecuente.get();
	}
	public void setClientefrecuente(BooleanProperty clientefrecuente) {
		this.clientefrecuente = clientefrecuente;
	}
	public Boolean getActivo() {
		return activo.get();
	}
	public void setActivo(BooleanProperty activo) {
		this.activo = activo;
	}
	
	public Clientes(){
		nombre1 = nombre2 = apellidopaterno = apellidomaterno = telefono1 = telefono2 = celular1 = celular2 = domicilio =
		numerointerior = numeroexterior = calle = localidad = ciudad = estado = codigopostal  = new SimpleStringProperty();
		idcliente = numerocliente = new SimpleIntegerProperty();
		clientefrecuente = activo = new SimpleBooleanProperty();
		
		con = Conexion.getInstancia();
	}
	
	
	public ObservableList<Clientes> getCliente(Boolean estatus) throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "";
			if(estatus)
				sql = "select  idcliente, nombre1, nombre2, apellidopaterno, apellidomaterno, telefono1, telefono2, celular1, celular2, domicilio, numerointerior, numeroexterior, calle, localidad, ciudad, estado, codigopostal, numerocliente, clientefrecuente, activo from cliente where activo = '1'";
			else
				sql = "select idcliente, nombre1, nombre2, apellidopaterno, apellidomaterno, telefono1, telefono2, celular1, celular2, domicilio, numerointerior, numeroexterior, calle, localidad, ciudad, estado, codigopostal, numerocliente, clientefrecuente, activo from cliente where activo = '0'";
			
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			
			while(rs.next()){
				Clientes c = new Clientes();
				c.idcliente = new SimpleIntegerProperty(rs.getInt("idcliente"));
				c.nombre1 = new SimpleStringProperty(rs.getString("nombre1"));
				c.nombre2 = new SimpleStringProperty(rs.getString("nombre2"));
				c.apellidopaterno = new SimpleStringProperty(rs.getString("apellidopaterno"));
				c.apellidomaterno = new SimpleStringProperty(rs.getString("apellidomaterno"));
				c.telefono1 = new SimpleStringProperty(rs.getString("telefono1"));
				c.telefono2 = new SimpleStringProperty(rs.getString("telefono2"));
				c.celular1 = new SimpleStringProperty(rs.getString("celular1"));
				c.celular2 = new SimpleStringProperty(rs.getString("celular2"));
				c.domicilio = new SimpleStringProperty(rs.getString("domicilio"));
				c.numerointerior = new SimpleStringProperty(rs.getString("numerointerior"));
				c.numeroexterior = new SimpleStringProperty(rs.getString("numeroexterior"));
				c.calle = new SimpleStringProperty(rs.getString("calle"));
				c.localidad = new SimpleStringProperty(rs.getString("localidad"));
				c.ciudad = new SimpleStringProperty(rs.getString("ciudad"));
				c.estado = new SimpleStringProperty(rs.getString("estado"));
				c.codigopostal = new SimpleStringProperty(rs.getString("codigopostal"));
				c.numerocliente = new SimpleIntegerProperty(rs.getInt("numerocliente"));
				c.clientefrecuente = new SimpleBooleanProperty(rs.getBoolean("clientefrecuente"));
				c.activo = new SimpleBooleanProperty(rs.getBoolean("activo"));
				informacion.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return informacion;
	}
	
	public boolean guardar(){
		try {
			
			String sql="select fn_agregarcliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setString(1, this.getNombre1());
			comando.setString(2, this.getNombre2());
			comando.setString(3, this.getApellidopaterno());
			comando.setString(4, this.getApellidomaterno());
			comando.setString(5,this.getTelefono1());
			comando.setString(6, this.getTelefono2());
			comando.setString(7,this.getCelular1());
			comando.setString(8, this.getCelular2());
			comando.setString(9, this.getDomicilio());
			comando.setString(10, this.getNumerointerior());
			comando.setString(11, this.getNumeroexterior());
			comando.setString(12, this.getCalle());
			comando.setString(13, this.getLocalidad());
			comando.setString(14, this.getCiudad());
			comando.setString(15, this.getEstado());
			comando.setString(16, this.getCodigopostal());
			comando.setInt(17, this.getNumerocliente());
			comando.setBoolean(18, this.getClientefrecuente());
			System.out.println(comando.toString());
			comando.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	
	public boolean eliminar(){
		try {
			
			String sql="select fn_eliminarcliente(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdcliente());
			comando.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	
	public boolean actualizar(){
		try {
			
			String sql="select fn_actualizarcliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setString(1, this.getNombre1());
			comando.setString(2, this.getNombre2());
			comando.setString(3, this.getApellidopaterno());
			comando.setString(4, this.getApellidomaterno());
			comando.setString(5,this.getTelefono1());
			comando.setString(6, this.getTelefono2());
			comando.setString(7,this.getCelular1());
			comando.setString(8, this.getCelular2());
			comando.setString(9, this.getDomicilio());
			comando.setString(10, this.getNumerointerior());
			comando.setString(11, this.getNumeroexterior());
			comando.setString(12, this.getCalle());
			comando.setString(13, this.getLocalidad());
			comando.setString(14, this.getCiudad());
			comando.setString(15, this.getEstado());
			comando.setString(16, this.getCodigopostal());
			comando.setInt(17, this.getNumerocliente());
			comando.setBoolean(18, this.getClientefrecuente());
			comando.setInt(19, this.getIdcliente());
			System.out.println(comando.toString());
			comando.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	
}
