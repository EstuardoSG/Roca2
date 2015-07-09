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

public class MarcaCombo {
	
	private StringProperty nombremarca;
	private IntegerProperty idmarca;
	private Conexion con;
	private ObservableList<MarcaCombo> elementos;
	
	
	public StringProperty getNombremarca() {
		return nombremarca;
	}
	public IntegerProperty getIdmarca() {
		return idmarca;
	}
	public void setIdmarca(IntegerProperty idmarca) {
		this.idmarca = idmarca;
	}
	public ObservableList<MarcaCombo> getElementos() {
		return elementos;
	}
	public void setElementos(ObservableList<MarcaCombo> elementos) {
		this.elementos = elementos;
	}
	
	public void setNombre(StringProperty nombremarca) {
		this.nombremarca = nombremarca;
	}
	
	
	
	public MarcaCombo(){
		con = Conexion.getInstancia();
		nombremarca= new SimpleStringProperty();
		idmarca = new SimpleIntegerProperty();
		
	}
	
	public ObservableList<MarcaCombo> getMarcaCombo() throws SQLException{
		ResultSet rs = null;
		try{
			String sql ="select l.idmarca, l.nombre as nombremarca from marca l where l.activo = '1'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				MarcaCombo l = new MarcaCombo();
				l.nombremarca.set(rs.getString("nombremarca"));
				l.idmarca.set(rs.getInt("idmarca"));
				elementos.add(l);//se agrega el objeto a la lista
			}
		}catch (Exception e){
			e.printStackTrace();//se imprime la traza del error
		}
		finally{
			con.desconectar();//se cierra conexion
			rs.close();
		}
		return elementos;
	}
	
	public String toString(){
		return nombremarca.getValue();
	}

}
