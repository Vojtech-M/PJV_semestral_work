package cz.cvut.fel.pjv.tests.ViewTests;

import cz.fel.cvut.pjv.semestral.model.Door;
import cz.fel.cvut.pjv.semestral.model.GameButton;
import cz.fel.cvut.pjv.semestral.view.TileMap;
import cz.fel.cvut.pjv.semestral.model.Collectable;
import cz.fel.cvut.pjv.semestral.view.TileType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;
import static cz.fel.cvut.pjv.semestral.view.TileType.BLUE_DIAMOND;
import static org.junit.jupiter.api.Assertions.*;

public class TileMapTest {

    @Test
    void testTileMapLoadsOneDiamond() {
        int[][] testLevelData = {
                {0, 0, 0},
                {0, BLUE_DIAMOND, 0},
                {0, 0, 0}
        };
        TileMap tileMap = new TileMap(testLevelData);
        List<Collectable> diamonds = tileMap.getDiamonds();
        assertEquals(1, diamonds.size(), "Should load 1 collectable");
    }

    @Test
    void testGetButtons() {
        int[][] levelData = {
                {0, 0, TileType.BUTTON_PINK},
                {0, TileType.BUTTON_GREEN, 0}
        };
        TileMap tileMap = new TileMap(levelData);
        List<GameButton> buttons = tileMap.getButtons();
        assertEquals(2, buttons.size(), "Should load two buttons");
    }

    @Test
    void testGetDoors() {
        int[][] levelData = {
                {0, TileType.DOOR_PINK, 0},
                {TileType.DOOR_GREEN, 0, 0}
        };
        TileMap tileMap = new TileMap(levelData);
        List<Door> doors = tileMap.getDoors();
        assertEquals(2, doors.size(), "Should load two doors");
    }

    @Test
    void testIsSolidTileAt() {
        int[][] levelData = {
                {0, 1, 62}
        };
        TileMap tileMap = new TileMap(levelData);

        // 1 is solid
        assertTrue(tileMap.isSolidTileAt(1 * TILE_SIZE, 0), "Tile at (1, 0) should be solid");

        // 0 is empty
        assertFalse(tileMap.isSolidTileAt(0, 0), "Tile at (0, 0) should not be solid");
    }

    @Test
    void testGetCollectables() {
        int[][] levelData = {
                {TileType.BLUE_DIAMOND, TileType.RED_DIAMOND, TileType.CAT}
        };
        TileMap tileMap = new TileMap(levelData);
        List<Collectable> collectables = tileMap.getDiamonds();
        assertEquals(3, collectables.size(), "Should load three collectables");
    }
}
