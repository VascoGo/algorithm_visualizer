package com.algorithm_visualizer.view.structures;

import javafx.scene.canvas.Canvas;

public abstract class DataStructureViewer extends Canvas{

    public DataStructureViewer(double width, double height) {
        super(width, height);
    }

    public abstract void render();
}
