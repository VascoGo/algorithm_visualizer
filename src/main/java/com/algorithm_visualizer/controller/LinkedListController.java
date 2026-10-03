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
    private LinkedListNode nextNode;

    private int counter = 1;

    public LinkedListController(LinkedList list) {
        this.list = list;
        this.reset();
    }

    public void reset() {
        this.currentNode = list.getRoot();
        if (currentNode != null) nextNode = currentNode.getAfter();
        else nextNode = null;
        this.counter = 1;
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
            case INSERT:
                this.insert(value);
                break;
            case INSERT_INDEX:
                this.insertIndex(value, index);
                break;
            case DELETE:
                this.delete(value);
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
        currentNode.setExplored(true);
        if (currentNode.getAfter() == null) {
            currentNode.addElement(value);
            currentNode.getAfter().setHit(true);
            this.setRunning(false);
        } else {
            currentNode = currentNode.getAfter();
        }
    }

    private void insertIndex(int value, int index) {

        currentNode.setExplored(true);

        if (index == 0) {
            currentNode.setExplored(false);
            this.list.setRoot(new LinkedListNode(value));
            this.list.getRoot().setAfter(currentNode);
            currentNode = this.list.getRoot();
            currentNode.setHit(true);
            this.setRunning(false);
        } else if (counter == index) {
            LinkedListNode forAfter = currentNode.getAfter();
            currentNode.setAfter(new LinkedListNode(value));
            currentNode.getAfter().setAfter(forAfter);
            currentNode.getAfter().setHit(true);
            this.setRunning(false);
        } else if (currentNode.getAfter() != null) currentNode = currentNode.getAfter();
        else this.setRunning(false);

        counter++;
    }

    public void delete(int value) {
        if (currentNode == null) this.setRunning(false);
        else if (currentNode.getValue() == value) {
            if (currentNode.isHit()) {
                list.setRoot(nextNode);
                this.setRunning(false);
            } else currentNode.setHit(true);
        } else if (!currentNode.isExplored()) currentNode.setExplored(true);
        else {
            if (nextNode == null) this.setRunning(false);
            else if (nextNode.isHit()) {
                currentNode.setAfter(nextNode.getAfter());
                nextNode = null;
                this.setRunning(false);
            }
            else if (nextNode.getValue() == value) nextNode.setHit(true);
            else {
                currentNode = nextNode;
                currentNode.setExplored(true);
                nextNode = nextNode.getAfter();
            }
        }
    } 

    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.SEARCH,Algorithm.INSERT, Algorithm.INSERT_INDEX, Algorithm.DELETE);
    }
}
