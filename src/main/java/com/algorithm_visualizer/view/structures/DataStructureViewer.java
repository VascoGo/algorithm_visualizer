package com.algorithm_visualizer.view.structures;

import com.algorithm_visualizer.controller.Controller;
import javafx.scene.canvas.Canvas;

public abstract class DataStructureViewer extends Canvas{

    protected Controller controller;

    public DataStructureViewer(double width, double height, Controller controller) {
        super(width, height);
        this.controller = controller;
    }

    public abstract void render();
}
