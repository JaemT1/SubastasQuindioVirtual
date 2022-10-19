package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CrearAnuncioViewController implements Initializable {
	
	//Declaracion de atributos fxml
	@FXML
	private Button btnCrearAnuncio;
	@FXML
	private ChoiceBox<String> cBoxTipoProducto;
	@FXML
	private TextField txtNombreProducto;
	@FXML
	private TextField txtDesProducto;
	@FXML
	private TextField txtNombreAnuncio;
	@FXML
	private DatePicker dtPckrFechaPublicacion;
	@FXML
	private DatePicker dtPckrFechaFin;
	@FXML
	private TextField txtValorInicialPuja;
	@FXML
	private Button btnInsetarImagen;
	@FXML
	private Button btnVolver;
	@FXML
	private ImageView imgVwImagenProducto;
	@FXML
	private Label lblRutaImagen;
	
	Stage stage = new Stage();
	
	/**
	 * Método que crea un anuncio
	 * @param event
	 */
	@FXML
	public void crearAnuncio(ActionEvent event) {
		//Obtencion de datos
		String tipoProducto = cBoxTipoProducto.getValue();
		String nombreProducto = txtNombreProducto.getText();
		String descripcion = txtDesProducto.getText();
		String imagenProducto = lblRutaImagen.getText();
		String nombreAnunciante = txtNombreAnuncio.getText();
		LocalDate fechaPublicacion = dtPckrFechaPublicacion.getValue();
		LocalDate fechaFin = dtPckrFechaFin.getValue();
		int tiempoLimite = fechaFin.getDayOfYear() - fechaPublicacion.getDayOfYear();
		String fechaInicioAnuncio = fechaPublicacion.toString();
		String fechaFinAnuncio = fechaFin.toString();
		double valorInicial = Double.parseDouble(txtValorInicialPuja.getText());
		boolean estado = true;
		ArrayList<Puja> pujas = new ArrayList<Puja>();
		//Construccion del objeto anuncio
		Anuncio anuncio = new Anuncio(tipoProducto,tiempoLimite,nombreProducto,descripcion,imagenProducto,nombreAnunciante, fechaInicioAnuncio, fechaFinAnuncio, valorInicial, estado, pujas);
		//Añadiendo el anuncio al arraylist de anuncios
		ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios().add(anuncio);
		//Se añade el anuncio al arraylist de anuncios del anunciante que lo creó
		for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(nombreAnunciante) && usuario instanceof Anunciante) {
				((Anunciante) usuario).getAnuncios().add(anuncio);
			}
		}
		ModelFactoryController.getInstance().guardarLog("El usuario : " + nombreAnunciante + " crea un nuevo anuncio", 1, "Crear anuncio");
		JOptionPane.showMessageDialog(null, "El anuncio ha sido creado");
		ModelFactoryController.getInstance().serializarModeloXml();
	}

	/**
	 * Método que permite insertar una imagen desde el equipo
	 * @param event
	 */
	@FXML
	private void insertarImagen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Buscar Imagen");

		// Agregar filtros para facilitar la busqueda
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos perzonalizados", "*.jpg; *.png;*.jpeg"));

		// Obtener la imagen seleccionada
		File imgFile = fileChooser.showOpenDialog(stage);

		// Mostar la imagen
		if (imgFile != null) {
			lblRutaImagen.setText(imgFile.getAbsolutePath());
			Image image = new Image("file:" + imgFile.getAbsolutePath());
			imgVwImagenProducto.setImage(image);
		}
	}
	
	/**
	 *Metodo para regresar a la ventana principal 
	 */
	@FXML
	private void regresar(ActionEvent event) {
		cerrarVentanaCrearAnuncio();
		try {
			ModelFactoryController.getInstance().gestorVentanas.start(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que cierra la ventana de creacion de anuncios
	 */
	@FXML
	public void cerrarVentanaCrearAnuncio() {
		Stage stage = (Stage) this.btnVolver.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		lblRutaImagen.setVisible(false);
		dtPckrFechaPublicacion.setEditable(false);
		dtPckrFechaFin.setEditable(false);
		ModelFactoryController.getInstance().cargarTiposProductos();
		cBoxTipoProducto.getItems().add(ModelFactoryController.getInstance().tiposProductos[0]);
		cBoxTipoProducto.getItems().add(ModelFactoryController.getInstance().tiposProductos[1]);
		cBoxTipoProducto.getItems().add(ModelFactoryController.getInstance().tiposProductos[2]);
		cBoxTipoProducto.getItems().add(ModelFactoryController.getInstance().tiposProductos[3]);
		cBoxTipoProducto.getItems().add(ModelFactoryController.getInstance().tiposProductos[4]);
	}

}
