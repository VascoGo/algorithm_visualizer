package com.algorithm_visualizer.model.structures;

import com.algorithm_visualizer.model.structures.components.TreeNode;

public class Tree implements DataStructure {
    
    private TreeNode root;

    public Tree() {
        this.initialize();
    }

    public void initialize() {
        int depth = 5;
        root = new TreeNode(0, depth, 3, null);
    }

    public void reset() {
        this.initialize();
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public void run() {
        
    }
}
