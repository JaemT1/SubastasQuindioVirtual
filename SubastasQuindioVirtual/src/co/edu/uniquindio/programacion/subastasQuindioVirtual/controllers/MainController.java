package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	@FXML
	Button btnAnunciar;
	
	@FXML
	public void anunciar(ActionEvent event) {
		Stage stage = (Stage) this.btnAnunciar.getScene().getWindow();
	    stage.close();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaCrearAnuncioView();
	}
	
}
