package com.gbccccc.javafxdrawer.shape.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Translation {
    public static final Translation ZERO = new Translation(0,0);
    private double x;
    private double y;

    public Translation add(Translation t) {
        return new Translation(x + t.x, y + t.y);
    }

    public Translation negative() {
        return new Translation(-x, -y);
    }

    public Translation minus(Translation v) {
        return this.add(v.negative());
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }
}
