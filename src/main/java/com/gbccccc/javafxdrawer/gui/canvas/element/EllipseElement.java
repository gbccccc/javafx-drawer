package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Ellipse;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class EllipseElement extends CanvasElement {
    private Ellipse ellipse;

    public EllipseElement(Point base) {
        super(base, "Ellipse", 2, 2);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (ellipse == null) {
            ellipse = new Ellipse();
        }

        ellipse.setA((points.get(1).getX() - getBase().getX()) / 2);
        ellipse.setB((points.get(1).getY() - getBase().getY()) / 2);
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.strokeOval(
                getBase().getX() + (ellipse.getA() < 0 ? ellipse.getA() * 2 : 0),
                getBase().getY() + (ellipse.getB() < 0 ? ellipse.getB() * 2 : 0),
                Math.abs(ellipse.getA()) * 2, Math.abs(ellipse.getB()) * 2
        );
    }
}
