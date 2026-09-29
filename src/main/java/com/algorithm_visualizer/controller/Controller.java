package com.algorithm_visualizer.controller;

import java.util.List;

import com.algorithm_visualizer.model.structures.DataStructure;

public abstract class Controller {
    private DataStructure ds;
    protected Algorithm algorithm;
    private Boolean running = false;

    public Controller(DataStructure ds) {
        this.ds = ds;
    }

    public DataStructure getDataStructure() {
        return this.ds;
    }

    public abstract void step();
    public abstract void run();

    public abstract List<Algorithm> supportedAlgorithms();
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Boolean isRunning() {
        return this.running;
    }
}
