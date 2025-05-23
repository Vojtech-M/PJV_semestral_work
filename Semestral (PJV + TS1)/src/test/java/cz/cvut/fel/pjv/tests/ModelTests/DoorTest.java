package cz.cvut.fel.pjv.tests.ModelTests;

import cz.fel.cvut.pjv.semestral.model.Door;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {

    private Door door;
    private GraphicsContext graphicsContext;
    private Image doorImage;
    private Image openedImage;

    @BeforeEach
    public void setUp() {
        doorImage = mock(Image.class);
        openedImage = mock(Image.class);
        door = new Door(100.0, 150.0, doorImage, openedImage, "red", 1);
        graphicsContext = mock(GraphicsContext.class);
    }

    @Test
    public void testInitialState() {
        assertEquals(100.0, door.getX());
        assertEquals(150.0, door.getY());
        assertEquals("red", door.getType());
        assertFalse(door.isOpen());
        assertEquals(1, door.getDoorID());
    }

    @Test
    public void testOpenDoor() {
        door.open();
        assertTrue(door.isOpen());
    }

    @Test
    public void testCloseDoor() {
        door.open();
        door.close();
        assertFalse(door.isOpen());
    }

    @Test
    public void testRenderWhenClosed() {
        door.render(graphicsContext);
        verify(graphicsContext).drawImage(doorImage, 100.0, 150.0, TILE_SIZE, TILE_SIZE);
    }

    @Test
    public void testRenderWhenOpened() {
        door.open();
        door.render(graphicsContext);
        verify(graphicsContext).drawImage(openedImage, 100.0, 150.0, TILE_SIZE, TILE_SIZE);
    }

    @Test
    public void testCollidesWithWhenClosed() {
        boolean collision = door.collidesWith(110.0, 160.0, TILE_SIZE, TILE_SIZE);
        assertTrue(collision);
    }

    @Test
    public void testCollidesWithWhenOpen() {
        door.open();
        boolean collision = door.collidesWith(110.0, 160.0, TILE_SIZE, TILE_SIZE);
        assertFalse(collision);
    }
}
