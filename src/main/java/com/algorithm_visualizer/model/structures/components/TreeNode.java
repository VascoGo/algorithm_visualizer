package com.algorithm_visualizer.model.structures.components;


import java.util.ArrayList;
import java.util.List;

public class TreeNode extends Node{
    private List<TreeNode> nodes = new ArrayList<>();
    private TreeNode parent;

    public TreeNode(int value, int depth, int max_child_number, TreeNode parent) {
        super(value);
        this.parent = parent;

        if (depth == 0) return;

        int n_nodes = (int)(Math.random() * (max_child_number + 1));

        int child_value;
        for (int i = 0; i < n_nodes; i++) {
            child_value = (int)(Math.random() * 100);
            nodes.add(new TreeNode(child_value, depth - 1, max_child_number, this));
        }
    }

    public void appendNode(int value) {
        TreeNode node = new TreeNode(value, 0, 0, this);
        nodes.add(node);
    }

    public List<TreeNode> getChildren() {
        return this.nodes;
    }

    public TreeNode getParent() {
        return parent;
    }
}
