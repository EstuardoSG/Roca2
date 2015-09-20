package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Reparacion {

	private IntegerProperty idreparacion, idchecklist;
	private StringProperty nombre1, apellidopaterno, modelo, descripcionrefaccion, descripcionllaves, fechaentrega;
	private BooleanProperty dejarefaccion, dejallaves;
	private FloatProperty anticipo;
	
	private Conexion con;
	private ObservableList<Reparacion> elementos;
	
	public Reparacion(){
		con = Conexion.getInstancia();
		idreparacion = idchecklist = new SimpleIntegerProperty();
		nombre1 = apellidopaterno = modelo = descripcionrefaccion = descripcionllaves = fechaentrega = new SimpleStringProperty();
		dejarefaccion = dejallaves = new SimpleBooleanProperty();
		anticipo = new SimpleFloatProperty();
	}

	

	public Float getAnticipo() {
		return anticipo.get();
	}
	public void setAnticipo(FloatProperty anticipo) {
		this.anticipo = anticipo;
	}
	public Integer getIdchecklist() {
		return idchecklist.get();
	}

	public void setIdchecklist(IntegerProperty idchecklist) {
		this.idchecklist = idchecklist;
	}

	public String getNombre1() {
		return nombre1.get();
	}

	public void setNombre1(StringProperty nombre1) {
		this.nombre1 = nombre1;
	}

	public String getApellidopaterno() {
		return apellidopaterno.get();
	}
	public void setApellidopaterno(StringProperty apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}
	public String getModelo() {
		return modelo.get();
	}
	public void setModelo(StringProperty modelo) {
		this.modelo = modelo;
	}
	public String getDescripcionrefaccion() {
		return descripcionrefaccion.get();
	}
	public void setDescripcionrefaccion(StringProperty descripcionrefaccion) {
		this.descripcionrefaccion = descripcionrefaccion;
	}
	public String getDescripcionllaves() {
		return descripcionllaves.get();
	}
	public void setDescripcionllaves(StringProperty descripcionllaves) {
		this.descripcionllaves = descripcionllaves;
	}
	public String getFechaentrega() {
		return fechaentrega.get();
	}
	public void setFechaentrega(StringProperty fechaentrega) {
		this.fechaentrega = fechaentrega;
	}
	public Boolean getDejarefaccion() {
		return dejarefaccion.get();
	}
	public void setDejarefaccion(BooleanProperty dejarefaccion) {
		this.dejarefaccion = dejarefaccion;
	}
	public Boolean getDejallaves() {
		return dejallaves.get();
	}
	public void setDejallaves(BooleanProperty dejallaves) {
		this.dejallaves = dejallaves;
	}
	public Integer getIdreparacion() {
		return idreparacion.get();
	}
	
	public void setIdreparacion(IntegerProperty idreparacion) {
		this.idreparacion = idreparacion;
	}



	public ObservableList<Reparacion> getReparacion(boolean estatus) throws SQLException{
		ResultSet rs=null;
		try {
			String sql = "";
			if(estatus)
				sql="select r.idreparacion, r.idchecklist, c.idcliente,l.nombre1,l.apellidopaterno, c.idmotocicleta,m.modelo, r.dejarefaccion, r.descripcionrefaccion, r.anticipo, r.dejallaves, r.descripcionllaves, r.fechadeentrega, r.activo from registroreparacion r join checklist c on r.idchecklist = c.idchecklist join cliente l on c.idcliente = l.idcliente join motocicleta m on c.idmotocicleta = m.idmotocicleta where r.activo = '1'";
			else
				sql = "select r.idreparacion, r.idchecklist, c.idcliente,l.nombre1,l.apellidopaterno, c.idmotocicleta,m.modelo, r.dejarefaccion, r.descripcionrefaccion, r.anticipo, r.dejallaves, r.descripcionllaves, r.fechadeentrega, r.activo from registroreparacion r join checklist c on r.idchecklist = c.idchecklist join cliente l on c.idcliente = l.idcliente join motocicleta m on c.idmotocicleta = m.idmotocicleta where r.activo = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Reparacion re = new Reparacion();
				re.idreparacion.set(rs.getInt("idreparacion"));
				re.idchecklist.set(rs.getInt("idchecklist"));
				re.nombre1.set(rs.getString("nombre1"));
				re.apellidopaterno.set(rs.getString("apellidopaterno"));
				re.modelo.set(rs.getString("modelo"));
				re.dejarefaccion.set(rs.getBoolean("dejarefaccion"));
				re.descripcionrefaccion.set(rs.getString("descripcionrefaccion"));
				re.anticipo.set(rs.getFloat("anticipo"));
				re.dejallaves.set(rs.getBoolean("dejallaves"));
				re.descripcionllaves.set(rs.getString("descripcionllaves"));
				re.fechaentrega.set(rs.getString("fechadeentrega"));
				
				elementos.add(re); //Se agrega el objeto a la lista 
			}		
		} catch (Exception e) {
			e.printStackTrace();//Se imprime la traza del error
		}
		finally{
			con.desconectar(); //Se cierra la conexión
			rs.close();
		}
		return elementos;
	}
	

}
