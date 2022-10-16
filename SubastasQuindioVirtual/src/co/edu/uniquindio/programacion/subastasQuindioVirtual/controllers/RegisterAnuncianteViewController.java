package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AgeNotAllowedException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AlreadyTakenUsernameException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterAnuncianteViewController {
	@FXML
	private TextField txtEdadAnunciante;
	@FXML
	private TextField txtCorreoAnunciante;
	@FXML
	private TextField txtContraAnunciante;
	@FXML
	private Button btnRegistroAnunciante;
	@FXML
	private TextField txtNombreAnunciante;

	/**
	 * Método que registra un anunciante 
	 * @param event
	 * @throws AgeNotAllowedException
	 * @throws AlreadyTakenUsernameException
	 */
	@FXML
	private void registrarAnunciante(ActionEvent event) throws AgeNotAllowedException, AlreadyTakenUsernameException{
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		String nombre = txtNombreAnunciante.getText();
		int edad = Integer.parseInt(txtEdadAnunciante.getText());
		String correo = txtCorreoAnunciante.getText();
		String contrasena = txtContraAnunciante.getText();
		if (edad < 18) {
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, es menor de edad", 2, "Registrar anunciante");
			JOptionPane.showMessageDialog(null, "Para registrarse debe ser mayor de edad");
			throw new AgeNotAllowedException("Para registrarse debe ser mayor de edad");
		}else if (verificarUserName(nombre)) {
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, nombre de usuario ya está en uso", 2, "Registrar anunciante");
			JOptionPane.showMessageDialog(null, "Lo sentimos, el nombre de usuario ya está en uso");
			throw new AlreadyTakenUsernameException("Lo sentimos, el nombre de usuario ya está en uso");
		}else {
			Anunciante anunciante = new Anunciante(anuncios, contrasena, nombre, edad, correo);
			ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios().add(anunciante);
			ModelFactoryController.getInstance().guardarAnunciante(anunciante);
			ModelFactoryController.getInstance().guardarLog("Se registra el anunciante con nombre: " + nombre, 1, "Registrar anunciante");
			JOptionPane.showMessageDialog(null, "Anunciante registrado con éxito");
			vaciarTxtFields();
			cerrarVentanaRegister();
			ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginAnuncianteView();
		}
	}
	
	/**
	 * Método que verifica si un nombre de usuario ya está en uso
	 * @param userName nombre de usuario a verificar
	 * @return retorna false si no está en uso y true si si lo está
	 */
	public boolean verificarUserName(String userName) {
		boolean nombreTomado = true;
		for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (!usuario.getNombre().equals(userName)) {
				nombreTomado = false;
			}
		}
		return nombreTomado;
	}
	
	/**
	 * Método que vacía los txt fields
	 */
	@FXML
	public void vaciarTxtFields() {
		txtContraAnunciante.setText("");
		txtCorreoAnunciante.setText("");
		txtEdadAnunciante.setText("");
		txtNombreAnunciante.setText("");
	}
	
	/**
	 * Método que cierra la ventana de registro de anunciantes
	 */
	@FXML
	public void cerrarVentanaRegister() {
		Stage stage = (Stage) this.btnRegistroAnunciante.getScene().getWindow();
	    stage.close();
	}
}
