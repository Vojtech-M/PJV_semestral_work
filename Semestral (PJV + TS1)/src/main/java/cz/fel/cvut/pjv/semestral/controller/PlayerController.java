package cz.fel.cvut.pjv.semestral.controller;


import cz.fel.cvut.pjv.semestral.model.Collectable;
import cz.fel.cvut.pjv.semestral.model.utils.Constants;
import cz.fel.cvut.pjv.semestral.model.Player;
import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import cz.fel.cvut.pjv.semestral.view.TileMap;
import java.util.logging.Logger;
import static cz.fel.cvut.pjv.semestral.model.Player.WIDTH;

/**
 * The PlayerController class handles the player input and interactions with the game world.
 * It manages the collection of diamonds and checks for player collisions with screen borders.
 */
public class PlayerController {
    Logger log = Logger.getLogger(PlayerController.class.getName());
    private final Player player;
    private final TileMap tileMap;


    /**
     * Constructor for the PlayerController class.
     * This class handles the player input and interactions with the game world.
     *
     * @param player The player object to control.
     * @param tileMap The tile map of the game.
     */
    public PlayerController(Player player, TileMap tileMap) {
        this.player   = player;
        this.tileMap  = tileMap;
    }

    /**
     * Updates the player position based on the input.
     * This method is called every frame to update the player's position.
     */
    public void collectDiamonds() {
        String targetColor = switch (player.getType()) {
            case FIREBOY -> "red";
            case WATERGIRL -> "blue";

        };
        for (Collectable d : tileMap.getDiamonds()) {
            if (!d.isCollected()
                    && (d.getType().equals(targetColor) || d.getType().equals("any"))
                    && d.collidesWith(player.getX(), player.getY(), WIDTH, Player.HEIGHT))
            {
                d.collect();
                player.setCollectedCount(player.getCollectedCount() + 1);
                log.info("Collected " + d.getType() + " diamond");
                Sounds.playCollectSound();
            }
        }
    }

    /**
     * Checks if the player has collided with the screen borders.
     * If the player goes out of bounds, they are marked as not alive.
     *
     * @param p The player object to check for collisions.
     */
    public static void checkPlayerCollisionScreenBorders(Player p) {
        if (p.getX() > Constants.GameConstants.SCREEN_WIDTH ||
                p.getX() < 0 ||
                p.getY() > Constants.GameConstants.SCREEN_HEIGHT ||
                p.getY() < 0) {
            p.setAlive(false);
        }
    }
}

