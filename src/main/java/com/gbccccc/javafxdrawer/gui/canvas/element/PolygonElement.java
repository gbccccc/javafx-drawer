package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Polygon;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class PolygonElement extends CanvasElement {
    private Polygon polygon;

    public PolygonElement(Point base) {
        super(base, 2, -1);
    }

    @Override
    public void updatePoints(List<Point> points) {
        if (points.size() < getMinPointNum()) {
            return;
        }
        if (polygon == null) {
            polygon = new Polygon();
        }

        this.polygon.setPoints(points);
    }

    @Override
    public void paint(GraphicsContext gc) {
        int size = polygon.getPoints().size();
        double[] doubles = new double[size], doubles1 = new double[size];
        for (int i = 0; i < size; i++) {
            doubles[i] = getBase().getX() + polygon.getPoints().get(i).getX();
            doubles1[i] = getBase().getY() + polygon.getPoints().get(i).getY();
        }

        gc.strokePolygon(doubles, doubles1, size);
    }
}
