package com.algorithm_visualizer.view.structures;

import java.util.HashMap;
import java.util.Map;

import com.algorithm_visualizer.controller.LinkedListController;
import com.algorithm_visualizer.model.structures.LinkedList;
import com.algorithm_visualizer.model.structures.components.LinkedListNode;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class LinkedListViewer extends DataStructureViewer {

    private LinkedList list;

    private final Map<LinkedListNode, Point2D> positions = new HashMap<>();

    private static final double BASE_NODE_RADIUS = 20.0;
    private static final double BASE_X_DIFF = 85.0;
    private static final double BASE_Y_DIFF = 85.0;
    private static final double PADDING = 35.0;

    private double currentRadius = BASE_NODE_RADIUS;
    private double currentXDiff = BASE_X_DIFF;
    private double currentYDiff = BASE_Y_DIFF;
    private int nodesPerRow = 7;

    public LinkedListViewer(double width, double height, LinkedList list) {
        super(width, height, new LinkedListController(list));
        this.list = list;
    }

    private int countNodes(LinkedListNode head) {
        int count = 0;
        LinkedListNode curr = head;
        while (curr != null) {
            count++;
            curr = curr.getAfter();
        }
        return count;
    }

    private void adaptMetricsForList(int count) {
        if (count > 35) {
            currentRadius = 13.0;
            currentXDiff = 50.0;
            currentYDiff = 55.0;
            nodesPerRow = 10;
        } else if (count > 20) {
            currentRadius = 16.0;
            currentXDiff = 65.0;
            currentYDiff = 70.0;
            nodesPerRow = 8;
        } else {
            currentRadius = BASE_NODE_RADIUS;
            currentXDiff = BASE_X_DIFF;
            currentYDiff = BASE_Y_DIFF;
            nodesPerRow = 7;
        }
    }

    public void computeLayout(LinkedListNode head) {
        positions.clear();
        if (head == null) return;

        int count = countNodes(head);
        adaptMetricsForList(count);

        LinkedListNode curr = head;
        int index = 0;
        while (curr != null) {
            int row = index / nodesPerRow;
            int col = index % nodesPerRow;
            double x = col * currentXDiff;
            double y = row * currentYDiff;
            positions.put(curr, new Point2D(x, y));
            curr = curr.getAfter();
            index++;
        }
    }

    @Override
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.web("#18181c"));
        gc.fillRect(0, 0, getWidth(), getHeight());

        LinkedListNode root = (list != null) ? list.getRoot() : null;
        if (root == null) return;

        computeLayout(root);

        // Bounding box across all placed nodes and HEAD badge
        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        for (Point2D pos : positions.values()) {
            minX = Math.min(minX, pos.getX() - currentRadius - 5.0);
            maxX = Math.max(maxX, pos.getX() + currentRadius + 5.0);
            minY = Math.min(minY, pos.getY() - currentRadius - 35.0);
            maxY = Math.max(maxY, pos.getY() + currentRadius + 5.0);
        }

        double listWidth = maxX - minX;
        double listHeight = maxY - minY;

        double availableWidth = Math.max(1.0, getWidth() - (PADDING * 2));
        double availableHeight = Math.max(1.0, getHeight() - (PADDING * 2));

        double scaleX = availableWidth / Math.max(listWidth, 1.0);
        double scaleY = availableHeight / Math.max(listHeight, 1.0);
        double scale = Math.min(1.0, Math.min(scaleX, scaleY));

        double scaledW = listWidth * scale;
        double scaledH = listHeight * scale;

        double offsetX = (getWidth() - scaledW) / 2.0 - (minX * scale);
        double offsetY = (getHeight() - scaledH) / 2.0 - (minY * scale);

        gc.save();
        gc.translate(offsetX, offsetY);
        gc.scale(scale, scale);

        drawHeadLabel(gc, root);
        drawEdges(gc, root);
        drawNodes(gc, root);

        gc.restore();
    }

    private void drawHeadLabel(GraphicsContext gc, LinkedListNode root) {
        Point2D rootPos = positions.get(root);
        if (rootPos == null) return;

        double x = rootPos.getX();
        double y = rootPos.getY() - currentRadius - 28.0;

        // Head badge background
        gc.setFill(Color.web("#800c1c"));
        gc.setStroke(Color.web("#ffffff"));
        gc.setLineWidth(1.5);
        gc.fillRoundRect(x - 22.0, y - 10.0, 44.0, 20.0, 8.0, 8.0);
        gc.strokeRoundRect(x - 22.0, y - 10.0, 44.0, 20.0, 8.0, 8.0);

        // Head text
        gc.setFill(Color.web("#ffffff"));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font("System", FontWeight.BOLD, 10.0));
        gc.fillText("HEAD", x, y);

        // Pointer down arrow
        gc.setStroke(Color.web("#e2e8f0"));
        gc.setLineWidth(2.0);
        gc.strokeLine(x, y + 10.0, x, rootPos.getY() - currentRadius - 3.0);

        gc.setFill(Color.web("#e2e8f0"));
        gc.fillPolygon(
            new double[]{x, x - 4.0, x + 4.0},
            new double[]{rootPos.getY() - currentRadius - 1.0, rootPos.getY() - currentRadius - 7.0, rootPos.getY() - currentRadius - 7.0},
            3
        );
    }

    private void drawEdges(GraphicsContext gc, LinkedListNode root) {
        LinkedListNode curr = root;
        int index = 0;

        while (curr != null) {
            Point2D fromPos = positions.get(curr);
            LinkedListNode next = curr.getAfter();

            if (fromPos != null && next != null && positions.containsKey(next)) {
                Point2D toPos = positions.get(next);
                int currRow = index / nodesPerRow;
                int nextRow = (index + 1) / nodesPerRow;

                if (currRow == nextRow) {
                    drawStraightArrow(gc, fromPos.getX(), fromPos.getY(), toPos.getX(), toPos.getY(), currentRadius);
                } else {
                    drawRowTransitionArrow(gc, fromPos.getX(), fromPos.getY(), toPos.getX(), toPos.getY(), currentRadius);
                }
            }

            curr = next;
            index++;
        }
    }

    private void drawStraightArrow(GraphicsContext gc, double fromX, double fromY, double toX, double toY, double radius) {
        double angle = Math.atan2(toY - fromY, toX - fromX);
        double startX = fromX + radius * Math.cos(angle);
        double startY = fromY + radius * Math.sin(angle);
        double endX = toX - radius * Math.cos(angle);
        double endY = toY - radius * Math.sin(angle);

        gc.setStroke(Color.web("#e2e8f0"));
        gc.setLineWidth(2.5);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeLine(startX, startY, endX, endY);

        double arrowLength = 9.0;
        double arrowAngle = Math.PI / 6;

        double x1 = endX - arrowLength * Math.cos(angle - arrowAngle);
        double y1 = endY - arrowLength * Math.sin(angle - arrowAngle);
        double x2 = endX - arrowLength * Math.cos(angle + arrowAngle);
        double y2 = endY - arrowLength * Math.sin(angle + arrowAngle);

        gc.setFill(Color.web("#e2e8f0"));
        gc.fillPolygon(new double[]{endX, x1, x2}, new double[]{endY, y1, y2}, 3);
    }

    private void drawRowTransitionArrow(GraphicsContext gc, double fromX, double fromY, double toX, double toY, double radius) {
        double startX = fromX + radius;
        double startY = fromY;
        double endX = toX;
        double endY = toY - radius;

        double midX = startX + 25.0;

        gc.setStroke(Color.web("#e2e8f0"));
        gc.setLineWidth(2.5);
        gc.setLineCap(StrokeLineCap.ROUND);

        gc.beginPath();
        gc.moveTo(startX, startY);
        gc.bezierCurveTo(midX, startY, midX, endY, endX, endY);
        gc.stroke();

        gc.setFill(Color.web("#e2e8f0"));
        gc.fillPolygon(
            new double[]{endX, endX - 5.0, endX + 5.0},
            new double[]{endY, endY - 8.0, endY - 8.0},
            3
        );
    }

    private void drawNodes(GraphicsContext gc, LinkedListNode root) {
        LinkedListNode curr = root;

        while (curr != null) {
            Point2D pos = positions.get(curr);
            if (pos != null) {
                double x = pos.getX();
                double y = pos.getY();

                // 1. Drop shadow
                gc.setFill(Color.web("#050508", 0.65));
                gc.fillOval(x - currentRadius + 2.0, y - currentRadius + 3.0, currentRadius * 2, currentRadius * 2);

                // 2. Main Node Body
                if (curr.isHit()) {
                    gc.setFill(Color.web("#10b981"));
                    gc.setStroke(Color.web("#ffffff"));
                }
                else if (curr.isExplored()) {
                    gc.setFill(Color.web("#cec518"));
                    gc.setStroke(Color.web("#ffffff"));
                } else {
                    gc.setFill(Color.web("#800c1c"));
                    gc.setStroke(Color.web("#ffffff"));
                }

                gc.fillOval(x - currentRadius, y - currentRadius, currentRadius * 2, currentRadius * 2);
                gc.setLineWidth(2.5);
                gc.strokeOval(x - currentRadius, y - currentRadius, currentRadius * 2, currentRadius * 2);

                // 3. Specular Gloss Arc Highlight
                gc.setStroke(Color.web("#ffffff", 0.45));
                gc.setLineWidth(1.5);
                double arcOffset = Math.max(2.0, currentRadius * 0.2);
                double arcRadius = currentRadius - arcOffset;
                gc.strokeArc(x - arcRadius, y - arcRadius, arcRadius * 2, arcRadius * 2, 45, 90, ArcType.OPEN);

                // 4. Value Text
                gc.setFill(Color.web("#ffffff"));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setTextBaseline(VPos.CENTER);
                gc.setFont(Font.font("System", FontWeight.BOLD, Math.max(10.0, currentRadius * 0.75)));
                gc.fillText(String.valueOf(curr.getValue()), x, y + 0.5);
            }

            curr = curr.getAfter();
        }
    }

    @Override
    public void restart() {
        this.positions.clear();
        this.list = new LinkedList();
        this.getController().updateStructure(this.list);
        this.render();
    }
}
