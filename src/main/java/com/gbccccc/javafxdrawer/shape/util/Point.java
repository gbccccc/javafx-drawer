package com.gbccccc.javafxdrawer.shape.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    public static final Point ORIGIN = new Point(0,0);

    private double x;
    private double y;

    public Point add(Translation t) {
        return new Point(x + t.getX(), y + t.getY());
    }

    public Translation minus(Point p) {
        return new Translation(x - p.x, y - p.y);
    }
}
