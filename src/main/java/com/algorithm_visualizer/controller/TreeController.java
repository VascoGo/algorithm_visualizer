package com.algorithm_visualizer.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.algorithm_visualizer.model.structures.DataStructure;
import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.model.structures.components.TreeNode;

public class TreeController extends Controller {

    private Tree tree;
    private TreeNode currentNode;
    private Queue<TreeNode> bfsQueue = new LinkedList<TreeNode>();

    public TreeController(Tree tree) {
        super();
        this.tree = tree;
        this.currentNode = tree.getRoot();
    }

    @Override 
    public void updateStructure(DataStructure tree) {
        this.tree = (Tree) tree;
        this.reset();
    }

    @Override 
    public void reset() {
        this.currentNode = this.tree.getRoot();
        this.bfsQueue.clear();
        tree.reset();
    }

    @Override 
    public void step(Integer value, Integer index) {
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
            default:
                break;
        }
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

    private void bfs() {
        currentNode.setExplored(true);

        if (currentNode.getChildren().isEmpty() && bfsQueue.isEmpty()) {
            super.setRunning(false);
            return;
        }

        for (TreeNode node: currentNode.getChildren()) {
            bfsQueue.add(node);
        }

        currentNode = bfsQueue.poll();
    }

    private void dfsPost() {
        if (currentNode.isExplored()) {
            if (currentNode.getParent() == null) {
                super.setRunning(false);
                return;
            } else {
                currentNode = currentNode.getParent();
                dfsPost();
            }

            return;
        }

        for (TreeNode node: currentNode.getChildren()) {
            if (!node.isExplored()) {
                currentNode = node;
                dfsPost();
                return;
            }
        }

        currentNode.setExplored(true);
    }

    @Override 
    public List<Algorithm> supportedAlgorithms() {
        return Arrays.asList(Algorithm.BFS, Algorithm.DFS, Algorithm.DFS_POST);
    }

    public TreeNode getCurrentNode() {
        return currentNode;
    }
}
