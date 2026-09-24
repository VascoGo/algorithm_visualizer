package com.algorithm_visualizer.view;

import javafx.scene.control.Label;

import com.algorithm_visualizer.state.State;
import com.algorithm_visualizer.state.State.DataStructureIdentifier;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuViewer extends Viewer<VBox>{


    public MenuViewer(State context) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        super(vbox, context);
    }

    public void render() {
        VBox container = getRoot();
        container.getChildren().clear();

        // 1. Label
        Label label = new Label("Select Data Structure:");
        label.setStyle("-fx-text-fill: #cdd6f4; -fx-font-size: 14px; -fx-font-weight: bold;");

        // 2. Data Structure Selector (ComboBox)
        ComboBox<DataStructureIdentifier> selector = new ComboBox<>();
        selector.getItems().addAll(DataStructureIdentifier.values());
        selector.setValue(DataStructureIdentifier.TREE);

        selector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.getContext().setDataStructure(newValue);
        });

        // 3. Launch / Action Button
        Button launchButton = new Button("Start");
        launchButton.setStyle("-fx-background-color: #ff5555; -fx-text-fill: #11111b; -fx-font-weight: bold; -fx-cursor: hand;");
        launchButton.setOnAction((action) -> {
            this.getContext().swapState();
        });

        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-background-color: #990000; -fx-text-fill: #000000; -fx-font-weight: bold; -fx-cursor: hand;");
        quitButton.setOnAction((action) -> {
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        });

        container.getChildren().addAll(label, selector, launchButton, quitButton);
    }
}