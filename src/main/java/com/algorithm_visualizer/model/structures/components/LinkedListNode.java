package com.algorithm_visualizer.model.structures.components;

public class LinkedListNode extends Node {
    LinkedListNode after = null;

    public LinkedListNode(int value) {
        super(value);
    }

    public void cleanList() {
        this.setExplored(false);
        this.setHit(false);
        if (this.after != null) this.after.cleanList();
    }

    public LinkedListNode getAfter() {
        return this.after;
    }

    public void setAfter(LinkedListNode after) {
        this.after = after;
    }

    public void addElement(int value) {
        if (this.after == null) this.after = new LinkedListNode(value);
        else this.after.addElement(value);
    }
}
