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

public class RestaurarC {
	private Conexion con;
	private ObservableList<RestaurarC> datos;
	private StringProperty nombreEmpresa, domicilio, numeroInterior, numeroExterior, calle, localidad,
	ciudad, estado, codigoPostal, telefonoEmpresa, 
	nombre, apellidoPaterno, apellidoMaterno, celular, correo;
	private IntegerProperty  idProveedorContacto, idproveedor;
	
	public Company p;


	
	public RestaurarC(){
		nombreEmpresa = domicilio = numeroInterior = numeroExterior = calle = localidad =
				ciudad = estado = codigoPostal = telefonoEmpresa = nombre = apellidoPaterno =
				apellidoMaterno = celular = correo = new SimpleStringProperty();
		idProveedorContacto = idproveedor = new SimpleIntegerProperty();
		p = new Company();
		con = Conexion.getInstancia();
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

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno.get();
	}

	public void setApellidoPaterno(StringProperty apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno.get();
	}

	public void setApellidoMaterno(StringProperty apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getCelular() {
		return celular.get();
	}

	public void setCelular(StringProperty celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo.get();
	}

	public void setCorreo(StringProperty correo) {
		this.correo = correo;
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
		public ObservableList<RestaurarC> getRestaurarC(boolean estatus) throws SQLException{
			ResultSet rs = null;
			try {
				String sql = "select c.idproveedorcontacto, c.idproveedor, p.nombreempresa, p.domicilio, p.numerointerior, p.numeroexterior, p.calle, p.localidad, p.ciudad, p.estado, p.codigopostal, p.telefonoempresa,c.nombre, c.apellidopaterno, c.apellidomaterno, c.celular, c.correo, c.estatus, c.fechainicial, c.fechafinal from proveedorcontacto c join proveedor p on p.idproveedor = c.idproveedor where c.estatus = '0';";
				con.conectar();
				PreparedStatement comando =  con.getConexion().prepareStatement(sql);
				rs = comando.executeQuery();
				datos = FXCollections.observableArrayList();
				while(rs.next()){
					RestaurarC c = new RestaurarC();
					c.idProveedorContacto = new SimpleIntegerProperty(rs.getInt("idProveedorContacto"));
					c.p.setIdProveedor(new SimpleIntegerProperty(rs.getInt("idProveedor")));
					c.p.setNombreEmpresa(new SimpleStringProperty(rs.getString("nombreEmpresa")));
					c.nombre = new SimpleStringProperty(rs.getString("nombre"));
					c.apellidoPaterno = new SimpleStringProperty(rs.getString("apellidoPaterno"));
					c.apellidoMaterno = new SimpleStringProperty(rs.getString("apellidoMaterno"));
					c.celular = new SimpleStringProperty(rs.getString("celular"));
					c.correo = new SimpleStringProperty(rs.getString("correo"));
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
				e.printStackTrace();
			}
			finally {
				rs.close();
				con.desconectar();
			}
			return datos;
		}
		// #endregion
	
	
		
	// #region metodos para RestaurarContacto
		
		// #region Restaurar proveedorContacto
		public boolean restaurarc(){
			try {
				String sql = "select fn_restaurarC(?)";
				con.conectar();
				PreparedStatement comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.getIdProveedorContacto());
				comando.execute();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			finally {
				con.desconectar();
			}
		}
		// #endregion
}