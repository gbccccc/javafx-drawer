package com.gbccccc.javafxdrawer.log;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import javafx.collections.ObservableList;

import java.util.*;

public class AddLog extends Log {
    private final List<CanvasElement> toAdds;
    private final ObservableList<CanvasElement> elements;

    public AddLog(CanvasElement toRemove, ObservableList<CanvasElement> elements) {
        this.toAdds = new ArrayList<>();
        this.toAdds.add(toRemove);
        this.elements = elements;
    }

    public AddLog(List<CanvasElement> toRemoves, ObservableList<CanvasElement> elements) {
        this.toAdds = new ArrayList<>(toRemoves);
        this.elements = elements;
    }
    @Override
    public void undo() {
        elements.removeAll(toAdds);
    }

    @Override
    public void redo() {
        elements.addAll(toAdds);
    }
}
