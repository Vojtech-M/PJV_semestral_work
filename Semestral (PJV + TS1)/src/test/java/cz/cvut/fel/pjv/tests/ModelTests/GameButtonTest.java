package cz.cvut.fel.pjv.tests.ModelTests;
import cz.fel.cvut.pjv.semestral.controller.GameApplication;
import cz.fel.cvut.pjv.semestral.model.Door;
import cz.fel.cvut.pjv.semestral.model.GameButton;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameButtonTest {
    private Logger logger;

    @Test
    void testPressToggle() {
        // Create a mock Door
        List<Door> doors = new ArrayList<>();
        Door door = mock(Door.class);
        when(door.getDoorID()).thenReturn(1);
        when(door.isOpen()).thenReturn(false); // Initially closed
        doors.add(door);

        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);

        // First press
        button.press(doors);
        verify(door, times(1)).open();
        verify(door, never()).close();
        when(door.isOpen()).thenReturn(true);

        // Second press
        button.press(doors);
        verify(door, times(1)).close();
    }

    @Test
    void testIsPlayerNearby() {
        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);

        // is nearby
        assertTrue(button.isPlayerNearby(5, 15, 10, 10));
        assertTrue(button.isPlayerNearby(15, 25, 10, 10));

        // is not nearby
        assertFalse(button.isPlayerNearby(0, 0, 10, 10));
        assertFalse(button.isPlayerNearby(100, 100, 10, 10));
    }
    @Test
    void testRender() {
        GraphicsContext gc = Mockito.mock(GraphicsContext.class);

        Image defaultImage = mock(Image.class);
        Image pressedImage = mock(Image.class);
        GameButton button = new GameButton(10, 20, defaultImage, pressedImage, "Test Button", 1);

        button.render(gc);
        verify(gc).drawImage(defaultImage, 10, 20, GameButton.SIZE, GameButton.SIZE);

        button.press(new ArrayList<>()); // Activate the button
        button.render(gc);
        verify(gc).drawImage(pressedImage, 10, 20, GameButton.SIZE, GameButton.SIZE);
    }
    @Test
    void testPressWithNoDoors() {
        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);
        button.press(new ArrayList<>());

        assertTrue(button.isActivated());
    }
    @Test
    void testPressWithNoImage() {
        GraphicsContext gc = Mockito.mock(GraphicsContext.class);
        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);

        button.render(gc);

        // Verify that no image is drawn
        verify(gc, never()).drawImage(any(Image.class), anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }
    @Test
    void testIsActivated() {
        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);

        assertFalse(button.isActivated());

        button.press(new ArrayList<>());
        assertTrue(button.isActivated());

        button.press(new ArrayList<>());
        assertFalse(button.isActivated());
    }

    @Test
    void testPressWithMultipleDoors() {
        List<Door> doors = new ArrayList<>();
        Door door1 = mock(Door.class);
        when(door1.getDoorID()).thenReturn(1);
        when(door1.isOpen()).thenReturn(false); // Initially closed
        doors.add(door1);

        Door door2 = mock(Door.class);
        when(door2.getDoorID()).thenReturn(2);
        when(door2.isOpen()).thenReturn(false); // Initially closed
        doors.add(door2);

        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);

        // Press the button
        button.press(doors);

        // Verify that only the correct door is opened
        verify(door1, times(1)).open();
        verify(door2, never()).open();
    }
    @Test
    void testPressWithDoorNotFound() {
        List<Door> doors = new ArrayList<>();
        Door door = mock(Door.class);
        when(door.getDoorID()).thenReturn(2); // Different ID
        when(door.isOpen()).thenReturn(false); // Initially closed
        doors.add(door);

        GameButton button = new GameButton(10, 20, null, null, "Test Button", 1);

        // Press the button
        button.press(doors);

        // Verify that the door is not opened
        verify(door, never()).open();
    }

}