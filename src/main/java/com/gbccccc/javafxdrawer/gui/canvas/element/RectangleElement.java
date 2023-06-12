package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Polygon;
import com.gbccccc.javafxdrawer.shape.model.Rectangle;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class RectangleElement extends CanvasElement {
    private Rectangle rectangle;

    public RectangleElement(Point base) {
        super(base, 2, 2);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (rectangle == null) {
            rectangle = new Rectangle();
        }

        rectangle.setLength(points.get(1).getX() - getBase().getX());
        rectangle.setWidth(points.get(1).getY() - getBase().getY());
    }

    @Override
    public void paint(GraphicsContext gc) {

    }
}
