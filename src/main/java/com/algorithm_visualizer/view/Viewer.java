package com.algorithm_visualizer.view;

import com.algorithm_visualizer.state.State;

import javafx.scene.Parent;

public abstract class Viewer<T extends Parent> {

    private T root;
    private State context;

    public Viewer(T root, State context) {
        this.root = root;
        this.context = context;
    }

    public T getRoot() {
        return this.root;
    }

    public State getContext() {
        return context;
    }

    public abstract void render();
}
