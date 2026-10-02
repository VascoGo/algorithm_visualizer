package com.algorithm_visualizer.model.structures.components;

public abstract class Node {
    private int value;
    private Boolean explored = false;
    private Boolean hit = false;


    public Node(int value) {
        this.setValue(value);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Boolean isExplored() {
        return this.explored;
    }

    public void setExplored(Boolean value) {
        this.explored = value;
    }

    public Boolean isHit() {
        return this.hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }
}
