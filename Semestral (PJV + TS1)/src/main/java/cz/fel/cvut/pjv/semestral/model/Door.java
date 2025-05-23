package cz.fel.cvut.pjv.semestral.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;

/**
 * The Door class represents a door in the game.
 * It contains properties such as position, type, and whether it is open or closed.
 * The class also provides methods to open, close, render the door, and check for collisions with the player.
 */
@Getter
public class Door {
    Logger log = Logger.getLogger(Door.class.getName());
    private double x, y;
    private String type; // "blue", "red", "black", etc.
    private boolean isOpen;
    private int doorID;
    private Image doorImage;
    private Image openedImage;

    /**
     * Constructor for the Door class.
     *
     * @param x
     * @param y
     * @param doorImage
     * @param openedImage
     * @param type
     * @param doorID
     */
    public Door(double x, double y, Image doorImage, Image openedImage, String type, int doorID) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.doorImage = doorImage;
        this.openedImage = openedImage;
        this.isOpen = false;
        this.doorID = doorID;
    }


    public void open() {
        isOpen = true;
        log .info("Door of type " + type + " with ID " + doorID + " opened.");
    }

    public void close() {
        isOpen = false;
        log.info("Door of type " + type + " with ID " + doorID + " closed.");
    }

    public void render(GraphicsContext gc) {
        Image toDraw = isOpen && openedImage != null ? openedImage : doorImage;
        if (toDraw != null) {
            gc.drawImage(toDraw, x, y, TILE_SIZE, TILE_SIZE);
        }
    }

    /**
     * Checks if the door collides with the player.
     *
     * @param playerX
     * @param playerY
     * @param playerWidth
     * @param playerHeight
     * @return true if the door collides with the player, false otherwise
     */
    public boolean collidesWith(double playerX, double playerY, double playerWidth, double playerHeight) {
        if (isOpen) return false; // No collision if door is open

        return (playerX < x + TILE_SIZE &&
                playerX + playerWidth > x &&
                playerY < y + TILE_SIZE &&
                playerY + playerHeight > y);
    }

    /**
     * Checks if the door is open.
     *
     * @return true if the door is open, false otherwise
     */
    public void setImage(Image newImage) {
        this.doorImage = newImage;
    }
}