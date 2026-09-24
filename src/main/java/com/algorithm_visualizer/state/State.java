package com.algorithm_visualizer.state;

import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.view.MenuViewer;
import com.algorithm_visualizer.view.RuntimeViewer;
import com.algorithm_visualizer.view.Viewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.scene.Parent;

public class State {
    private static final int MENU_STATE = 0;
    private static final int RUNTIME_STATE = 1;
    DataStructure ds = null;
    Viewer viewer;
    private int state;

    public State() {
        this.state = RUNTIME_STATE;
        viewer = new MenuViewer(this);
        viewer.render();
    }

    public int getState() {
        return this.state;
    }

    public  void run() {
        viewer.render();
    }

    public Parent getActiveView() {
        return viewer.getRoot();
    }
}
