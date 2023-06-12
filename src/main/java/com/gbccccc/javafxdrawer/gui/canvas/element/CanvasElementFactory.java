package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.log.LogList;
import com.gbccccc.javafxdrawer.shape.util.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class CanvasElementFactory {
    private static ArrayList<String> shapeNames = new ArrayList<>(List.of(new String[]{
            "Circle", "Composite", "Ellipse", "Line", "Rectangle", "Polygon"
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

    public CanvasElementFactory setListener(ElementFactoryListener listener) {
        this.listener = listener;
        return this;
    }

    public CanvasElementFactory setType(String type) {
        this.type = type;
        return this;
    }

    public CanvasElementFactory setBase(Point base) {
        this.base = base;
        return this;
    }

    public CanvasElementFactory reset() {
        type = shapeNames.get(0);
        base = new Point(0, 0);
        element = null;
        points = null;
        return this;
    }

    public CanvasElementFactory buildElement() {
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
        if (element != null) {
            points.set(points.size() - 1, point);
        }
        return this;
    }

    public CanvasElementFactory commit() {
        // last point is the temporary point
        if (points.size() > element.getMaxPointNum()) {
            return this.finish();
        }
        element.updatePoints(points);
        return this;
    }

    public CanvasElementFactory finish() {
        if (element != null && listener != null) {
            // last point is the temporary point
            points.remove(points.size() - 1);
            if (points.size() >= element.getMinPointNum()) {
                element.updatePoints(points);
                listener.onFinish(element);
            }
            this.reset();
        }
        return this;
    }

    public CanvasElement getElement() {
        return element;
    }

    public void paintElement(GraphicsContext gc) {
        if (element != null) {
            element.paint(gc);
        }
    }
}