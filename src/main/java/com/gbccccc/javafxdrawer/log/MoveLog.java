package com.gbccccc.javafxdrawer.log;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;

import java.util.ArrayList;
import java.util.List;

import com.gbccccc.javafxdrawer.shape.util.Translation;

public class MoveLog extends Log {
    private final List<CanvasElement> toMoves;
    private final Translation translation;

    public MoveLog(CanvasElement toRemove, Translation translation) {
        this.toMoves = new ArrayList<>();
        this.toMoves.add(toRemove);
        this.translation = translation;
    }

    public MoveLog(List<CanvasElement> toRemoves, Translation translation) {
        this.toMoves = new ArrayList<>(toRemoves);
        this.translation = translation;
    }

    @Override
    public void undo() {
        for (CanvasElement toMove : toMoves) {
            toMove.move(translation.negative());
        }
    }

    @Override
    public void redo() {
        for (CanvasElement toMove : toMoves) {
            toMove.move(translation);
        }
    }
}
