package cz.cvut.fel.pjv.tests.ModelTests;

import cz.fel.cvut.pjv.semestral.model.Fluid;
import cz.fel.cvut.pjv.semestral.model.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FluidTest {
    private GraphicsContext gc;
    private Image image;

    @BeforeEach
    void setup() {
        gc = mock(GraphicsContext.class);
        image = mock(Image.class);
    }


    @Test
    void testRender_drawsImageAtCorrectPosition() {
        Fluid fluid = new Fluid(10, 20, image, Fluid.FluidType.LAVA);
        fluid.render(gc);
        verify(gc, times(1)).drawImage(image, 10, 20, Fluid.SIZE, Fluid.SIZE);
    }

    @Test
    void testCollidesWith_playerTouchingFromTop_returnsTrue() {
        Fluid fluid = new Fluid(10, 20, image, Fluid.FluidType.WATER);

        // Test player touching fluid from the top
        // Player at x=0, y=-12, size=64x64, bottom at y=52 (within y+32=52)
        double px = 0, py = -12, pw = Player.WIDTH, ph = Player.HEIGHT;
        boolean result = fluid.collidesWith(px, py, pw, ph);
        assertTrue(result);

        // Test player not touching fluid
        // Player at x=500, y=500, size=64x64, far away
        result = fluid.collidesWith(500, 500, Player.WIDTH, Player.HEIGHT);
        assertFalse(result);
    }

    @Test
    void testCollisionSolid_playerOverlapsTile_returnsTrue() {
        Fluid fluid = new Fluid(10, 20, image, Fluid.FluidType.LAVA);
        boolean result = fluid.collisionSolid(5, 15, 20, 20);
        assertTrue(result);
        result = fluid.collisionSolid(100, 100, 20, 20); // Player far away
        assertFalse(result);
    }
}