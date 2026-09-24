package com.algorithm_visualizer.controller;

import com.algorithm_visualizer.model.structures.DataStructure;

public abstract class Controller {
    private DataStructure ds;

    public Controller(DataStructure ds) {
        this.ds = ds;
    }

    public DataStructure getDataStructure() {
        return this.ds;
    }

    public abstract void step();
    public abstract void run();
}
