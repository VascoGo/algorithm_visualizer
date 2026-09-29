package com.algorithm_visualizer.view;

import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.state.State;
import com.algorithm_visualizer.view.structures.DataStructureViewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class RuntimeViewer extends Viewer<BorderPane> {
    private DataStructureViewer dsv;

    // Controls exposed for later binding/wiring
    private Button backButton;
    private Button stepButton;
    private Button runButton;
    private ComboBox<String> operationSelector;

    public RuntimeViewer(State context) {
        super(new BorderPane(), context);

        initDataStructureViewer(context);
        initControls();

        // Key handler setup
        if (super.getContext().getScene() != null) {
            super.getContext().getScene().setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case ESCAPE -> super.getContext().swapState();
                    default -> {}
                }
            });
        }
    }

    private void initDataStructureViewer(State context) {
        switch (context.getDsIdentifier()) {
            case TREE:
                this.dsv = new TreeViewer(new Tree(), 1000, 1000);
                break;
            default:
                break;
        }

        if (this.dsv != null) {
            super.getRoot().setCenter(this.dsv);
        }
    }

    private void initControls() {
        // --- Navigation (Left Group) ---
        backButton = new Button("← Back to Menu");

        // --- Controls (Center/Right Group) ---
        operationSelector = new ComboBox<>();
        operationSelector.getItems().addAll("Insert", "Delete", "Search", "Traverse");
        operationSelector.setValue("Insert");

        stepButton = new Button("Step");
        runButton = new Button("Run");

        // Spacer to separate back navigation from action controls
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // --- Top Bar Assembly ---
        HBox topBar = new HBox(12);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12, 16, 12, 16));
        topBar.setStyle("-fx-background-color: #24273a; -fx-border-color: #363a4f; -fx-border-width: 0 0 1 0;");

        topBar.getChildren().addAll(
            backButton,
            spacer,
            operationSelector,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            stepButton,
            runButton
        );

        super.getRoot().setTop(topBar);
    }

    // Getters for wiring handlers externally
    public Button getBackButton() { return backButton; }
    public Button getStepButton() { return stepButton; }
    public Button getRunButton() { return runButton; }
    public ComboBox<String> getOperationSelector() { return operationSelector; }

    @Override
    public void render() {
        if (dsv != null) {
            dsv.render();
        }
    }
}