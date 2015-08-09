package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private StringProperty nombre1;
	

	private Repair rep;
	
	
	private ObservableList<Repair> elementos;
	
	public Repair(){
		con = Conexion.getInstancia();
		 idreparacion = new SimpleIntegerProperty();
		nombre1 = new SimpleStringProperty();
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

	
	
	public ObservableList<Repair> getRepair() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select r.idreparacion, r.idchecklist, c.idcliente,l.nombre1,l.apellidopaterno, c.idmotocicleta,m.modelo, r.dejarefaccion, r.descripcionrefaccion, r.anticipo, r.dejallaves, r.descripcionllaves, r.fechadeentrega, r.activo from registroreparacion r join checklist c on r.idchecklist = c.idchecklist join cliente l on c.idcliente = l.idcliente join motocicleta m on c.idmotocicleta = m.idmotocicleta where r.activo = '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Repair re = new Repair();
				re.idreparacion.set(rs.getInt("idreparacion"));
				re.nombre1.set(rs.getString("nombre1"));
				
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
	
	public String toString(){
		return this.nombre1.getValue();
	}
	
}
