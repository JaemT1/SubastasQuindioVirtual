package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable{
	//Declaracion de atributos
	@FXML
	Button btnAnunciar;
	@FXML
	Button btnMisOfertas;
	
	/**
	 * Metodo que nos lleva a la ventana para login de anunciantes
	 * @param event
	 */
	@FXML
	public void anunciar(ActionEvent event) {
		Stage stage = (Stage) this.btnAnunciar.getScene().getWindow();
	    stage.close();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginAnuncianteView();
	}
	
	/**
	 * Metodo que nos lleva a el login de comprador
	 * @param event
	 */
	@FXML
	public void verOfertas(ActionEvent event) {
		Stage stage = (Stage) this.btnMisOfertas.getScene().getWindow();
	    stage.close();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginCompradorView();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ModelFactoryController.getInstance().cargarDatosModelo();
	}
	
}
