package com.algorithm_visualizer.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.algorithm_visualizer.model.structures.BinaryTree;
import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.model.structures.components.BinaryTreeNode;

public class BinaryTreeController extends Controller{

    private BinaryTree binaryTree;
    private BinaryTreeNode currentNode;
    private Queue<BinaryTreeNode> bfsQueue = new LinkedList<BinaryTreeNode>();

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
    public void step(Integer value) {
        switch (algorithm) {
            case DFS:
                this.dfs();
                break;
            case BFS:
                this.bfs();
                break;
            case DFS_POST:
                this.dfsPost();
                break;
            case SEARCH:
                this.search(value);
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

    private void bfs() {
        currentNode.setExplored(true);
        if (currentNode.getLeft() != null && !currentNode.getLeft().isExplored()) {
            bfsQueue.add(currentNode.getLeft());
        }
        if (currentNode.getRight() != null && !currentNode.getRight().isExplored()) {
            bfsQueue.add(currentNode.getRight());
        }

        if (bfsQueue.isEmpty()) this.setRunning(false);
        else currentNode = bfsQueue.poll();

    }

    private void dfsPost() {
        if (currentNode.getLeft() != null && !currentNode.getLeft().isExplored()) {
            currentNode = currentNode.getLeft();
            dfsPost();
        } else if (currentNode.getRight() != null && !currentNode.getRight().isExplored()) {
            currentNode = currentNode.getRight();
            dfsPost();
        } else {
            currentNode.setExplored(true);
            if (currentNode.getParent() == null) this.setRunning(false);
            else currentNode = currentNode.getParent();
        }
    }

    private void search(Integer value) {
        currentNode.setExplored(true);
        if (currentNode.getValue() > value && currentNode.getLeft() != null) currentNode = currentNode.getLeft();
        else if (currentNode.getValue() < value && currentNode.getRight() != null) currentNode = currentNode.getRight();
        else this.setRunning(false);
    }
            

    @Override 
    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.DFS, Algorithm.BFS, Algorithm.DFS_POST, Algorithm.SEARCH);
    }
}
