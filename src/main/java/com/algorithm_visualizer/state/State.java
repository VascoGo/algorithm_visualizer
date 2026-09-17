package com.algorithm_visualizer.state;

import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.view.RuntimeViewer;
import com.algorithm_visualizer.view.Viewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.scene.canvas.Canvas;

public class State {
    DataStructure ds = null;
    Viewer viewer;


    public State(Canvas canvas) {
        viewer = new RuntimeViewer(canvas, new TreeViewer(new Tree()));
    }

    public  void run() {
        viewer.render();
    }
}
