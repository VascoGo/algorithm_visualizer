package com.algorithm_visualizer.state;

import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.view.MenuViewer;
import com.algorithm_visualizer.view.RuntimeViewer;
import com.algorithm_visualizer.view.Viewer;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class State {
    public enum DataStructureIdentifier {
        TREE
    }

    private static final int MENU_STATE = 0;
    private static final int RUNTIME_STATE = 1;
    DataStructureIdentifier ds = DataStructureIdentifier.TREE;
    Viewer viewer;
    private int state;
    private Scene scene;

    public State() {
        this.state = MENU_STATE;
        viewer = new MenuViewer(this);
        run();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return this.scene;
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

    public DataStructureIdentifier getDsIdentifier() {
        return this.ds;
    }

    public void setDataStructure(DataStructureIdentifier ds) {
        this.ds = ds;
    }

    public void swapState() {
        if (this.state == MENU_STATE) {
            this.state = RUNTIME_STATE;
            this.viewer = new RuntimeViewer(this);
            this.scene.setRoot(getActiveView());
        } else {
            this.state = MENU_STATE;
            this.viewer = new MenuViewer(this);
            this.scene.setRoot(getActiveView());
        }

        run();
    }
}
