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

public class RestaurarChecklist {

	public Customer cus;
	private Motorcycle mc;

	private Errores er;
	private Conexion con;
	private ObservableList<RestaurarChecklist> informacion;
	private StringProperty gasolina, luces, espejoizquierdo, espejoderecho, llantadelantera, 
	llantatrasera, fallas, diagnostico, fecha;
	private IntegerProperty  idchecklist;

	public Customer getCus() {
		return cus;
	}
	public void setCus(Customer cus) {
		this.cus = cus;
	}
	public Motorcycle getMc() {
		return mc;
	}
	public void setMc(Motorcycle mc) {
		this.mc = mc;
	}
	
	public String getGasolina() {
		return gasolina.get();
	}
	public void setGasolina(StringProperty gasolina) {
		this.gasolina = gasolina;
	}
	public String getLuces() {
		return luces.get();
	}
	public void setLuces(StringProperty luces) {
		this.luces = luces;
	}
	public String getEspejoizquierdo() {
		return espejoizquierdo.get();
	}
	public void setEspejoizquierdo(StringProperty espejoizquierdo) {
		this.espejoizquierdo = espejoizquierdo;
	}
	public String getEspejoderecho() {
		return espejoderecho.get();
	}
	public void setEspejoderecho(StringProperty espejoderecho) {
		this.espejoderecho = espejoderecho;
	}
	public String getLlantadelantera() {
		return llantadelantera.get();
	}
	public void setLlantadelantera(StringProperty llantadelantera) {
		this.llantadelantera = llantadelantera;
	}
	public String getLlantatrasera() {
		return llantatrasera.get();
	}
	public void setLlantatrasera(StringProperty llantatrasera) {
		this.llantatrasera = llantatrasera;
	}
	public String getFallas() {
		return fallas.get();
	}
	public void setFallas(StringProperty fallas) {
		this.fallas = fallas;
	}
	public String getDiagnostico() {
		return diagnostico.get();
	}
	public void setDiagnostico(StringProperty diagnostico) {
		this.diagnostico = diagnostico;
	}
	public String getFecha() {
		return fecha.get();
	}
	public void setFecha(StringProperty fecha) {
		this.fecha = fecha;
	}
	public Integer getIdchecklist() {
		return idchecklist.get();
	}
	public void setIdchecklist(IntegerProperty idchecklist) {
		this.idchecklist = idchecklist;
	}

	public RestaurarChecklist(){
		con = Conexion.getInstancia();
		gasolina = luces =  espejoizquierdo = espejoderecho = llantadelantera = llantatrasera = fallas =
				diagnostico = fecha = new SimpleStringProperty();
		idchecklist = new SimpleIntegerProperty();
		cus = new Customer();
		mc = new Motorcycle();
		er = new Errores();
	}
	
	public ObservableList<RestaurarChecklist> getCheck(boolean estatus) throws SQLException{
		ResultSet rs = null;
		try{
			String sql = "select k.idchecklist, k.idcliente, c.nombre1, k.idmotocicleta, m.modelo, k.gasolina, k.luces, k.espejoizquierdo, k.espejoderecho, k.llantadelantera, k.llantatrasera, k.fallas, k.diagnostico, k.fechahoraregistro, k.activo from checklist k join cliente c on k.idcliente = c.idcliente join motocicleta m on k.idmotocicleta = m.idmotocicleta where k.activo = '0'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs=comando.executeQuery();
			informacion = FXCollections.observableArrayList();
			while(rs.next()){
				RestaurarChecklist ch = new RestaurarChecklist();
				ch.idchecklist = new SimpleIntegerProperty(rs.getInt("idchecklist"));
				ch.cus.setIdcliente(new SimpleIntegerProperty(rs.getInt("idcliente")));
				ch.cus.setNombre1(new SimpleStringProperty(rs.getString("nombre1")));
				ch.mc.setIdmotocicleta(new SimpleIntegerProperty(rs.getInt("idmotocicleta")));
				ch.mc.setModelo(new SimpleStringProperty(rs.getString("modelo")));
				ch.gasolina = new SimpleStringProperty(rs.getString("gasolina"));
				ch.luces = new SimpleStringProperty(rs.getString("luces"));
				ch.espejoizquierdo = new SimpleStringProperty(rs.getString("espejoizquierdo"));
				ch.espejoderecho = new SimpleStringProperty(rs.getString("espejoderecho"));
				ch.llantadelantera = new SimpleStringProperty(rs.getString("llantadelantera"));
				ch.llantatrasera = new SimpleStringProperty(rs.getString("llantatrasera"));
				ch.fallas = new SimpleStringProperty(rs.getString("fallas"));
				ch.diagnostico = new SimpleStringProperty(rs.getString("diagnostico"));
				ch.fecha = new SimpleStringProperty(rs.getString("fechahoraregistro"));
				informacion.add(ch);
			}
		}catch (Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
		}
		finally{
			rs.close();
			con.desconectar();
		}
		return informacion;
	}
	
	
	public boolean restaurar(){
		try {
			String sql="select fn_restaurarck(?)";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			comando.setInt(1, this.getIdchecklist());
			comando.execute();
			return true;
		} catch (Exception e) {
			er.printLog(e.getMessage(), this.getClass().toString());
			return false;
		}
		finally{
			con.desconectar();
		}
	}
}