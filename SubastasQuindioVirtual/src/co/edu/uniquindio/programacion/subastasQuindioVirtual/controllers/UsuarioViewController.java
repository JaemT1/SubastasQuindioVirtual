package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.Main;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.MyListenerCopia;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UsuarioViewController implements Initializable {
	//Declaracion de atributos FXML
	@FXML
	private VBox chosenFruitCard;

	@FXML
	private Button btnIniciarSesion;

	@FXML
	private Label lblNombreProducto;
	
	@FXML 
	private Label lblNombreAnunciante;

	@FXML
	private Label lblPrecioProducto;

	@FXML
	private ImageView imgProducto;

	@FXML
	private Label lblFechaInicio;

	@FXML
	private Label lblFechaFin;

	@FXML
	private Label lblDescripcion;

	@FXML
	private Label lblCategoria;

	@FXML
	private ScrollPane scroll;

	@FXML
	private GridPane grid;

	private Image image;
	private MyListenerCopia myListener;
	
	/**
	 * Metodo que nos permite setear los datos de un anuncio en especifíco a la izq de la interfaz
	 * @param anuncio anuncio a setear
	 */
	private void setChosenAnnounce(Anuncio anuncio) {
		lblNombreProducto.setText(anuncio.getNombreProducto());
		lblPrecioProducto.setText(Main.CURRENCY + anuncio.getValorInicial());
		image = new Image(anuncio.getFoto());
		imgProducto.setImage(image);
		lblDescripcion.setText(anuncio.getDescripcion());
		lblFechaInicio.setText("Va desde: " + anuncio.getFechaPublicacion());
		lblFechaFin.setText("Hasta: " + anuncio.getFechaFinPublicacion());
		lblCategoria.setText("Categoria: " + anuncio.getTipoProducto());
		lblNombreAnunciante.setText("Anunciante: " + anuncio.getNombreAnunciante());
	}

	/**
	 * Metodo que nos lleva a la ventana para seleccionar un rol y logearnos
	 * @param event
	 */
	@FXML
	private void selectorRolView(ActionEvent event) {
		cerrarVentanaPrincipal();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaSelectorRolView();
	}

	/**
	 * Metodo que cierra la ventana principal
	 */
	@FXML
	public void cerrarVentanaPrincipal() {
		Stage stage = (Stage) this.btnIniciarSesion.getScene().getWindow();
		stage.close();
	}
	
	public void crearCopiaXML() {
		//Se obtiene la fecha
		Calendar cal1 = Calendar.getInstance();

		int dia = cal1.get(Calendar.DAY_OF_MONTH);
		int mes = cal1.get(Calendar.MONTH) + 1;
		int anio = cal1.get(Calendar.YEAR);
		int hora = cal1.get(Calendar.HOUR);
		int minuto = cal1.get(Calendar.MINUTE);
		int segundo = cal1.get(Calendar.SECOND);
		
		//Declaracion de rutas
		Path origenPath = FileSystems.getDefault().getPath("C:\\td\\persistencia\\model.xml");
        Path destinoPath = FileSystems.getDefault().getPath("C:\\td\\persistencia\\respaldo\\" + "model_"+dia+"_"+mes+"_"+anio+"_"+hora+"_"+minuto+"_"+segundo+".xml");
        
		//Se hace la copia del archivo
		try {
			Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void pujar(ActionEvent event) {
		cerrarVentanaPrincipal();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaLoginCompradorView();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		crearCopiaXML();
		ModelFactoryController.getInstance().cargarDatosModelo();
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		anuncios = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		// Pone el primer anuncio a la izq
		if (anuncios.size() > 0) {
			setChosenAnnounce(anuncios.get(0));
			myListener = new MyListenerCopia() {
				@Override
				public void onClickListener(Anuncio anuncio) {
					setChosenAnnounce(anuncio);
				}
			};
		}
		int column = 0;
		int row = 1;
		try {
			for (int i = 0; i < anuncios.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource(
						"/co/edu/uniquindio/programacion/subastasQuindioVirtual/view/PlantillaAnuncioCopia.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();

				PlantillaAnuncioControllerCopia PlantillaController = fxmlLoader.getController();
				PlantillaController.setData(anuncios.get(i), myListener);

				if (column == 3) {
					column = 0;
					row++;
				}

				grid.add(anchorPane, column++, row); // (child,column,row)
				// set grid width
				grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				grid.setMaxWidth(Region.USE_PREF_SIZE);

				// set grid height
				grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				grid.setMaxHeight(Region.USE_PREF_SIZE);

				GridPane.setMargin(anchorPane, new Insets(10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
