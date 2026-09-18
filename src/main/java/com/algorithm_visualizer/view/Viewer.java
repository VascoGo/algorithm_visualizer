package com.algorithm_visualizer.view;

import javafx.scene.canvas.Canvas;

public abstract class Viewer {

    private Canvas canvas;

    public Viewer(Canvas canvas) {
        this.canvas = canvas;
    }

    protected Canvas getCanvas() {
        return this.canvas;
    }

    public abstract void render();
}
