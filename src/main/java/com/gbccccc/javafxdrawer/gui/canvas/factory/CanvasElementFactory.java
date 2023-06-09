package com.gbccccc.javafxdrawer.gui.canvas.factory;

import com.gbccccc.javafxdrawer.gui.canvas.element.*;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class CanvasElementFactory {
    private static final ArrayList<String> shapeNames = new ArrayList<>(List.of(new String[]{
            "Line", "Rectangle", "Polygon", "Circle", "Ellipse"
    }));

    public static String[] getShapeNames() {
        return shapeNames.toArray(new String[0]);
    }

    private String type = shapeNames.get(0);
    private Point base = new Point(0, 0);
    private List<Point> points;
    private CanvasElement element;
    private ElementFactoryListener listener;

    private CanvasElementFactory() {
    }


    // lazy singleton holder
    private static final class CanvasFactoryHolder {
        private static final CanvasElementFactory CANVAS_ELEMENT_FACTORY = new CanvasElementFactory();
    }

    public static CanvasElementFactory getCanvasElementFactory() {
        return CanvasFactoryHolder.CANVAS_ELEMENT_FACTORY;
    }

    public void setListener(ElementFactoryListener listener) {
        this.listener = listener;
    }

    public CanvasElementFactory setType(String type) {
        this.type = type;
        return this;
    }

    public CanvasElementFactory setBase(Point base) {
        this.base = base;
        return this;
    }

    public void reset() {
        type = shapeNames.get(0);
        base = new Point(0, 0);
        element = null;
        points = null;

        listener.onBuildingChanged();
    }

    public boolean isElementBuilding() {
        return element != null;
    }

    public CanvasElementFactory buildElementPrototype() {
        if ("Circle".equals(type)) {
            element = new CircleElement(base);
        } else if ("Ellipse".equals(type)) {
            element = new EllipseElement(base);
        } else if ("Line".equals(type)) {
            element = new LineElement(base);
        } else if ("Rectangle".equals(type)) {
            element = new RectangleElement(base);
        } else if ("Polygon".equals(type)) {
            element = new PolygonElement(base);
        }
        points = new ArrayList<>();
        points.add(base);
        return this;
    }

    public CanvasElementFactory addPoint(Point point) {
        if (element == null) {
            return this;
        }
        points.add(point);
        return this;
    }

    public CanvasElementFactory amendPoint(Point point) {
        if (element == null) {
            return this;
        }
        points.set(points.size() - 1, point);
        return this;
    }

    public void commit() {
        if (element == null) {
            return;
        }

        // last point is the temporary point
        if (points.size() - 1 >= element.getMaxPointNum() && element.getMaxPointNum() != -1) {
            this.finish();
            return;
        }
        element.updatePoints(points);

        listener.onBuildingChanged();
    }

    public void finish() {
        if (element != null && listener != null) {
            // last point is the temporary point
            points.remove(points.size() - 1);
            if (points.size() >= element.getMinPointNum()) {
                element.updatePoints(points);
                listener.onBuildingFinished(element);
            }
            this.reset();
        }
    }

    public void paintElement(GraphicsContext gc) {
        if (element != null && points.size() >= element.getMinPointNum()) {
            element.paint(gc);
        }
    }
}