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

public class Brand {

	private StringProperty nombre;
	private IntegerProperty idmarca;
	private Conexion con;
	private ObservableList<Brand> elementos1;
	
	public Brand(){
		con = Conexion.getInstancia();
		nombre = new SimpleStringProperty();
		idmarca = new SimpleIntegerProperty();
	}

	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}
	public Integer getIdmarca() {
		return idmarca.get();
	}
	public void setIdmarca(IntegerProperty idmarca) {
		this.idmarca = idmarca;
	}

	public ObservableList<Brand> getBrand() throws SQLException{
		ResultSet rs=null;
		try {
			String sql="select idmarca, nombre from marca where activo =  '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos1 = FXCollections.observableArrayList();
			while(rs.next()){
				Brand br = new Brand();
				br.nombre.set(rs.getString("nombre"));
				br.idmarca.set(rs.getInt("idmarca"));
				elementos1.add(br); //Se agrega el objeto a la lista 
			}		
		} catch (Exception e) {
			e.printStackTrace();//Se imprime la traza del error
		}
		finally{
			con.desconectar(); //Se cierra la conexión
			rs.close();
		}
		return elementos1;
	}
	
	public String toString(){
		return nombre.getValue();
	}
}
