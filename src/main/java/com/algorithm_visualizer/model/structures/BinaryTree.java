package com.algorithm_visualizer.model.structures;

import java.util.List;
import java.util.Random;

import com.algorithm_visualizer.model.structures.components.BinaryTreeNode;

public class BinaryTree implements DataStructure{
    BinaryTreeNode root = null;

    public BinaryTree() {
        List<Integer> values = new Random().ints(25, 1, 30).boxed().distinct().sorted().toList();

        this.addElement(values.get(values.size() / 2));
        this.init(values.subList(0, values.size() / 2 - 1), values.subList(values.size() / 2 + 1, values.size()));
    }

    public void init(List<Integer> left, List<Integer> right) {
        if (left.isEmpty()) return;
        else {
            this.addElement(left.get(left.size() / 2));
            if (left.size() > 1) this.init(left.subList(0, left.size() / 2 - 1), left.subList(left.size() / 2 + 1, left.size()));
        }

        if (right.isEmpty()) return;
        else {
            this.addElement(right.get(right.size() / 2));
            if (right.size() > 1) this.init(right.subList(0, right.size() / 2 - 1), right.subList(right.size() / 2 + 1, right.size()));
        }
    }

    public void reset() {
        this.root.cleanTree();
    }

    public BinaryTreeNode getRoot() {
        return this.root;
    }

    private void addElement(int value) {
        if (this.root == null) this.root = new BinaryTreeNode(value, null);
        else root.addElement(value);
    }
}
