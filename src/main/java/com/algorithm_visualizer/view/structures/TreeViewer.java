package com.algorithm_visualizer.view.structures;

import com.algorithm_visualizer.model.structures.Tree;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TreeViewer implements DataStructureViewer {
    private Tree tree;
    private Canvas canvas;

    public TreeViewer(Tree tree, Canvas canvas) {
        this.tree = tree;
        this.canvas = canvas;
    }

    public void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw background or initial tree state
        gc.setFill(Color.DARKSLATEBLUE);
        gc.fillOval(canvas.getWidth() / 2 - 15, 60, 30, 30);
    }
}
