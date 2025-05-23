package cz.fel.cvut.pjv.semestral.controller;

import cz.fel.cvut.pjv.semestral.view.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * The main class for the game application.
 * It initializes the JavaFX application and sets up the main menu scene.
 */
public class GameApplication extends Application {
    private static final Logger log = Logger.getLogger(GameApplication.class.getName());

    /**
     * The start method is the main entry point for JavaFX applications.
     * It sets up the primary stage and shows the main menu scene.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        MenuScene menuScene = new MenuScene(stage);
        stage.setScene(menuScene);
        stage.setTitle("Fireboy and Watergirl");
        stage.show();
        log.info("Showing menu");

        /*Set the icon for the stage
         Image image = new Image(getClass().getResourceAsStream("/images/Tiles/green_tile.png"));
        stage.getIcons().add(image);
        */
    }

    /**
     * The main method is the entry point for the Java application.
     * It launches the JavaFX application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }
}