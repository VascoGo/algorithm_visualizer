package com.algorithm_visualizer.model.structures.components;

public abstract class Node {
    private int value;
    private Boolean explored = false;


    public Node(int value) {
        this.setValue(value);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
