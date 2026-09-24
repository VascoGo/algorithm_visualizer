package com.algorithm_visualizer.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class MenuViewer extends Viewer<HBox>{
    public MenuViewer() {
        super(new HBox());
    }

    private void init() {
        Rectangle rect = new Rectangle(20, 10, null);
    }

    public void render() {
        
    }
}