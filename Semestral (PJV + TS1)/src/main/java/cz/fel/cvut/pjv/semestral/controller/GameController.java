package cz.fel.cvut.pjv.semestral.controller;

import cz.fel.cvut.pjv.semestral.model.*;
import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import cz.fel.cvut.pjv.semestral.model.utils.Timer;
import cz.fel.cvut.pjv.semestral.view.LevelScene;
import cz.fel.cvut.pjv.semestral.view.TileMap;
import cz.fel.cvut.pjv.semestral.view.GameScene;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.logging.Logger;


/**
 * The GameController class handles the game logic and input for the players.
 * It manages the game loop, player updates, rendering, and input handling.
 * It also handles player deaths and level completion.
 */
public class GameController {
    Logger log = Logger.getLogger(GameController.class.getName());
    private final GameScene gameScene;
    private final Player player1, player2;
    private final TileMap tileMap;

    private final PlayerController pc1, pc2;
    private static boolean gameRunning = true;
    private final Timer timer = new Timer();

    /**
     * Constructor for the GameController class.
     * This class handles the game logic and input for the players.
     *
     * @param scene Scene is the game scene
     * @param map   The tile map of the game
     * @param p1    Player 1 object
     * @param p2    Player 2 object
     */
    public GameController(GameScene scene, TileMap map, Player p1, Player p2) {
        this.gameScene = scene;
        this.tileMap = map;
        this.player1 = p1;
        this.player2 = p2;
        this.pc1 = new PlayerController(p1, tileMap);
        this.pc2 = new PlayerController(p2, tileMap);

        setupInputHandling();
        startGameLoop();
        timer.start();
    }

    /**
     * Sets up the input handling for the game.
     * This method listens for key presses and releases to control the players.
     */
    public void setupInputHandling() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> player1.setLeftPressed(true);
                case RIGHT -> player1.setRightPressed(true);
                case UP -> player1.setJumpPressed(true);
                case A -> player2.setLeftPressed(true);
                case D -> player2.setRightPressed(true);
                case W -> player2.setJumpPressed(true);
                case E -> handleButtonPress();
                case SPACE -> handleLevelEnd();
            }
        });

        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT -> player1.setLeftPressed(false);
                case RIGHT -> player1.setRightPressed(false);
                case UP -> player1.setJumpPressed(false);
                case A -> player2.setLeftPressed(false);
                case D -> player2.setRightPressed(false);
                case W -> player2.setJumpPressed(false);
            }
        });
    }

    /**
     * Starts the game loop.
     * This method creates an AnimationTimer that updates and renders the game.
     */
    public void startGameLoop() {
        GraphicsContext gc = gameScene.getGc();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        }.start();
    }

    /**
     * Updates the game state.
     * This method updates the players and checks for collisions.
     */
    public void update() {
        if (!gameRunning) return;

        player1.update();
        player2.update();

        if (!player1.isAlive() || !player2.isAlive()) {
            Sounds.playPlayerDeath();
            handlePlayerDeath();
            return;
        }
        // screen‐edge death
        PlayerController.checkPlayerCollisionScreenBorders(player1);
        PlayerController.checkPlayerCollisionScreenBorders(player2);

        // diamond pickups
        pc1.collectDiamonds();
        pc2.collectDiamonds();
    }

    /**
     * Renders the game on the screen.
     * This method clears the screen and draws the tile map, players, and HUD.
     *
     * @param gc The GraphicsContext to draw on.
     */
    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, gameScene.getWidth(), gameScene.getHeight());
        tileMap.render(gc);
        player1.render(gc);
        player2.render(gc);
        drawHUD(gc);
    }


    /**
     * Draws the HUD (Heads-Up Display) on the screen.
     * This method displays the players' collected items and the timer.
     *
     * @param gc The GraphicsContext to draw on.
     */
    public void drawHUD(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.6));
        gc.fillRoundRect(1110, 30, 220, 140, 15, 15);

        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText("Fireboy: " + player1.getCollectedCount(), 1130, 60);

        gc.setFill(Color.DEEPSKYBLUE);
        gc.fillText("Watergirl: " + player2.getCollectedCount(), 1130, 100);

        gc.setFill(Color.GOLD);
        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
        gc.fillText("Time: " + timer.getFormattedTime(), 1130, 140);

    }

    /**
     * Handles button presses by checking if players are nearby and pressing the button.
     * This method also checks for door interactions.
     */
    public void handleButtonPress() {
        tileMap.getButtons().forEach(button -> {
            if (button.isPlayerNearby(player1.getX(), player1.getY(), Player.WIDTH, Player.HEIGHT) ||
                    button.isPlayerNearby(player2.getX(), player2.getY(), Player.WIDTH, Player.HEIGHT)) {
                button.press(tileMap.getDoors());
            }
        });
    }

    /**
     * Handles level end by checking if players are nearby the level end.
     * This method switches to the next level scene if both players are nearby.
     */
    public void handleLevelEnd() {
        for (LevelEnd levelEnd : tileMap.getLevelEnds()) {
            if (levelEnd.isPlayerNearby(player1.getX(), player1.getY(), Player.WIDTH, Player.HEIGHT) && levelEnd.isPlayerNearby(player2.getX(), player2.getY(), Player.WIDTH, Player.HEIGHT)) {
                Sounds.playClickSound();
                levelEnd.activate();

                Stage stage = (Stage) gameScene.getWindow(); // Get the stage from GameScene
                LevelScene levelScene = new LevelScene(stage);
                stage.setScene(levelScene);

                timer.stop(); // Stop the timer when level ends
                log.info("Level completed in: " + timer.getElapsedSeconds() + " seconds");

            }
        }
    }

    /**
     * Handles player death by resetting the players and stopping the game.
     * This method plays the player death sound and resets the players' positions.
     */
    public void handlePlayerDeath() {
        gameRunning = false;
        Sounds.playPlayerDeath();

        // Delay of sound and switch
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Delay 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.warning("Error during sleep: " + e.getMessage());
            }
            javafx.application.Platform.runLater(() -> {
                player1.resetToStart();
                player2.resetToStart();
                gameRunning = true;

            });
        }).start();
    }
}
