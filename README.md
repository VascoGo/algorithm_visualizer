# Algorithm Visualizer

A modular JavaFX application for interactively visualizing data structures and algorithms. The project focuses on clean software architecture, state-driven UI navigation, and strict decoupling between domain models and presentation layers.

---

## Motivation & Architecture

While the underlying algorithms (such as tree traversals and linked list operations) are straightforward, this project serves primarily as a showcase of clean software structuring in Java UI applications.

### Key Design Pillars

1. **Decoupled Domain Models (`model`)**
   - Data structures (`Tree`, `BinaryTree`, `LinkedList`, and their corresponding nodes) are implemented in pure Java with zero dependencies on JavaFX (`javafx.*`).
   - Domain logic remains portable, testable, and completely independent of the visual interface.

2. **State-Driven Application Flow (`state`)**
   - The application manages transitions between states (such as menu and visualizer runtime) through a centralized `State` context.
   - State changes dynamically update the active scene root without re-creating window contexts.

3. **Separation of Presentation & Control (`view` & `controller`)**
   - **Viewers (`view`)**: Handle rendering structures onto a JavaFX `Canvas` and constructing control panels.
   - **Controllers (`controller`)**: Manage algorithm execution steps, pauses, resets, and bridge domain models with viewer updates.

---

## Tech Stack & Dependencies

The project build configuration uses the following versions:

| Component | Version | Details |
| :--- | :--- | :--- |
| **Java** | `26` | Java Language Version specified in Gradle toolchain |
| **JavaFX** | `27` | OpenJFX controls and FXML modules (`org.openjfx:javafx-*`) |
| **JavaFX Plugin** | `0.1.0` | `org.openjfx.javafxplugin` |
| **Gradle** | `9.3.1` | Configured via Gradle Wrapper |

---

## Project Structure

```text
com.algorithm_visualizer
├── Main.java                # Application entry point
├── state/
│   └── State.java           # Context manager for menu and runtime states
├── model/                   # Pure Java domain models (no JavaFX dependencies)
│   └── structures/
│       ├── DataStructure.java
│       ├── Tree.java
│       ├── BinaryTree.java
│       └── LinkedList.java
├── view/                    # JavaFX UI controls and canvas viewers
│   ├── Viewer.java
│   ├── MenuViewer.java
│   ├── RuntimeViewer.java
│   └── structures/
│       ├── DataStructureViewer.java
│       ├── TreeViewer.java
│       ├── BinaryTreeViewer.java
│       └── LinkedListViewer.java
└── controller/              # Algorithm controllers and execution logic
    ├── Controller.java
    ├── Algorithm.java
    ├── TreeController.java
    ├── BinaryTreeController.java
    └── LinkedListController.java
```

---

## Getting Started

For setup instructions, prerequisites, and build steps, refer to [INSTRUCTIONS.md](INSTRUCTIONS.md).
