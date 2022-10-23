package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.Main;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.MyListenerCopia;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anuncio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;
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

public class AnuncianteViewController implements Initializable{
	//Declaracion de atributos FXML
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
	private Label lblUserName;

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

    /**
     * Método que agrega los datos del anuncio seleccionado a la izq de la interfaz
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
     * @param event
     */
    @FXML
    private void selectorRolView(ActionEvent event) {
    	cerrarVentanaAnuncianteView();
    	ModelFactoryController.getInstance().gestorVentanas.abrirVentanaSelectorRolView();
    }
    
    /**
     * Metodo que nos permite cerrar sesion como anunciantes
     * @param event
     * @throws Exception 
     */
    @FXML
    public void cerrarSesion(ActionEvent event) throws Exception {
    	ModelFactoryController.getInstance().guardarLog("El " + lblUserName.getText() + " cierra sesión", 1, "Cerrar Sesión");
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
	 * @param event
	 */
	@FXML
	public void anunciar(ActionEvent event) {
		cerrarVentanaAnuncianteView();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaCrearAnuncioView();
	}
	
	/**
	 * Metodo que nos permite eliminar un anuncio
	 * @param event
	 */
	@FXML
	public void eliminarAnuncio(ActionEvent event) {
		//Se obtiene el nombre del producto a eliminar
		String nombreProducto = lblNombreProducto.getText();
		//Se crea un arraylist temporal para trabajarlo mejor
		ArrayList<Anuncio> anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();
		//Se elimina del usuario con la sesion iniciada
		for (Anuncio anuncio : anuncios) {
			if (nombreProducto.equals(anuncio.getNombreProducto())) {
				anuncios.remove(anuncio);
				break;
			}
		}
		
		//-------------------------------------------------------------------------------------------------
		//Se elimina del arraylist de anuncios global
		ArrayList<Anuncio> anunciosGlobales = ModelFactoryController.getInstance().aplicacionSubastas.getAnuncios();
		for (Anuncio anuncio : anunciosGlobales) {
			if (nombreProducto.equals(anuncio.getNombreProducto())) {
				anunciosGlobales.remove(anuncio);
				break;
			}
		}
		ModelFactoryController.getInstance().aplicacionSubastas.setAnuncios(anunciosGlobales);
		
		//-------------------------------------------------------------------------------------------------
		
		
		//Se setea el nuevo arraylist sin el anuncio al anunciante con la sesion iniciada
		ModelFactoryController.getInstance().anuncianteSesionIniciada.setAnuncios(anuncios);
		//Se elimina del arraylist de usuarios global
		ArrayList<Usuario> usuarios = ModelFactoryController.getInstance().aplicacionSubastas.getUsuarios();
		for (Usuario usuario : usuarios) {
			if (usuario.getNombre().equals(ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre()) && usuario instanceof Anunciante) {
				((Anunciante) usuario).setAnuncios(anuncios);
				break;
			}
		}
		ModelFactoryController.getInstance().serializarModeloXml();
		JOptionPane.showMessageDialog(null, "Se eliminó el anuncio con exito");
		ModelFactoryController.getInstance().guardarLog("Se elimina el anuncio: " + nombreProducto, 1, "Eliminar anuncio");
	}

	/**
	 * Metodo que nos lleva a la ventana de modificar anuncio
	 * @param event
	 */
	public void modificarAnuncio(ActionEvent event) {
		ModelFactoryController.getInstance().nombreAnuncioAModificar =  lblNombreProducto.getText();
		ModelFactoryController.getInstance().gestorVentanas.abrirVentanaModificarAnuncioView();
	}
	
    @Override	
    public void initialize(URL location, ResourceBundle resources) {
    	ModelFactoryController.getInstance().cargarDatosModelo();
    	lblUserName.setText("Anunciante : " + ModelFactoryController.getInstance().anuncianteSesionIniciada.getNombre());
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		anuncios = ModelFactoryController.getInstance().anuncianteSesionIniciada.getAnuncios();
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

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
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
