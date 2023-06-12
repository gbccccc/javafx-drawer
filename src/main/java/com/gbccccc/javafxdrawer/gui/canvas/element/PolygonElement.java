package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.model.Line;
import com.gbccccc.javafxdrawer.shape.model.Polygon;
import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Vector;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class PolygonElement extends CanvasElement{
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

    }
}
