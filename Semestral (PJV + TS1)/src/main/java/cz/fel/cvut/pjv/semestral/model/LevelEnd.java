package cz.fel.cvut.pjv.semestral.model;

import cz.fel.cvut.pjv.semestral.controller.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.logging.Logger;

/**
 * The LevelEnd class represents the end of a level in the game.
 * It handles rendering the level end and checking if the player is nearby.
 */
public class LevelEnd {
    private static Logger log = Logger.getLogger(GameApplication.class.getName());
    private double x, y;
    private Image defaultImage;
    private boolean isActivated;
    public static final int SIZE = 64;

    /**
     * Constructor for the LevelEnd class.
     * This class represents the end of a level in the game.
     * It handles rendering the level end and checking if the player is nearby.
     *
     * @param x The x-coordinate of the level end.
     * @param y The y-coordinate of the level end.
     * @param defaultImage The image representing the level end.
     */
    public LevelEnd(double x, double y, Image defaultImage) {
        this.x = x;
        this.y = y;
        this.defaultImage = defaultImage;
        this.isActivated = false;
    }

    /**
     * Renders the level end on the given GraphicsContext.
     *
     * @param gc The GraphicsContext to render on.
     */
    public void render(GraphicsContext gc) {
        if (defaultImage != null) {
            gc.drawImage(defaultImage, x, y, SIZE, SIZE);
        } else {
            log.warning("No image to draw for level end");
        }
    }

    /**
     * Checks if the player is nearby the level end.
     *
     * @param playerX The x-coordinate of the player.
     * @param playerY The y-coordinate of the player.
     * @param playerWidth The width of the player.
     * @param playerHeight The height of the player.
     * @return true if the player is nearby, false otherwise.
     */
    public boolean isPlayerNearby(double playerX, double playerY, double playerWidth, double playerHeight) {
        return (playerX < x + SIZE &&
                playerX + playerWidth > x &&
                playerY < y + SIZE &&
                playerY + playerHeight > y);
    }

    /**
     * Checks if the level end is activated.
     *
     * @return true if the level end is activated, false otherwise.
     */
    public void activate() {
        isActivated = true;
        log.info("Level end activated");
    }
}