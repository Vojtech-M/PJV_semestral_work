package cz.fel.cvut.pjv.semestral.model.utils;

/**
 * Interface representing a collidable object in the game.
 * Provides methods to get the position and dimensions of the object,
 * as well as a method to check for collisions with another object.
 */
public interface Collidable {
    double getX();
    double getY();
    double getWidth();
    double getHeight();

    /**
     * Checks if this collidable object collides with another object.
     *
     * @param x      The x-coordinate of the other object.
     * @param y      The y-coordinate of the other object.
     * @param width  The width of the other object.
     * @param height The height of the other object.
     * @return true if there is a collision, false otherwise.
     */
    default boolean collidesWith(double x, double y, double width, double height) {
        return (x < getX() + getWidth() &&
                x + width > getX() &&
                y < getY() + getHeight() &&
                y + height > getY());
    }
}
