package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.MyListener;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;

public class PlantillaAnuncioController {
	@FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(fruit);
    }

    private Fruit fruit;
    private MyListener myListener;

    public void setData(Fruit fruit, MyListener myListener) {
        this.fruit = fruit;
        this.myListener = myListener;
        nameLabel.setText(fruit.getName());
        priceLable.setText(Main.CURRENCY + fruit.getPrice());
        Image image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        img.setImage(image);
    }
}
