package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelectorRolViewController {
	//Declaracion de atributos FXML
	@FXML
    private Button btnAnunciante;
    @FXML
    private Button btnComprador;
    
    private Stage stage = new Stage();
    
    @FXML
    private void anuncianteView(ActionEvent event) {
    	cerrarVentanaPrincipalA();
    	ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginAnuncianteView();
    }
    
    @FXML
    private void compradorView(ActionEvent event) {
    	cerrarVentanaPrincipalC();
    	ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginCompradorView();
    }
    
    @FXML
    public void volverAPaginaPrin(ActionEvent event) throws Exception {
    	cerrarVentanaPrincipalA();
    	ModelFactoryController.getInstance().gestorVentanas.start(stage);
    }
    
    /**
	 * Metodo que cierra la ventana principal 
	 */
	@FXML
	public void cerrarVentanaPrincipalA() {
		Stage stage = (Stage) this.btnAnunciante.getScene().getWindow();
	    stage.close();
	}
	
	/**
	 * Metodo que cierra la ventana principal 
	 */
	@FXML
	public void cerrarVentanaPrincipalC() {
		Stage stage = (Stage) this.btnComprador.getScene().getWindow();
	    stage.close();
	}
}
