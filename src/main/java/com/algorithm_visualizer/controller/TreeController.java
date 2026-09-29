package com.algorithm_visualizer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.algorithm_visualizer.model.structures.Tree;

public class TreeController extends Controller {

    public TreeController(Tree tree) {
        super(tree);
    }

    @Override 
    public void step() {
        
    }

    @Override 
    public void run() {
        
    }

    @Override 
    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.BFS, Algorithm.DFS);
    }
}
