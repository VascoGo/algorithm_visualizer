package com.algorithm_visualizer.controller;

public enum Algorithm {
    NULL(Category.NONE),
    BFS(Category.SIMPLE),
    DFS(Category.SIMPLE),
    DFS_POST(Category.SIMPLE),
    SEARCH(Category.VALUE),
    INSERT(Category.VALUE),
    DELETE(Category.VALUE),
    INSERT_INDEX(Category.DOUBLE);

    public enum Category {
        NONE,
        SIMPLE,
        VALUE,
        DOUBLE,
    }

    Category category;

    Algorithm(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }
}
