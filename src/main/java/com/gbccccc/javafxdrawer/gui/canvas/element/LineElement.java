package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Line;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class LineElement extends CanvasElement {
    private Line line;

    public LineElement(Point base) {
        super(base, "Line", 2, 2);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (line == null) {
            line = new Line();
        }

        line.setV(points.get(1).minus(getBase()));
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.strokeLine(
                getBase().getX(), getBase().getY(),
                getBase().getX() + line.getV().getX(), getBase().getY() + line.getV().getY()
        );
    }
}
