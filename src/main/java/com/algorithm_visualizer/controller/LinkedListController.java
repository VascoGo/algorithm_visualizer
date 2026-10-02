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

    @Override 
    public void step(Integer value, Integer index) {
        switch (this.algorithm) {
            case SEARCH:
                this.search(value);
                break;
            default:
                break;
        }
    }

    private void search(int value) {
        currentNode.setExplored(true);
        if (currentNode.getValue() == value) {
            currentNode.setHit(true);
            this.setRunning(false);
        } else if (currentNode.getAfter() != null) currentNode = currentNode.getAfter();
        else this.setRunning(false);
    }

    private void insert(int value) {

    }

    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.SEARCH,Algorithm.INSERT, Algorithm.INSERT_INDEX, Algorithm.DELETE);
    }
}
