package cz.fel.cvut.pjv.semestral.model.utils;

import cz.fel.cvut.pjv.semestral.view.TileMap;
import javafx.scene.media.AudioClip;

/**
 * The Sounds class is responsible for managing sound effects in the game.
 * It provides methods to play various sound effects such as click, collect, player death, and player jump.
 */
public class Sounds {
    // File paths as Strings
    private static final String CLICK_SOUND_PATH = TileMap.class.getResource("/music/sounds/click.wav").toExternalForm();
    private static final String COLLECT_SOUND_PATH = TileMap.class.getResource("/music/sounds/pickupCoin.wav").toExternalForm();
    private static final String PLAYER_DEATH_PATH = TileMap.class.getResource("/music/sounds/player_death.wav").toExternalForm();
    private static final String PAYER_JUMP_PATH = TileMap.class.getResource("/music/sounds/jump.wav").toExternalForm();

    // AudioClip instances
    public static final AudioClip clickSound = new AudioClip(CLICK_SOUND_PATH);
    public static final AudioClip collectSound = new AudioClip(COLLECT_SOUND_PATH);
    public static final AudioClip playerDeath = new AudioClip(PLAYER_DEATH_PATH);
    public static final AudioClip playerJump = new AudioClip(PAYER_JUMP_PATH);

    // Methods to play sounds
    public static void playClickSound() {
        clickSound.play();
    }

    public static void playCollectSound() {
        collectSound.play();
    }

    public static void playPlayerDeath() {
        playerDeath.play();
    }

    public static void playPlayerJump() {
        playerJump.play();
    }


}
