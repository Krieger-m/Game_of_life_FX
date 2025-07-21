package com.krieger;

import java.util.HashMap;
import java.util.Map;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLife extends Application {

            //x// configuration here //x//
            //-------------------------//
    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;
    private static final int BOARD_PIXEL_SIZE = 720; // this is the pixel size
    private static final int BOARD_LOGICAL_SIZE = BOARD_PIXEL_SIZE / WIDTH; // This is the logical cell count

    private Map<String, StackPane> boardMap = new HashMap<>();
    private Board board = new Board(BOARD_LOGICAL_SIZE); // Use the logical size for the Board

    @Override
    public void start(Stage primaryStage) {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                iterateBoard();
            }
        }), new KeyFrame(Duration.millis(100)));

        timeline.setCycleCount(Timeline.INDEFINITE);

        board.initBoard(0.35);

        Pane root = new Pane();
        Scene scene = new Scene(root, BOARD_PIXEL_SIZE, BOARD_PIXEL_SIZE);

        scene.getStylesheets().add(getClass().getResource("/com/krieger/gol.css").toExternalForm());

                    // Create a board with dead cells
        for (int x = 0; x < BOARD_LOGICAL_SIZE; x ++) {             // Iterate using logical board size
            for (int y = 0; y < BOARD_LOGICAL_SIZE; y ++) {             // Iterate using logical board size
                StackPane cell = new StackPane();
                        // Position visually
                cell.setLayoutX(x * WIDTH); 
                        // Position visually
                cell.setLayoutY(y * HEIGHT); 
                cell.setPrefHeight(HEIGHT);
                cell.setPrefWidth(WIDTH);
                cell.getStyleClass().add("dead-cell");
                root.getChildren().add(cell);

                            //Store the cell in a HashMap for fast access
                            //Use logical coordinates for the key
                boardMap.put(x + "," + y, cell); 
            }
        }

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        timeline.play();
    }

    private void iterateBoard() {
        board.nextPopulation();
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                            // Retrieve using the same logical coordinates as stored
                StackPane pane = boardMap.get(x + "," + y); 
                if (pane != null) {            // Added a null check just in case
                    pane.getStyleClass().clear();
                                // If the cell at (x,y) is a alive use css styling 'alive-cell'
                                // otherwise use the styling 'dead-cell'.
                    if (board.getField(x, y) == 1) {
                        pane.getStyleClass().add("alive-cell");
                    } else {
                        pane.getStyleClass().add("dead-cell");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
