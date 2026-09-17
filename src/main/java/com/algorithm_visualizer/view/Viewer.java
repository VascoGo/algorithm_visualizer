package com.algorithm_visualizer.view;

import com.algorithm_visualizer.model.structures.DataStructure;

import javafx.scene.canvas.Canvas;

public abstract class Viewer {

    private Canvas canvas;

    public Viewer(Canvas canvas) {
        this.canvas = canvas;
    }

    public abstract void render();
}
