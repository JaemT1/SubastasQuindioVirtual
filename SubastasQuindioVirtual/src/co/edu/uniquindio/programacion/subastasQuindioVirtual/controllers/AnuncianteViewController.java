package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.Main;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.MyListener;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.AdvertisementLimitedAmountException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.EmptyArrayException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.InvalidInputException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.OfferNotFoundException;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Transaccion;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.persistence.Persistencia;
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
    private Label lblEstado;
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
	@FXML
    private Button btnGuardarCsv;
	@FXML
    private Component panelBase;
	@FXML
	private Label lblRutaCSV;

	private Image image;
	private MyListener myListener;
	private Stage stage = new Stage();
	Calendar cal1 = Calendar.getInstance();

	/**
	 * Método que agrega los datos del anuncio seleccionado a la izq de la interfaz
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
		ModelFactoryController.getInstance().guardarLog("El " + lblUserName.getText() + " cierra sesión", 1,"Cerrar Sesión");
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
	 * Método que nos lleva a la ventana de crear anuncios
	 * 
	 * @param event
	 * @throws AdvertisementLimitedAmountException 
	 */
	@FXML
	public void anunciar(ActionEvent event) throws AdvertisementLimitedAmountException {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>(); 
		for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
				anuncios = ((Anunciante) usuario).getAnuncios();
				break;
			}
		}
		if (anuncios.size() == 3) {
			JOptionPane.showMessageDialog(null, "Ya ha alcanzado el límite de anuncios por anunciante");
			ModelFactoryController.getInstance().guardarLog("Limite de anuncios alcanzado", 2, "Anunciar");
			throw new AdvertisementLimitedAmountException("Limite de anuncios alcanzado por: " + ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
		}else {
			cerrarVentanaAnuncianteView();
			ModelFactoryController.getInstance().gestorVentanas.abrirVentanaCrearAnuncioView();			
		}
		
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
		//ArrayList<Anuncio> anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>(); 
		for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
				anuncios = ((Anunciante) usuario).getAnuncios();
				break;
			}
		}

		
		// Se elimina del usuario con la sesion iniciada
		for (Anuncio anuncio : anuncios) {
			if (nombreProducto.equals(anuncio.getNombreProducto())) {
				anuncios.remove(anuncio);
				break;
			}
		}

		// Se elimina del arraylist de anuncios global
		ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		for (Anuncio anuncio : anunciosGlobales) {
			if (nombreProducto.equals(anuncio.getNombreProducto())) {
				anunciosGlobales.remove(anuncio);
				break;
			}
		}
		ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);


		// Se setea el nuevo arraylist sin el anuncio al anunciante con la sesion iniciada
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
		JOptionPane.showMessageDialog(null, "Se eliminó el anuncio con exito");
		ModelFactoryController.getInstance().guardarLog("Se elimina el anuncio: " + nombreProducto, 1,"Eliminar anuncio");
		grid.getChildren().clear();
		cargarAnuncios();
	}

	/**
	 * Metodo que nos lleva a la ventana de modificar anuncio
	 * @param event
	 */
	public void modificarAnuncio(ActionEvent event) {
		ModelFactoryController.getInstance().nombreAnuncioAModificar = lblNombreProducto.getText();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaModificarAnuncioView();
	}
	
	/**
	 * Metodo que nos lleva a la ventana de ver ofertas
	 * @param event
	 */
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
	 * @throws Exception 
	 */
	@FXML
	public void vender(ActionEvent event) throws Exception {
		int registro = JOptionPane.showConfirmDialog(null,"Ha realizado los acuerdos" + "\n" + "con el comprador?");
        if (registro == 0) {
			if (!esNumero(txtCodigoPuja.getText())) {
				JOptionPane.showMessageDialog(null, "El codigo solo debe contener numeros");
				ModelFactoryController.getInstance().guardarLog("El codigo solo debe contener numeros", 2, "vender");
				throw new InvalidInputException("El codigo solo debe contener numeros");
			}
			if (txtCodigoPuja.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar el c�digo de una oferta");
				ModelFactoryController.getInstance().guardarLog("Se debe ingresar el codigo de una oferta", 2, "vender");
				throw new InvalidInputException("Debe ingresar el c�digo de una oferta");
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
				
				//Se obtienen los anuncios del usuario
				ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>(); 
				for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
					if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
						anuncios = ((Anunciante) usuario).getAnuncios();
						break;
					}
				}
				
				//Se obtienen las pujas de ese anuncio
				ArrayList<Puja> pujasAnuncio = new ArrayList<Puja>();
				for (Anuncio anuncio : anuncios) {
					if (nombreProducto.equals(anuncio.getNombreProducto())) {
						pujasAnuncio = anuncio.getPujas();
						break;
					}
				}
				
				//Se obtiene el valor de la puja y el nombre del comprador
				for (Puja puja : pujasAnuncio) {
					if (codigoPujaSeleccionado == puja.getCodigoPuja()) {
						valorFinal = puja.getValor();
						nombreComprador = puja.getNombreComprador();
						break;
					}else {
						JOptionPane.showMessageDialog(null, "El codigo de la puja no existe");
						ModelFactoryController.getInstance().guardarLog("Ingresó codigo incorrecto de la puja", 2,"Vender");
						throw new OfferNotFoundException("El codigo de la puja es incorrecto");
					}
				}
				
				//Se setea el estado del anuncio en false indicando que ya está vendido
				for (Anuncio anuncio2 : anuncios) {
					if (nombreProducto.equals(anuncio2.getNombreProducto())) {
						anuncio2.setEstado(false);
						break;
					}
				}
				
				// Se modifica el arraylist de anuncios globales
				ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
				for (Anuncio anuncio : anunciosGlobales) {
					if (nombreProducto.equals(anuncio.getNombreProducto())) {
						anuncio.setEstado(false);
						break;
					}
				}
				
				// Se añade el arraylist modificado a los anuncios globales
				ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);
				
				
				//Se procesa el codigo de la transaccion
				
				// Se obtiene el codigo de la puja
				int codigoTransaccion = ModelFactoryController.getInstance().aplicacionSubastas.getCantidadTransacciones();

				// Se le suma a la cantidad de pujas
				int nuevaCantidadTransacciones = ModelFactoryController.getInstance().aplicacionSubastas.getCantidadTransacciones() + 1;

				// Se setea la nueva cantidad de pujas
				ModelFactoryController.getInstance().aplicacionSubastas.setCantidadTransacciones(nuevaCantidadTransacciones);
				
				
				Transaccion transaccion = new Transaccion(codigoTransaccion, fecha, valorFinal, nombreAnunciante, nombreComprador,nombreProducto);
				
				//ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
				for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
					if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
						((Anunciante) usuario).getTransacciones().add(transaccion);
						break;
					}
				}
				
				ModelFactoryController.getInstance().guardarLog("Se vende el producto: " + nombreProducto + " a " + nombreComprador, 1, "Vender Producto");
				ModelFactoryController.getInstance().guardarTransaccion(transaccion);
				ModelFactoryController.getInstance().serializarModeloXml();
				ModelFactoryController.getInstance().serializarModeloBinario();
				JOptionPane.showMessageDialog(null, "Se vendió con exito el anuncio");
				txtCodigoPuja.setVisible(true);
			}
		}else if(registro == 1) {
			JOptionPane.showMessageDialog(null, "Vuelva a intentar cuando esten los acuerdos hechos" + "\n" + "Puede comunicarse mediante el correo del comprador");
		}
	}
	
	/**
	 * Verifica el campo de texto que contenga solo numeros
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
	 * Método que carga los anuncios, usado para actualizar el grid cuando se han hecho operaciones CRUD
	 */
	@FXML
	public void cargarAnuncios() {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario instanceof Anunciante && usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre())) {
				anuncios = ((Anunciante)usuario).getAnuncios();
			}
		}
		lblUserName.setText("Anunciante : " + ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
		// Pone el primer anuncio a la izq
		if (anuncios.size() > 0) {
			setChosenAnnounce(anuncios.get(0));
			myListener = new MyListener() {
				@Override
				public void onClickListener(Anuncio anuncio) {
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
							lblCodigoPuja.setVisible(true);
							txtCodigoPuja.setVisible(true);
							btnVender.setVisible(true);
							btnModificarAnuncio.setDisable(true);
							lblEstado.setText("Estado: No disponible");
						} else {
							lblCodigoPuja.setVisible(false);
							txtCodigoPuja.setVisible(false);
							btnVender.setVisible(false);
							btnModificarAnuncio.setDisable(false);
							lblEstado.setText("Estado: Disponible");
						}
					}else {
						lblCodigoPuja.setVisible(false);
						txtCodigoPuja.setVisible(false);
						btnVender.setVisible(false);
						btnModificarAnuncio.setDisable(false);
						lblEstado.setText("Estado: Disponible");
					}
					
					if (!anuncio.getEstado()) {
						lblEstado.setText("Estado: Vendido");
						lblCodigoPuja.setVisible(false);
						txtCodigoPuja.setVisible(false);
						btnVender.setVisible(false);
						btnModificarAnuncio.setDisable(true);
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

				PlantillaAnuncioController PlantillaController = fxmlLoader.getController();
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
	
	/**
	 * Método que permite guardar en .csv los anuncios y transacciones de un anunciante
	 * @param event
	 * @throws EmptyArrayException
	 */
	@FXML
	private void guardarCsv(ActionEvent event) throws EmptyArrayException {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
		for (Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario instanceof Anunciante && usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre())) {
				anuncios = ((Anunciante) usuario).getAnuncios();
				transacciones = ((Anunciante) usuario).getTransacciones();
			}
		}
		//Verifica que los arraylists no estén vacios
		if (anuncios.size() == 0 || transacciones.size() == 0) {
			JOptionPane.showMessageDialog(null, "Debe tener como minimo un anuncio y una compra realizada para exportar en csv");
			ModelFactoryController.getInstance().guardarLog("Array de transacciones o anuncios vacios", 2,"guardarCSV");
			throw new EmptyArrayException("Array vacio");
		} else {
			JFileChooser selectorCarpeta = new JFileChooser();
			selectorCarpeta.setCurrentDirectory(new File("*"));
			selectorCarpeta.setDialogTitle("Seleccione la carpeta");
			selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (selectorCarpeta.showOpenDialog(panelBase) == JFileChooser.APPROVE_OPTION) {

				File carpetaSelecciona = selectorCarpeta.getSelectedFile();
				lblRutaCSV.setText(carpetaSelecciona.toString());
				String ruta = lblRutaCSV.getText();
				Persistencia.RUTA_ARCHIVO_CSV = ruta + "\\archivoAnunciante.csv";
			}

			ModelFactoryController.getInstance().guardarLog("Se crear archivo csv", 1, "guardarCSV");
			ModelFactoryController.getInstance().guardarCSV(anuncios, transacciones);
			JOptionPane.showMessageDialog(null, "Se guardo el archivo .csv");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ModelFactoryController.getInstance().cargarDatosModelo();
		lblUserName.setText("Anunciante : " + ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		//anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();

		for(Usuario usuario : ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios()) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
				anuncios = ((Anunciante) usuario).getAnuncios();
				break;
			}
		}
		
		// Pone el primer anuncio a la izq
		if (anuncios.size() > 0) {
			setChosenAnnounce(anuncios.get(0));
			myListener = new MyListener() {
				@Override
				public void onClickListener(Anuncio anuncio) {
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
							lblCodigoPuja.setVisible(true);
							txtCodigoPuja.setVisible(true);
							btnVender.setVisible(true);
							btnModificarAnuncio.setDisable(true);
							lblEstado.setText("Estado: No disponible");
						} else {
							lblCodigoPuja.setVisible(false);
							txtCodigoPuja.setVisible(false);
							btnVender.setVisible(false);
							btnModificarAnuncio.setDisable(false);
							lblEstado.setText("Estado: Disponible");
						}
					}else {
						lblCodigoPuja.setVisible(false);
						txtCodigoPuja.setVisible(false);
						btnVender.setVisible(false);
						btnModificarAnuncio.setDisable(false);
						lblEstado.setText("Estado: Disponible");
					}
					
					if (!anuncio.getEstado()) {
						lblEstado.setText("Estado: Vendido");
						lblCodigoPuja.setVisible(false);
						txtCodigoPuja.setVisible(false);
						btnVender.setVisible(false);
						btnModificarAnuncio.setDisable(true);
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

				PlantillaAnuncioController PlantillaController = fxmlLoader.getController();
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
