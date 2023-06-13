package com.gbccccc.javafxdrawer.shape.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector {
    public static final Vector ZERO = new Vector(0,0);
    private double x;
    private double y;

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public Vector negative() {
        return new Vector(-x, -y);
    }

    public Vector minus(Vector v) {
        return this.add(v.negative());
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }
}
