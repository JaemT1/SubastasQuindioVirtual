package co.edu.uniquindio.programacion.subastasQuindioVirtual.application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/UsuarioView.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cerrarVentanaUsuarioView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/UsuarioView.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void abrirVentanaCrearAnuncioView() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/co/edu/uniquindio/programacion/subastasquindioVirtual/view/CrearAnuncioView.fxml"));
		try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
