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
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("menu-background");
        super(vbox, context);
    }

    @Override
    public void render() {
        VBox container = getRoot();
        container.getChildren().clear();

        // Central Card Container
        VBox card = new VBox(22);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(440);
        card.setPrefWidth(420);
        card.getStyleClass().add("menu-card");

        // App Title & Subtitle Header
        Label titleLabel = new Label("ALGORITHM VISUALIZER");
        titleLabel.getStyleClass().add("menu-title");

        Label subtitleLabel = new Label("Interactive Data Structure Explorer");
        subtitleLabel.getStyleClass().add("menu-subtitle");

        VBox headerBox = new VBox(6, titleLabel, subtitleLabel);
        headerBox.setAlignment(Pos.CENTER);

        // Data Structure Selector Section
        Label label = new Label("Select Data Structure:");
        label.getStyleClass().add("menu-label");

        ComboBox<DataStructureIdentifier> selector = new ComboBox<>();
        selector.getItems().addAll(DataStructureIdentifier.values());
        selector.setValue(this.getContext().getDsIdentifier());
        selector.getStyleClass().add("menu-combo");

        selector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.getContext().setDataStructure(newValue);
        });

        VBox selectBox = new VBox(10, label, selector);
        selectBox.setAlignment(Pos.CENTER);

        // Action Buttons (Bigger Buttons with Dark Red & White Shine Contrast)
        Button launchButton = new Button("Start Visualizer");
        launchButton.getStyleClass().add("menu-button-primary");
        launchButton.setOnAction((action) -> {
            this.getContext().swapState();
        });

        Button quitButton = new Button("Quit Application");
        quitButton.getStyleClass().add("menu-button-secondary");
        quitButton.setOnAction((action) -> {
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        });

        VBox buttonBox = new VBox(14, launchButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);

        card.getChildren().addAll(headerBox, selectBox, buttonBox);
        container.getChildren().add(card);
    }
}