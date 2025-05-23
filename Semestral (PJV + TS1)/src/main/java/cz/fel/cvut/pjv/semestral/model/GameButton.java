package cz.fel.cvut.pjv.semestral.model;

import cz.fel.cvut.pjv.semestral.controller.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.List;
import java.util.logging.Logger;

/**
 * The GameButton class represents a button in the game.
 * It handles rendering the button, checking if the player is nearby, and toggling its state.
 */
@Getter
public class GameButton {
    private static final Logger log = Logger.getLogger(GameApplication.class.getName());
    private double x, y;
    private Image defaultImage;
    private Image pressedImage;
    private boolean isActivated;
    private String label;
    private int buttonID;
    public static final int SIZE = 64;


    /**
     * Constructor for the GameButton class.
     *
     * @param x            X coordinate of the button
     * @param y            Y coordinate of the button
     * @param defaultImage Image to be displayed when the button is not pressed
     * @param pressedImage Image to be displayed when the button is pressed
     * @param label        Label for the button
     * @param buttonID     Unique identifier for the button
     */
    public GameButton(double x, double y, Image defaultImage, Image pressedImage, String label, int buttonID) {
        this.x = x;
        this.y = y;
        this.defaultImage = defaultImage;
        this.pressedImage = pressedImage;
        this.label = label;
        this.isActivated = false;
        this.buttonID = buttonID;
    }

    public void render(GraphicsContext gc) {
        Image toDraw = isActivated && pressedImage != null ? pressedImage : defaultImage;
        if (toDraw != null) {
            gc.drawImage(toDraw, x, y, SIZE, SIZE);
        } else {
            log.warning("No image to draw for button " + label);
        }
    }

    /**
     * Checks if the player is nearby the button.
     *
     * @param playerX      The x-coordinate of the player.
     * @param playerY      The y-coordinate of the player.
     * @param playerWidth  The width of the player.
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
     * Toggles the button state and affects the doors associated with it.
     *
     * @param doors List of doors to be affected by the button press
     */
    public void press(List<Door> doors) {
        isActivated = !isActivated;
        for (Door door : doors) {
            if (door.getDoorID() == buttonID) {
                log.info("Button " + label + " affects door ID " + door.getDoorID());
                if (door.isOpen()) {
                    door.close();
                } else {
                    door.open();
                }
                log.info("Toggled pink door ID " + door.getDoorID() + " to " + (door.isOpen() ? "open" : "closed"));
            }
        }
        log.info("Button [" + label + "] pressed. New state: " + (isActivated ? "ON" : "OFF"));
    }
}