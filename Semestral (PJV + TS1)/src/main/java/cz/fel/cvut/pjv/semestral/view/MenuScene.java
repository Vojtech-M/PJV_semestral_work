package cz.fel.cvut.pjv.semestral.view;

import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_WIDTH;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_HEIGHT;


/**
 * The MenuScene class represents the initial menu scene of the game.
 * It contains a label and buttons for starting the game and exiting the application.
 * The scene is displayed when the game starts.
 */
public class MenuScene extends Scene {
    Logger log = Logger.getLogger(MenuScene.class.getName());

    /**
     * Creates a new MenuScene.
     * <p>
     * This scene is displayed when the game starts and contains a label and a button.
     * The label displays a welcome message and the button allows the user to start the game.
     *
     * @param stage
     */
    public MenuScene(Stage stage) {
        super(new Pane(), SCREEN_WIDTH, SCREEN_HEIGHT);
        Pane root = (Pane) getRoot();

        Label label = new Label("Press Play to start");
        label.getStyleClass().add("menu-label");

        Button button = new Button("Play");
        button.getStyleClass().add("menu-button");

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("menu-button");

        button.setOnAction(actionEvent -> {
            // introSong.stop();
            LevelScene levelScene = new LevelScene(stage);
            stage.setScene(levelScene);
            log.info("Show level select");
            Sounds.playClickSound();
        });


        exitButton.setOnAction(actionEvent -> {
            log.info("Exit button clicked");
            Platform.exit(); // This will close the application cleanly
        });


        label.setLayoutX(SCREEN_WIDTH / 2 - 150);
        label.setLayoutY(SCREEN_HEIGHT / 2 - 50);

        // Center the button
        button.setLayoutX(SCREEN_WIDTH / 2 - 50);
        button.setLayoutY(SCREEN_HEIGHT / 2 + 20);

        exitButton.setLayoutX(SCREEN_WIDTH / 2 - 50);
        exitButton.setLayoutY(SCREEN_HEIGHT / 2 + 250); // Slightly below the "Play" button

        root.getChildren().add(exitButton);

        root.getChildren().addAll(label, button);
        this.getStylesheets().add(getClass().getResource("/styles/menuStyles.css").toExternalForm());
        root.getStyleClass().add("menu-pane");
    }

    // Method added for testing
    public Logger getLogger() {
        return log;
    }
}