package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Vector;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

import java.util.List;

public abstract class CanvasElement {
    @Getter
    private Point base;

    @Getter
    private final int minPointNum;
    @Getter
    private final int maxPointNum;

    public CanvasElement(Point base, int minPointNum, int maxPointNum) {
        this.base = base;
        this.minPointNum = minPointNum;
        this.maxPointNum = maxPointNum;
    }

    public abstract void updatePoints(List<Point> points);

    public abstract void paint(GraphicsContext gc);

    public void translate(Vector v) {
        base = base.add(v);
    }
}
