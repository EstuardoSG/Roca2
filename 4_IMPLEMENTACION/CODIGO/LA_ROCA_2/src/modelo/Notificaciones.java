package modelo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Notificaciones {
	/*Muestra una alerta en caso de que el formulario no haya sido llenado correctamente.
	 */
	public void faltanDatos(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Atención");
		alert.setHeaderText("Problema con el formulario");
		alert.setContentText("Faltan datos por ingresar");
		alert.showAndWait();
	}
	/*Muestra un aviso cuando los datos han sido guardados o modificados correctamente.
	 */
	public void datosGuardados(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notificación");
		alert.setHeaderText(null);
		alert.setContentText("Los datos han sido guardados satisfactoriamente");
		alert.showAndWait();
	}
	/*Muestra un aviso en caso de que el estatus haya sido modificado.
	 */
	public void datosEliminados(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notificación");
		alert.setHeaderText(null);
		alert.setContentText("El estatus ha sido modificado exitosamente");
		alert.showAndWait();
	}
	/*Muestra una alerta cuando se ha producido un error en el registro o actualización.
	 */
	public void datosNoGuardados(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Alerta");
		alert.setHeaderText("Problema con el registro");
		alert.setContentText("Los datos no se han podido guardar");
		alert.showAndWait();
	}
	/*Muestra una alerta cuando no se haya selecionado un registro a elminar.
	 */
	public void seleccionarRegistro(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Alerta");
		alert.setHeaderText("No se puede eliminar");
		alert.setContentText("Debe seleccionar un registro a eliminar");
		alert.showAndWait();
	}
	/*Muestra una alerta cuando no se haya podido realizar el borrado lógico.
	 */
	public void falloEliminar(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Alerta");
		alert.setHeaderText("No se puede eliminar");
		alert.setContentText("Se ha presentado un fallo durante la eliminacion");
		alert.showAndWait();
	}
}
