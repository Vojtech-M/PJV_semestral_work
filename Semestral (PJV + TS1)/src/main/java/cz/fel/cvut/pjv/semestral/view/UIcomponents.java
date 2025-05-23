package cz.fel.cvut.pjv.semestral.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.InputStream;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_HEIGHT;
import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.SCREEN_WIDTH;

/**
 * UIcomponents class provides utility methods for positioning UI elements and loading images.
 * This class contains static methods that can be used to position buttons in the center of the screen
 * and to load images from resources.
 */
public class UIcomponents {

    public static void positionElementCenter(Button button, double offsetY) {
        button.setLayoutX(SCREEN_WIDTH / 2 - 100);
        button.setLayoutY(SCREEN_HEIGHT / 2 + offsetY);
    }

    public static Image loadImage(String path) {
        InputStream stream = UIcomponents.class.getResourceAsStream(path);
        return (stream != null) ? new Image(stream) : null;
    }
}