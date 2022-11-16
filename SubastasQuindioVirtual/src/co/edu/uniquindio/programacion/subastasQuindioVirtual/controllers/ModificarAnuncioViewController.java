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
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModificarAnuncioViewController implements Initializable {
	//Declaracion de atributos FXML
    @FXML
    private ImageView imageViewProducto;

    @FXML
    private TextField txtDesProducto;

    @FXML
    private Button btnInsetarImagen;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnModificarAnuncio;

    @FXML
    private TextField txtNombreDelAnunciante;

    @FXML
    private TextField txtValorInicialPuja;

    @FXML
    private TextField txtNombreProducto;

    @FXML
    private TextField txtFechaInicioAnuncio;

    @FXML
    private DatePicker dtPckrFechaFinalAnuncio;
    
    @FXML
    private Label lblRutaImagen;
    
    @FXML
	private AnchorPane panelBase;
    
    //Obtencion del nombre del anuncio a modificar para facilitar las comprobaciones en los if statements
    private String nombreAnuncioAModificar = ModelFactoryController.getInstance().nombreAnuncioAModificar;
    
    //Stage para abrir el main.start
    private Stage stage = new Stage();
    
    /**
     * Metodo que permite modificar un anuncio
     * @param event
     * @throws Exception
     */
    @FXML
    public void modificarAnuncio(ActionEvent event) throws Exception {
    	if(verificarCampoVacio()) {
			JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
			ModelFactoryController.getInstance().guardarLog("Se intentó registrar sin suministrar la suficiente información", 2, "Modificar anuncio");
			throw new InvalidInputException("Debe llenar todos los campos");
		}else {
			if (!verificarFechas()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar una fecha válida");
				ModelFactoryController.getInstance().guardarLog("Fechas inválidas", 2, "Modificar Anuncio");
			}else {
		
		// Obtencion de datos
		String nuevoNombreAnuncio = txtNombreProducto.getText();
		String descripcion = txtDesProducto.getText();
		String fechaPublicacion = txtFechaInicioAnuncio.getText();
		LocalDate fechaFin = dtPckrFechaFinalAnuncio.getValue();
		String fechaInicioAnuncio = fechaPublicacion.toString();
		String fechaFinAnuncio = fechaFin.toString();
		String rutaImagen = lblRutaImagen.getText();
		
		// Se crea un arraylist temporal para trabajarlo mejor
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>(); 
		for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
				anuncios = ((Anunciante) usuario).getAnuncios();
				break;
			}
		}
		
		
		// se modifica el anuncio
		for (Anuncio anuncio : anuncios) {
			if (nombreAnuncioAModificar.equals(anuncio.getNombreProducto())) {
				anuncio.setNombreProducto(nuevoNombreAnuncio);
				anuncio.setDescripcion(descripcion);
				anuncio.setFechaPublicacion(fechaInicioAnuncio);
				anuncio.setFechaFinPublicacion(fechaFinAnuncio);
				anuncio.setFoto(rutaImagen);
				anuncio.setTiempoLimite(0);
				break;
			}
		}
		// Se modifica el arraylist de anuncios globales
		ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		for (Anuncio anuncio : anunciosGlobales) {
			if (nombreAnuncioAModificar.equals(anuncio.getNombreProducto())) {
				anuncio.setNombreProducto(nuevoNombreAnuncio);
				anuncio.setDescripcion(descripcion);
				anuncio.setFechaPublicacion(fechaInicioAnuncio);
				anuncio.setFechaFinPublicacion(fechaFinAnuncio);
				anuncio.setFoto(rutaImagen);
				anuncio.setTiempoLimite(0);
				break;
			}
		}
		
		// Se añade el arraylist modificado a los anuncios globales
		ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);
		// Se setea el nuevo arraylist con el anuncio modificado al anunciante con la sesion iniciada
		ModelFactoryController.getInstance().anuncianteSesionIniciada.setAnuncios(anuncios);
		// Se modifica del arraylist de usuarios global
		ArrayList<Usuario> usuarios = ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios();
		for (Usuario usuario : usuarios) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre())&& usuario instanceof Anunciante) {
				((Anunciante) usuario).setAnuncios(anuncios);
				break;
			}
		}
		ModelFactoryController.getInstance().serializarModeloXml();
		ModelFactoryController.getInstance().serializarModeloBinario();
		JOptionPane.showMessageDialog(null, "Se modifico el anuncio con exito");
		ModelFactoryController.getInstance().guardarLog("Se modifico el anuncio: " + nombreAnuncioAModificar, 1,"Modificar anuncio");
			}
		}
	}
    
    /**
     * Metodo que nos permite seleccionar una imagen de nuestro sistema
     * @param event
     */
    @FXML
    public void insertarImagen(ActionEvent event) {
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
			imageViewProducto.setImage(image);

			try {
				Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
    }
    
    /**
     * Metodo que nos permite volver a la ventana de anunciante
     * @param event
     */
    @FXML
    public void volverVentanaAnuncianteView(ActionEvent event) {
    	cerrarVentanaModificarAnuncio();
		try {
			ModelFactoryController.getInstance().gestorVentanas.abrirVentanaAnuncianteView();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Metodo que cierra la ventana de modificacion de anuncios
	 */
	@FXML
	public void cerrarVentanaModificarAnuncio() {
		Stage stage = (Stage) this.btnVolver.getScene().getWindow();
	    stage.close();
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
		if (dtPckrFechaFinalAnuncio.getValue() == null) {
			centinela = true;
		}
		if (txtFechaInicioAnuncio.getText().isEmpty()) {
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
		String fechaPublicacion = txtFechaInicioAnuncio.getText();
		String[] fechaSplit = fechaPublicacion.split("-");
		int diaInicioAnuncio = Integer.parseInt(fechaSplit[2]);
		int mesInicioAnuncio = Integer.parseInt(fechaSplit[1]) - 1;
		int anioInicioAnuncio = Integer.parseInt(fechaSplit[0]);
		GregorianCalendar fechaInicioAnuncio = new GregorianCalendar(anioInicioAnuncio, mesInicioAnuncio,diaInicioAnuncio);
		LocalDate fechaFin = dtPckrFechaFinalAnuncio.getValue();
		//Verifica si las fechas son iguales
		if(fechaInicioAnuncio.get(GregorianCalendar.DAY_OF_YEAR) == fechaFin.getDayOfYear()) {
			return isValida;
		}
		//
		if (fechaFin.getYear() > fechaInicioAnuncio.get(GregorianCalendar.YEAR)) {
			isValida = true;
		}else if (fechaFin.getYear() == fechaInicioAnuncio.get(GregorianCalendar.YEAR)) {
			if (fechaFin.getDayOfYear() > fechaInicioAnuncio.get(GregorianCalendar.DAY_OF_YEAR) && fechaFin.getMonthValue() >= fechaInicioAnuncio.get(GregorianCalendar.MONTH)) {
				isValida = true;
			}else {
				ModelFactoryController.getInstance().guardarLog("La fecha final es menor a la inicial", 2, "Modificar Anuncio");
			}
		}
		return isValida;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtNombreDelAnunciante.setText(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>(); 
		for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
				anuncios = ((Anunciante) usuario).getAnuncios();
				break;
			}
		}
		for (Anuncio anuncio : anuncios) {
			if (nombreAnuncioAModificar.equals(anuncio.getNombreProducto())) {
				txtFechaInicioAnuncio.setText(anuncio.getFechaPublicacion());
				break;
			}
		}
	}
}

