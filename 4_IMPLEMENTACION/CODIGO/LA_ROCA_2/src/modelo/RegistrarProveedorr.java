package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.Errores;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegistrarProveedorr {
	private Errores er;
	private Conexion con;
	private ObservableList<RegistrarProveedorr> datos;
	private StringProperty nombreEmpresa, domicilio, numeroInterior, numeroExterior, calle, localidad,
	ciudad, estado, codigoPostal, telefonoEmpresa;
	private IntegerProperty  idProveedorContacto, idproveedor;


	public Company p;
	
	public RegistrarProveedorr(){
		nombreEmpresa = domicilio = numeroInterior = numeroExterior = calle = localidad =
				ciudad = estado = codigoPostal = telefonoEmpresa = new SimpleStringProperty();
		idProveedorContacto = idproveedor = new SimpleIntegerProperty();
		p = new Company();
		con = Conexion.getInstancia();
		er = new Errores();
	}
	
	// #region Getters and Setters

	public Integer getIdproveedor() {
		return idproveedor.get();
	}

	public void setIdproveedor(IntegerProperty idproveedor) {
		this.idproveedor = idproveedor;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa.get();
	}

	public void setNombreEmpresa(StringProperty nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDomicilio() {
		return domicilio.get();
	}

	public void setDomicilio(StringProperty domicilio) {
		this.domicilio = domicilio;
	}

	public String getNumeroInterior() {
		return numeroInterior.get();
	}

	public void setNumeroInterior(StringProperty numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getNumeroExterior() {
		return numeroExterior.get();
	}

	public void setNumeroExterior(StringProperty numeroExterior) {
		this.numeroExterior = numeroExterior;
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

	public String getCodigoPostal() {
		return codigoPostal.get();
	}

	public void setCodigoPostal(StringProperty codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTelefonoEmpresa() {
		return telefonoEmpresa.get();
	}

	public void setTelefonoEmpresa(StringProperty telefonoEmpresa) {
		this.telefonoEmpresa = telefonoEmpresa;
	}

	public Integer getIdProveedorContacto() {
		return idProveedorContacto.get();
	}

	public void setIdProveedorContacto(IntegerProperty idProveedorContacto) {
		this.idProveedorContacto = idProveedorContacto;
	}
	
	public Company getP() {
		return p;
	}

	public void setP(Company p) {
		this.p = p;
	}

		// #region select
		public ObservableList<RegistrarProveedorr> getProveedorContacto(boolean estatus) throws SQLException{
			ResultSet rs = null;
			try {
				String sql = "select idproveedor, nombreempresa, domicilio, numerointerior, numeroexterior, calle, localidad, ciudad, estado, codigopostal, telefonoempresa from proveedor where estatus= '1';";
				con.conectar();
				PreparedStatement comando =  con.getConexion().prepareStatement(sql);
				rs = comando.executeQuery();
				datos = FXCollections.observableArrayList();
				while(rs.next()){
					RegistrarProveedorr c = new RegistrarProveedorr();
					c.idproveedor = new SimpleIntegerProperty(rs.getInt("idproveedor"));
					c.nombreEmpresa = new SimpleStringProperty(rs.getString("nombreempresa"));
					c.domicilio = new SimpleStringProperty(rs.getString("domicilio"));
					c.numeroInterior = new SimpleStringProperty(rs.getString("numerointerior"));
					c.numeroExterior = new SimpleStringProperty(rs.getString("numeroexterior"));
					c.calle = new SimpleStringProperty(rs.getString("calle"));
					c.localidad = new SimpleStringProperty(rs.getString("localidad"));
					c.ciudad = new SimpleStringProperty(rs.getString("ciudad"));
					c.estado = new SimpleStringProperty(rs.getString("estado"));
					c.codigoPostal = new SimpleStringProperty(rs.getString("codigopostal"));
					c.telefonoEmpresa = new SimpleStringProperty(rs.getString("telefonoempresa"));
					
					datos.add(c);
				}
			} catch (Exception e){
				er.printLog(e.getMessage(), this.getClass().toString());
			}
			finally {
				rs.close();
				con.desconectar();
			}
			return datos;
		}
		// #endregion
	
	// #region metodos para proveedor
	
	// #region insertar proveedor
	public boolean insertp(){
		try{
			String sql = "select fn_agregarproveedor(?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setString(1, this.getNombreEmpresa());
			comando.setString(2, this.getDomicilio());
			comando.setString(3, this.getNumeroInterior());
			comando.setString(4, this.getNumeroExterior());
			comando.setString(5, this.getCalle());
			comando.setString(6, this.getLocalidad());
			comando.setString(7, this.getCiudad());
			comando.setString(8, this.getEstado());
			comando.setString(9, this.getCodigoPostal());
			comando.setString(10, this.getTelefonoEmpresa());
			System.out.println(comando.toString());
			comando.execute();
			return true;
		} catch (Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	// #endregion
	
	// #region borrar proveedor
	public boolean eliminarp(){
		try {
			String sql = "select fn_eliminarproveedpr (?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdproveedor());
			comando.execute();
			return true;
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		finally {
			con.desconectar();
		}
	}
	// #endregion
	
	// #reion actualizar proveedor
	public boolean actualizarp(){
		try{
			String sql = "select fn_actualizarproveedor(?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setString(1, this.getNombreEmpresa());
			comando.setString(2, this.getDomicilio());
			comando.setString(3, this.getNumeroInterior());
			comando.setString(4, this.getNumeroExterior());
			comando.setString(5, this.getCalle());
			comando.setString(6, this.getLocalidad());
			comando.setString(7, this.getCiudad());
			comando.setString(8, this.getEstado());
			comando.setString(9, this.getCodigoPostal());
			comando.setString(10, this.getTelefonoEmpresa());
			comando.setInt(11, this.getIdproveedor());
			comando.execute();
			return true;
		} catch (Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	// #endregion
	
}
