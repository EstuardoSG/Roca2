package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

	private String bd;
	private String usuario;
	private String contrasenia;
	private String servidor;
	private String ip;
	private String puerto;
	
	private static Conexion instancia;
	
	private Connection con;
	
	private Conexion(){
		bd = "Roca2";
		usuario = "postgres";
		contrasenia = "root";
		servidor = "jdbc:postgresql://localhost:5432/";
		ip = "127.0.0.1";
		puerto = "5432";
		con = null;
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
	
	private Conexion(String bd, String usuario, String contrasenia, String ip, String puerto){
		
		this.servidor = "jdbc:postgresql://localhost:5432/";
		this.contrasenia = contrasenia;
		this.usuario = usuario;
		this.bd = bd;
		this.ip = ip;
		this.puerto = puerto;
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
	 * M�todo para conectar al servidor de Postgresql
	 */
	
	public String conectar(){
		try{
			//verifica que este el driver en el proyecto.
			Class.forName("org.postgresql.Driver");
			servidor = "jdbc:postgresql://"+ip+":"+puerto+"/";
			con = DriverManager.getConnection(servidor+bd, usuario, contrasenia);
			System.out.println("Se ha establecido la conexi�n");
			return "Conexi�n �xitosa";
		}catch(Exception e){
			e.printStackTrace();
			return "No se establecio la conexi�n. Consulte su administrador";
		}
	}
	
	/*
	 * M�todo para desconectar del servidor de postgresql.
	 */
	
	public String desconectar(){
		try{
			//Cerrar Conexi�n.
			con.close();
			System.out.println("Se ha desconectado del servidor");
			return "Se ha desconectado del servidor";
		}catch(Exception e){
			e.printStackTrace();
			return "La conexi�n esta siendo ocupada. No se puede desconectar.";
		}
	}
	
	public Connection getConexion(){
		return con;
	}
}
