package com.gbccccc.javafxdrawer.gui.log;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;

import java.util.*;

public class RemoveLog extends Log {
    private final List<CanvasElement> toRemoves;
    private final ObservableList<CanvasElement> elements;

    public RemoveLog(CanvasElement toRemove, ObservableList<CanvasElement> elements) {
        this.toRemoves = new ArrayList<>();
        this.toRemoves.add(toRemove);
        this.elements = elements;
    }

    public RemoveLog(List<CanvasElement> toRemoves, ObservableList<CanvasElement> elements) {
        this.toRemoves = new ArrayList<>(toRemoves);
        this.elements = elements;
    }

    @Override
    public void undo() {
        elements.addAll(toRemoves);
    }

    @Override
    public void redo() {
        elements.removeAll(toRemoves);
    }
}
