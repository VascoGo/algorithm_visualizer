package com.algorithm_visualizer.view;

import com.algorithm_visualizer.state.State;
import com.algorithm_visualizer.view.structures.DataStructureViewer;

import javafx.scene.layout.BorderPane;

public class RuntimeViewer extends Viewer<BorderPane> {
    private DataStructureViewer dsv;

    public RuntimeViewer(DataStructureViewer dsv, State context) {
        super(new BorderPane(), context);
        this.dsv = dsv;
        super.getRoot().getChildren().add(dsv);
    }

    public void render() {
        dsv.render();
    }
}
