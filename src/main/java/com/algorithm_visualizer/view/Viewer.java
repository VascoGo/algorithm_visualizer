package com.algorithm_visualizer.view;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;

public abstract class Viewer<T extends Parent> {

    private T root;

    public Viewer(T root) {
        this.root = root;
    }

    public T getRoot() {
        return this.root;
    }

    public abstract void render();
}
