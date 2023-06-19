package com.gbccccc.javafxdrawer.gui.canvas.element;

import com.gbccccc.javafxdrawer.shape.util.Point;
import com.gbccccc.javafxdrawer.shape.util.Translation;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class CompositeElement extends CanvasElement {
    private List<CanvasElement> children = new ArrayList<>();

    public List<CanvasElement> getChildren() {
        return new ArrayList<>(children);
    }

    public CompositeElement(List<CanvasElement> children) {
        super(Point.ORIGIN, "Composite", -1, -1);
        this.children.addAll(children);
    }

    @Override
    public void updatePoints(List<Point> points) {

    }

    @Override
    public void move(Translation v) {
        for (CanvasElement child : children) {
            child.move(v);
        }
    }

    @Override
    public void paint(GraphicsContext gc) {
        for (CanvasElement child : children) {
            child.paint(gc);
        }
    }

    @Override
    public CanvasElement clone() {
        CompositeElement clone = (CompositeElement) super.clone();
        // TODO: copy mutable state here, so the clone can't change the internals of the original
        clone.children = new ArrayList<>();
        for (CanvasElement child : this.children) {
            clone.children.add(child.clone());
        }
        return clone;
    }
}
