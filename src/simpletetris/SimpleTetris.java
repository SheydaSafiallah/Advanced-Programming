package simpletetris;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class SimpleTetris extends Application {

    static final int COLUMNS = 10;
    static final int ROWS = 20;
    static final int CELL = 30;

    private int[][] board = new int[ROWS][COLUMNS];
    private Canvas canvas;
    private GraphicsContext gc;

    private Tetromino current;
    private int curRow, curCol, curRotation;

    private long lastDropTime = 0;
    private long dropInterval = 500_000_000;
    private Random rand = new Random();

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(COLUMNS * CELL, ROWS * CELL);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        initNewPiece();
        draw();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) move(-1, 0);
            else if (e.getCode() == KeyCode.RIGHT) move(1, 0);
            else if (e.getCode() == KeyCode.DOWN) move(0, 1);
            else if (e.getCode() == KeyCode.UP) rotate();
            else if (e.getCode() == KeyCode.SPACE) hardDrop();
            draw();
        });

        stage.setScene(scene);
        stage.setTitle("Simple Tetris");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastDropTime > dropInterval) {
                    if (!move(0, 1)) {
                        lockPiece();
                        clearLines();
                        initNewPiece();
                        if (!canPlace(current, curRow, curCol, curRotation)) {
                            for (int r = 0; r < ROWS; r++)
                                for (int c = 0; c < COLUMNS; c++)
                                    board[r][c] = 0;
                        }
                    }
                    lastDropTime = now;
                    draw();
                }
            }
        };

        timer.start();
    }

    private void initNewPiece() {
        Tetromino[] vals = Tetromino.values();
        current = vals[rand.nextInt(vals.length)];
        curRotation = 0;
        curRow = 0;
        curCol = COLUMNS / 2 - 2;
    }

    private boolean move(int dx, int dy) {
        if (canPlace(current, curRow + dy, curCol + dx, curRotation)) {
            curRow += dy;
            curCol += dx;
            return true;
        }
        return false;
    }

    private void rotate() {
        int next = (curRotation + 1) % current.rotations.length;
        if (canPlace(current, curRow, curCol, next)) {
            curRotation = next;
        }
    }

    private void hardDrop() {
        while (move(0, 1)) {}
    }

    private boolean canPlace(Tetromino t, int row, int col, int rot) {
        int[][] shape = t.rotations[rot];
        for (int i = 0; i < 4; i++) {
            int r = row + shape[i][1];
            int c = col + shape[i][0];

            if (c < 0 || c >= COLUMNS || r >= ROWS) return false;
            if (r >= 0 && board[r][c] != 0) return false;
        }
        return true;
    }

    private void lockPiece() {
        int[][] shape = current.rotations[curRotation];
        for (int i = 0; i < 4; i++) {
            int r = curRow + shape[i][1];
            int c = curCol + shape[i][0];
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS) {
                board[r][c] = current.colorId;
            }
        }
    }

    private void clearLines() {
        for (int r = ROWS - 1; r >= 0; r--) {
            boolean full = true;
            for (int c = 0; c < COLUMNS; c++)
                if (board[r][c] == 0) {
                    full = false;
                    break;
                }

            if (full) {
                for (int rr = r; rr > 0; rr--)
                    System.arraycopy(board[rr - 1], 0, board[rr], 0, COLUMNS);

                for (int cc = 0; cc < COLUMNS; cc++)
                    board[0][cc] = 0;

                r++;
            }
        }
    }

    private void draw() {
        gc.setFill(Color.web("#111"));
        gc.fillRect(0, 0, COLUMNS * CELL, ROWS * CELL);

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                if (board[r][c] != 0) {
                    gc.setFill(colorById(board[r][c]));
                    gc.fillRect(c * CELL, r * CELL, CELL - 1, CELL - 1);
                }

        int[][] shape = current.rotations[curRotation];
        gc.setFill(colorById(current.colorId));
        for (int i = 0; i < 4; i++) {
            int r = curRow + shape[i][1];
            int c = curCol + shape[i][0];
            if (r >= 0)
                gc.fillRect(c * CELL, r * CELL, CELL - 1, CELL - 1);
        }
    }

    private Color colorById(int id) {
        switch (id) {
            case 1: return Color.CYAN;
            case 2: return Color.GOLD;
            case 3: return Color.PURPLE;
            case 4: return Color.ORANGE;
            default: return Color.WHITE;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
