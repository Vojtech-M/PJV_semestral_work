package cz.cvut.fel.pjv.tests.ModelTests;

import cz.fel.cvut.pjv.semestral.model.Collectable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CollectableTest {

    private Collectable collectable;
    private GraphicsContext graphicsContext;
    private Image collectableImage;

    // Mock dependencies
    @BeforeEach
    public void setUp() {
        graphicsContext = mock(GraphicsContext.class);
        collectableImage = mock(Image.class);
        collectable = new Collectable(50.0, 100.0, collectableImage, "red");
    }

    // Test initial values, not collected
    @Test
    public void testCollectableInitialization() {
        assertEquals(50.0, collectable.getX());
        assertEquals(100.0, collectable.getY());
        assertEquals("red", collectable.getType());
        assertFalse(collectable.isCollected());
    }

    @Test
    public void testCollectMethod() {
        collectable.collect();
        assertTrue(collectable.isCollected());
    }

    @Test
    public void testRenderWhenNotCollected() {
        collectable.render(graphicsContext);
        verify(graphicsContext).drawImage(collectableImage, 50.0, 100.0, TILE_SIZE, TILE_SIZE);
    }

    @Test
    public void testRenderWhenCollected() {
        collectable.collect();
        collectable.render(graphicsContext);
        verify(graphicsContext, never()).drawImage(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }
}
