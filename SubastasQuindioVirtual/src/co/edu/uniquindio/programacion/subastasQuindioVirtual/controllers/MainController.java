package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable{
	@FXML
	Button btnAnunciar;
	
	@FXML
	public void anunciar(ActionEvent event) {
		Stage stage = (Stage) this.btnAnunciar.getScene().getWindow();
	    stage.close();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginAnuncianteView();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ModelFactoryController.getInstance().cargarAnunciantes();
		
	}
	
}
