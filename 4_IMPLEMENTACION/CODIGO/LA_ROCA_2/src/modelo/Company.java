package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Company {
	private SimpleStringProperty nombreEmpresa;
	private SimpleIntegerProperty idProveedor;
	private Conexion con;
	private ObservableList<Company> datos;

	public Company(){
		con = Conexion.getInstancia();
		nombreEmpresa = new SimpleStringProperty();
		idProveedor = new SimpleIntegerProperty();
	}

	public String getNombreEmpresa() {
		return nombreEmpresa.get();
	}

	public void setNombreEmpresa(SimpleStringProperty nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Integer getIdProveedor() {
		return idProveedor.get();
	}

	public void setIdProveedor(SimpleIntegerProperty idProveedor) {
		this.idProveedor = idProveedor;
	}

	public ObservableList<Company> getCompany() throws SQLException {
		ResultSet rs=null;
		try{
			String sql ="select idProveedor, nombreEmpresa from proveedor";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			datos = FXCollections.observableArrayList();
			while(rs.next()){
				Company p = new Company();
				p.nombreEmpresa.set(rs.getString("nombreEmpresa"));
				p.idProveedor.set(rs.getInt("idProveedor"));
				datos.add(p);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
			rs.close();
		}
		return datos;
	}
	
	public String toString(){
		return nombreEmpresa.getValue();
	}
	
	

}
