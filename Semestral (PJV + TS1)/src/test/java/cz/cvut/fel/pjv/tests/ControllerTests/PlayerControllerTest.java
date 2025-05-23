package cz.cvut.fel.pjv.tests.ControllerTests;

import cz.fel.cvut.pjv.semestral.controller.PlayerController;
import cz.fel.cvut.pjv.semestral.model.Collectable;
import cz.fel.cvut.pjv.semestral.model.Player;
import cz.fel.cvut.pjv.semestral.model.utils.Constants;
import cz.fel.cvut.pjv.semestral.model.utils.PlayerType;
import cz.fel.cvut.pjv.semestral.view.TileMap;
import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

public class PlayerControllerTest {

    private Player player;
    private TileMap tileMap;
    private PlayerController playerController;

    // Mock Player and TileMap
    @BeforeEach
    public void setUp() {
        player = mock(Player.class);
        tileMap = mock(TileMap.class);
        playerController = new PlayerController(player, tileMap);
    }

    @Test
    public void testCollectDiamonds_FireboyCollectsRedDiamond() {
        when(player.getType()).thenReturn(PlayerType.FIREBOY);

        // Create a mock Collectable red diamond
        Collectable redDiamond = mock(Collectable.class);
        when(redDiamond.getType()).thenReturn("red");
        when(redDiamond.isCollected()).thenReturn(false);
        when(redDiamond.collidesWith(anyDouble(), anyDouble(), eq(Player.WIDTH), eq(Player.HEIGHT))).thenReturn(true);
        when(tileMap.getDiamonds()).thenReturn(List.of(redDiamond));

        //collecting
        playerController.collectDiamonds();

        verify(redDiamond).collect();
        verify(player).setCollectedCount(1);
        Sounds.playCollectSound();
    }

    @Test
    public void testCollectDiamonds_WatergirlCollectsBlueDiamond() {
        when(player.getType()).thenReturn(PlayerType.WATERGIRL);

        // Create a mock Collectable blue diamond
        Collectable blueDiamond = mock(Collectable.class);
        when(blueDiamond.getType()).thenReturn("blue");
        when(blueDiamond.isCollected()).thenReturn(false);
        when(blueDiamond.collidesWith(anyDouble(), anyDouble(), eq(Player.WIDTH), eq(Player.HEIGHT))).thenReturn(true);

        when(tileMap.getDiamonds()).thenReturn(List.of(blueDiamond));
        playerController.collectDiamonds();

        verify(blueDiamond).collect();
        verify(player).setCollectedCount(1);
    }

    @Test
    public void testCheckPlayerCollisionScreenBorders_PlayerOutsideRightEdge() {
        // Mock player position at the right edge
        when(player.getX()).thenReturn((double) (Constants.GameConstants.SCREEN_WIDTH + 1));
        when(player.getY()).thenReturn(100.0);

        // Call the method to check if player is out of bounds
        PlayerController.checkPlayerCollisionScreenBorders(player);

        // Verify that the player's alive status is set to false
        verify(player).setAlive(false);
    }

    @Test
    public void testCheckPlayerCollisionScreenBorders_PlayerOutsideLeftEdge() {
        when(player.getX()).thenReturn(-1.0);
        when(player.getY()).thenReturn(100.0);

        PlayerController.checkPlayerCollisionScreenBorders(player);

        verify(player).setAlive(false);
    }

    @Test
    public void testCheckPlayerCollisionScreenBorders_PlayerInsideScreen() {
        when(player.getX()).thenReturn(100.0);
        when(player.getY()).thenReturn(100.0);

        PlayerController.checkPlayerCollisionScreenBorders(player);

        verify(player, never()).setAlive(false);
    }
}
