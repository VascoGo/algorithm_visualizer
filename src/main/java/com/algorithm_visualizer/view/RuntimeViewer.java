package com.algorithm_visualizer.view;

import com.algorithm_visualizer.view.structures.DataStructureViewer;

import javafx.scene.canvas.Canvas;

public class RuntimeViewer extends Viewer {
    private DataStructureViewer dsv;

    public RuntimeViewer(Canvas canvas, DataStructureViewer dsv) {
        super(canvas);
        this.dsv = dsv;
    }

    public void render() {
        dsv.render();
    }
}
