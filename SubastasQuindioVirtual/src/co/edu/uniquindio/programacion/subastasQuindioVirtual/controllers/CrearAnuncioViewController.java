package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AdvertisementLimitedAmountException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CrearAnuncioViewController implements Initializable {

	// Declaracion de atributos fxml
	@FXML
	private Button btnCrearAnuncio;
	@FXML
	private ChoiceBox<String> cBoxTipoProducto;
	@FXML
	private TextField txtNombreProducto;
	@FXML
	private TextField txtDesProducto;
	@FXML
	private TextField txtNombreAnunciante;
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
	@FXML
	private AnchorPane panelBase;

	Stage stage = new Stage();

	/**
	 * Método que crea un anuncio
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void crearAnuncio(ActionEvent event) throws Exception {
		if(verificarCampoVacio()) {
			JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
			ModelFactoryController.getInstance().guardarLog("Se intentó registrar sin suministrar la suficiente información", 2, "Crear anuncio");
			throw new InvalidInputException("Debe llenar todos los campos");
		}else {
			if (!verificarFechas()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar una fecha válida");
				ModelFactoryController.getInstance().guardarLog("Fechas inválidas", 2, "Crear Anuncio");
			}else {
			
			if (ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios().size() == 3) {
				JOptionPane.showMessageDialog(null, "Ya ha alcanzado el límite de anuncios por anunciante");
				ModelFactoryController.getInstance().guardarLog("Limite de anuncios alcanzado", 2, "Anunciar");
				throw new AdvertisementLimitedAmountException("Limite de anuncios alcanzado por: " + ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
			}
		// Obtencion de datos
		String tipoProducto = cBoxTipoProducto.getValue();
		String nombreProducto = txtNombreProducto.getText();
		String descripcion = txtDesProducto.getText();
		String imagenProducto = lblRutaImagen.getText();
		String nombreAnunciante = txtNombreAnunciante.getText();
		LocalDate fechaPublicacion = dtPckrFechaPublicacion.getValue();
		LocalDate fechaFin = dtPckrFechaFin.getValue();
		int tiempoLimite = fechaFin.getDayOfYear() - fechaPublicacion.getDayOfYear();
		String fechaInicioAnuncio = fechaPublicacion.toString();
		String fechaFinAnuncio = fechaFin.toString();
		double valorInicial = 0;
		// Se verifica que el campo del valor inicial sean solo numeros
		if (!esNumero(txtValorInicialPuja.getText())) {
			ModelFactoryController.getInstance().guardarLog("No se crea el anuncio, el valor inicial contiene letras",2, "Crear Anuncio");
			JOptionPane.showMessageDialog(null, "El valor inicial solo debe contener numeros");
			throw new InvalidInputException("Se ingresaron letras en el campo de valor inicial");
		} else {
			valorInicial = Double.parseDouble(txtValorInicialPuja.getText());
		}
		boolean estado = true;
		ArrayList<Puja> pujas = new ArrayList<Puja>();
		// Construccion del objeto anuncio
		Anuncio anuncio = new Anuncio(tipoProducto, tiempoLimite, nombreProducto, descripcion, imagenProducto,nombreAnunciante, fechaInicioAnuncio, fechaFinAnuncio, valorInicial, estado, pujas);
		// Añadiendo el anuncio al arraylist de anuncios
		ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios().add(anuncio);
		// Se añade el anuncio al arraylist de anuncios del anunciante que lo creó
		ArrayList<Usuario> usuarios = ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios();
		for (Usuario usuario : usuarios) {
			if (usuario.getNombre().equals(nombreAnunciante) && usuario instanceof Anunciante) {
				((Anunciante) usuario).getAnuncios().add(anuncio);
				break;
			}
		}
		
		ModelFactoryController.getInstance().aplicacionSubastas.setUsuarios(usuarios);
		ModelFactoryController.getInstance().guardarLog("El usuario : " + nombreAnunciante + " crea un nuevo anuncio",1, "Crear anuncio");
		JOptionPane.showMessageDialog(null, "El anuncio ha sido creado");
		ModelFactoryController.getInstance().serializarModeloXml();
		ModelFactoryController.getInstance().serializarModeloBinario();
		vaciarCampos();
			}
		}
	}

	/**
	 * Método que permite insertar una imagen desde el equipo
	 * 
	 * @param event
	 */
	@FXML
	private void insertarImagen(ActionEvent event) {
		// Se declara el FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Buscar Imagen");

		// Agregar filtros para facilitar la busqueda
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Archivos perzonalizados", "*.jpg; *.png;*.jpeg"));

		// Obtener la imagen seleccionada
		File imgFile = fileChooser.showOpenDialog(stage);

		// Mostar la imagen
		if (imgFile != null) {
			// Se realiza la copia de la imagen en la carpeta de persistencia ubicada en
			// Disco local (C:)
			Path origenPath = FileSystems.getDefault().getPath(imgFile.getAbsolutePath());
			Path destinoPath = FileSystems.getDefault()
					.getPath("C:\\td\\persistencia\\imagenesProductos\\" + imgFile.getName());

			// Se guarda la ruta para despues obtenerla mas facil
			lblRutaImagen.setText(destinoPath.toString());
			// Se muestra la imagen
			Image image = new Image("file:" + imgFile.getAbsolutePath());
			imgVwImagenProducto.setImage(image);

			try {
				Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo para regresar a la ventana principal
	 */
	@FXML
	private void regresar(ActionEvent event) {
		cerrarVentanaCrearAnuncio();
		try {
			ModelFactoryController.getInstance().gestorVentanas.abrirVentanaAnuncianteView();
		} catch (Exception e) {
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
	
	/**
	 * Metodo que vacia los campos txt
	 */
	@FXML
	public void vaciarCampos() {
		txtNombreAnunciante.setText("");
		txtDesProducto.setText("");
		txtNombreProducto.setText("");
		txtValorInicialPuja.setText("");
		imgVwImagenProducto.setImage(null);
	}

	/**
	 * Método que verifica si el campo del valor inicial contiene solo numeros
	 * @param valor valor a verificar
	 * @return retorna false si contiene letras o true si solo contiene numeros
	 */
	public boolean esNumero(String esNumero) {
        boolean esApta = true;
        char[] car = esNumero.toCharArray();
        for (char c : car) {
            if (!Character.isDigit(c)) {
                esApta = false;
            }
        }
        return esApta;
    }
	
	/**
	 * Metodo que verifica si los campos están vacíos 
	 * @return
	 */
	public boolean verificarCampoVacio() {
		boolean centinela = false;
		// creamos un objeto tipo textField
		TextField txt = new TextField();
		// recorremos el panel donde esta todo los txtfields
		if (lblRutaImagen.getText().isEmpty()) {
			centinela = true;
		}
		if (dtPckrFechaFin.getValue() == null) {
			centinela = true;
		}
		if (dtPckrFechaPublicacion.getValue() == null) {
			centinela = true;
		}
		if (cBoxTipoProducto.getValue() == null) {
			centinela = true;
		}
		for (Node c : panelBase.getChildren()) {
			// verificamos que evaluemos objetos del tipo txtfield
			if (c.getClass().getName().equals(txt.getClass().getName())) {

				txt = (TextField) c;
				if (txt.getText().isEmpty()) {

					centinela = true;
					break;
				}
			}
		}
		return centinela;
	}
	
	/**
	 * Metodo que realiza verificaciones sobre las fechas
	 * @return
	 * @throws InvalidInputException
	 */
	public boolean verificarFechas() throws InvalidInputException{
		boolean isValida = false;
		LocalDate fechaPublicacion = dtPckrFechaPublicacion.getValue();
		LocalDate fechaFin = dtPckrFechaFin.getValue();
		//Verifica si las fechas son iguales
		if(fechaPublicacion.getDayOfYear() == fechaFin.getDayOfYear()) {
			return isValida;
		}
		//
		if (fechaFin.getYear() > fechaPublicacion.getYear()) {
			isValida = true;
		}else if (fechaFin.getYear() == fechaPublicacion.getYear()) {
			if (fechaFin.getDayOfYear() > fechaPublicacion.getDayOfYear() && fechaFin.getMonthValue() >= fechaPublicacion.getMonthValue()) {
				isValida = true;
			}else {
				ModelFactoryController.getInstance().guardarLog("La fecha final es menor a la inicial", 2, "Crear Anuncio");
			}
		}
		return isValida;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		txtNombreAnunciante.setEditable(false);
		txtNombreAnunciante.setText(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
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
