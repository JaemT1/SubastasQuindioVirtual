package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.Main;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.MyListenerCopia;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Transaccion;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnuncianteViewController implements Initializable {
	// Declaracion de atributos FXML
	@FXML
	private VBox chosenFruitCard;

	@FXML
	private Button btnIniciarSesion;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnModificarAnuncio;

	@FXML
	private Button btnEliminarAnuncio;

	@FXML
	private Button btnAnunciar;

	@FXML
	private Button btnVerOfertas;

	@FXML
	private Label lblUserName;

	@FXML
	private Label lblCodigoPuja;

	@FXML
	private Button btnVender;

	@FXML
	private TextField txtCodigoPuja;

	@FXML
	private Label lblNombreProducto;

	@FXML
	private Label lblPrecioProducto;

	@FXML
	private Label lblFechaInicio;

	@FXML
	private Label lblFechaFin;

	@FXML
	private Label lblDescripcion;

	@FXML
	private Label lblCategoria;

	@FXML
	private ImageView imgProducto;

	@FXML
	private ScrollPane scroll;

	@FXML
	private GridPane grid;

	private Image image;
	private MyListenerCopia myListener;
	private Stage stage = new Stage();
	Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
	Calendar cal3 = Calendar.getInstance();

	/**
	 * M茅todo que agrega los datos del anuncio seleccionado a la izq de la interfaz
	 * 
	 * @param anuncio
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
	}

	/**
	 * Metodo que nos lleva a la ventana para seleccionar un rol y logearnos
	 * 
	 * @param event
	 */
	@FXML
	private void selectorRolView(ActionEvent event) {
		cerrarVentanaAnuncianteView();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaSelectorRolView();
	}

	/**
	 * Metodo que nos permite cerrar sesion como anunciantes
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void cerrarSesion(ActionEvent event) throws Exception {
		ModelFactoryController.getInstance().guardarLog("El " + lblUserName.getText() + " cierra sesi贸n", 1,
				"Cerrar Sesi贸n");
		Anunciante anunciante = new Anunciante();
		ModelFactoryController.getInstance().anuncianteSesionIniciada = anunciante;
		cerrarVentanaAnuncianteView();
		ModelFactoryController.getInstance().gestorVentanas.start(stage);
	}

	/**
	 * Metodo que cierra la ventana principal
	 */
	@FXML
	public void cerrarVentanaAnuncianteView() {
		Stage stage = (Stage) this.btnCerrarSesion.getScene().getWindow();
		stage.close();
	}

	/**
	 * M茅todo que nos lleva a la ventana de crear anuncios
	 * 
	 * @param event
	 */
	@FXML
	public void anunciar(ActionEvent event) {
		cerrarVentanaAnuncianteView();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaCrearAnuncioView();
	}

	/**
	 * Metodo que nos permite eliminar un anuncio
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void eliminarAnuncio(ActionEvent event) throws Exception {
		// Se obtiene el nombre del producto a eliminar
		String nombreProducto = lblNombreProducto.getText();
		// Se crea un arraylist temporal para trabajarlo mejor
		ArrayList<Anuncio> anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();
		// Se elimina del usuario con la sesion iniciada
		for (Anuncio anuncio : anuncios) {
			if (nombreProducto.equals(anuncio.getNombreProducto())) {
				anuncios.remove(anuncio);
				break;
			}
		}

		// -------------------------------------------------------------------------------------------------
		// Se elimina del arraylist de anuncios global
		ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		for (Anuncio anuncio : anunciosGlobales) {
			if (nombreProducto.equals(anuncio.getNombreProducto())) {
				anunciosGlobales.remove(anuncio);
				break;
			}
		}
		ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);

		// -------------------------------------------------------------------------------------------------

		// Se setea el nuevo arraylist sin el anuncio al anunciante con la sesion
		// iniciada
		ModelFactoryController.getInstance().anuncianteSesionIniciada.setAnuncios(anuncios);
		// Se elimina del arraylist de usuarios global
		ArrayList<Usuario> usuarios = ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios();
		for (Usuario usuario : usuarios) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre())
					&& usuario instanceof Anunciante) {
				((Anunciante) usuario).setAnuncios(anuncios);
				break;
			}
		}
		ModelFactoryController.getInstance().serializarModeloXml();
		ModelFactoryController.getInstance().serializarModeloBinario();
		JOptionPane.showMessageDialog(null, "Se elimin贸 el anuncio con exito");
		ModelFactoryController.getInstance().guardarLog("Se elimina el anuncio: " + nombreProducto, 1,
				"Eliminar anuncio");
	}

	/**
	 * Metodo que nos lleva a la ventana de modificar anuncio
	 * 
	 * @param event
	 */
	public void modificarAnuncio(ActionEvent event) {
		ModelFactoryController.getInstance().nombreAnuncioAModificar = lblNombreProducto.getText();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaModificarAnuncioView();
	}

	public void verOfertas(ActionEvent event) {
		Stage stage = new Stage();
		ModelFactoryController.getInstance().nombreAnuncioAModificar = lblNombreProducto.getText();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaOfertasAnuncioView(stage);
	}

	/**
	 * Metodo que nos permite venderle un producto especifico a un comprador, cuando
	 * se proporciona el id de la oferta o puja
	 * 
	 * @param event
	 * @throws InvalidInputException
	 */
	@FXML
	public void vender(ActionEvent event) throws InvalidInputException {
		if (!verificarCampoEdad(txtCodigoPuja.getText())) {
			JOptionPane.showMessageDialog(null, "El codigo solo debe contener n鷐eros");
			throw new InvalidInputException("El cogigo solo debe contener numeros");
		}
		if (txtCodigoPuja.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe ingresar el c骴igo de una oferta");
		} else {

			// Se obtiene el codigo de la puja seleccionada
			int codigoPujaSeleccionado = Integer.parseInt(txtCodigoPuja.getText());

			// Se obtiene la fecha
			int dia = cal1.get(Calendar.DAY_OF_MONTH);
			int mes = cal1.get(Calendar.MONTH) + 1;
			int anio = cal1.get(Calendar.YEAR);

			// Valores para construir el objeto transaccion
			String fecha = "" + anio + "-" + mes + "-" + dia;
			double valorFinal = 0;
			String nombreAnunciante = ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre();
			String nombreComprador = "";
			String nombreProducto = lblNombreProducto.getText();

			ArrayList<Anuncio> anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();
			ArrayList<Puja> pujasAnuncio = new ArrayList<Puja>();

			for (Anuncio anuncio : anuncios) {
				if (lblNombreProducto.getText().equals(anuncio.getNombreProducto())) {
					pujasAnuncio = anuncio.getPujas();
					break;
				}
			}

			for (Puja puja : pujasAnuncio) {
				if (codigoPujaSeleccionado == puja.getCodigoPuja()) {
					valorFinal = puja.getValor();
					nombreComprador = puja.getNombreComprador();
					break;
				}
			}

			Transaccion transaccion = new Transaccion(0, fecha, valorFinal, nombreAnunciante, nombreComprador,
					nombreProducto);
			ModelFactoryController.getInstance().guardarLog(
					"Se vende el producto: " + nombreProducto + " a " + nombreComprador, 1, "Vender Producto");
			ModelFactoryController.getInstance().guardarTransaccion(transaccion);
			JOptionPane.showMessageDialog(null, "Se vendi贸 con exito el anuncio");

		}
	}

	public boolean verificarCampoEdad(String edad) {
		boolean esApta = true;
		char[] edadChar = edad.toCharArray();
		for (char c : edadChar) {
			if (!Character.isDigit(c)) {
				esApta = false;
			}
		}
		return esApta;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ModelFactoryController.getInstance().cargarDatosModelo();
		lblUserName
				.setText("Anunciante : " + ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();
		// Pone el primer anuncio a la izq
		if (anuncios.size() > 0) {
			setChosenAnnounce(anuncios.get(0));
			myListener = new MyListenerCopia() {
				@Override
				public void onClickListener(Anuncio anuncio) {
					// Se obtiene el dia del a駉 actual
					int diaActual = cal1.get(Calendar.DAY_OF_YEAR);
					// Se setea el anuncio clickeado
					setChosenAnnounce(anuncio);
					// Se obtiene y construye en objeto la fecha final del anuncio
					String fecha = anuncio.getFechaFinPublicacion();
					String[] fechaSplit = fecha.split("-");
					int diaFinAnuncio = Integer.parseInt(fechaSplit[2]);
					int mesFinAnuncio = Integer.parseInt(fechaSplit[1]) - 1;
					int a駉FinAnuncio = Integer.parseInt(fechaSplit[0]);
					GregorianCalendar fechaFinAnuncio = new GregorianCalendar(a駉FinAnuncio, mesFinAnuncio,
							diaFinAnuncio);

					if (diaActual > fechaFinAnuncio.get(GregorianCalendar.DAY_OF_YEAR)) {// Se hace la comparacion con
						lblCodigoPuja.setVisible(true);
						txtCodigoPuja.setVisible(true);
						btnVender.setVisible(true);
						// el dia actual del a駉 y
						// el dia del a駉 de la
						// fecha fin del anuncio
					} else {
						lblCodigoPuja.setVisible(false);
						txtCodigoPuja.setVisible(false);
						btnVender.setVisible(false);
					}
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
