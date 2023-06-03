package com.gbccccc.javafxdrawer.gui;

import com.gbccccc.javafxdrawer.shape.ShapeFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawerController implements Initializable {
    @FXML
    private ChoiceBox<String> shapeChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapeChoiceBox.getItems().addAll(ShapeFactory.getShapeNames());
    }

}