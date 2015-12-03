package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario {


	/*
	 * Atributos.
	 */
	
	
	private String nombre, contrasenia, privilegio;
	private Boolean estatus;
	private Conexion con;
	//Objeto de conexión.
	private Connection miconexion;
	//Para ejecutar una instrucción SQL.
	private PreparedStatement comando;
	

	/*
	 * Constructor
	 */
	public Usuario(){
		this.nombre = null;
		this.contrasenia = null;
		this.privilegio = null;
		this.estatus = null;
		
	}
	

	/*
	 * Getters and Setters.
	 */
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	/*
	 * Métodos para verificar que existen en la base de datos.
	 */
	
	public boolean Existe(){
		con = Conexion.getInstancia();
		boolean bandera = false;
		
		try{
			String sql = "select * from empleados where usuario = ? and " +"contrasenia = ?";
			//Abrir conexión.
			con.conectar();
			//Asociamos el comando con la conexión.
			comando = con.getConexion().prepareStatement(sql);
			//Parámetros.
			comando.setString(1, this.nombre);
			comando.setString(2, this.contrasenia);
			//Se ejecuta.
			//ResultSet es una tabla(Repositorio) temporal.
			ResultSet rs = comando.executeQuery();
			//Validar los datos.
			//Si exiten datos en el ResultSet.
			while(rs.next()){
				/*
				 * En esta parte del codigo almacenamos los datos de la base de datos para despues poder utilizarlos
				 * con un get según sea la función 	que se le de.
				 */
				this.privilegio = rs.getString("privilegio");
				this.estatus = rs.getBoolean("estatus");
				bandera = true;
			}
			
		}catch(Exception e){
			System.out.println("Error");
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return bandera;
	}
}
