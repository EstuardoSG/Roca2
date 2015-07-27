package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Errores {
	private DateFormat dateFormat;
	private Date date;
	
	public Errores(){
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
	}
	public void printLog(String mensaje, String clase){
		//
		FileWriter pw = null;
		// Mandar lo que se escribio al archivo
		BufferedWriter  bw= null;
		try {
			 // si no exite la Clase File Lo crea y doble diagonal para respete la diagonal, para ubicación
			File archivo = new File("D:\\log.txt");
			// si no ponemos True borra lo anterior y guardara el ultimo error
			pw = new FileWriter(archivo, true);
			bw = new BufferedWriter(pw);
			bw.write(clase);
			//da un salto
			bw.newLine();
			bw.write(mensaje + " " + dateFormat.format(date) + " ");
			bw.newLine();
			bw.write("*************************************************************************************");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
