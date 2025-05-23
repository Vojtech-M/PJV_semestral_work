package cz.fel.cvut.pjv.semestral.model;

import cz.fel.cvut.pjv.semestral.model.utils.Constants;
import cz.fel.cvut.pjv.semestral.model.utils.PlayerType;
import cz.fel.cvut.pjv.semestral.view.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;
import static cz.fel.cvut.pjv.semestral.model.utils.Sounds.playerJump;


/**
 * The Player class represents a player in the game.
 * It handles player movement, collision detection, and rendering.
 */
@Getter
@Setter
public class Player {
    Logger log = Logger.getLogger(Player.class.getName());
    public static final double WIDTH = Constants.PlayerConstants.WIDTH;
    public static final double HEIGHT = Constants.PlayerConstants.HEIGHT;

    public double x;
    public double y;
    private boolean isAlive = true;
    private double startX, startY;
    private PlayerType type;

    private Image idleImage;
    private Image walkingLeftImage;
    private Image walkingRightImage;

    public double velocityX;
    public double velocityY;
    private boolean onGround;
    private TileMap tileMap;
    private Image playerImage;
    @Setter
    private boolean leftPressed, rightPressed, jumpPressed;
    private static final double GRAVITY = 0.5;
    @Setter
    private int collectedCount;


    /**
     * Constructor for the Player class.
     * This class represents a player in the game.
     * It handles player movement, collision detection, and rendering.
     *
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     * @param playerImage The image representing the player.
     * @param tileMap The tile map of the game.
     * @param type The type of the player (Fireboy or Watergirl).
     */
    public Player(double x, double y, Image playerImage, TileMap tileMap, PlayerType type) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.playerImage = playerImage;
        this.tileMap = tileMap;
        this.type = type;
        this.collectedCount = 0;
        initializeAnimations();
    }


    /**
     * Initializes the animations for the player based on its type.
     * This method sets the idle and walking images for the player.
     */
    private void initializeAnimations() {
        switch (type) {
            case FIREBOY:
                idleImage = new Image("file:src/main/resources/images/Players/FireBoyStanding.gif");
                walkingLeftImage = new Image("file:src/main/resources/images/Players/red_walking_left.gif");
                walkingRightImage = new Image("file:src/main/resources/images/Players/red_walking_right.gif");
                break;
            case WATERGIRL:
                idleImage = new Image("file:src/main/resources/images/Players/WaterGirlStanding.gif");
                walkingLeftImage = new Image("file:src/main/resources/images/Players/WaterGirlWalkingLeft.gif");
                walkingRightImage = new Image("file:src/main/resources/images/Players/WaterGirlWlakingRight.gif");
                break;
        }

        // Set the starting image
        this.playerImage = idleImage;
    }

    /**
     * Updates the player's position and handles collisions.
     * This method is called every frame to update the player's position.
     */
    public void update() {
        velocityX = 0;

        if (leftPressed) {
            velocityX = -5;
            playerImage = walkingLeftImage;
        } else if (rightPressed) {
            velocityX = 5;
            playerImage = walkingRightImage;
        } else {
            playerImage = idleImage;
        }
        if (jumpPressed && onGround) {
            playerJump.play();
            velocityY = -12;
            onGround = false;
        }
        if (!onGround) {
            velocityY += GRAVITY;
        }

        x += velocityX;
        handleHorizontalCollision();

        y += velocityY;
        handleVerticalCollision();

        for (Fluid fluid : tileMap.getFluids()) {
            if (fluid.collidesWith(x, y, WIDTH, HEIGHT)) {
                if ((type == PlayerType.FIREBOY && fluid.getType() == Fluid.FluidType.WATER) ||
                        (type == PlayerType.WATERGIRL && fluid.getType() == Fluid.FluidType.LAVA)) {
                    isAlive = false;
                    log.info(getType() + " has died!");
                }
            }
        }

    }

    /**
     * Checks if the player is alive.
     * This method returns true if the player is alive, false otherwise.
     *
     * @return true if the player is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the alive status of the player.
     * This method sets the alive status of the player to the given value.
     *
     * @param b The new alive status of the player.
     */
    public void setAlive(boolean b) {
        isAlive = b;
    }

    /**
     * Handles horizontal collision detection.
     * This method checks for collisions with solid tiles and adjusts the player's position accordingly.
     */
    private void handleHorizontalCollision() {
        double nextXLeft = x;
        double nextXRight = x + WIDTH - 2;

        if (tileMap.isSolidTileAt(nextXLeft, y) || tileMap.isSolidTileAt(nextXRight, y)) {
            if (velocityX > 0) {
                x = (int) (x / TILE_SIZE) * TILE_SIZE;
            } else if (velocityX < 0) {
                x = ((int) (x / TILE_SIZE) + 1) * TILE_SIZE;
            }
            velocityX = 0;
        }
    }

    /**
     * Handles vertical collision detection.
     * This method checks for collisions with solid tiles and adjusts the player's position accordingly.
     */
    private void handleVerticalCollision() {
        double nextYBottom = y + HEIGHT - 1;
        double nextYTop = y;

        if (velocityY >= 0) {
            if (tileMap.isSolidTileAt(x, nextYBottom) || tileMap.isSolidTileAt(x + WIDTH - 1, nextYBottom)) {
                y = (int) (nextYBottom / TILE_SIZE) * TILE_SIZE - HEIGHT;
                velocityY = 0;
                onGround = true;
            } else {
                onGround = false;
            }
        }

        if (velocityY < 0) {
            if (tileMap.isSolidTileAt(x, nextYTop) || tileMap.isSolidTileAt(x + WIDTH - 1, nextYTop)) {
                y = ((int) (nextYTop / TILE_SIZE) + 1) * TILE_SIZE;
                velocityY = 0;
            }
        }
    }

    public void render(GraphicsContext gc) {
        if(isAlive){
            gc.drawImage(playerImage, x, y, WIDTH, HEIGHT);
        }

    }

    public void resetPosition(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isAlive = true;
    }

    public void resetToStart() {
        resetPosition(startX, startY);
    }
}