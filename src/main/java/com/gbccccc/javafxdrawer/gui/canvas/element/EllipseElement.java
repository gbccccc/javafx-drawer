package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Ellipse;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class EllipseElement extends CanvasElement {
    private Ellipse ellipse;

    public EllipseElement(Point base) {
        super(base, 2, 2);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (ellipse == null) {
            ellipse = new Ellipse(0, 0);
        }

        ellipse.setA((points.get(1).getX() - getBase().getX()) / 2);
        ellipse.setB((points.get(1).getY() - getBase().getY()) / 2);
    }

    @Override
    public void paint(GraphicsContext gc) {

    }
}
