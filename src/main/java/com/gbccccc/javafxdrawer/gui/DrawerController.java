package com.gbccccc.javafxdrawer.gui;

import com.gbccccc.javafxdrawer.shape.element.ElementFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawerController implements Initializable {
    @FXML
    public ChoiceBox operationChoiceBox;
    @FXML
    public Canvas canvas;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapeChoiceBox.getItems().addAll(ElementFactory.getShapeNames());
    }

}