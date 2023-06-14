package com.gbccccc.javafxdrawer.gui.canvas.factory;

import com.gbccccc.javafxdrawer.gui.canvas.element.CanvasElement;

public interface ElementFactoryListener {
    void onBuildingFinished(CanvasElement element);

    void onBuildingChanged();
}
