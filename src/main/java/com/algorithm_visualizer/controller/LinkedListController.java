package com.algorithm_visualizer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.model.structures.LinkedList;
import com.algorithm_visualizer.model.structures.components.LinkedListNode;

public class LinkedListController extends Controller{
    
    private LinkedList list;
    private LinkedListNode currentNode;

    public LinkedListController(LinkedList list) {
        this.list = list;
        this.reset();
    }

    public void reset() {
        this.currentNode = list.getRoot();
        this.list.reset();
    }

    public void updateStructure(DataStructure ds) {
        this.list = (LinkedList) ds;
    }

    public void step(Integer value) {

    }

    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.SEARCH, Algorithm.INSERT, Algorithm.DELETE);
    }
}
