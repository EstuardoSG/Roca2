package modelo;

import java.math.BigDecimal;
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

public class RestaurarAlmacen {
	private StringProperty nombre, modelo, iva;
	private IntegerProperty idrefaccionalmacen, existencia, stockminimo, stockmaximo;
	private FloatProperty precio1, precio2;
	private MarcaCombo l;
	private ObservableList<RestaurarAlmacen> elementos;
	private Conexion con;
	

	public RestaurarAlmacen(){
		nombre = modelo = iva = new SimpleStringProperty();
		idrefaccionalmacen = existencia = stockminimo = stockmaximo = new SimpleIntegerProperty();
		precio1 = precio2 = new SimpleFloatProperty();
		l = new MarcaCombo();	
		con = Conexion.getInstancia();
	}

	// #region Getters and Setters
	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	public String getModelo() {
		return modelo.get();
	}

	public void setModelo(StringProperty modelo) {
		this.modelo = modelo;
	}

	public String getIva() {
		return iva.get();
	}

	public void setIva(StringProperty iva) {
		this.iva = iva;
	}

	public Integer getIdrefaccionalmacen() {
		return idrefaccionalmacen.get();
	}

	public void setIdrefaccionalmacen(IntegerProperty idrefaccionalmacen) {
		this.idrefaccionalmacen = idrefaccionalmacen;
	}

	public Integer getExistencia() {
		return existencia.get();
	}

	public void setExistencia(IntegerProperty existencia) {
		this.existencia = existencia;
	}

	public Integer getStockminimo() {
		return stockminimo.get();
	}

	public void setStockminimo(IntegerProperty stockminimo) {
		this.stockminimo = stockminimo;
	}

	public Integer getStockmaximo() {
		return stockmaximo.get();
	}

	public void setStockmaximo(IntegerProperty stockmaximo) {
		this.stockmaximo = stockmaximo;
	}

	public Float getPrecio1() {
		return precio1.get();
	}

	public void setPrecio1(FloatProperty precio1) {
		this.precio1 = precio1;
	}

	public Float getPrecio2() {
		return precio2.get();
	}

	public void setPrecio2(FloatProperty precio2) {
		this.precio2 = precio2;
	}
	
	public MarcaCombo getL() {
		return l;
	}


	public void setL(MarcaCombo l) {
		this.l = l;
	}
	

	public ObservableList<RestaurarAlmacen> getAlmacen(boolean activo) throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select f.idrefaccionalmacen, f.idmarca, l.nombre as nombremarca, f.nombre, f.modelo, f.precio1, f.precio2, f.iva, f.existencia, f.stockminimo, f.stockmaximo from almacen f inner join marca l on l.idmarca=f.idmarca where f.activo = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs= comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while(rs.next()){
				RestaurarAlmacen f = new RestaurarAlmacen();
				f.idrefaccionalmacen = new SimpleIntegerProperty(rs.getInt("idrefaccionalmacen"));
				f.l.setIdmarca(new SimpleIntegerProperty(rs.getInt("idmarca")));
				f.l.setNombre(new SimpleStringProperty(rs.getString("nombremarca")));
				f.nombre = new SimpleStringProperty(rs.getString("nombre"));
				f.modelo = new SimpleStringProperty(rs.getString("modelo"));
				f.precio1=new SimpleFloatProperty(rs.getFloat("precio1"));
				f.precio2=new SimpleFloatProperty(rs.getFloat("precio2"));
				f.iva= new SimpleStringProperty(rs.getString("iva"));
				f.existencia= new SimpleIntegerProperty(rs.getInt("existencia"));
				f.stockminimo= new SimpleIntegerProperty(rs.getInt("stockminimo"));
				f.stockmaximo= new SimpleIntegerProperty(rs.getInt("stockmaximo"));
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
				
	public boolean restaurar(){
		try{
			String sql = "select fn_restaurara(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdrefaccionalmacen());
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
}
