package com.algorithm_visualizer.view;

import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.state.State;
import com.algorithm_visualizer.view.structures.DataStructureViewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.scene.layout.BorderPane;

public class RuntimeViewer extends Viewer<BorderPane> {
    DataStructureViewer dsv;

    public RuntimeViewer(State context) {
        switch (context.getDsIdentifier()) {
            case TREE:
                this.dsv = new TreeViewer(new Tree(), 1000, 1000);
                break;
            default:
                break;
        }
        super(new BorderPane(), context);
        super.getRoot().setCenter(dsv);

        super.getContext().getScene().setOnKeyPressed((event) -> {
            switch (event.getCode()) {
                case ESCAPE:
                    super.getContext().swapState();
                    break;
                default:
                    break;
            }
        });
    }

    public void render() {
        dsv.render();
    }
}
