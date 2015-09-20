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

public class Marca {
	private StringProperty nombre;
	private IntegerProperty idmarca;
	private ObservableList<Marca> elementos;
	private Conexion con;
	

	public Marca(){
		nombre = new SimpleStringProperty();
		con = Conexion.getInstancia();
	}
	
	
	// #region Getters and Setters
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

	
	public ObservableList<Marca> getMarcas(boolean activo) throws SQLException{
		// TODO Auto-generated method stub
		ResultSet rs = null;
		try {
			String sql="";
			if(activo)
			sql  ="select f.idmarca, f.nombre from marca f where f.activo = '1'";
			else
				sql  ="select f.idmarca, f.nombre from marca f where f.activo = '0'";	
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				Marca f = new Marca();
				
				//new line write in this method
				f.idmarca = new SimpleIntegerProperty(rs.getInt("idmarca"));
				f.nombre = new SimpleStringProperty(rs.getString("nombre"));	
				elementos.add(f);
				
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			rs.close();
			con.desconectar();			
		}
		return elementos;
	}	
	
	
		
		
		public boolean insertar(){
			try{
				String sql= "select fn_agregarmarca (?)";
				con.conectar();
				PreparedStatement comando = con.getConexion().prepareStatement(sql);
				comando.setString(1,this.getNombre());
				
				comando.execute();
				return  true;
				
			}catch(Exception e){	
				return false;
			}
			
		}
		
		public boolean eliminar(){
			try {
				
				String sql="select fn_eliminarmarca (?)";
				con.conectar();
				PreparedStatement comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.getIdmarca());
				comando.execute();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			finally{
				con.desconectar();
			}
		}
		
		
		
		public boolean actualizar (){
			{
				try{
					String sql= "select fn_actualizarmarca (?,?)";
					con.conectar();
					PreparedStatement comando = con.getConexion().prepareStatement(sql);
					comando.setString(1,this.getNombre());
					comando.setInt(2, this.getIdmarca());
					comando.execute();
					return  true;
				}catch (Exception ex){
					ex.printStackTrace();
					return false;
				}
				finally{
					con.desconectar();
				}
			}
		}


		


		
	

		
}
