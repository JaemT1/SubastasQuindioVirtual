package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AgeNotAllowedException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AlreadyTakenUsernameException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
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
	 * @throws Exception 
	 */
	@FXML
	private void registrarAnunciante(ActionEvent event)
			throws Exception {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		String nombre = txtNombreAnunciante.getText();
		String correo = txtCorreoAnunciante.getText();
		String contrasena = txtContraAnunciante.getText();
		int edad = 0;
		// Verifica si hay letras en el txtField de la edad
		if (!verificarCampoEdad(txtEdadAnunciante.getText())) {
			ModelFactoryController.getInstance().guardarLog(
					"Usuario no registrado, ingresó letras en el campo de la edad", 2, "Registrar anunciante");
			JOptionPane.showMessageDialog(null, "La edad solo debe contener n�meros");
			throw new InvalidInputException("La edad solo debe contener n�meros");
		} else {
			edad = Integer.parseInt(txtEdadAnunciante.getText());
		}
		// Verifica si es mayor de edad
		if (edad < 18) {
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, es menor de edad", 2,
					"Registrar anunciante");
			JOptionPane.showMessageDialog(null, "Para registrarse debe ser mayor de edad");
			throw new AgeNotAllowedException("Para registrarse debe ser mayor de edad");
		}
		if (verificarUserName(nombre)){ // Verifica si el nombre de usuario ya está en uso
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, nombre de usuario ya está en uso",2, "Registrar anunciante");
			JOptionPane.showMessageDialog(null, "Lo sentimos, el nombre de usuario ya está en uso");
			throw new AlreadyTakenUsernameException("Lo sentimos, el nombre de usuario ya está en uso");
		} else {
			Anunciante anunciante = new Anunciante(anuncios, contrasena, nombre, edad, correo);
			ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios().add(anunciante);
			ModelFactoryController.getInstance().guardarAnunciante(anunciante);
			ModelFactoryController.getInstance().serializarModeloXml();
			ModelFactoryController.getInstance().serializarModeloBinario();
			ModelFactoryController.getInstance().guardarLog("Se registra el anunciante con nombre: " + nombre, 1,"Registrar anunciante");
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
		boolean nombreTomado = false;
		for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(userName)) {
				nombreTomado = true;
			}
		}
		return nombreTomado;
	}
	
	/**
	 * Método que verifica si la edad tiene solo numeros o tambien tiene letras
	 * @param edad la edad a verificar
	 * @return retorna true si solo tiene n�meros o false si tiene letras
	 */
	public boolean verificarCampoEdad(String edad) {
		boolean esApta = true;
		char[] edadChar = edad.toCharArray();
		for (char c : edadChar) {
			if (!Character.isDigit(c)) {
				esApta = false;
			}
		}
		return esApta;
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
