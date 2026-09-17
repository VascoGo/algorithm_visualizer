package com.algorithm_visualizer;

import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.view.RuntimeViewer;
import com.algorithm_visualizer.view.Viewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override 
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Algorithm Visualizer");

        Canvas canvas = new Canvas(500, 500);

        Tree tree = new Tree();
        Viewer viewer = new RuntimeViewer(canvas, new TreeViewer(tree, canvas));
        viewer.render();

        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);
        

        Scene scene = new Scene(layout, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
