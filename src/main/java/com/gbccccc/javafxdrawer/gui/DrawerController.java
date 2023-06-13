package com.gbccccc.javafxdrawer.gui;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElementFactory;
import com.gbccccc.javafxdrawer.gui.canvas.element.ElementFactoryListener;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class DrawerController implements Initializable, ElementFactoryListener {
    @FXML
    private TabPane tabPane;
    @FXML
    private ChoiceBox<String> operationChoiceBox;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;
    @FXML
    private Canvas canvas;

    private final List<CanvasElement> elements = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CanvasElementFactory.getCanvasElementFactory().setListener(this);
        shapeChoiceBox.getItems().addAll(CanvasElementFactory.getShapeNames());

        tabPane.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, tabSingleSelectionModel, t1) -> {
                    operationChoiceBox.getSelectionModel().clearSelection();
                    shapeChoiceBox.getSelectionModel().clearSelection();
                }
        );

        shapeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> CanvasElementFactory.getCanvasElementFactory().reset()
        );

        canvas.setOnMousePressed(
                mouseEvent -> {
                    // right click will end the drawing process
                    if (mouseEvent.isSecondaryButtonDown()) {
                        CanvasElementFactory.getCanvasElementFactory().finish();
                        return;
                    }

                    Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
                    if (CanvasElementFactory.getCanvasElementFactory().isElementBuilding()) {
                        CanvasElementFactory.getCanvasElementFactory()
                                .amendPoint(point).addPoint(point).commit();
                    } else {
                        CanvasElementFactory.getCanvasElementFactory().setType(
                                shapeChoiceBox.getValue()
                        ).setBase(point).buildElementPrototype().addPoint(point).commit();
                    }
                }
        );
        canvas.setOnMouseMoved(
                mouseEvent -> {
                    if (CanvasElementFactory.getCanvasElementFactory().isElementBuilding()) {
                        CanvasElementFactory.getCanvasElementFactory().amendPoint(
                                new Point(mouseEvent.getX(), mouseEvent.getY())
                        ).commit();
                    }
                });
    }

    @Override
    public void onBuildingFinished(CanvasElement newElement) {
        elements.add(newElement);
        repaint();
    }

    @Override
    public void onBuildingChanged() {
        repaint();
    }

    private void repaint() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        elements.forEach(
                element -> element.paint(canvas.getGraphicsContext2D())
        );
        CanvasElementFactory.getCanvasElementFactory().paintElement(canvas.getGraphicsContext2D());
    }
}