package com.algorithm_visualizer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.model.structures.components.TreeNode;

public class TreeController extends Controller {

    private Tree tree;
    private TreeNode currentNode;

    public TreeController(Tree tree) {
        super();
        this.tree = tree;
        this.currentNode = tree.getRoot();
    }

    @Override 
    public void step() {
        switch (algorithm) {
            case DFS:
                this.dfs();
                break;
            case BFS:
            default:
                break;
        }
    }

    @Override 
    public void reset() {
        this.currentNode = this.tree.getRoot();
        tree.reset();
    }

    private void dfs() {
        currentNode.setExplored(true);
        for (TreeNode i: currentNode.getChildren()) {
            if (!i.isExplored()) {
                currentNode = i;
                return;
            } 
        }

        if (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            dfs();
        } else {
            this.setRunning(false);
        }

    }

    @Override 
    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.BFS, Algorithm.DFS);
    }

    public TreeNode getCurrentNode() {
        return currentNode;
    }
}
