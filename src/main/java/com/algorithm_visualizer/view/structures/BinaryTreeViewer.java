package com.algorithm_visualizer.view.structures;

import java.util.HashMap;
import java.util.Map;

import com.algorithm_visualizer.controller.BinaryTreeController;
import com.algorithm_visualizer.model.structures.BinaryTree;
import com.algorithm_visualizer.model.structures.components.BinaryTreeNode;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class BinaryTreeViewer extends DataStructureViewer {

    private BinaryTree binaryTree;

    private final Map<BinaryTreeNode, Point2D> positions = new HashMap<>();
    private final Map<BinaryTreeNode, Double> subtreeWidths = new HashMap<>();

    // Base layout metrics
    private static final double BASE_NODE_RADIUS = 18.0;
    private static final double BASE_Y_DIFF = 65.0;
    private static final double BASE_MIN_WIDTH = 44.0;
    private static final double PADDING = 24.0;

    private double currentRadius = BASE_NODE_RADIUS;
    private double currentYDiff = BASE_Y_DIFF;
    private double currentMinWidth = BASE_MIN_WIDTH;

    public BinaryTreeViewer(double width, double height, BinaryTree tree) {
        super(width, height, new BinaryTreeController(tree));
        this.binaryTree = tree;
    }

    private int getMaxDepth(BinaryTreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getMaxDepth(node.getLeft()), getMaxDepth(node.getRight()));
    }

    private void adaptMetricsForTree(BinaryTreeNode root) {
        int depth = getMaxDepth(root);
        if (depth > 7) {
            currentRadius = 12.0;
            currentYDiff = 45.0;
            currentMinWidth = 28.0;
        } else if (depth > 5) {
            currentRadius = 15.0;
            currentYDiff = 55.0;
            currentMinWidth = 36.0;
        } else {
            currentRadius = BASE_NODE_RADIUS;
            currentYDiff = BASE_Y_DIFF;
            currentMinWidth = BASE_MIN_WIDTH;
        }
    }

    private double computeSubtreeWidths(BinaryTreeNode node) {
        if (node == null) return 0.0;

        BinaryTreeNode left = node.getLeft();
        BinaryTreeNode right = node.getRight();

        if (left == null && right == null) {
            subtreeWidths.put(node, currentMinWidth);
            return currentMinWidth;
        }

        double leftW = computeSubtreeWidths(left);
        double rightW = computeSubtreeWidths(right);

        double total = Math.max(leftW + rightW, currentMinWidth);
        subtreeWidths.put(node, total);
        return total;
    }

    private void assignCoordinates(BinaryTreeNode node, double leftX, int depth) {
        if (node == null) return;

        double y = depth * currentYDiff;
        BinaryTreeNode left = node.getLeft();
        BinaryTreeNode right = node.getRight();

        if (left == null && right == null) {
            positions.put(node, new Point2D(leftX + (currentMinWidth / 2.0), y));
            return;
        }

        double leftW = (left != null) ? subtreeWidths.get(left) : (currentMinWidth / 2.0);
        double rightW = (right != null) ? subtreeWidths.get(right) : (currentMinWidth / 2.0);

        if (left != null) {
            assignCoordinates(left, leftX, depth + 1);
        }
        if (right != null) {
            assignCoordinates(right, leftX + leftW, depth + 1);
        }

        // Parent X is positioned proportionally between its two child branches
        double nodeX;
        if (left != null && right != null) {
            nodeX = (positions.get(left).getX() + positions.get(right).getX()) / 2.0;
        } else if (left != null) {
            nodeX = positions.get(left).getX() + (currentMinWidth / 2.0);
        } else {
            nodeX = positions.get(right).getX() - (currentMinWidth / 2.0);
        }

        positions.put(node, new Point2D(nodeX, y));
    }

    public void computeLayout(BinaryTreeNode root) {
        positions.clear();
        subtreeWidths.clear();

        if (root == null) return;

        adaptMetricsForTree(root);
        computeSubtreeWidths(root);
        assignCoordinates(root, 0.0, 0);
    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        BinaryTreeNode root = (binaryTree != null) ? binaryTree.getRoot() : null;
        if (root == null) return;

        computeLayout(root);

        // Calculate bounding box across all placed nodes
        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        for (Point2D pos : positions.values()) {
            minX = Math.min(minX, pos.getX() - currentRadius);
            maxX = Math.max(maxX, pos.getX() + currentRadius);
            minY = Math.min(minY, pos.getY() - currentRadius);
            maxY = Math.max(maxY, pos.getY() + currentRadius);
        }

        double treeWidth = maxX - minX;
        double treeHeight = maxY - minY;

        double availableWidth = Math.max(1.0, getWidth() - (PADDING * 2));
        double availableHeight = Math.max(1.0, getHeight() - (PADDING * 2));

        // Uniform aspect ratio scaling
        double scaleX = availableWidth / Math.max(treeWidth, 1.0);
        double scaleY = availableHeight / Math.max(treeHeight, 1.0);
        double scale = Math.min(1.0, Math.min(scaleX, scaleY));

        double scaledW = treeWidth * scale;
        double scaledH = treeHeight * scale;

        // Center within canvas bounds
        double offsetX = (getWidth() - scaledW) / 2.0 - (minX * scale);
        double offsetY = (getHeight() - scaledH) / 2.0 - (minY * scale);

        gc.save();
        gc.translate(offsetX, offsetY);
        gc.scale(scale, scale);

        drawEdges(gc, root);
        drawNodes(gc, root);

        gc.restore();
    }

    private void drawEdges(GraphicsContext gc, BinaryTreeNode node) {
        if (node == null) return;

        Point2D parentPos = positions.get(node);
        if (parentPos == null) return;

        gc.setStroke(Color.web("#6c7086"));
        gc.setLineWidth(2.0);

        BinaryTreeNode left = node.getLeft();
        BinaryTreeNode right = node.getRight();

        if (left != null && positions.containsKey(left)) {
            Point2D leftPos = positions.get(left);
            gc.strokeLine(parentPos.getX(), parentPos.getY(), leftPos.getX(), leftPos.getY());
            drawEdges(gc, left);
        }

        if (right != null && positions.containsKey(right)) {
            Point2D rightPos = positions.get(right);
            gc.strokeLine(parentPos.getX(), parentPos.getY(), rightPos.getX(), rightPos.getY());
            drawEdges(gc, right);
        }
    }

    private void drawNodes(GraphicsContext gc, BinaryTreeNode node) {
        if (node == null) return;

        Point2D pos = positions.get(node);
        if (pos == null) return;

        double x = pos.getX();
        double y = pos.getY();

        if (node.isExplored()) {
            gc.setFill(Color.web("#a6e3a1"));
            gc.setStroke(Color.web("#40a02b"));
        } else {
            gc.setFill(Color.web("#89b4fa"));
            gc.setStroke(Color.web("#1e66f5"));
        }

        gc.fillOval(x - currentRadius, y - currentRadius, currentRadius * 2, currentRadius * 2);
        gc.setLineWidth(2.0);
        gc.strokeOval(x - currentRadius, y - currentRadius, currentRadius * 2, currentRadius * 2);

        gc.setFill(Color.web("#11111b"));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font("System", Math.max(9.0, currentRadius * 0.75)));
        gc.fillText(String.valueOf(node.getValue()), x, y);

        drawNodes(gc, node.getLeft());
        drawNodes(gc, node.getRight());
    }

    public void restart() {
        this.positions.clear();
        this.subtreeWidths.clear();
        this.binaryTree = new BinaryTree();
        this.getController().updateStructure(this.binaryTree);
        this.render();
    }
}