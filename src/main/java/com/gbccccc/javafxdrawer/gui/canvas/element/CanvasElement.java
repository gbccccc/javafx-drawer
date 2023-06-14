package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Vector;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class CanvasElement {
    @Getter
    private Point base;

    @Getter
    private final String type;
    @Getter
    private final int minPointNum;
    @Getter
    private final int maxPointNum;

    @Getter
    private String label;

    public void setLabel(String label) {
        this.label = label;
        if (listener != null) {
            listener.onElementChanged();
        }
    }

    @Setter
    private ElementListener listener;

    public CanvasElement(Point base, String type, int minPointNum, int maxPointNum) {
        this.base = base;
        this.type = type;
        this.minPointNum = minPointNum;
        this.maxPointNum = maxPointNum;
    }

    public abstract void updatePoints(List<Point> points);

    public abstract void paint(GraphicsContext gc);

    public void translate(Vector v) {
        base = base.add(v);
        if (listener != null) {
            listener.onElementChanged();
        }
    }
}
