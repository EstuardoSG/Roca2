package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

import controlador.Errores;

public class Conexion {

	private String bd;
	private String usuario;
	private String contrasenia;
	private String servidor;
	private String ip;
	private String puerto;
	
	private static Conexion instancia;
	
	private Connection con;
	private Errores er;
	
	private Conexion(){
		ip = "127.0.0.1";
		puerto = "5432";
		bd = "Roca2";
		usuario = "postgres";
		contrasenia = "12345";
		servidor = "jdbc:postgresql://localhost:5432/";
		con = null;
		er = new Errores();
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getBd() {
		return bd;
	}

	public void setBd(String bd) {
		this.bd = bd;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	private Conexion(String bd, String usuario, String contrasenia, String ip, String puerto){
		this.ip = ip;
		this.puerto = puerto;
		this.bd = bd;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.servidor = "jdbc:postgresql://localhost:5432/";
		con = null;
	}
	
	/*
	 * Este metodo va estar controlando que no halla instancia.
	 */
	
	public static Conexion getInstancia(){
		if(instancia == null){
			//la instancia manda a llamar al contructor que no tiene parametros
			instancia = new Conexion();
		}
		return instancia;
	}
	
	/*
	 * Método para conectar al servidor de Postgresql
	 */
	
	public String conectar(){
		try{
			//verifica que este el driver en el proyecto.
			Class.forName("org.postgresql.Driver");
			servidor = "jdbc:postgresql://"+ip+":"+puerto+"/";
			con = DriverManager.getConnection(servidor+bd, usuario, contrasenia);
			System.out.println("Se ha establecido la conexión");
			return "Conexión éxitosa";
		}catch(Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
			return "No se establecio la conexión. Consulte su administrador";
		}
	}
	
	/*
	 * Método para desconectar del servidor de postgresql.
	 */
	
	public String desconectar(){
		try{
			//Cerrar Conexión.
			con.close();
			System.out.println("Se ha desconectado del servidor");
			return "Se ha desconectado del servidor";
		}catch(Exception e){
			er.printLog(e.getMessage(), this.getClass().toString());
			return "La conexión esta siendo ocupada. No se puede desconectar.";
		}
	}
	
	public Connection getConexion(){
		return con;
	}
}
