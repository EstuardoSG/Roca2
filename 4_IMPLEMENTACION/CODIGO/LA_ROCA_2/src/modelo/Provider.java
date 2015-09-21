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

public class Provider {

	private Errores er;
	private Conexion con;
	private StringProperty nombre;
	private IntegerProperty idproveedor;
	private ObservableList<Provider> elementos;
	
	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}
	public Integer getIdproveedor() {
		return idproveedor.get();
	}
	public void setIdproveedor(IntegerProperty idproveedor) {
		this.idproveedor = idproveedor;
	}
	
	
	
	
	
	public Provider(){
		con = Conexion.getInstancia();
		nombre = new SimpleStringProperty();
		idproveedor = new SimpleIntegerProperty();
		er = new Errores();
	}
	
	public ObservableList<Provider> getProvider() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select idproveedor, nombreempresa from proveedor where estatus =  '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Provider pr = new Provider();
				pr.nombre.set(rs.getString("nombreempresa"));
				pr.idproveedor.set(rs.getInt("idproveedor"));
				elementos.add(pr); //Se agrega el objeto a la lista 
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
		return nombre.getValue();
	}
}
