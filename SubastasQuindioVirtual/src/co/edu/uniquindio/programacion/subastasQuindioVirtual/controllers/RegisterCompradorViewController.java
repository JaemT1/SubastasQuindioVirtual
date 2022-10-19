package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AgeNotAllowedException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AlreadyTakenUsernameException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Comprador;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegisterCompradorViewController {

    @FXML
    private TextField txtEdadComprador;
    @FXML
    private TextField txtCorreoComprador;
    @FXML
    private TextField txtContraComprador;
    @FXML
    private Button btnRegistroComprador;
    @FXML
    private TextField txtNombreComprador;

    /**
     * M�todo para registrar un comprador
     * @param event
     * @throws AgeNotAllowedException
     * @throws AlreadyTakenUsernameException
     * @throws IOException
     * @throws InvalidInputException
     */
    @FXML
	private void registrarComprador(ActionEvent event)throws AgeNotAllowedException, AlreadyTakenUsernameException, IOException, InvalidInputException {
		ArrayList<Puja> pujas = new ArrayList<Puja>();
		String nombre = txtNombreComprador.getText();
		String correo = txtCorreoComprador.getText();
		String contrasena = txtContraComprador.getText();
		int edad = 0;
		// Verifica si hay letras en el txtField de la edad
		if (!verificarCampoEdad(txtEdadComprador.getText())) {
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, ingres� letras en el campo de la edad", 2, "Registrar comprador");
			JOptionPane.showMessageDialog(null, "La edad solo debe contener n�meros");
			throw new InvalidInputException("La edad solo debe contener n�meros");
		} else {
			edad = Integer.parseInt(txtEdadComprador.getText());
		}
		// Verifica si es mayor de edad
		if (edad < 18) {
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, es menor de edad", 2,"Registrar comprador");
			JOptionPane.showMessageDialog(null, "Para registrarse debe ser mayor de edad");
			throw new AgeNotAllowedException("Para registrarse debe ser mayor de edad");
		}
		if (verificarUserName(nombre)){ // Verifica si el nombre de usuario ya est� en uso
			ModelFactoryController.getInstance().guardarLog("Usuario no registrado, nombre de usuario ya est� en uso",2, "Registrar comprador");
			JOptionPane.showMessageDialog(null, "Lo sentimos, el nombre de usuario ya est� en uso");
			throw new AlreadyTakenUsernameException("Lo sentimos, el nombre de usuario ya est� en uso");
		} else {
			Comprador comprador = new Comprador(pujas, contrasena, nombre, edad, correo);
			ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios().add(comprador);
			ModelFactoryController.getInstance().guardarComprador(comprador);
			ModelFactoryController.getInstance().serializarModeloXml();
			ModelFactoryController.getInstance().guardarLog("Se registra el comprador con nombre: " + nombre, 1,"Registrar comprador");
			JOptionPane.showMessageDialog(null, "Comprador registrado con éxito");
			vaciarTxtFields();
			cerrarVentanaRegister();
			ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginCompradorView();
		}
	}
	
	/**
	 * M�todo que verifica si un nombre de usuario ya est� en uso
	 * @param userName nombre de usuario a verificar
	 * @return retorna false si no est� en uso y true si si lo est�
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
		txtContraComprador.setText("");
		txtCorreoComprador.setText("");
		txtEdadComprador.setText("");
		txtNombreComprador.setText("");
	}
	
	/**
	 * Método que cierra la ventana de registro de comprador
	 */
	@FXML
	public void cerrarVentanaRegister() {
		Stage stage = (Stage) this.btnRegistroComprador.getScene().getWindow();
	    stage.close();
	}
    
}