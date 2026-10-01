package com.algorithm_visualizer.controller;

import java.util.Arrays;
import java.util.List;

import com.algorithm_visualizer.model.structures.BinaryTree;
import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.model.structures.components.BinaryTreeNode;

public class BinaryTreeController extends Controller{

    private BinaryTree binaryTree;
    private BinaryTreeNode currentNode;

    public BinaryTreeController(BinaryTree tree) {
        super();
        this.binaryTree = tree;
    }

    @Override 
    public void updateStructure(DataStructure tree) {
        this.binaryTree = (BinaryTree) tree;
        this.reset();
    }

    @Override 
    public void reset() {
        this.currentNode = this.binaryTree.getRoot();
        this.binaryTree.reset();
    }

    @Override 
    public void step() {
        switch (algorithm) {
            case DFS:
                this.dfs();
                break;
            default:
                break;
        }
    }

    private void dfs() {
        currentNode.setExplored(true);
        if (currentNode.getLeft() != null && !currentNode.getLeft().isExplored()) {
            currentNode = currentNode.getLeft();
        } else if (currentNode.getRight() != null && !currentNode.getRight().isExplored()) {
            currentNode = currentNode.getRight();
        } else if (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            dfs();
        } else {
            this.setRunning(false);
        }
    }
            

    @Override 
    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.DFS);
    }
}
