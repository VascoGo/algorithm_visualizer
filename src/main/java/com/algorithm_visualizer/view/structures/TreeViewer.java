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

    private static final double Y_DIFFERENCE = 75.0;
    private static final double NODE_RADIUS = 18.0;
    private static final double MIN_WIDTH = 55.0;
    private static final double TOP_MARGIN = 50.0;
    private static final double SIDE_PADDING = 30.0;

    public TreeViewer(Tree tree, double width, double height) {
        super(width, height, new TreeController(tree));
        this.tree = tree;
    }

    public void computeLayout(TreeNode root) {
        positions.clear();
        subtree_widths.clear();

        if (root == null) return;

        // 1. Compute tree footprint
        computeSubtreeWidths(root);

        // 2. Initial pass relative to left = 0
        assignCoordinates(root, 0.0, 0);

        // 3. Center horizontally based on root's position
        Point2D rootPos = positions.get(root);
        double targetCenterX = getWidth() / 2.0;
        double shiftX = targetCenterX - rootPos.getX();

        for (Map.Entry<TreeNode, Point2D> entry : positions.entrySet()) {
            Point2D current = entry.getValue();
            entry.setValue(new Point2D(current.getX() + shiftX, current.getY()));
        }
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

    @Override
    public void render() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        TreeNode root = (tree != null) ? tree.getRoot() : null;
        if (root == null) return;

        computeLayout(root);

        // 4. Calculate bounds of the generated tree
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Point2D pos : positions.values()) {
            minX = Math.min(minX, pos.getX() - NODE_RADIUS);
            maxX = Math.max(maxX, pos.getX() + NODE_RADIUS);
            maxY = Math.max(maxY, pos.getY() + NODE_RADIUS);
        }

        double treeWidth = maxX - minX;
        double treeHeight = maxY;
        double availableWidth = getWidth() - (SIDE_PADDING * 2);
        double availableHeight = getHeight() - (SIDE_PADDING * 2);

        // 5. Compute scale factor if tree exceeds canvas area
        double scaleX = availableWidth / Math.max(treeWidth, 1.0);
        double scaleY = availableHeight / Math.max(treeHeight, 1.0);
        double scale = Math.min(1.0, Math.min(scaleX, scaleY));

        gc.save();

        if (scale < 1.0) {
            // Scale inward from top-center so the root stays centered
            double originX = getWidth() / 2.0;
            double originY = TOP_MARGIN;
            gc.translate(originX, originY);
            gc.scale(scale, scale);
            gc.translate(-originX, -originY);
        }

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

        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.setLineWidth(2.5);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setFill(Color.web("#11111b"));
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