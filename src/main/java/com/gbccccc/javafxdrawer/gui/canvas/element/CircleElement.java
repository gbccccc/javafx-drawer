package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Circle;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class CircleElement extends CanvasElement {
    private Circle circle;

    public CircleElement(Point base) {
        super(base, 2, 2);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (circle == null) {
            circle = new Circle();
        }

        circle.setRadius(points
                .get(1)
                .minus(getBase())
                .length());
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.strokeOval(
                getBase().getX() - circle.getRadius(), getBase().getY() - circle.getRadius(),
                circle.getRadius() * 2, circle.getRadius() * 2
        );
    }
}
