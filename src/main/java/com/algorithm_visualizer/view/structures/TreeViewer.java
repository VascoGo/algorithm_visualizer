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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TreeViewer extends DataStructureViewer {
    private Tree tree;

    private final Map<TreeNode, Point2D> positions = new HashMap<>();
    private final Map<TreeNode, Double> subtree_widths = new HashMap<>();

    // Base dimensions (dynamically scaled down for large trees)
    private static final double BASE_NODE_RADIUS = 18.0;
    private static final double BASE_Y_DIFF = 70.0;
    private static final double BASE_MIN_WIDTH = 48.0;
    private static final double PADDING = 24.0;

    private double currentRadius = BASE_NODE_RADIUS;
    private double currentYDiff = BASE_Y_DIFF;
    private double currentMinWidth = BASE_MIN_WIDTH;

    public TreeViewer(Tree tree, double width, double height) {
        super(width, height, new TreeController(tree));
        this.tree = tree;
    }

    private int getMaxDepth(TreeNode node) {
        if (node == null) return 0;
        List<TreeNode> children = node.getChildren();
        if (children == null || children.isEmpty()) return 1;

        int max = 0;
        for (TreeNode child : children) {
            max = Math.max(max, getMaxDepth(child));
        }
        return 1 + max;
    }

    private void adaptMetricsForTree(TreeNode root) {
        int depth = getMaxDepth(root);
        
        // Dynamically shrink spacing and radius if depth is large
        if (depth > 8) {
            currentRadius = 12.0;
            currentYDiff = 45.0;
            currentMinWidth = 30.0;
        } else if (depth > 5) {
            currentRadius = 15.0;
            currentYDiff = 55.0;
            currentMinWidth = 38.0;
        } else {
            currentRadius = BASE_NODE_RADIUS;
            currentYDiff = BASE_Y_DIFF;
            currentMinWidth = BASE_MIN_WIDTH;
        }
    }

    public void computeLayout(TreeNode root) {
        positions.clear();
        subtree_widths.clear();

        if (root == null) return;

        adaptMetricsForTree(root);
        computeSubtreeWidths(root);
        assignCoordinates(root, 0.0, 0);
    }

    private double computeSubtreeWidths(TreeNode node) {
        if (node == null) return 0.0;

        List<TreeNode> children = node.getChildren();
        if (children == null || children.isEmpty()) {
            subtree_widths.put(node, currentMinWidth);
            return currentMinWidth;
        }

        double totalWidth = 0.0;
        for (TreeNode child : children) {
            totalWidth += computeSubtreeWidths(child);
        }

        totalWidth = Math.max(totalWidth, currentMinWidth);
        subtree_widths.put(node, totalWidth);
        return totalWidth;
    }

    private void assignCoordinates(TreeNode node, double leftX, int depth) {
        if (node == null) return;

        double y = depth * currentYDiff;
        List<TreeNode> children = node.getChildren();

        if (children == null || children.isEmpty()) {
            double x = leftX + (currentMinWidth / 2.0);
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

    @Override
    public void render() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        TreeNode root = (tree != null) ? tree.getRoot() : null;
        if (root == null) return;

        computeLayout(root);

        // 1. Calculate the bounding box of the entire tree
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

        // 2. Uniform scaling factor so aspect ratio doesn't distort
        double scaleX = availableWidth / Math.max(treeWidth, 1.0);
        double scaleY = availableHeight / Math.max(treeHeight, 1.0);
        double scale = Math.min(1.0, Math.min(scaleX, scaleY));

        // 3. Center the bounding box within the canvas
        double scaledTreeWidth = treeWidth * scale;
        double scaledTreeHeight = treeHeight * scale;

        double offsetX = (getWidth() - scaledTreeWidth) / 2.0 - (minX * scale);
        double offsetY = (getHeight() - scaledTreeHeight) / 2.0 - (minY * scale);

        gc.save();
        gc.translate(offsetX, offsetY);
        gc.scale(scale, scale);

        drawEdges(gc, root);
        drawNodes(gc, root);

        gc.restore();
    }

    private void drawEdges(GraphicsContext gc, TreeNode node) {
        if (node == null) return;

        Point2D parentPos = positions.get(node);
        if (parentPos == null) return;

        gc.setStroke(Color.web("#6c7086"));
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

        if (node.isExplored()) {
            gc.setFill(Color.web("#a6e3a1"));
            gc.setStroke(Color.web("#40a02b"));
        } else {
            gc.setFill(Color.web("#89b4fa"));
            gc.setStroke(Color.web("#1e66f5"));
        }

        // Draw circle
        gc.fillOval(x - currentRadius, y - currentRadius, currentRadius * 2, currentRadius * 2);
        gc.setLineWidth(2.0);
        gc.strokeOval(x - currentRadius, y - currentRadius, currentRadius * 2, currentRadius * 2);

        // Draw label text
        gc.setFill(Color.web("#11111b"));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font("System", Math.max(9.0, currentRadius * 0.75)));
        gc.fillText(String.valueOf(node.getValue()), x, y);

        List<TreeNode> children = node.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                drawNodes(gc, child);
            }
        }
    }
}