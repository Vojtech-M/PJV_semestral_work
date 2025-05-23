package cz.fel.cvut.pjv.semestral.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;

public class Fluid {
    public static final double SIZE = TILE_SIZE;

    public enum FluidType {LAVA, WATER}

    private double x, y;
    private Image defaultImage;
    @Getter
    private FluidType type;


    /**
     * Constructor for the Fluid class.
     *
     * @param x     X coordinate of the fluid
     * @param y     Y coordinate of the fluid
     * @param image Image representing the fluid
     * @param type  Type of the fluid (LAVA or WATER)
     */
    public Fluid(double x, double y, Image image, FluidType type) {
        this.x = x;
        this.y = y;
        this.defaultImage = image;
        this.type = type;
    }

    /**
     * Renders the fluid on the given GraphicsContext.
     *
     * @param gc The GraphicsContext to render on.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(defaultImage, x, y, SIZE, SIZE);
    }

    public boolean collidesWith(double px, double py, double pWidth, double pHeight) {
        double playerBottom = py + pHeight;
        int horizontalBuffer = 32;

        boolean horizontalOverlap = (px + pWidth - horizontalBuffer > x) && (px + horizontalBuffer < x + SIZE);
        boolean verticalTouchFromTop = playerBottom >= y && playerBottom <= y + 32;

        return horizontalOverlap && verticalTouchFromTop;
    }

    /**
     * Checks if the player is nearby the fluid.
     *
     * @param playerX      The x-coordinate of the player.
     * @param playerY      The y-coordinate of the player.
     * @param playerWidth  The width of the player.
     * @param playerHeight The height of the player.
     * @return true if the player is nearby, false otherwise.
     */
    public boolean collisionSolid(double playerX, double playerY, double playerWidth, double playerHeight) {
        return (playerX < x + TILE_SIZE &&
                playerX + playerWidth > x &&
                playerY < y + TILE_SIZE &&
                playerY + playerHeight > y);
    }
}
