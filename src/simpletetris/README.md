# SimpleTetris

**SimpleTetris** is a modular JavaFX implementation of the classic Tetris game.  
This project was developed as part of a university course.

---

## Features

- Classic Tetris gameplay with a small set of tetrominoes: I, O, T, L  
- Keyboard controls:
  - **Left / Right**: Move the piece horizontally  
  - **Down**: Soft drop  
  - **Up**: Rotate piece  
  - **Space**: Hard drop  
- Line clearing logic and game over detection  
- Color-coded blocks for each tetromino  

---

## Requirements

- Java 25 or later  
- JavaFX 25 SDK installed (e.g., `/opt/javafx-sdk-25.0.1/lib`) 

## How to Compile

From the project root:

```bash
javac --module-path /opt/javafx-sdk-25.0.1/lib -d out $(find src -name "*.java")
```

## How to run

``` bash
java --module-path out:/opt/javafx-sdk-25.0.1/lib \
     --add-modules javafx.controls,javafx.graphics \
     -m simpletetris/simpletetris.SimpleTetris
```
