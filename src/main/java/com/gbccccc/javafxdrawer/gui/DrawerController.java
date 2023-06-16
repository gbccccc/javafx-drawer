package com.gbccccc.javafxdrawer.gui;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import com.gbccccc.javafxdrawer.gui.canvas.element.CompositeElement;
import com.gbccccc.javafxdrawer.gui.canvas.factory.CanvasElementFactory;
import com.gbccccc.javafxdrawer.gui.canvas.factory.ElementFactoryListener;
import com.gbccccc.javafxdrawer.gui.canvas.element.ElementListener;
import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Vector;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class DrawerController implements Initializable, ElementFactoryListener, ElementListener {
    @FXML
    private SplitPane mainScene;
    @FXML
    private ChoiceBox<String> operationChoiceBox;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;
    @FXML
    private Canvas canvas;
    @FXML
    public TableView<CanvasElement> elementTable;

    private Point originMousePoint;
    private Point lastMousePoint;

    private final ObservableList<CanvasElement> elements = FXCollections.observableList(new LinkedList<>());
    private List<CanvasElement> copiedElements = new ArrayList<>();

    private final Map<String, EventHandler<MouseEvent>> mousePressedHandlers = new HashMap<>();
    private final Map<String, EventHandler<MouseEvent>> mouseMovedHandlers = new HashMap<>();
    private final Map<String, EventHandler<MouseEvent>> mouseDraggedHandlers = new HashMap<>();
    private final Map<String, EventHandler<MouseEvent>> mouseReleasedHandlers = new HashMap<>();
    private final Map<KeyCode, EventHandler<KeyEvent>> keyHandlers = new HashMap<>();
    private final Map<KeyCode, EventHandler<KeyEvent>> keyWithControlHandlers = new HashMap<>();
    private final Map<KeyCode, EventHandler<KeyEvent>> keyWithShiftHandlers = new HashMap<>();


    private static final ArrayList<String> operationNames = new ArrayList<>(List.of(new String[]{
            "draw", "move"
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
        initializeCanvas();
        initializeTable();
        initializeShortcutKey();
    }

    private void initializeChoiceBoxes() {
        shapeChoiceBox.getItems().addAll(CanvasElementFactory.getShapeNames());
        shapeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> {
                    CanvasElementFactory.getCanvasElementFactory().reset();
                    operationChoiceBox.getSelectionModel().select("draw");
                }
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

        mousePressedHandlers.put("move",
                mouseEvent -> {
                    originMousePoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                    lastMousePoint = originMousePoint;
                }
        );
        mouseDraggedHandlers.put("move",
                mouseEvent -> {
                    Point curMousePoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                    Vector translation = curMousePoint.minus(lastMousePoint);
                    for (CanvasElement element : elementTable.getSelectionModel().getSelectedItems()) {
                        element.move(translation);
                    }
                    lastMousePoint = curMousePoint;
                }
        );
        mouseReleasedHandlers.put("move",
                mouseEvent -> {
                    Point curMousePoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                    Vector translation = curMousePoint.minus(lastMousePoint);
                    for (CanvasElement element : elementTable.getSelectionModel().getSelectedItems()) {
                        element.move(translation);
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
        canvas.setOnMouseDragged(
                mouseEvent -> {
                    String operation = operationChoiceBox.getValue();
                    if (mouseDraggedHandlers.containsKey(operation)) {
                        mouseDraggedHandlers.get(operation).handle(mouseEvent);
                    }
                }
        );
        canvas.setOnMouseReleased(
                mouseEvent -> {
                    String operation = operationChoiceBox.getValue();
                    if (mouseReleasedHandlers.containsKey(operation)) {
                        mouseReleasedHandlers.get(operation).handle(mouseEvent);
                    }
                }
        );
    }

    private void initializeTable() {
        elementTable.setItems(elements);
        elementTable.setEditable(true);
        elementTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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


    private void initializeShortcutKey() {
        // copy
        keyWithControlHandlers.put(KeyCode.C,
                keyEvent -> {
                    copiedElements.clear();
                    for (CanvasElement element : elementTable.getSelectionModel().getSelectedItems()) {
                        copiedElements.add(element.clone());
                    }
                }
        );
        // paste
        keyWithControlHandlers.put(KeyCode.V,
                keyEvent -> {
                    if (copiedElements == null || copiedElements.isEmpty()) {
                        return;
                    }

                    for (CanvasElement element : copiedElements) {
                        addElement(element);
                    }

                    List<CanvasElement> tempCopiedElements = new ArrayList<>(copiedElements);
                    copiedElements.clear();
                    for (CanvasElement element : tempCopiedElements) {
                        copiedElements.add(element.clone());
                    }
                }
        );

        // bind
        keyWithControlHandlers.put(KeyCode.B,
                keyEvent -> {
                    List<CanvasElement> selectedElements = new ArrayList<>(elementTable.getSelectionModel().getSelectedItems());
                    if (selectedElements.isEmpty()) {
                        return;
                    }

                    bindElements(selectedElements);
                }
        );
        // unbind
        keyWithControlHandlers.put(KeyCode.D,
                keyEvent -> {
                    if (elementTable.getSelectionModel().getSelectedItems().size() != 1) {
                        return;
                    }
                    if (elementTable.getSelectionModel().getSelectedItem() instanceof CompositeElement compositeElement) {
                        unbindElements(compositeElement);
                    }
                }
        );

        // remove
        keyHandlers.put(KeyCode.DELETE,
                keyEvent -> {
                    List<CanvasElement> selectedElements = new ArrayList<>(elementTable.getSelectionModel().getSelectedItems());
                    if (selectedElements.isEmpty()) {
                        return;
                    }

                    for (CanvasElement element : selectedElements) {
                        removeElement(element);
                    }
                }
        );

        // operation shortcuts
        keyHandlers.put(KeyCode.DIGIT1,
                keyEvent -> operationChoiceBox.getSelectionModel().select("draw")
        );
        keyHandlers.put(KeyCode.DIGIT2,
                keyEvent -> operationChoiceBox.getSelectionModel().select("move")
        );

        // selection shortcuts
        keyHandlers.put(KeyCode.W,
                keyEvent ->
                {
                    if (elementTable.getSelectionModel().getSelectedItems().isEmpty()) {
                        elementTable.getSelectionModel().selectLast();
                        return;
                    }

                    elementTable.getSelectionModel().clearAndSelect(
                            elementTable.getSelectionModel().getFocusedIndex() - 1
                    );
                }
        );
        keyHandlers.put(KeyCode.S,
                keyEvent -> {
                    if (elementTable.getSelectionModel().getSelectedItems().isEmpty()) {
                        elementTable.getSelectionModel().selectFirst();
                        return;
                    }

                    elementTable.getSelectionModel().clearAndSelect(
                            elementTable.getSelectionModel().getFocusedIndex() + 1
                    );
                }
        );
        keyWithShiftHandlers.put(KeyCode.W,
                keyEvent -> {
                    if (elementTable.getSelectionModel().getSelectedItems().isEmpty()) {
                        elementTable.getSelectionModel().selectLast();
                        return;
                    }

                    elementTable.getSelectionModel().selectPrevious();
                }
        );
        keyWithShiftHandlers.put(KeyCode.S,
                keyEvent -> {
                    if (elementTable.getSelectionModel().getSelectedItems().isEmpty()) {
                        elementTable.getSelectionModel().selectFirst();
                        return;
                    }

                    elementTable.getSelectionModel().selectNext();
                }
        );

        mainScene.setOnKeyPressed(
                keyEvent -> {
                    if (keyEvent.isControlDown()) {
                        if (keyWithControlHandlers.containsKey(keyEvent.getCode())) {
                            keyWithControlHandlers.get(keyEvent.getCode()).handle(keyEvent);
                        }
                        return;
                    }
                    if (keyEvent.isShiftDown()) {
                        if (keyWithShiftHandlers.containsKey(keyEvent.getCode())) {
                            keyWithShiftHandlers.get(keyEvent.getCode()).handle(keyEvent);
                        }
                        return;
                    }

                    if (keyHandlers.containsKey(keyEvent.getCode())) {
                        keyHandlers.get(keyEvent.getCode()).handle(keyEvent);
                    }
                }
        );
    }

    private void repaint() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (CanvasElement element : elements) {
            element.paint(canvas.getGraphicsContext2D());
        }
        CanvasElementFactory.getCanvasElementFactory().paintElement(canvas.getGraphicsContext2D());
    }

    private void bindElements(List<CanvasElement> children) {
        CompositeElement compositeElement = new CompositeElement(children);
        elements.add(compositeElement);
        elements.removeAll(children);
        repaint();
    }

    private void unbindElements(CompositeElement compositeElement) {
        elements.addAll(compositeElement.getChildren());
        elements.remove(compositeElement);
        repaint();
    }

    private void addElement(CanvasElement element) {
        element.setListener(this);
        elements.add(element);
        repaint();
    }

    private void removeElement(CanvasElement element) {
        elements.remove(element);
        repaint();
    }

    @Override
    public void onBuildingFinished(CanvasElement newElement) {
        addElement(newElement);
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