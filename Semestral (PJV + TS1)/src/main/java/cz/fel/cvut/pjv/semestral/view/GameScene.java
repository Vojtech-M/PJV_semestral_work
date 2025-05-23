package cz.fel.cvut.pjv.semestral.view;

import cz.fel.cvut.pjv.semestral.controller.GameController;
import cz.fel.cvut.pjv.semestral.model.Player;
import cz.fel.cvut.pjv.semestral.model.utils.PlayerType;
import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Objects;
import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.*;

@Getter
public class GameScene extends Scene {
    private static Logger log = Logger.getLogger(GameScene.class.getName());

    private final Canvas canvas;
    private final GraphicsContext gc;

    /**
     * Constructor for the GameScene class.
     * @param root
     * @param levelData
     * @param stage
     * @param player1StartX
     * @param player1StartY
     * @param player2StartX
     * @param player2StartY
     */
    public GameScene(Pane root, int[][] levelData, Stage stage, int player1StartX, int player1StartY, int player2StartX, int player2StartY) {
        super(root, SCREEN_WIDTH, SCREEN_HEIGHT);


        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        TileMap tileMap = new TileMap(levelData);

        Image fireImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Players/walking_red_blob.gif")));
        Player player1 = new Player(player1StartX,player1StartY, fireImage, tileMap, PlayerType.FIREBOY);

        Image waterImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Players/watergirl.png")));
        Player player2 = new Player(player2StartX, player2StartY, waterImage, tileMap, PlayerType.WATERGIRL);

        Button backButton = new Button("Back to Levels");
        backButton.setOnAction(actionEvent -> {
            LevelScene levelScene = new LevelScene(stage);
            stage.setScene(levelScene);
            log.info("Back to level selection");
            Sounds.playClickSound();
        });
        Label timerLabel = new Label("Time: 0s");
        timerLabel.setId("timer-label");

        Label score1 = new Label("Fireboy: 0");
        Label score2 = new Label("Watergirl: 0");


        backButton.getStyleClass().add("button");
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        backButton.setFocusTraversable(false);
        this.getStylesheets().add(getClass().getResource("/styles/levelStyles.css").toExternalForm());
        root.getChildren().addAll(backButton);

        new GameController(this, tileMap, player1, player2);

    }
}
