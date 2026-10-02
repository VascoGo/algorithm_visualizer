package com.algorithm_visualizer.model.structures;

import java.util.List;
import java.util.Random;

import com.algorithm_visualizer.model.structures.components.LinkedListNode;

public class LinkedList implements DataStructure {

    private LinkedListNode root = null;

    public LinkedList() {
        List<Integer> elems = new Random().ints(20, 1, 30).boxed().toList();

        for (Integer elem: elems) {
            if (this.root == null) this.root = new LinkedListNode(elem);
            else this.root.addElement(elem);
        }
    }

    public void reset() {
        if (this.root != null) this.root.cleanList();
    }

    public LinkedListNode getRoot() {
        return this.root;
    }

    public void setRoot(int value) {
        this.root = new LinkedListNode(value);
    }
}
