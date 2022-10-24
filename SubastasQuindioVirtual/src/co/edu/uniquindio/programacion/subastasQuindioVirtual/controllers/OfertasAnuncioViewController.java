package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OfertasAnuncioViewController implements Initializable{

    @FXML
    private TableColumn<Puja, Double> ClmnValorOferta;

    @FXML
    private TableColumn<Puja, Integer> ClmnCodigoPuja;

    @FXML
    private TableColumn<Puja, String> ClmnNombreComprador;

    @FXML
    private TableView<Puja> tblOfertasAnuncio;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String nombreProductoString = ModelFactoryController.getInstance().nombreAnuncioAModificar;
		ObservableList<Puja> datosPujas = FXCollections.observableArrayList();
		ArrayList<Anuncio> anuncios = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		ArrayList<Puja> pujas = new ArrayList<Puja>();
		for (Anuncio anuncio : anuncios) {
			if (nombreProductoString.equals(anuncio.getNombreProducto())) {
				pujas = anuncio.getPujas();
				for (Puja puja : pujas) {
					datosPujas.add(puja);
				}
				break;
			}
		}
		ClmnCodigoPuja.setCellValueFactory(new PropertyValueFactory<Puja, Integer>("codigoPuja"));
		ClmnNombreComprador.setCellValueFactory(new PropertyValueFactory<Puja, String>("nombreComprador"));
		ClmnValorOferta.setCellValueFactory(new PropertyValueFactory<Puja, Double>("valor"));
		tblOfertasAnuncio.setItems(datosPujas);

	}
    
    
    
}
