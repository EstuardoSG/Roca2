package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import controlador.Respaldo;

public class Backup {
	
	Respaldo con;
	//Se define el formato de fecha y hora tal como se imprimirá en el archivo
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss", Locale.getDefault());
	Date ahora = new Date();
	private String algoritmo;
	private File archivo,archivo_original;
	private Respaldo r;
	
	public Backup(String algoritmo,String ruta){
		this.algoritmo=algoritmo; 
		this.archivo=new File(ruta); 
		this.archivo_original = new File (ruta);
		
	}
	
	public void encriptar() throws Exception{
		FileInputStream archivo_entrada =new FileInputStream(archivo);
		/*al archivo de salida le asigna el nombre del archivo original 
		 * sustituyendo la extensión, asignado fecha y hora y asignando una nueva extensión. */
		archivo=new File(archivo.getAbsolutePath().replace(".sql","_")+formato.format(ahora)+".roc"); 
		FileOutputStream archivo_salida =new FileOutputStream(archivo);
		System.out.println(archivo.toString()+ archivo_original.toString());
		byte k[] = "contra87".getBytes();   
		SecretKeySpec contrasenia = new SecretKeySpec(k,"DES");  
		
		Cipher crypter =  Cipher.getInstance(algoritmo);  
		crypter.init(Cipher.ENCRYPT_MODE, contrasenia);  
		CipherOutputStream contador =new CipherOutputStream(archivo_salida, crypter);
		
		byte[] buffer = new byte[1024];
		int lector;
		
		while((lector=archivo_entrada.read(buffer))!=-1)  
		contador.write(buffer,0,lector);     
		archivo_entrada.close();
		contador.flush();
		contador.close();
		limpiar(archivo_original);
	}
	
	public void desencriptar() throws Exception{
		FileInputStream archivo_entrada =new FileInputStream(archivo);
		/*al archivo de salida le asigna el nombre del archivo original 
		 * sustituyendo la extensión, asignado fecha y hora y asignando una nueva extensión. */
		archivo=new File(archivo.getAbsolutePath().replace(".roca","_")+".sql");
		FileOutputStream archivo_salida =new FileOutputStream(archivo); 

		byte k[] = "contra87".getBytes();   
		SecretKeySpec contrasenia = new SecretKeySpec(k,"DES");  

		Cipher decrypter =  Cipher.getInstance(algoritmo);  
		decrypter.init(Cipher.DECRYPT_MODE, contrasenia);  
		CipherInputStream crypter_salida=new CipherInputStream(archivo_entrada, decrypter);
		
		byte[] buffer = new byte[1024];
		int lector=0;
		
		while((lector=crypter_salida.read(buffer))!=-1) 
		{
			archivo_salida.write(buffer,0,lector);     
		}
		
		crypter_salida.close();
		archivo_salida.flush();
		archivo_salida.close();
		limpiar(archivo_original);
		
	}
	
	//Recibe el path del archivo original y lo borra
	public void limpiar(File ruta){
		this.archivo = ruta;
		archivo.delete();
		
	}

	
}
