package com.algorithm_visualizer.controller;

import java.util.List;

import com.algorithm_visualizer.model.structures.DataStructure;


public abstract class Controller {
    protected Algorithm algorithm;
    private Boolean running = false;

    public abstract void updateStructure(DataStructure structure);
    public abstract void reset();
    public abstract void step(Integer value, Integer index);

    public abstract List<Algorithm> supportedAlgorithms();
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Boolean isRunning() {
        return this.running;
    }
    public void setRunning(Boolean value) {
        this.running = value;
    }
}
