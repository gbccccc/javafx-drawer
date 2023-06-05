package com.gbccccc.javafxdrawer.shape.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector {
    private int x;
    private int y;

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public Vector negative() {
        return new Vector(-x, -y);
    }

    public Vector minus(Vector v){
        return this.add(v.negative());
    }
}
