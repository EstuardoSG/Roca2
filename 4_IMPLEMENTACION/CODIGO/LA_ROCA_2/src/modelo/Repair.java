package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.Errores;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repair {

	private Conexion con;
	private IntegerProperty idreparacion;
	private StringProperty nombre1, apellidoPaterno, apellidoMaterno;

	private Repair rep;
	private Errores er;
	
	private ObservableList<Repair> elementos;
	
	public Repair(){
		con = Conexion.getInstancia();
		 idreparacion = new SimpleIntegerProperty();
		nombre1 = apellidoPaterno = apellidoMaterno = new SimpleStringProperty();
		er = new Errores();
	}


	public Repair getRep() {
		return rep;
	}


	public void setRep(Repair rep) {
		this.rep = rep;
	}

	public Integer getIdreparacion() {
		return idreparacion.get();
	}
	public void setIdreparacion(IntegerProperty idreparacion) {
		this.idreparacion = idreparacion;
	}
	public String getNombre1() {
		return nombre1.get();
	}
	public void setNombre1(StringProperty nombre1) {
		this.nombre1 = nombre1;
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

	
	
	public ObservableList<Repair> getRepair() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select r.idreparacion, r.idchecklist, c.idcliente, l.nombre1, l.apellidopaterno, l.apellidomaterno, c.idmotocicleta,m.modelo, r.dejarefaccion, r.descripcionrefaccion, r.anticipo, r.dejallaves, r.descripcionllaves, r.fechadeentrega, r.activo from registroreparacion r join checklist c on r.idchecklist = c.idchecklist join cliente l on c.idcliente = l.idcliente join motocicleta m on c.idmotocicleta = m.idmotocicleta where r.activo = '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Repair re = new Repair();
				re.idreparacion.set(rs.getInt("idreparacion"));
				re.nombre1 = new SimpleStringProperty(rs.getString("nombre1"));
				re.apellidoPaterno = new SimpleStringProperty(rs.getString("apellidopaterno"));
				re.apellidoMaterno = new SimpleStringProperty(rs.getString("apellidomaterno"));

				elementos.add(re); //Se agrega el objeto a la lista 
			}		
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());//Se imprime la traza del error
		}
		finally{
			con.desconectar(); //Se cierra la conexión
			rs.close();
		}
		return elementos;
	}
	
	public String toString(){
		return nombre1.getValue() + " " + apellidoPaterno.getValue() + " " + apellidoMaterno.getValue();
		
	}
	
}
