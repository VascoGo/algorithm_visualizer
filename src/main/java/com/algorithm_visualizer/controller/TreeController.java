package com.algorithm_visualizer.controller;

import com.algorithm_visualizer.model.structures.Tree;

public class TreeController extends Controller {

    public enum TreeAlgorithm {
        DFS, BFS
    }

    TreeAlgorithm algorithm = null;

    public TreeController(Tree tree) {
        super(tree);
    }

    @Override 
    public void step() {
        
    }

    @Override 
    public void run() {
        
    }

    void setAlgorithm(TreeAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
