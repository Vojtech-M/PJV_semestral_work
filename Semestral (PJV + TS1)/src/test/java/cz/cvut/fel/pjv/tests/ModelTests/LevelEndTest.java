package cz.cvut.fel.pjv.tests.ModelTests;

import cz.fel.cvut.pjv.semestral.model.LevelEnd;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.PlayerConstants.HEIGHT;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.PlayerConstants.WIDTH;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LevelEndTest {

    private LevelEnd levelEnd;
    private GraphicsContext graphicsContext;
    private Image levelEndImage;

    @BeforeEach
    public void setUp() {
        levelEndImage = mock(Image.class);
        levelEnd = new LevelEnd(100.0, 150.0, levelEndImage);
        graphicsContext = mock(GraphicsContext.class);
    }

    @Test
    public void testRender() {
        levelEnd.render(graphicsContext);
        verify(graphicsContext).drawImage(levelEndImage, 100.0, 150.0, LevelEnd.SIZE, LevelEnd.SIZE);
    }

    @Test
    public void testRenderWhenImageIsNull() {
        LevelEnd levelEndWithoutImage = new LevelEnd(100.0, 150.0, null);
        levelEndWithoutImage.render(graphicsContext);
    }

    @Test
    public void testIsPlayerNearby() {
        boolean isNearby = levelEnd.isPlayerNearby(110.0, 160.0, WIDTH, HEIGHT); // Player is near
        assertTrue(isNearby);

        boolean isNotNearby = levelEnd.isPlayerNearby(200.0, 250.0, WIDTH, HEIGHT); // Player is far
        assertFalse(isNotNearby);
    }

}
