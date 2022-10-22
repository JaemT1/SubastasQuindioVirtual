package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import javax.swing.JOptionPane;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class LoginCompradorViewController {

    @FXML
    private PasswordField txtContraLoginComprador;
    @FXML
    private Button btnLoginComprador;
    @FXML
    private TextField txtCorreoLoginComprador;

    /**
     * Metodo que verifica si un comprador ya esta registrado y si no esta registrado lanza una excepci�n, y abre una ventana para registrarse.
     * @param event
     * @throws UserNotFoundException
     * @throws IOException
     */
    @FXML
    private void iniciarSesionComprador(ActionEvent event) throws UserNotFoundException, Exception {
    	Stage primaryStage = new Stage();
        String correoComprador = txtCorreoLoginComprador.getText();
        String contraseniaComprador = txtContraLoginComprador.getText();
        boolean usuarioEncontrado = false;
        //Búsqueda del usuario
        for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {

            if (correoComprador.equals(usuario.getCorreo()) && contraseniaComprador.equals(usuario.getContrasena())) {
                JOptionPane.showMessageDialog(null, "Sesi�n Iniciada");
                ModelFactoryController.getInstance().guardarLog("El usuario con correo: " + correoComprador + " inicia sesi�n", 1, "Se inicia sesi�n");
                cerrarVentanaLogin();
                ModelFactoryController.getInstance().gestorVentanas.start(primaryStage);
                usuarioEncontrado = true;
            }

        }
        //Si no es encontrado se lanza la excepci�n y se abre la ventana de registro
        if (usuarioEncontrado == false) {
            ModelFactoryController.getInstance().guardarLog("El usuario con correo: " + correoComprador + " no pudo iniciar sesi�n", 2, "No Se inicia sesi�n");
            int registro = JOptionPane.showConfirmDialog(null,"No se encuentra registrado" + "\n" + "Desea registrarse?");
            if (registro == 0) {
                cerrarVentanaLogin();
                ModelFactoryController.getInstance().gestorVentanas.abrirVentanaRegistroCompradorView();
            }
            throw new UserNotFoundException("Usuario no encontrado");
        }
    }
    /**
     * cierra la ventana login
     */
    public void cerrarVentanaLogin() {
		Stage stage = (Stage) this.btnLoginComprador.getScene().getWindow();
	    stage.close();
	}
    
}