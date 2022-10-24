package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OfertasCompradorViewController implements Initializable{

    @FXML
    private TableColumn<Puja, Double> ClmnValorOferta;

    @FXML
    private TableColumn<Puja, Integer> ClmnCodigo;

    @FXML
    private TableView<Puja> tblOfertasComprador;

    @FXML
    private TableColumn<Puja, String> ClmnNomProducto;

    @FXML
    private TableColumn<Puja, String> ClmnNomAnunciante;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Puja> datosPujas = FXCollections.observableArrayList();
    	ArrayList<Puja> pujasComprador = ModelFactoryController.getInstance().compradorSesionIniciada.getPujas();
    	for (Puja puja : pujasComprador) {
			datosPujas.add(puja);
		}
    	ClmnCodigo.setCellValueFactory(new PropertyValueFactory<Puja, Integer>("codigoPuja"));
    	ClmnNomAnunciante.setCellValueFactory(new PropertyValueFactory<Puja, String>("nombreAnunciante"));
    	ClmnNomProducto.setCellValueFactory(new PropertyValueFactory<Puja, String>("nombreProducto"));
    	ClmnValorOferta.setCellValueFactory(new PropertyValueFactory<Puja, Double>("valor"));
    	
		tblOfertasComprador.setItems(datosPujas);
	}
    
}