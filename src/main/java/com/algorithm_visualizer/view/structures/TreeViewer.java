package com.algorithm_visualizer.view.structures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.algorithm_visualizer.controller.TreeController;
import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.model.structures.components.TreeNode;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class TreeViewer extends DataStructureViewer {
    private Tree tree;

    private final Map<TreeNode, Point2D> positions = new HashMap<>();
    private final Map<TreeNode, Double> subtree_widths = new HashMap<>();

    private static final double Y_DIFFERENCE = 70.0;
    private static final double NODE_RADIUS = 18.0;
    private static final double MIN_WIDTH = 55.0;
    private static final double TOP_MARGIN = 50.0;

    public TreeViewer(Tree tree, double width, double height) {
        super(width, height, new TreeController(tree));
        this.tree = tree;
        if (this.tree != null && this.tree.getRoot() != null) {
            computeLayout(this.tree.getRoot());
        }
    }

    public void computeLayout(TreeNode root) {
        positions.clear();
        subtree_widths.clear();

        if (root == null) return;

        computeSubtreeWidths(root);
        assignCoordinates(root, 20.0, 0);
    }

    private double computeSubtreeWidths(TreeNode node) {
        if (node == null) return 0.0;

        List<TreeNode> children = node.getChildren();
        if (children == null || children.isEmpty()) {
            subtree_widths.put(node, MIN_WIDTH);
            return MIN_WIDTH;
        }

        double totalWidth = 0.0;
        for (TreeNode child : children) {
            totalWidth += computeSubtreeWidths(child);
        }

        totalWidth = Math.max(totalWidth, MIN_WIDTH);
        subtree_widths.put(node, totalWidth);
        return totalWidth;
    }

    private void assignCoordinates(TreeNode node, double leftX, int depth) {
        if (node == null) return;

        double y = depth * Y_DIFFERENCE + TOP_MARGIN;
        List<TreeNode> children = node.getChildren();

        if (children == null || children.isEmpty()) {
            double x = leftX + (MIN_WIDTH / 2.0);
            positions.put(node, new Point2D(x, y));
            return;
        }

        double currentLeft = leftX;
        for (TreeNode child : children) {
            assignCoordinates(child, currentLeft, depth + 1);
            currentLeft += subtree_widths.get(child);
        }

        double firstChildX = positions.get(children.get(0)).getX();
        double lastChildX = positions.get(children.get(children.size() - 1)).getX();
        double parentX = (firstChildX + lastChildX) / 2.0;

        positions.put(node, new Point2D(parentX, y));
    }

    
    public void render() {
        
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        TreeNode root = (tree != null) ? tree.getRoot() : null;
        if (root == null) return;

        if (positions.isEmpty()) {
            computeLayout(root);
        }

        drawEdges(gc, root);
        drawNodes(gc, root);
    }

    private void drawEdges(GraphicsContext gc, TreeNode node) {
        if (node == null) return;

        Point2D parentPos = positions.get(node);
        if (parentPos == null) return;

        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2.0);

        List<TreeNode> children = node.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                Point2D childPos = positions.get(child);
                if (childPos != null) {
                    gc.strokeLine(parentPos.getX(), parentPos.getY(), childPos.getX(), childPos.getY());
                }
                drawEdges(gc, child);
            }
        }
    }

    private void drawNodes(GraphicsContext gc, TreeNode node) {
        if (node == null) return;

        Point2D pos = positions.get(node);
        if (pos == null) return;

        double x = pos.getX();
        double y = pos.getY();

        gc.setFill(Color.DODGERBLUE);
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setStroke(Color.DARKBLUE);
        gc.setLineWidth(2.0);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(String.valueOf(node.getValue()), x, y);

        List<TreeNode> children = node.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                drawNodes(gc, child);
            }
        }
    }
}