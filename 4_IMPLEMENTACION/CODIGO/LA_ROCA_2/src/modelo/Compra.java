package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Compra {

	private Conexion con;
	private ObservableList<Compra> informacion;
	private StringProperty refaccion,  fecha;

	private IntegerProperty  folioCompra, cantidad;
	private BooleanProperty estatus, activo, actual;
	private Empleado em;
	private Provider pr;
	
	public Boolean getActual() {
		return actual.get();
	}
	public void setActual(BooleanProperty actual) {
		this.actual = actual;
	}
	public String getRefaccion() {
		return refaccion.get();
	}
	public void setRefaccion(StringProperty refaccion) {
		this.refaccion = refaccion;
	}
	public Integer getCantidad() {
		return cantidad.get();
	}
	public void setCantidad(IntegerProperty cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha() {
		return fecha.get();
	}
	public void setFecha(StringProperty fecha) {
		this.fecha = fecha;
	}
	public Integer getFolioCompra() {
		return folioCompra.get();
	}
	public void setFolioCompra(IntegerProperty folioCompra) {
		this.folioCompra = folioCompra;
	}
	public Boolean getEstatus() {
		return estatus.get();
	}
	public void setEstatus(BooleanProperty estatus) {
		this.estatus = estatus;
	}
	public Boolean getActivo() {
		return activo.get();
	}
	public void setActivo(BooleanProperty activo) {
		this.activo = activo;
	}
	
	public Empleado getEm() {
		return em;
	}
	public void setEm(Empleado em) {
		this.em = em;
	}
	public Provider getPr() {
		return pr;
	}
	public void setPr(Provider pr) {
		this.pr = pr;
	}
	
	public Compra(){
		refaccion = fecha = new SimpleStringProperty();
		folioCompra = cantidad = new SimpleIntegerProperty();
		estatus = activo = new SimpleBooleanProperty();
		con = Conexion.getInstancia();
		em = new Empleado();
		pr = new Provider();
	}

	public ObservableList<Compra> getCompraActivo(boolean estatus) throws SQLException{
		ResultSet rs = null;
		try{
			String sql = "";
			if(estatus)
				sql = "select c.foliocompra, c.idempleado, e.nombre1, c.idproveedor, p.nombreempresa, c.refaccion, c.cantidad, c.fechacompra, c.estatus, c.activo, c.actual from compra c join empleados e on c.idempleado = e.idempleado join proveedor p on c.idproveedor = p.idproveedor where c.activo = '1'";
			else
				sql = "select c.foliocompra, c.idempleado, e.nombre1, c.idproveedor, p.nombreempresa, c.refaccion, c.cantidad, c.fechacompra, c.estatus, c.activo, c.actual from compra c join empleados e on c.idempleado = e.idempleado join proveedor p on c.idproveedor = p.idproveedor where c.activo = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs=comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			while(rs.next()){
				Compra co = new Compra();
				co.folioCompra = new SimpleIntegerProperty(rs.getInt("foliocompra"));
				co.em.setIdempleado(new SimpleIntegerProperty(rs.getInt("idempleado")));
				co.em.setNombre1(new SimpleStringProperty(rs.getString("nombre1")));
				co.pr.setIdproveedor(new SimpleIntegerProperty(rs.getInt("idproveedor")));
				co.pr.setNombre(new SimpleStringProperty(rs.getString("nombreempresa")));
				co.refaccion = new SimpleStringProperty(rs.getString("refaccion"));
				co.cantidad = new SimpleIntegerProperty(rs.getInt("cantidad"));
				co.fecha = new SimpleStringProperty(rs.getString("fechacompra"));
				co.estatus = new SimpleBooleanProperty(rs.getBoolean("estatus"));
				co.activo = new SimpleBooleanProperty(rs.getBoolean("activo"));
				co.actual = new SimpleBooleanProperty(rs.getBoolean("actual"));
				
				informacion.add(co);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return informacion;
	}
	
	public ObservableList<Compra> getCompraEstatus(boolean estatus) throws SQLException{
		ResultSet rs = null;
		try{
			String sql = "";
			if(estatus)
				sql = "select c.foliocompra, c.idempleado, e.nombre1, c.idproveedor, p.nombreempresa, c.refaccion, c.cantidad, c.fechacompra, c.estatus, c.activo, c.actual from compra c join empleados e on c.idempleado = e.idempleado join proveedor p on c.idproveedor = p.idproveedor where c.estatus = '1'";
			else
				sql = "select c.foliocompra, c.idempleado, e.nombre1, c.idproveedor, p.nombreempresa, c.refaccion, c.cantidad, c.fechacompra, c.estatus, c.activo, c.actual from compra c join empleados e on c.idempleado = e.idempleado join proveedor p on c.idproveedor = p.idproveedor where c.estatus = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs=comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			while(rs.next()){
				Compra co = new Compra();
				co.folioCompra = new SimpleIntegerProperty(rs.getInt("foliocompra"));
				co.em.setIdempleado(new SimpleIntegerProperty(rs.getInt("idempleado")));
				co.em.setNombre1(new SimpleStringProperty(rs.getString("nombre1")));
				co.pr.setIdproveedor(new SimpleIntegerProperty(rs.getInt("idproveedor")));
				co.pr.setNombre(new SimpleStringProperty(rs.getString("nombreempresa")));
				co.refaccion = new SimpleStringProperty(rs.getString("refaccion"));
				co.cantidad = new SimpleIntegerProperty(rs.getInt("cantidad"));
				co.fecha = new SimpleStringProperty(rs.getString("fechacompra"));
				co.estatus = new SimpleBooleanProperty(rs.getBoolean("estatus"));
				co.activo = new SimpleBooleanProperty(rs.getBoolean("activo"));
				co.actual = new SimpleBooleanProperty(rs.getBoolean("actual"));
				
				informacion.add(co);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return informacion;
	}
	
	
	public ObservableList<Compra> getCompraActual(boolean estatus) throws SQLException{
		ResultSet rs = null;
		try{
			String sql = "";
			if(estatus)
				sql = "select c.foliocompra, c.idempleado, e.nombre1, c.idproveedor, p.nombreempresa, c.refaccion, c.cantidad, c.fechacompra, c.estatus, c.activo, c.actual from compra c join empleados e on c.idempleado = e.idempleado join proveedor p on c.idproveedor = p.idproveedor where c.actual = '1'";
			else
				sql = "select c.foliocompra, c.idempleado, e.nombre1, c.idproveedor, p.nombreempresa, c.refaccion, c.cantidad, c.fechacompra, c.estatus, c.activo, c.actual from compra c join empleados e on c.idempleado = e.idempleado join proveedor p on c.idproveedor = p.idproveedor where c.actual = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs=comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			while(rs.next()){
				Compra co = new Compra();
				co.folioCompra = new SimpleIntegerProperty(rs.getInt("foliocompra"));
				co.em.setIdempleado(new SimpleIntegerProperty(rs.getInt("idempleado")));
				co.em.setNombre1(new SimpleStringProperty(rs.getString("nombre1")));
				co.pr.setIdproveedor(new SimpleIntegerProperty(rs.getInt("idproveedor")));
				co.pr.setNombre(new SimpleStringProperty(rs.getString("nombreempresa")));
				co.refaccion = new SimpleStringProperty(rs.getString("refaccion"));
				co.cantidad = new SimpleIntegerProperty(rs.getInt("cantidad"));
				co.fecha = new SimpleStringProperty(rs.getString("fechacompra"));
				co.estatus = new SimpleBooleanProperty(rs.getBoolean("estatus"));
				co.activo = new SimpleBooleanProperty(rs.getBoolean("activo"));
				co.actual = new SimpleBooleanProperty(rs.getBoolean("actual"));
				
				informacion.add(co);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return informacion;
	}
	
	
	public boolean guardar(){
		try {
			String sql="select fn_agregarCompra(?,?,?,?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getEm().getIdempleado());
			comando.setInt(2, this.getPr().getIdproveedor());
			comando.setString(3, this.getRefaccion());
			comando.setInt(4, this.getCantidad());
			System.out.println(comando.toString());
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
	
	
	public boolean recibido(){
		try {
			String sql="select  fn_listaCompra(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getFolioCompra());
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
	
	public boolean eliminar(){
		try {
			String sql="select fn_eliminarCompra(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getFolioCompra());
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
