package com.gbccccc.javafxdrawer.log;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import com.gbccccc.javafxdrawer.gui.canvas.element.CompositeElement;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BindLog extends Log {
    private final CompositeElement compositeElement;
    private final ObservableList<CanvasElement> elements;

    @Override
    public void undo() {
        elements.remove(compositeElement);
        elements.addAll(compositeElement.getChildren());
    }

    @Override
    public void redo() {
        elements.add(compositeElement);
        elements.removeAll(compositeElement.getChildren());
    }
}
