package com.gbccccc.javafxdrawer.gui.log;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LabelLog extends Log{
    private final CanvasElement element;
    private final String oldLabel;
    private final String newLabel;

    @Override
    public void undo() {
        element.setLabel(oldLabel);
    }

    @Override
    public void redo() {
        element.setLabel(newLabel);
    }
}
