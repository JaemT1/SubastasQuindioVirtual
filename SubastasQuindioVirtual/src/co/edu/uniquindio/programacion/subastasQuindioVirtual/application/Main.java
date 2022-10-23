package co.edu.uniquindio.programacion.subastasQuindioVirtual.application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private Stage primaryStage = new Stage();
	public static final String CURRENCY = "$";
   
	@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/UsuarioViewTestNuevo.fxml"));
        primaryStage.setTitle("Subastas Quindio");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
	
	/**
	 * Método que abre la ventana para crear un anuncio
	 */
	public void abrirVentanaCrearAnuncioView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/CrearAnuncioVi.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que abre la ventana para seleccionar el rol para el login
	 */
	public void abrirVentanaSelectorRolView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/SelectorRolView.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que abre la ventana de login anunciante
	 */
	public void abrirVentanaLoginAnuncianteView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/LoginAnuncianteView.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que abre la ventana de registro de anunciantes
	 */
	public void abrirVentanaRegistroAnuncianteView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/RegisterAnuncianteView.fxml"));
		try {
			Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que abre la ventana de iniciar sesi�n del comprador
	 */
	public void abrirVentanaLoginCompradorView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/LoginCompradorView.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que abre la ventana de resgistro del comprador
	 */
	public void abrirVentanaRegistroCompradorView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/RegisterCompradorView.fxml"));
		try {
			Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que abre la ventana del anunciante
	 */
	public void abrirVentanaAnuncianteView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/AnuncianteView.fxml"));
		try {
			Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que abre la ventana del comprador
	 */
	public void abrirVentanaCompradorView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/CompradorView.fxml"));
		try {
			Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que abre la ventana de modificar anuncio
	 */
	public void abrirVentanaModificarAnuncioView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/ModificarAnuncioView.fxml"));
		try {
			Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
