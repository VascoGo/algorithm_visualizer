package com.algorithm_visualizer.view;

import com.algorithm_visualizer.view.structures.DataStructureViewer;

import javafx.scene.layout.BorderPane;

public class RuntimeViewer extends Viewer<BorderPane> {
    private DataStructureViewer dsv;

    public RuntimeViewer(DataStructureViewer dsv) {
        super(new BorderPane());
        this.dsv = dsv;
        super.getRoot().getChildren().add(dsv);
    }

    public void render() {
        dsv.render();
    }
}
