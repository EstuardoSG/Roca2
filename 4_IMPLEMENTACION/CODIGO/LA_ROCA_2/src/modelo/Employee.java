package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.plaf.synth.SynthSeparatorUI;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Employee {

	private StringProperty nombre1, nombre2, apellidopaterno, apellidomaterno;
	
	private IntegerProperty idempleado;
	private Conexion con;
	private ObservableList<Employee> elementos;

	public Employee(){
		con = Conexion.getInstancia();
		nombre1 = new SimpleStringProperty();
		idempleado = new SimpleIntegerProperty();
	}

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
	public Integer getIdempleado() {
		return idempleado.get();
	}
	public void setIdempleado(IntegerProperty idempleado) {
		this.idempleado = idempleado;
	}
	

	public ObservableList<Employee> getEmployee() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select idempleado, nombre1, nombre2  from empleados where estatus = '1'"; 
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Employee em = new Employee();
				em.nombre1.set(rs.getString("nombre1"));
				em.idempleado.set(rs.getInt("idempleado"));
				elementos.add(em); //Se agrega el objeto a la lista 
				System.out.println(em);
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
