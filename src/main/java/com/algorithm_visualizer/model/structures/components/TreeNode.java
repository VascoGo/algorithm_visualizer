package com.algorithm_visualizer.model.structures.components;


import java.util.ArrayList;
import java.util.List;

public class TreeNode extends Node{
    private List<TreeNode> nodes = new ArrayList<>();

    public TreeNode(int value, int depth, int max_child_number) {
        super(value);

        if (depth == 0) return;

        int n_nodes = (int)(Math.random() * (max_child_number + 1));

        for (int i = 0; i < n_nodes; i++) {
            nodes.add(new TreeNode(value, depth - 1, max_child_number));
        }
    }

    public void appendNode(int value) {
        TreeNode node = new TreeNode(value, 0, 0);
        nodes.add(node);
    }
}
