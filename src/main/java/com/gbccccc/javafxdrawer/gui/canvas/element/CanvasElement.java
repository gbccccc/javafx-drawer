package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Translation;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class CanvasElement implements Cloneable{
    private Point base;

    @Getter
    private final String type;
    @Getter
    private final int minPointNum;
    @Getter
    private final int maxPointNum;

    @Getter
    private String label;
    @Setter
    private ElementListener listener;

    protected Point getBase() {
        return base;
    }

    public void setLabel(String label) {
        this.label = label;
        if (listener != null) {
            listener.onElementChanged();
        }
    }


    public CanvasElement(Point base, String type, int minPointNum, int maxPointNum) {
        this.base = base;
        this.type = type;
        this.minPointNum = minPointNum;
        this.maxPointNum = maxPointNum;
    }

    public abstract void updatePoints(List<Point> points);

    public abstract void paint(GraphicsContext gc);

    public void move(Translation translation) {
        base = base.add(translation);
        if (listener != null) {
            listener.onElementChanged();
        }
    }

    @Override
    public CanvasElement clone() {
        try {
            CanvasElement clone = (CanvasElement) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
