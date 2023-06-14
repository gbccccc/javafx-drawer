package com.gbccccc.javafxdrawer.gui;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElementFactory;
import com.gbccccc.javafxdrawer.gui.canvas.element.ElementFactoryListener;
import com.gbccccc.javafxdrawer.gui.canvas.element.ElementListener;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class DrawerController implements Initializable, ElementFactoryListener, ElementListener {
    @FXML
    private TabPane tabPane;
    @FXML
    private ChoiceBox<String> operationChoiceBox;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;
    @FXML
    private Canvas canvas;
    @FXML
    public TableView<CanvasElement> elementTable;

    private final ObservableList<CanvasElement> elements = FXCollections.observableList(new LinkedList<>());

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

        initializeCanvas();

        initializeTable();
    }

    private void initializeCanvas() {
        canvas.setOnMousePressed(
                mouseEvent -> {
                    // right click will end the drawing process
                    if (mouseEvent.isSecondaryButtonDown()) {
                        CanvasElementFactory.getCanvasElementFactory().finish();
                        return;
                    }
                    if (!mouseEvent.isPrimaryButtonDown()) {
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

    private void initializeTable() {
        elementTable.setItems(elements);
        elementTable.setEditable(true);

        TableColumn<CanvasElement, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(element -> new SimpleStringProperty(element.getValue().getType()));
        elementTable.getColumns().add(type);

        TableColumn<CanvasElement, String> label = new TableColumn<>("Label");
        label.setCellFactory(TextFieldTableCell.forTableColumn());
        label.setCellValueFactory(element -> new SimpleStringProperty(element.getValue().getLabel()));
        label.setEditable(true);
        label.setOnEditCommit(event -> event.getRowValue().setLabel(event.getNewValue()));
        elementTable.getColumns().add(label);
    }

    private void repaint() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (CanvasElement element : elements) {
            element.paint(canvas.getGraphicsContext2D());
        }
        CanvasElementFactory.getCanvasElementFactory().paintElement(canvas.getGraphicsContext2D());
    }

    @Override
    public void onBuildingFinished(CanvasElement newElement) {
        newElement.setListener(this);
        elements.add(newElement);
        repaint();
    }

    @Override
    public void onBuildingChanged() {
        repaint();
    }


    @Override
    public void onElementChanged() {
        repaint();
    }
}