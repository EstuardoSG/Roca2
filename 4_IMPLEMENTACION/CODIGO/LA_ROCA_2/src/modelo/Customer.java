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

public class Customer {

	private StringProperty nombre1;
	private IntegerProperty idcliente;
	private Conexion con;
	private ObservableList<Customer> elementos;
	
	public Customer(){
		nombre1 = new SimpleStringProperty();
		idcliente = new SimpleIntegerProperty();
		con = Conexion.getInstancia();
	}

	public String getNombre1() {
		return nombre1.get();
	}
	public void setNombre1(StringProperty nombre1) {
		this.nombre1 = nombre1;
	}
	public Integer getIdcliente() {
		return idcliente.get();
	}
	public void setIdcliente(IntegerProperty idcliente) {
		this.idcliente = idcliente;
	}
	
	public ObservableList<Customer> getCustomer() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select idcliente, nombre1 from cliente where activo =  '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Customer cus = new Customer();
				cus.nombre1.set(rs.getString("nombre1"));
				cus.idcliente.set(rs.getInt("idcliente"));
				elementos.add(cus); //Se agrega el objeto a la lista 
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
		return nombre1.getValue();
	}
}
