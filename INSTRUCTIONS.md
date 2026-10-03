# Setup & Running Instructions

This guide covers environment prerequisites, configuration, and commands to build and run the Algorithm Visualizer project.

---

## Prerequisites

Before building or running the project, ensure your environment meets the following requirements:

### 1. Java Development Kit (JDK 26)
- **Required Version**: **Java 26**
- You can download JDK 26 from distributions such as:
  - [Oracle OpenJDK](https://jdk.java.net/)
  - [Eclipse Temurin](https://adoptium.net/)
  - [Azul Zulu](https://www.azul.com/downloads/)
- Verify your installed Java version:
  ```bash
  java -version
  ```

### 2. Gradle Build Tool
- **Configured Version**: **Gradle 9.3.1**
- Manual Gradle installation is optional. The repository includes the Gradle Wrapper (`gradlew` / `gradlew.bat`), which handles Gradle **9.3.1** automatically.

---

## Environment Setup

Ensure `JAVA_HOME` points to your JDK 26 installation directory.

### Linux / macOS
```bash
export JAVA_HOME=/path/to/jdk-26
export PATH=$JAVA_HOME/bin:$PATH
```

### Windows (Command Prompt)
```cmd
set JAVA_HOME=C:\Path\To\jdk-26
set PATH=%JAVA_HOME%\bin;%PATH%
```

### Windows (PowerShell)
```powershell
$env:JAVA_HOME="C:\Path\To\jdk-26"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
```

---

## Building the Project

Compile the source code and process application resources using the Gradle wrapper:

### Linux / macOS
```bash
./gradlew build
```

### Windows
```cmd
gradlew.bat build
```

---

## Running the Application

Start the JavaFX application directly:

### Linux / macOS
```bash
./gradlew run
```

### Windows
```cmd
gradlew.bat run
```

---

## Troubleshooting

- **JDK Version Mismatch**: If Gradle reports toolchain issues, verify that `JAVA_HOME` points explicitly to JDK 26.
- **Display Environment (Linux)**: JavaFX requires an active graphical window system (X11 or Wayland). Ensure you run the application in a desktop GUI environment.
