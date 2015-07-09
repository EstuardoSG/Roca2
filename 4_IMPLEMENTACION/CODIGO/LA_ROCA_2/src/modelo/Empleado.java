package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Empleado {
	//***************************************************************************************************
	//VARIABLES Y OBJETOS
	private Conexion con;
	private ObservableList<Empleado> informacion;
	private IntegerProperty idempleado;
	private StringProperty nombre1, nombre2, apellidopaterno, apellidomaterno,
	telefono1, telefono2, celular1, celular2, domicilio, numerointerior, numeroexterior, calle, localidad, 
	ciudad, estado, codigopostal, correo, usuario, contrasenia, privilegio, fechaingreso, fechadesalida ;
	
	public Empleado(){
		nombre1 = nombre2 = apellidopaterno = apellidomaterno = telefono1 = telefono2 = celular1 = celular2 = domicilio =
		numerointerior = numeroexterior = calle = localidad = ciudad = estado = codigopostal = correo = contrasenia =
		privilegio = fechaingreso = fechadesalida = new SimpleStringProperty();

	
		
		con = Conexion.getInstancia();
	}
	//***************************************************************************************************
	//METODOS BOLEANOS
	public boolean guardarEmpleado(){
		try{
			//aqui se puede sustituir el insertr  por la funcion 
			
			String sql= "select fninsertarempleado(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement empleado = con.getConexion().prepareStatement(sql);
			empleado.setString(1, this.getNombre1());
			empleado.setString(2, this.getNombre2());
			empleado.setString(3, this.getApellidopaterno());
			empleado.setString(4, this.getApellidomaterno());
			empleado.setString(5, this.getTelefono1());
			empleado.setString(6, this.getTelefono2());
			empleado.setString(7, this.getCelular1());
			empleado.setString(8, this.getCelular2());
			empleado.setString(9,this.getDomicilio());
			empleado.setString(10, this.getNumerointerior());
			empleado.setString(11, this.getNumeroexterior());
			empleado.setString(12, this.getCalle());
			empleado.setString(13, this.getLocalidad());
			empleado.setString(14, this.getCiudad());
			empleado.setString(15, this.getEstado());
			empleado.setString(16, this.getCodigopostal());
			empleado.setString(17, this.getCorreo());
			empleado.setString(18, this.getUsuario());
			empleado.setString(19, this.getContrasenia());
			empleado.setString(20, this.getPrivilegio());
			empleado.setString(21, this.getFechadesalida());
			empleado.execute();
			return  true;
			
	
			
		}catch(Exception e){	
			return false;
		}
		
	}
	
	public boolean actualizarEmpleado(){
		System.out.println("TEST");

		try{
			//aqui se puede sustituir el insertr  por la funcion 
			
			String sql= "select fn_actualizarE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement empleado = con.getConexion().prepareStatement(sql);
			empleado.setInt(1,this.getIdempleado());
			empleado.setString(2, this.getNombre1());
			empleado.setString(3, this.getNombre2());
			empleado.setString(4, this.getApellidopaterno());
			empleado.setString(5, this.getApellidomaterno());
			empleado.setString(6, this.getTelefono1());
			empleado.setString(7, this.getTelefono2());
			empleado.setString(8, this.getCelular1());
			empleado.setString(9, this.getCelular2());
			empleado.setString(10,this.getDomicilio());
			empleado.setString(11, this.getNumerointerior());
			empleado.setString(12, this.getNumeroexterior());
			empleado.setString(13, this.getCalle());
			empleado.setString(14, this.getLocalidad());
			empleado.setString(15, this.getCiudad());
			empleado.setString(16, this.getEstado());
			empleado.setString(17, this.getCodigopostal());
			empleado.setString(18, this.getCorreo());
			empleado.setString(19, this.getUsuario());
			empleado.setString(20, this.getContrasenia());
			empleado.setString(21, this.getPrivilegio());
			empleado.setString(22, this.getFechadesalida());
			empleado.execute();
			return  true;
			
		}catch(Exception e){	
			return false;
		}
		
	}
	
	public boolean eEmpleado(){
		
		try{
			String sql = "select fneliminarempleado(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdempleado());
			comando.execute();
			return true;
			
			
		}catch(Exception ex){
			return  false;
		}

}


	
	//***************************************************************************************************
	//OBSERVABLES
	public ObservableList<Empleado> getEmpleadoInactivo() throws SQLException{
		ResultSet rs = null;
		try {
			String sql  ="select idempleado,nombre1,nombre2,apellidopaterno,apellidomaterno,telefono1,telefono2,celular1,celular2,domicilio,numerointerior,numeroexterior,calle,localidad,ciudad,estado,codigopostal,correo,usuario,contrasenia,privilegio,fechaingreso,fechadesalida from empleados where estatus = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			
			while(rs.next()){
				Empleado e = new Empleado();
				e.idempleado = new SimpleIntegerProperty(rs.getInt("idempleado"));
				e.nombre1 = new SimpleStringProperty(rs.getString("nombre1"));
				e.nombre2 = new SimpleStringProperty(rs.getString("nombre2"));
				e.apellidopaterno = new SimpleStringProperty(rs.getString("apellidopaterno"));
				e.apellidomaterno = new SimpleStringProperty(rs.getString("apellidomaterno"));
				e.telefono1 = new SimpleStringProperty(rs.getString("telefono1"));
				e.telefono2 = new SimpleStringProperty(rs.getString("telefono2"));
				e.celular1 = new SimpleStringProperty(rs.getString("celular1"));
				e.celular2 = new SimpleStringProperty(rs.getString("celular2"));
				e.domicilio = new SimpleStringProperty(rs.getString("domicilio"));
				e.numerointerior = new SimpleStringProperty(rs.getString("numerointerior"));
				e.numeroexterior = new SimpleStringProperty(rs.getString("numeroexterior"));
				e.calle = new SimpleStringProperty(rs.getString("calle"));
				e.localidad = new SimpleStringProperty(rs.getString("localidad"));
				e.ciudad = new SimpleStringProperty(rs.getString("ciudad"));
				e.estado = new SimpleStringProperty(rs.getString("estado"));
				e.codigopostal = new SimpleStringProperty(rs.getString("codigopostal"));
				e.correo = new SimpleStringProperty(rs.getString("correo"));
				e.usuario = new SimpleStringProperty(rs.getString("usuario"));
				e.contrasenia = new SimpleStringProperty(rs.getString("contrasenia"));
				e.privilegio = new SimpleStringProperty(rs.getString("privilegio"));
				e.fechaingreso = new SimpleStringProperty(rs.getString("fechaingreso"));
				e.fechadesalida = new SimpleStringProperty(rs.getString("fechadesalida"));
				informacion.add(e);
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
	
	public ObservableList<Empleado> getEmpleado() throws SQLException{
		ResultSet rs = null;
		try {
			String sql  ="select idempleado,nombre1,nombre2,apellidopaterno,apellidomaterno,telefono1,telefono2,celular1,celular2,domicilio,numerointerior,numeroexterior,calle,localidad,ciudad,estado,codigopostal,correo,usuario,contrasenia,privilegio,fechaingreso,fechadesalida from empleados where estatus = '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			
			while(rs.next()){
				Empleado e = new Empleado();
				e.idempleado = new SimpleIntegerProperty(rs.getInt("idempleado"));
				e.nombre1 = new SimpleStringProperty(rs.getString("nombre1"));
				e.nombre2 = new SimpleStringProperty(rs.getString("nombre2"));
				e.apellidopaterno = new SimpleStringProperty(rs.getString("apellidopaterno"));
				e.apellidomaterno = new SimpleStringProperty(rs.getString("apellidomaterno"));
				e.telefono1 = new SimpleStringProperty(rs.getString("telefono1"));
				e.telefono2 = new SimpleStringProperty(rs.getString("telefono2"));
				e.celular1 = new SimpleStringProperty(rs.getString("celular1"));
				e.celular2 = new SimpleStringProperty(rs.getString("celular2"));
				e.domicilio = new SimpleStringProperty(rs.getString("domicilio"));
				e.numerointerior = new SimpleStringProperty(rs.getString("numerointerior"));
				e.numeroexterior = new SimpleStringProperty(rs.getString("numeroexterior"));
				e.calle = new SimpleStringProperty(rs.getString("calle"));
				e.localidad = new SimpleStringProperty(rs.getString("localidad"));
				e.ciudad = new SimpleStringProperty(rs.getString("ciudad"));
				e.estado = new SimpleStringProperty(rs.getString("estado"));
				e.codigopostal = new SimpleStringProperty(rs.getString("codigopostal"));
				e.correo = new SimpleStringProperty(rs.getString("correo"));
				e.usuario = new SimpleStringProperty(rs.getString("usuario"));
				e.contrasenia = new SimpleStringProperty(rs.getString("contrasenia"));
				e.privilegio = new SimpleStringProperty(rs.getString("privilegio"));
				e.fechaingreso = new SimpleStringProperty(rs.getString("fechaingreso"));
				e.fechadesalida = new SimpleStringProperty(rs.getString("fechadesalida"));
				informacion.add(e);
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
	//***************************************************************************************************
	//METODOS SET Y GET

	public String getUsuario() {
		return usuario.get();
	}
	public void setUsuario(StringProperty usuario) {
		this.usuario = usuario;
	}	
	public String getNombre1() {
		return nombre1.get();
	}
	public String getNombre2() {
		return nombre2.get();
	}
	public String getApellidopaterno() {
		return apellidopaterno.get();
	}
	public String getApellidomaterno() {
		return apellidomaterno.get();
	}
	public String getTelefono1() {
		return telefono1.get();
	}
	public String getTelefono2() {
		return telefono2.get();
	}
	public String getCelular1() {
		return celular1.get();
	}
	public String getCelular2() {
		return celular2.get();
	}
	public String getDomicilio() {
		return domicilio.get();
	}
	public String getNumerointerior() {
		return numerointerior.get();
	}
	public String getNumeroexterior() {
		return numeroexterior.get();
	}
	public String getCalle() {
		return calle.get();
	}
	public String getLocalidad() {
		return localidad.get();
	}
	public String getCiudad() {
		return ciudad.get();
	}
	public String getEstado() {
		return estado.get();
	}
	public String getCodigopostal() {
		return codigopostal.get();
	}
	public String getCorreo() {
		return correo.get();
	}
	public String getContrasenia() {
		return contrasenia.get();
	}
	public String getPrivilegio() {
		return privilegio.get();
	}
	public String getFechaingreso() {
		return fechaingreso.get();
	}
	public String getFechadesalida() {
		return fechadesalida.get();
	}
	public Integer getIdempleado() {
		return idempleado.get();
	}

	public void setNombre1(StringProperty nombre1) {
		this.nombre1 = nombre1;
	}
	public void setNombre2(StringProperty nombre2) {
		this.nombre2 = nombre2;
	}
	public void setApellidopaterno(StringProperty apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}
	public void setApellidomaterno(StringProperty apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}
	public void setTelefono1(StringProperty telefono1) {
		this.telefono1 = telefono1;
	}
	public void setTelefono2(StringProperty telefono2) {
		this.telefono2 = telefono2;
	}
	public void setCelular1(StringProperty celular1) {
		this.celular1 = celular1;
	}
	public void setCelular2(StringProperty celular2) {
		this.celular2 = celular2;
	}
	public void setDomicilio(StringProperty domicilio) {
		this.domicilio = domicilio;
	}
	public void setNumerointerior(StringProperty numerointerior) {
		this.numerointerior = numerointerior;
	}
	public void setNumeroexterior(StringProperty numeroexterior) {
		this.numeroexterior = numeroexterior;
	}
	public void setCalle(StringProperty calle) {
		this.calle = calle;
	}

	public void setLocalidad(StringProperty localidad) {
		this.localidad = localidad;
	}
	public void setCiudad(StringProperty ciudad) {
		this.ciudad = ciudad;
	}
	public void setEstado(StringProperty estado) {
		this.estado = estado;
	}
	public void setCodigopostal(StringProperty codigopostal) {
		this.codigopostal = codigopostal;
	}
	public void setCorreo(StringProperty correo) {
		this.correo = correo;
	}
	public void setContrasenia(StringProperty contrasenia) {
		this.contrasenia = contrasenia;
	}
	public void setPrivilegio(StringProperty privilegio) {
		this.privilegio = privilegio;
	}
	public void setFechaingreso(StringProperty fechaingreso) {
		this.fechaingreso = fechaingreso;
	}
	public void setFechadesalida(StringProperty fechadesalida) {
		this.fechadesalida = fechadesalida;
	}
	public void setIdempleado(IntegerProperty idempleado) {
		this.idempleado = idempleado;
	}
	

}
