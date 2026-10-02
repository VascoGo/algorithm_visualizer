package com.algorithm_visualizer.model.structures.components;

public class BinaryTreeNode extends Node {
    BinaryTreeNode left = null, right = null, parent;

    public BinaryTreeNode(int value, BinaryTreeNode parent) {
        super(value);
        this.parent = parent;
    }

    public void cleanTree() {
        this.setExplored(false);
        this.setHit(false);
        if (this.left != null) this.left.cleanTree();
        if (this.right != null) this.right.cleanTree();
    }

    public BinaryTreeNode getLeft() {
        return this.left;
    }

    public BinaryTreeNode getRight() {
        return this.right;
    }

    public BinaryTreeNode getParent() {
        return this.parent;
    }

    public void addElement(int value) {
        if (value < this.getValue()) {
            if (getLeft() == null) this.left = new BinaryTreeNode(value, this);
            else left.addElement(value);
        } else {
            if (getRight() == null) this.right = new BinaryTreeNode(value, this);
            else right.addElement(value);
        }
    }

}
