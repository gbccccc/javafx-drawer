package com.gbccccc.javafxdrawer.shape.model;

import java.util.ArrayList;
import java.util.List;

import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Translation;
import lombok.Data;

@Data
public class Polygon {
    private List<Point> points = new ArrayList<>();

    public void setPoints(List<Point> points) {
        if (points.size() == 0) {
            return;
        }

        this.points.clear();
        // force first point of this.points to be (0, 0)
        Translation displacement = Point.ORIGIN.minus(points.get(0));

        points.forEach(
                (point) -> this.points.add(point.add(displacement))
        );
    }
}
