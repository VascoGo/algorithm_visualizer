package com.algorithm_visualizer.view;

import com.algorithm_visualizer.controller.Algorithm;
import com.algorithm_visualizer.controller.Algorithm.Category;
import com.algorithm_visualizer.model.structures.BinaryTree;
import com.algorithm_visualizer.model.structures.LinkedList;
import com.algorithm_visualizer.model.structures.Tree;
import com.algorithm_visualizer.state.State;
import com.algorithm_visualizer.view.structures.BinaryTreeViewer;
import com.algorithm_visualizer.view.structures.DataStructureViewer;
import com.algorithm_visualizer.view.structures.LinkedListViewer;
import com.algorithm_visualizer.view.structures.TreeViewer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class RuntimeViewer extends Viewer<BorderPane> {
    private DataStructureViewer dsv;

    private Button backButton;
    private Button restartButton;
    private Button resetButton;
    private Button stepButton;
    private Button runButton;
    private Button pauseButton;
    private Spinner<Integer> valueSpinner;
    private ComboBox<Algorithm> operationSelector;
    private Timeline loop;

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
            case BINARY_TREE:
                this.dsv = new BinaryTreeViewer(1000, 1000, new BinaryTree());
                break;
            case LINKED_LIST:
                this.dsv = new LinkedListViewer(1000, 1000, new LinkedList());
                break;
            default:
                break;
        }

            if (this.dsv != null) {
            // Container that tracks the BorderPane center area
            javafx.scene.layout.Pane canvasHolder = new javafx.scene.layout.Pane(this.dsv);
            
            // Bind canvas dimensions to the container
            this.dsv.widthProperty().bind(canvasHolder.widthProperty());
            this.dsv.heightProperty().bind(canvasHolder.heightProperty());
            
            // Re-render when window or container resizes
            this.dsv.widthProperty().addListener((obs, oldVal, newVal) -> dsv.render());
            this.dsv.heightProperty().addListener((obs, oldVal, newVal) -> dsv.render());
            
            super.getRoot().setCenter(canvasHolder);
        }
    }

    private void initControls() {
        // --- Navigation (Left Group) ---
        backButton = new Button("← Back to Menu");
        backButton.getStyleClass().add("top-bar-button");
        backButton.setOnAction((action) -> {
            super.getContext().swapState();
        });

        restartButton = new Button("Restart Structure");
        restartButton.getStyleClass().add("top-bar-button");
        restartButton.setOnAction((action) -> {
            this.dsv.getController().setRunning(false);
            this.dsv.restart();
        });


        // --- Controls (Center/Right Group) ---
        resetButton = new Button("Reset");
        resetButton.getStyleClass().add("top-bar-button");
        resetButton.setOnAction(action -> {
            this.dsv.getController().setRunning(false);
            this.dsv.getController().reset();
            this.dsv.render();
        });

        valueSpinner = new Spinner<Integer>(0, 100, 25, 1);
        valueSpinner.setEditable(true);
        valueSpinner.setPrefWidth(90);
        valueSpinner.getStyleClass().add("top-bar-spinner");
        valueSpinner.managedProperty().bind(valueSpinner.visibleProperty());
        valueSpinner.setVisible(false);

        operationSelector = new ComboBox<>();
        operationSelector.getItems().addAll(dsv.getController().supportedAlgorithms());
        operationSelector.setValue(Algorithm.NULL);
        operationSelector.getStyleClass().add("top-bar-combo");
        operationSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            this.dsv.getController().setRunning(false);
            dsv.getController().setAlgorithm(newItem);
            this.dsv.getController().reset();
            this.dsv.render();

            if (newItem.getCategory() == Category.VALUE) valueSpinner.setVisible(true);
            else valueSpinner.setVisible(false);
        });

        stepButton = new Button("Step");
        stepButton.getStyleClass().add("top-bar-button");
        stepButton.setOnAction((action) -> {
            dsv.getController().step(valueSpinner.getValue());
            dsv.render();
        });

        runButton = new Button("Run ▶");
        runButton.getStyleClass().add("top-bar-button-run");
        runButton.setOnAction((action) -> {
            this.dsv.getController().reset();
            this.dsv.render();
            this.dsv.getController().setRunning(true);
            this.loop = new Timeline(
                new KeyFrame(Duration.millis(500), event -> {
                    this.dsv.getController().step(valueSpinner.getValue());
                    dsv.render();

                    if (!dsv.getController().isRunning()) {
                        loop.stop();
                    }
                })
            );

            loop.setCycleCount(Timeline.INDEFINITE);
            loop.play();
        });

        pauseButton = new Button("Pause ⏸");
        pauseButton.getStyleClass().add("top-bar-button-pause");
        pauseButton.setOnAction(action -> {
            dsv.getController().setRunning(false);
        });

        // Spacer to separate back navigation from action controls
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // --- Top Bar Assembly ---
        HBox topBar = new HBox(12);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        Separator sep = new Separator(javafx.geometry.Orientation.VERTICAL);
        sep.getStyleClass().add("top-bar-separator");

        topBar.getChildren().addAll(
            backButton,
            restartButton,
            spacer,
            resetButton,
            operationSelector,
            valueSpinner,
            sep,
            stepButton,
            runButton,
            pauseButton
        );

        super.getRoot().setTop(topBar);
    }

    // Getters for wiring handlers externally
    public Button getBackButton() { return backButton; }
    public Button getStepButton() { return stepButton; }
    public Button getRunButton() { return runButton; }
    public Button getPauseButton() { return pauseButton; }
    public ComboBox<Algorithm> getOperationSelector() { return operationSelector; }

    @Override
    public void render() {
        if (dsv != null) {
            dsv.render();
        }
    }
}