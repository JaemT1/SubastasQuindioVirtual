package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.MyListenerCopia;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AdvertisementLimitedAmountException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.IncorrectAmountOfferedException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Comprador;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
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

public class CompradorViewController implements Initializable {
	@FXML
	private Label lblUserName;

	@FXML
	private Label lblFechaInicio;

	@FXML
	private Label lblNombreProducto;

	@FXML
	private Label lblFechaFin;

	@FXML
	private ImageView imgProducto;

	@FXML
	private Label lblDesProducto;

	@FXML
	private GridPane grid;

	@FXML
	private Label lblNombreAnunciante;
	
    @FXML
    private Label lblEstado;

	@FXML
	private TextField txtValorPuja;

	@FXML
	private TextField txtEliminarPuja;

	@FXML
	private Label lblCategoria;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnPujar;

	@FXML
	private Button btnMostrarListaOfertas;

	@FXML
	private Button btnMisOfertas;

	@FXML
	private Button btnEliminarPuja;

	@FXML
	private ScrollPane scroll;

	@FXML
	private VBox chosenAnnounce;

	@FXML
	private Label lblPrecioProducto;

	private Image image;
	private MyListenerCopia myListener;
	Stage stage = new Stage();
	Calendar cal1 = Calendar.getInstance();


	/**
	 * Metodo que nos permite pujar
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void pujar(ActionEvent event) throws Exception {
		// Se obtiene el nombre del producto para buscar el anuncio
		String nombreProducto = lblNombreProducto.getText();
		double valorProducto = 0;
		double valorPuja = 0;
		Comprador comprador = new Comprador();
		for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario instanceof Comprador && usuario.getNombre()
					.equals(ModelFactoryController.getInstance().compradorSesionIniciada.getNombre())) {
				comprador = ((Comprador) usuario);
			}
		}
		int numOfertas = 0;
		// Verifica que no se excedan las 3 pujas por anuncio
		for (Puja pujas : comprador.getPujas()) {
			if (pujas.getNombreProducto().equals(nombreProducto)) {
				numOfertas++;
			}
			if (numOfertas == 3) {
				JOptionPane.showMessageDialog(null, "Ya ha alcanzado el limite de pujas en este anuncio");
				ModelFactoryController.getInstance().guardarLog("Limite de pujas alcanzado", 2, "Pujar");
				numOfertas = 0;
				throw new AdvertisementLimitedAmountException("Limite de pujas alcanzado para el anuncio "
						+ nombreProducto + " por el comprador " + comprador.getNombre());
			}
		}
		// Verifica si el campo de texto del valor a pujar está vacío
		if (txtValorPuja.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe ingresar un valor");
			ModelFactoryController.getInstance().guardarLog("No ingresó ningun dato en el campo del valor a pujar", 2,
					"Pujar");
			throw new InvalidInputException("Debe ingresar un valor");
		} else { // Si no está vacío verifica que solo sean numeros
			if (!esNumero(txtValorPuja.getText())) {
				JOptionPane.showMessageDialog(null, "El valor de la puja debe contener solo numeros");
				ModelFactoryController.getInstance().guardarLog("Ingresó letras en el campo del valor a pujar", 2,
						"Pujar");
				throw new InvalidInputException("El valor de la puja debe contener solo numeros");
			} else {// Y si son solo numeros obtiene los valores
				valorProducto = Double.parseDouble(lblPrecioProducto.getText());
				// Se obtiene el valor de la puja
				valorPuja = Double.parseDouble(txtValorPuja.getText());
			}
			// verificacion de que la puja sea mayor al valor inicial del producto
			if (valorProducto > valorPuja) {
				JOptionPane.showMessageDialog(null,
						"El valor de la puja debe ser mayor al precio del producto: " + "$" + valorProducto);
				throw new IncorrectAmountOfferedException(
						"El valor de la puja debe ser mayor al valor inicial de subasta");
			} else {// Si el valor a pujar es correcto obtiene los datos necesarios, crea la puja y
					// la guarda

				// Se setean los objetos necesarios para el constructor de la puja
				Anuncio anuncioAPujar = new Anuncio();

				// Se obtiene el codigo de la puja
				int codigoPuja = ModelFactoryController.getInstance().aplicacionSubastas.getCantidadPujas();

				// Se le suma a la cantidad de pujas
				int nuevaCantidadPujas = ModelFactoryController.getInstance().aplicacionSubastas.getCantidadPujas() + 1;

				// Se setea la nueva cantidad de pujas
				ModelFactoryController.getInstance().aplicacionSubastas.setCantidadPujas(nuevaCantidadPujas);

				// Se obtiene el anuncio
				ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas
						.getAnuncios();

				for (Anuncio anuncio : anunciosGlobales) {
					if (nombreProducto.equals(anuncio.getNombreProducto())) {
						anuncioAPujar = anuncio;
						break;
					}
				}

				// Se construye el objeto puja
				Puja puja = new Puja(valorPuja, anuncioAPujar.getNombreAnunciante(), anuncioAPujar.getNombreProducto(),
						comprador.getNombre(), codigoPuja, comprador.getCorreo());

				// Se añade al anuncio la puja
				anuncioAPujar.getPujas().add(puja);

				// Se setea el arraylist de anuncios globales con la nueva puja en el anuncio
				// especifico
				ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);

				// Se setea la puja en el anuncio del anunciante en especifico
				ArrayList<Usuario> usuarios = ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios();

				/*for (Usuario usuario : usuarios) {
					if (lblNombreAnunciante.getText().equals(usuario.getNombre()) && usuario instanceof Anunciante) {
						ArrayList<Anuncio> anunciosAnunciante = ((Anunciante) usuario).getAnuncios();
						for (Anuncio anuncio : anunciosAnunciante) {
							if (anuncioAPujar.getNombreProducto().equals(anuncio.getNombreProducto())) {
								anuncio.getPujas().add(puja);
								((Anunciante) usuario).setAnuncios(anunciosAnunciante);
								break;
							}
						}
						break;
					}
				}*/

				// Se guarda la puja en el arraylist de pujas del comprador
				for (Usuario usuario : usuarios) {
					if (ModelFactoryController.getInstance().compradorSesionIniciada.getNombre()
							.equals(usuario.getNombre()) && usuario instanceof Comprador) {
						((Comprador) usuario).getPujas().add(puja);
						break;
					}
				}

				ModelFactoryController.getInstance().aplicacionSubastas.setUsuarios(usuarios);
				ModelFactoryController.getInstance().guardarLog("Se guarda la puja", 1, "Pujar");
				ModelFactoryController.getInstance().serializarModeloXml();
				ModelFactoryController.getInstance().serializarModeloBinario();
				ModelFactoryController.getInstance().guardarPuja(puja);
				txtValorPuja.setText("");
				JOptionPane.showMessageDialog(null, "Puja Realizada con exito!");
				grid.getChildren().clear();
				cargarAnuncios();
			}
		}
	}

	/**
	 * Método que permite eliminar una puja
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void eliminarPuja(ActionEvent event) throws Exception {
		// Se obtiene el nombre del producto para buscar el anuncio
		String nombreProducto = lblNombreProducto.getText();
		Anuncio anuncioAEliminarPuja = new Anuncio();
		// Se obtiene el anuncio
		ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		// Verifica si el campo de texto del valor a pujar está¡ vacío
		if (txtEliminarPuja.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe ingresar un valor");
			ModelFactoryController.getInstance().guardarLog("No ingresó ningun dato en el campo del codigo de la puja a eliminar", 2, "Pujar");
			throw new InvalidInputException("Debe ingresar un valor");
		} else { // Si no está vacío verifica que solo sean numeros
			if (!esNumero(txtEliminarPuja.getText())) {
				JOptionPane.showMessageDialog(null, "El codigo de la puja debe contener solo numeros");
				ModelFactoryController.getInstance().guardarLog("Ingresó letras en el campo del valor a pujar", 2,"eliminarPuja");
				throw new InvalidInputException("El codigo de la puja debe contener solo numeros");
			}

			for (Anuncio anuncio : anunciosGlobales) {
				if (nombreProducto.equals(anuncio.getNombreProducto())) {
					anuncioAEliminarPuja = anuncio;
					break;
				}
			}

			// Se obtiene el comprador desde los usuarios globales
			Comprador comprador = new Comprador();
			for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
				if (usuario instanceof Comprador && usuario.getNombre()
						.equals(ModelFactoryController.getInstance().compradorSesionIniciada.getNombre())) {
					comprador = ((Comprador) usuario);
				}
			}
			int codigoPujaEliminar = Integer.parseInt(txtEliminarPuja.getText());
			boolean centinela = false;

			// Verifcamos que el codigo de la puja exista en el anuncio
			for (Puja pujaAnuncio : anuncioAEliminarPuja.getPujas()) {
				if (codigoPujaEliminar == pujaAnuncio.getCodigoPuja()) {
					for (Puja pujaComprador : comprador.getPujas()) {
						if (codigoPujaEliminar == pujaComprador.getCodigoPuja()) {
							centinela = true;
							break;
						}
					}
				}
			}
			// Si el centinela es false es porque el codigo no existe
			if (!centinela) {
				JOptionPane.showMessageDialog(null, "El codigo de la puja no existe");
				ModelFactoryController.getInstance().guardarLog("Ingresó codigo incorrecto de la puja", 2,
						"eliminarPuja");
				throw new InvalidInputException("El codigo de la puja es incorrecto");
			} else { // Si es true elimina la puja
						// Remueve la puja del anuncio en el arraylist global
				Puja pujaAEliminar = new Puja();
				for (Puja pujaEliminar : anuncioAEliminarPuja.getPujas()) {
					if (codigoPujaEliminar == pujaEliminar.getCodigoPuja()) {
						anuncioAEliminarPuja.getPujas().remove(pujaEliminar);
						pujaAEliminar = pujaEliminar;
						break;
					}
				}

				// Se setea el arraylist de anuncios globales sin puja en el anuncio especifico
				ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);

				// Se elimina la puja en el anuncio del anunciante en especifico
				ArrayList<Usuario> usuarios = ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios();

				for (Usuario usuario : usuarios) {
					if (lblNombreAnunciante.getText().equals(usuario.getNombre()) && usuario instanceof Anunciante) {
						ArrayList<Anuncio> anunciosAnunciante = ((Anunciante) usuario).getAnuncios();

						for (Anuncio anuncio : anunciosAnunciante) {
							if (anuncioAEliminarPuja.getNombreProducto().equals(anuncio.getNombreProducto())) {
								ArrayList<Puja> pujasAnuncio = anuncio.getPujas();

								for (Puja puja : pujasAnuncio) {
									if (puja.getCodigoPuja() == codigoPujaEliminar) {
										anuncio.getPujas().remove(puja);
										break;
									}
								}
								((Anunciante) usuario).setAnuncios(anunciosAnunciante);
								break;
							}
						}
						break;
					}
				}

				// Se elimina la puja en el arraylist de pujas del comprador
				for (Usuario usuario : usuarios) {
					if (ModelFactoryController.getInstance().compradorSesionIniciada.getNombre()
							.equals(usuario.getNombre()) && usuario instanceof Comprador) {
						((Comprador) usuario).getPujas().remove(pujaAEliminar);
						break;
					}
				}

				ModelFactoryController.getInstance().aplicacionSubastas.setUsuarios(usuarios);
				ModelFactoryController.getInstance().guardarLog("Se Elimina la puja", 1, "Pujar");
				ModelFactoryController.getInstance().serializarModeloXml();
				ModelFactoryController.getInstance().serializarModeloBinario();
				txtEliminarPuja.setText("");
				JOptionPane.showMessageDialog(null, "Puja Eliminada con exito!");
				grid.getChildren().clear();
				cargarAnuncios();
			}
		}
	}

	/**
	 * Método que muestra la lista de ofertas de un anuncio en específico
	 * 
	 * @param event
	 */
	@FXML
	void MostrarListaOfertas(ActionEvent event) {
		Stage stage = new Stage();
		ModelFactoryController.getInstance().nombreAnuncioAModificar = lblNombreProducto.getText();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaOfertasAnuncioView(stage);
	}

	/**
	 * Método que muestra las ofertas del comprador con sesión iniciada
	 */
	@FXML
	public void verMisOfertas() {
		Stage stage = new Stage();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaOfertasCompradorView(stage);
	}

	/**
	 * Metodo que nos permite setear los datos de un anuncio en especifíco a la izq
	 * de la interfaz
	 * 
	 * @param anuncio anuncio a setear
	 */
	private void setChosenAnnounce(Anuncio anuncio) {
		lblNombreProducto.setText(anuncio.getNombreProducto());
		lblPrecioProducto.setText(String.valueOf(anuncio.getValorInicial()));
		image = new Image(anuncio.getFoto());
		imgProducto.setImage(image);
		lblDesProducto.setText(anuncio.getDescripcion());
		lblFechaInicio.setText("Va desde: " + anuncio.getFechaPublicacion());
		lblFechaFin.setText("Hasta: " + anuncio.getFechaFinPublicacion());
		lblCategoria.setText("Categoria: " + anuncio.getTipoProducto());
		lblNombreAnunciante.setText(anuncio.getNombreAnunciante());
	}

	/**
	 * Metodo que cierra la ventana comprador
	 */
	@FXML
	public void cerrarVentanaCompradorView() {
		Stage stage = (Stage) this.btnCerrarSesion.getScene().getWindow();
		stage.close();
	}

	/**
	 * Metodo que nos permite cerrar sesion como comprador
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void cerrarSesion(ActionEvent event) throws Exception {
		ModelFactoryController.getInstance().guardarLog("El " + lblUserName.getText() + " cierra sesión", 1,"Cerrar Sesión");
		Comprador comprador = new Comprador();
		ModelFactoryController.getInstance().compradorSesionIniciada = comprador;
		cerrarVentanaCompradorView();
		ModelFactoryController.getInstance().gestorVentanas.start(stage);
	}

	/**
	 * Metodo que verifica si la informacion de un campo de texto tiene letras
	 * 
	 * @param esNumero
	 * @return
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
	 * Método que carga los anuncios, usado para recargar el grid después de haber
	 * hecho alguna operación
	 */
	public void cargarAnuncios() {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		anuncios = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		lblUserName.setText("Comprador : " + ModelFactoryController.getInstance().compradorSesionIniciada.getNombre());
		// Pone el primer anuncio a la izq
		if (anuncios.size() > 0) {
			setChosenAnnounce(anuncios.get(0));
			myListener = new MyListenerCopia() {
				@Override
				public void onClickListener(Anuncio anuncio) {
					//setChosenAnnounce(anuncio);
					// Se obtiene el dia del a�o actual
					int diaActual = cal1.get(Calendar.DAY_OF_YEAR);
					int anioActual = cal1.get(Calendar.YEAR);
					// Se setea el anuncio clickeado
					setChosenAnnounce(anuncio);
					// Se obtiene y construye en objeto la fecha final del anuncio
					String fecha = anuncio.getFechaFinPublicacion();
					String[] fechaSplit = fecha.split("-");
					int diaFinAnuncio = Integer.parseInt(fechaSplit[2]);
					int mesFinAnuncio = Integer.parseInt(fechaSplit[1]) - 1;
					int anioFinAnuncio = Integer.parseInt(fechaSplit[0]);
					GregorianCalendar fechaFinAnuncio = new GregorianCalendar(anioFinAnuncio, mesFinAnuncio,
							diaFinAnuncio);
					
					
					if (anioActual >= fechaFinAnuncio.get(GregorianCalendar.YEAR)) {
						if (diaActual > fechaFinAnuncio.get(GregorianCalendar.DAY_OF_YEAR)) {// Se hace la comparacion con el dia actual y el dia de la fecha fin
							btnPujar.setDisable(true);
							txtValorPuja.setDisable(true);
							btnEliminarPuja.setDisable(true);
							txtEliminarPuja.setDisable(true);
							lblEstado.setText("Estado: No disponible");
						}else {
							btnPujar.setDisable(false);
							txtValorPuja.setDisable(false);
							btnEliminarPuja.setDisable(false);
							txtEliminarPuja.setDisable(false);
							lblEstado.setText("Estado: Disponible");
						}
					}else {
						btnPujar.setDisable(false);
						txtValorPuja.setDisable(false);
						btnEliminarPuja.setDisable(false);
						txtEliminarPuja.setDisable(false);
						lblEstado.setText("Estado: Disponible");
					}
					
					if (!anuncio.getEstado()) {
						lblEstado.setText("Estado: Vendido");
						btnPujar.setDisable(true);
						txtValorPuja.setDisable(true);
						btnEliminarPuja.setDisable(true);
						txtEliminarPuja.setDisable(true);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ModelFactoryController.getInstance().cargarDatosModelo();
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		anuncios = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		lblUserName.setText("Comprador : " + ModelFactoryController.getInstance().compradorSesionIniciada.getNombre());
		// Pone el primer anuncio a la izq
		if (anuncios.size() > 0) {
			setChosenAnnounce(anuncios.get(0));
			myListener = new MyListenerCopia() {
				@Override
				public void onClickListener(Anuncio anuncio) {
					//setChosenAnnounce(anuncio);
					// Se obtiene el dia del a�o actual
					int diaActual = cal1.get(Calendar.DAY_OF_YEAR);
					int anioActual = cal1.get(Calendar.YEAR);
					// Se setea el anuncio clickeado
					setChosenAnnounce(anuncio);
					// Se obtiene y construye en objeto la fecha final del anuncio
					String fecha = anuncio.getFechaFinPublicacion();
					String[] fechaSplit = fecha.split("-");
					int diaFinAnuncio = Integer.parseInt(fechaSplit[2]);
					int mesFinAnuncio = Integer.parseInt(fechaSplit[1]) - 1;
					int anioFinAnuncio = Integer.parseInt(fechaSplit[0]);
					GregorianCalendar fechaFinAnuncio = new GregorianCalendar(anioFinAnuncio, mesFinAnuncio,
							diaFinAnuncio);
					
					
					if (anioActual >= fechaFinAnuncio.get(GregorianCalendar.YEAR)) {
						if (diaActual > fechaFinAnuncio.get(GregorianCalendar.DAY_OF_YEAR)) {// Se hace la comparacion con el dia actual y el dia de la fecha fin
							btnPujar.setDisable(true);
							txtValorPuja.setDisable(true);
							btnEliminarPuja.setDisable(true);
							txtEliminarPuja.setDisable(true);
							lblEstado.setText("Estado: No disponible");
						}else {
							btnPujar.setDisable(false);
							txtValorPuja.setDisable(false);
							btnEliminarPuja.setDisable(false);
							txtEliminarPuja.setDisable(false);
							lblEstado.setText("Estado: Disponible");
						}
					}else {
						btnPujar.setDisable(false);
						txtValorPuja.setDisable(false);
						btnEliminarPuja.setDisable(false);
						txtEliminarPuja.setDisable(false);
						lblEstado.setText("Estado: Disponible");
					}
					
					if (!anuncio.getEstado()) {
						lblEstado.setText("Estado: Vendido");
						btnPujar.setDisable(true);
						txtValorPuja.setDisable(true);
						btnEliminarPuja.setDisable(true);
						txtEliminarPuja.setDisable(true);
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
