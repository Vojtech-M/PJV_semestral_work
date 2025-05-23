package cz.fel.cvut.pjv.semestral.model;

import cz.fel.cvut.pjv.semestral.controller.GameApplication;
import cz.fel.cvut.pjv.semestral.model.utils.Collidable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;

/**
 * Represents a collectable item in the game, such as a diamond.
 * Implements the Collidable interface for collision detection.
 */
public class Collectable implements Collidable {
    private double x, y;
    private Image collectableImage;
    private boolean isCollected;
    private String type;
    private static Logger log = Logger.getLogger(GameApplication.class.getName());

    /**
     * Constructor for the Collectable class.
     *
     * @param x               The x-coordinate of the collectable.
     * @param y               The y-coordinate of the collectable.
     * @param collectableImage The image representing the collectable.
     * @param type            The type of the collectable (e.g., "red", "blue").
     */
    public Collectable(double x, double y, Image collectableImage, String type) {
        this.x = x;
        this.y = y;
        this.collectableImage = collectableImage;
        this.type = type;
        this.isCollected = false;
    }

    public void render(GraphicsContext gc) {
        if (!isCollected) {
            gc.drawImage(collectableImage, x, y, TILE_SIZE, TILE_SIZE);
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return TILE_SIZE;
    }

    @Override
    public double getHeight() {
        return TILE_SIZE;
    }

    public void collect() {
        isCollected = true;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public String getType() {
        return type;
    }
}
