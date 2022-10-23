package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import javax.swing.JOptionPane;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginAnuncianteController{
	
	@FXML
	private TextField txtCorreoLoginAnunciante;
	@FXML
	private PasswordField txtContraLoginAnunciante;
	@FXML
	private Button btnLoginAnunciante;
	
	private Stage stage = new Stage();
	
	/**
	 * M�todo que verifica si un anunciante ya est� registrado
	 * @param event
	 * @throws UserNotFoundException
	 */
	@FXML
	public void iniciarSesionAnunciante(ActionEvent event) throws UserNotFoundException, Exception, IOException{
        String correoAnunciante = txtCorreoLoginAnunciante.getText();
        String contraseniaAnunciante = txtContraLoginAnunciante.getText();
        boolean usuarioEncontrado = false;
        //Búsqueda del usuario
        for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {

            if (correoAnunciante.equals(usuario.getCorreo()) && contraseniaAnunciante.equals(usuario.getContrasena())) {
                JOptionPane.showMessageDialog(null, "Sesión Iniciada");
                ModelFactoryController.getInstance().guardarLog("El usuario con correo: " + correoAnunciante + " inicia sesión", 1, "Se inicia sesión");
                cerrarVentanaLogin();
                usuarioEncontrado = true;  
                Anunciante anunciante = (Anunciante)usuario;
                ModelFactoryController.getInstance().anuncianteSesionIniciada = anunciante;
                ModelFactoryController.getInstance().gestorVentanas.abrirVentanaAnuncianteView();
            }
        }
        //Si no es encontrado se lanza la excepción y se abre la ventana de registro
        if (usuarioEncontrado == false) {
            ModelFactoryController.getInstance().guardarLog("El usuario con correo: " + correoAnunciante + " no pudo iniciar sesión", 2, "No Se inicia sesión");
            int registro = JOptionPane.showConfirmDialog(null,"No se encuentra registrado" + "\n" + "Desea registrarse?");
            if (registro == 0) {
                cerrarVentanaLogin();
                ModelFactoryController.getInstance().gestorVentanas.abrirVentanaRegistroAnuncianteView();
            }
            throw new UserNotFoundException("Usuario no encontrado");
        }
	}
	
	/**
	 * Metodo que nos permite volver al selector de rol
	 * @param event
	 */
	@FXML
	public void volverASelector(ActionEvent event) {
		cerrarVentanaLogin();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaSelectorRolView();
	}
	
	/**
	 * M�todo que cierra la ventana de login de anunciantes
	 */
	@FXML
	public void cerrarVentanaLogin() {
		Stage stage = (Stage) this.btnLoginAnunciante.getScene().getWindow();
	    stage.close();
	}
}
