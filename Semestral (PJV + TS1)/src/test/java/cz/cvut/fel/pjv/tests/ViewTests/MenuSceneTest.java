package cz.cvut.fel.pjv.tests.ViewTests;

import cz.fel.cvut.pjv.semestral.model.utils.Sounds;
import cz.fel.cvut.pjv.semestral.view.LevelScene;
import cz.fel.cvut.pjv.semestral.view.MenuScene;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_HEIGHT;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_WIDTH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuSceneTest {

    @Mock
    private Stage stage;

    private MenuScene menuScene;
    private Pane root;

    //non window start of JavaFX
    @BeforeAll
    static void initJavaFX() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await();
    }

    @BeforeEach
    void setUp() {
        // Run setup on JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                menuScene = new MenuScene(stage);
                root = (Pane) menuScene.getRoot();
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted during setup");
        }
    }

    @Test
    void testSceneInitialization() {
        // Verify scene dimensions
        assertEquals(SCREEN_WIDTH, menuScene.getWidth(), "Scene width should match SCREEN_WIDTH");
        assertEquals(SCREEN_HEIGHT, menuScene.getHeight(), "Scene height should match SCREEN_HEIGHT");
        assertTrue(menuScene.getRoot() instanceof Pane, "Root should be a Pane");

        // Verify CSS
        assertTrue(menuScene.getStylesheets().contains(
                        MenuScene.class.getResource("/styles/menuStyles.css").toExternalForm()),
                "Stylesheet should be added");
        assertTrue(root.getStyleClass().contains("menu-pane"), "Root should have menu-pane style class");

        // Verify number of elements in root
        assertEquals(3, root.getChildren().size(), "Root should have exactly 3 children (Label and Buttons)");

        // Find Label
        Label label = root.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .findFirst()
                .orElse(null);
        assertNotNull(label, "Label should be present");
        assertEquals("Press Play to start", label.getText(), "Label text should be correct");
        assertTrue(label.getStyleClass().contains("menu-label"), "Label should have menu-label style class");
        assertEquals(SCREEN_WIDTH / 2 - 150, label.getLayoutX(), "Label X position should be centered");
        assertEquals(SCREEN_HEIGHT / 2 - 50, label.getLayoutY(), "Label Y position should be correct");

        // Find Play button
        Button playButton = root.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .filter(btn -> "Play".equals(btn.getText()))
                .findFirst()
                .orElse(null);
        assertNotNull(playButton, "Play button should be present");
        assertTrue(playButton.getStyleClass().contains("menu-button"), "Play button should have menu-button style class");
        assertEquals(SCREEN_WIDTH / 2 - 50, playButton.getLayoutX(), "Play button X position should be centered");
        assertEquals(SCREEN_HEIGHT / 2 + 20, playButton.getLayoutY(), "Play button Y position should be correct");

        // Find Exit button
        Button exitButton = root.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .filter(btn -> "Exit".equals(btn.getText()))
                .findFirst()
                .orElse(null);
        assertNotNull(exitButton, "Exit button should be present");
        assertTrue(exitButton.getStyleClass().contains("menu-button"), "Exit button should have menu-button style class");
        assertEquals(SCREEN_WIDTH / 2 - 50, exitButton.getLayoutX(), "Exit button X position should be centered");
        assertEquals(SCREEN_HEIGHT / 2 + 250, exitButton.getLayoutY(), "Exit button Y position should be correct");
    }

    @Test
    void testLoggerInitialization() {
        Logger logger = menuScene.getLogger();
        assertNotNull(logger, "Logger should be initialized");
        assertEquals(MenuScene.class.getName(), logger.getName(), "Logger name should match class name");
    }

}