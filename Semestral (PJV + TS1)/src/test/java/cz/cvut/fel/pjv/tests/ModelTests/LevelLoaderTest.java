package cz.cvut.fel.pjv.tests.ModelTests;

import cz.fel.cvut.pjv.semestral.model.LevelLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class LevelLoaderTest {

    @Test
    void testFileNotFound() {
        LevelLoader levelLoader = mock(LevelLoader.class);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            LevelLoader.loadLevel("invalid/path");
        });
        assertEquals("Failed to load level from invalid/path", thrown.getMessage());
    }

    @Test
    void testLoadLevel() {
        String path = "/levels/levelTest.txt";
        int[][] expectedLevel = {
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        };

        int[][] loadedLevel = LevelLoader.loadLevel(path);
        assertNotNull(loadedLevel);
        assertEquals(expectedLevel.length, loadedLevel.length);
        for (int i = 0; i < expectedLevel.length; i++) {
            assertArrayEquals(expectedLevel[i], loadedLevel[i]);
        }
    }
}
