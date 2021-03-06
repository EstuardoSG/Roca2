package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.Errores;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RestaurarMotocicleta {
	
	private Errores er;
	private Conexion con;
	private ObservableList<RestaurarMotocicleta> informacion;
	private StringProperty modelo, motor, color, descripcionMotocicleta, fecha;

	private IntegerProperty  idmotocicleta;
	private BooleanProperty placa;
	
	private Empleado em;
	private Brand br;
	
	public Brand getBr() {
		return br;
	}
	public void setBr(Brand br) {
		this.br = br;
	}
	public Empleado getEm() {
		return em;
	}
	public void setEm(Empleado em) {
		this.em = em;
	}
	
	public String getFecha() {
		return fecha.get();
	}
	public void setFecha(StringProperty fecha) {
		this.fecha = fecha;
	}
	public String getModelo() {
		return modelo.get();
	}
	public void setModelo(StringProperty modelo) {
		this.modelo = modelo;
	}
	public String getMotor() {
		return motor.get();
	}
	public void setMotor(StringProperty motor) {
		this.motor = motor;
	}
	public String getColor() {
		return color.get();
	}
	public void setColor(StringProperty color) {
		this.color = color;
	}
	public String getDescripcionMotocicleta() {
		return descripcionMotocicleta.get();
	}
	public void setDescripcionMotocicleta(StringProperty descripcionMotocicleta) {
		this.descripcionMotocicleta = descripcionMotocicleta;
	}
	
	public Integer getIdmotocicleta() {
		return idmotocicleta.get();
	}
	public void setIdmotocicleta(IntegerProperty idmotocicleta) {
		this.idmotocicleta = idmotocicleta;
	}
	public Boolean getPlaca() {
		return placa.get();
	}
	public void setPlaca(BooleanProperty placa) {
		this.placa = placa;
	}
	
	public RestaurarMotocicleta(){
		modelo = motor = color = descripcionMotocicleta = fecha = new SimpleStringProperty();
		idmotocicleta = new SimpleIntegerProperty();
		placa = new SimpleBooleanProperty();
		con = Conexion.getInstancia();
		em = new Empleado();
		br = new Brand();
		er = new Errores();
	}
	
	public ObservableList<RestaurarMotocicleta> getMotocicleta(boolean estatus) throws SQLException{
		ResultSet rs = null;
		try{
			String sql = "select m.idmotocicleta, m.idempleado, e.nombre1, m.idmarca, r.nombre, m.modelo, m.motor, m.color, m.placa, m.fechahoraregistro, m.descripcionmotocicleta, m.activo from motocicleta m join empleados e on m.idempleado = e.idempleado join marca r on m.idmarca = r.idmarca where m.activo = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs=comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			while(rs.next()){
				RestaurarMotocicleta m = new RestaurarMotocicleta();
				m.idmotocicleta = new SimpleIntegerProperty(rs.getInt("idMotocicleta"));
				m.em.setIdempleado(new SimpleIntegerProperty(rs.getInt("idempleado")));
				m.em.setNombre1(new SimpleStringProperty(rs.getString("nombre1")));
				m.br.setIdmarca(new SimpleIntegerProperty(rs.getInt("idmarca")));
				m.br.setNombre(new SimpleStringProperty(rs.getString("nombre")));
				m.modelo = new SimpleStringProperty(rs.getString("modelo"));
				m.motor = new SimpleStringProperty(rs.getString("motor"));
				m.color = new SimpleStringProperty(rs.getString("color"));
				m.placa = new SimpleBooleanProperty(rs.getBoolean("placa"));
				m.fecha = new SimpleStringProperty(rs.getString("fechahoraregistro"));
				m.descripcionMotocicleta = new SimpleStringProperty(rs.getString("descripcionMotocicleta"));
				informacion.add(m);
			}
		}catch (Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return informacion;
	}
	
	public boolean restaurar(){
		try {
			String sql="select fn_restaurarm(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdmotocicleta());
			comando.execute();
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