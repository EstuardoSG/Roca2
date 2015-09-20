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

public class Motorcycle {

	private StringProperty modelo;
	private IntegerProperty idmotocicleta;
	private Conexion con;
	private ObservableList<Motorcycle> elementos;
	
	public Motorcycle(){
		modelo = new SimpleStringProperty();
		idmotocicleta = new SimpleIntegerProperty();
		con = Conexion.getInstancia();
	}

	public String getModelo() {
		return modelo.get();
	}
	public void setModelo(StringProperty modelo) {
		this.modelo = modelo;
	}
	public Integer getIdmotocicleta() {
		return idmotocicleta.get();
	}
	public void setIdmotocicleta(IntegerProperty idmotocicleta) {
		this.idmotocicleta = idmotocicleta;
	}
	
	public ObservableList<Motorcycle> getMotorCycle() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select idmotocicleta, modelo from motocicleta where activo =  '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Motorcycle mc = new Motorcycle();
				mc.modelo.set(rs.getString("modelo"));
				mc.idmotocicleta.set(rs.getInt("idmotocicleta"));
				elementos.add(mc); //Se agrega el objeto a la lista 
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
		return modelo.getValue();
	}
}
