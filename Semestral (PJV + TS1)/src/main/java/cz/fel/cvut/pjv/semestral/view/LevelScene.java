package cz.fel.cvut.pjv.semestral.view;

import cz.fel.cvut.pjv.semestral.model.LevelLoader;
import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_HEIGHT;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_WIDTH;
import static cz.fel.cvut.pjv.semestral.view.UIcomponents.positionElementCenter;


/**
 * The LevelScene class represents the level selection screen of the game.
 * It allows the player to choose between different levels and navigate back to the main menu.
 */
public class LevelScene extends Scene {
    private static Logger log = Logger.getLogger(LevelScene.class.getName());


    /**
     * Constructor for the LevelScene class.
     * This class represents the level selection screen of the game.
     * It allows the player to choose between different levels and navigate back to the main menu.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     */
    public LevelScene(Stage stage) {
        super(new Pane(), SCREEN_WIDTH, SCREEN_HEIGHT);

        Pane root = (Pane) getRoot();
        this.getStylesheets().add(getClass().getResource("/styles/levelStyles.css").toExternalForm());

        // Create button for level 1
        Button buttonLevel1 = new Button("Level 1");
        buttonLevel1.setOnAction(actionEvent -> {
            int[][] levelData = LevelLoader.loadLevel("/levels/level1.txt");
            GameScene gameScene = new GameScene(new Pane(), levelData, stage, TILE_SIZE * 3, TILE_SIZE * 14,TILE_SIZE * 15, TILE_SIZE * 14);
            stage.setScene(gameScene);
            log.info("Start level 1");
            Sounds.playClickSound();
        });
        buttonLevel1.getStyleClass().add("button");

        // Create button for level 2
        Button buttonLevel2 = new Button("Level 2");
        buttonLevel2.setOnAction(actionEvent -> {
            int[][] levelData = LevelLoader.loadLevel("/levels/level2.txt");
            GameScene gameScene = new GameScene(new Pane(), levelData, stage,TILE_SIZE * 3, TILE_SIZE * 14,TILE_SIZE * 15, TILE_SIZE * 14);
            stage.setScene(gameScene);
            log.info("Start level 2");
            Sounds.playClickSound();
        });
        buttonLevel2.getStyleClass().add("button");

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("button");
        exitButton.setOnAction(actionEvent -> {
            log.info("Exit button clicked");
            Platform.exit(); // This will close the application cleanly
        });
        exitButton.setLayoutX(10);
        exitButton.setLayoutY(600); // Slightly below the "Play" button

        root.getChildren().add(exitButton);

        buttonLevel2.getStyleClass().add("level2");
        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(actionEvent -> {
            MenuScene menuScene = new MenuScene(stage);
            stage.setScene(menuScene);
            log.info("Back to menu");
            Sounds.playClickSound();
        });
        backButton.getStyleClass().add("button");
        backButton.setLayoutX(10);
        backButton.setLayoutY(500);

        root.getStyleClass().add("level-pane");

        positionElementCenter(buttonLevel1, 150);
        positionElementCenter(buttonLevel2, 250);

        root.getChildren().addAll(buttonLevel1, buttonLevel2, backButton);
    }
}
