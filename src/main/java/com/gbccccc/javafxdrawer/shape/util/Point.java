package com.gbccccc.javafxdrawer.shape.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    public static final Point ORIGIN = new Point(0,0);

    private double x;
    private double y;

    public Point add(Vector v) {
        return new Point(x + v.getX(), y + v.getY());
    }

    public Vector minus(Point p) {
        return new Vector(x - p.x, y - p.y);
    }
}
