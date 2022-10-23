package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;

public class PlantillaAnuncioControllerCopia {
	@FXML
    private Label lblNombreProducto;

    @FXML
    private Label lblPrecio;

    @FXML
    private ImageView imgProducto;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(anuncio);
    }

    private Anuncio anuncio;
    private MyListenerCopia myListener;

    public void setData(Anuncio anuncio, MyListenerCopia myListener) {
        this.anuncio = anuncio;
        this.myListener = myListener;
        lblNombreProducto.setText(anuncio.getNombreProducto());
        lblPrecio.setText(Main.CURRENCY + anuncio.getValorInicial());
        Image image = new Image(anuncio.getFoto());
        imgProducto.setImage(image);
    }
}
