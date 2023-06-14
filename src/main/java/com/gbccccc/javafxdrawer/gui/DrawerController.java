package com.gbccccc.javafxdrawer.gui;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import com.gbccccc.javafxdrawer.gui.canvas.factory.CanvasElementFactory;
import com.gbccccc.javafxdrawer.gui.canvas.factory.ElementFactoryListener;
import com.gbccccc.javafxdrawer.gui.canvas.element.ElementListener;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class DrawerController implements Initializable, ElementFactoryListener, ElementListener {
    @FXML
    private ChoiceBox<String> operationChoiceBox;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;
    @FXML
    private Canvas canvas;
    @FXML
    public TableView<CanvasElement> elementTable;

    private final ObservableList<CanvasElement> elements = FXCollections.observableList(new LinkedList<>());
    private final Map<String, EventHandler<MouseEvent>> mousePressedHandlers = new HashMap<>();
    private final Map<String, EventHandler<MouseEvent>> mouseMovedHandlers = new HashMap<>();
    private final Map<String, EventHandler<MouseEvent>> mouseReleasedHandlers = new HashMap<>();
    private static final ArrayList<String> operationNames = new ArrayList<>(List.of(new String[]{
            "draw", "translate"
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
        initializeCanvas();
        initializeTable();
    }

    private void initializeChoiceBoxes() {
        shapeChoiceBox.getItems().addAll(CanvasElementFactory.getShapeNames());
        shapeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> CanvasElementFactory.getCanvasElementFactory().reset()
        );

        operationChoiceBox.getItems().addAll(operationNames);
        operationChoiceBox.getSelectionModel().selectFirst();
    }

    private void initializeCanvas() {
        CanvasElementFactory.getCanvasElementFactory().setListener(this);
        mousePressedHandlers.put("draw",
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
        mouseMovedHandlers.put("draw",
                mouseEvent -> {
                    if (CanvasElementFactory.getCanvasElementFactory().isElementBuilding()) {
                        CanvasElementFactory.getCanvasElementFactory().amendPoint(
                                new Point(mouseEvent.getX(), mouseEvent.getY())
                        ).commit();
                    }
                }
        );

        canvas.setOnMousePressed(
                mouseEvent -> {
                    String operation = operationChoiceBox.getValue();
                    if (mousePressedHandlers.containsKey(operation)) {
                        mousePressedHandlers.get(operation).handle(mouseEvent);
                    }
                }
        );
        canvas.setOnMouseMoved(
                mouseEvent -> {
                    String operation = operationChoiceBox.getValue();
                    if (mouseMovedHandlers.containsKey(operation)) {
                        mouseMovedHandlers.get(operation).handle(mouseEvent);
                    }
                }
        );
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