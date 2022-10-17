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
	
	/**
	 * M�todo que verifica si un anunciante ya est� registrado
	 * @param event
	 * @throws UserNotFoundException
	 */
	@FXML
	public void iniciarSesionAnunciante(ActionEvent event) throws UserNotFoundException, IOException{
		Stage primaryStage = new Stage();
        String correoAnunciante = txtCorreoLoginAnunciante.getText();
        String contraseniaAnunciante = txtContraLoginAnunciante.getText();
        boolean usuarioEncontrado = false;
        //Búsqueda del usuario
        for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {

            if (correoAnunciante.equals(usuario.getCorreo()) && contraseniaAnunciante.equals(usuario.getContrasena())) {
                JOptionPane.showMessageDialog(null, "Sesi�n Iniciada");
                ModelFactoryController.getInstance().guardarLog("El usuario con correo: " + correoAnunciante + " inicia sesi�n", 1, "Se inicia sesi�n");
                cerrarVentanaLogin();
                ModelFactoryController.getInstance().gestorVentanas.start(primaryStage);
                usuarioEncontrado = true;
            }

        }
        //Si no es encontrado se lanza la excepci�n y se abre la ventana de registro
        if (usuarioEncontrado == false) {
            ModelFactoryController.getInstance().guardarLog("El usuario con correo: " + correoAnunciante + " no pudo iniciar sesi�n", 2, "No Se inicia sesi�n");
            int registro = JOptionPane.showConfirmDialog(null,"No se encuentra registrado" + "\n" + "Desea registrarse?");
            if (registro == 0) {
                cerrarVentanaLogin();
                ModelFactoryController.getInstance().gestorVentanas.abrirVentanaRegistroAnuncianteView();
            }
            throw new UserNotFoundException("Usuario no encontrado");
        }
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
