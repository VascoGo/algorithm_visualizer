package com.algorithm_visualizer;

import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.state.State;
import com.algorithm_visualizer.view.RuntimeViewer;
import com.algorithm_visualizer.view.Viewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override 
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Algorithm Visualizer");

        Rectangle2D bounds = Screen.getPrimary().getBounds();

        State state = new State();
        Scene scene = new Scene(state.getActiveView(), bounds.getWidth(), bounds.getHeight());
        state.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
