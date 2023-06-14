package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Rectangle;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class RectangleElement extends CanvasElement {
    private Rectangle rectangle;

    public RectangleElement(Point base) {
        super(base, "Rectangle", 2, 2);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (rectangle == null) {
            rectangle = new Rectangle();
        }

        rectangle.setWidth(points.get(1).getX() - getBase().getX());
        rectangle.setHeight(points.get(1).getY() - getBase().getY());
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.strokeRect(
                getBase().getX() + (rectangle.getWidth() < 0 ? rectangle.getWidth() : 0),
                getBase().getY() + (rectangle.getHeight() < 0 ? rectangle.getHeight() : 0),
                Math.abs(rectangle.getWidth()), Math.abs(rectangle.getHeight())
        );
    }
}
