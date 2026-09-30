package com.algorithm_visualizer.controller;

import java.util.Arrays;
import java.util.List;

import com.algorithm_visualizer.model.structures.BinaryTree;
import com.algorithm_visualizer.model.structures.DataStructure;

public class BinaryTreeController extends Controller{

    private BinaryTree binaryTree;

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
        this.binaryTree.reset();
    }

    @Override 
    public void step() {
        switch (algorithm) {
            default:
                break;
        }
    }

    @Override 
    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList();
    }
}
