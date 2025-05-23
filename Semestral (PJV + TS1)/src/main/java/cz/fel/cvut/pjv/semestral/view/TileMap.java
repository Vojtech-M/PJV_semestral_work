package cz.fel.cvut.pjv.semestral.view;

import cz.fel.cvut.pjv.semestral.model.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static cz.fel.cvut.pjv.semestral.model.utils.Constants.GameConstants.TILE_SIZE;
import static cz.fel.cvut.pjv.semestral.view.UIcomponents.loadImage;

import cz.fel.cvut.pjv.semestral.view.TileType;
import lombok.Getter;


/**
 * The TileMap class represents the game level and handles the rendering of tiles, collectables, buttons, doors, and fluids.
 * It also manages the loading of images and interactive elements in the game.
 */
@Getter
public class TileMap {
    private final int[][] levelData;
    public Image solidTileImage;
    private Image platformTileImage;
    private Image blueDiamondImage;
    private Image redDiamondImage;
    private Image doorImage;
    private Image floorButtonImage;
    private Image floorButtonPressedImage;
    private Image doorOpenedImage;
    private Image greenDoorImage;
    private Image floorButtonImagePink;

    private Image redEndImage;
    private Image lavaTileImage;
    private Image waterTileImage;
    private Image backgroundImage;
    private Image blueEndImage;
    private Image catImage;

    private Image greenDoorOpenedImage;

    private final List<Collectable> diamonds = new ArrayList<>();
    private final List<GameButton> buttons = new ArrayList<>();
    private final List<Door> doors = new ArrayList<>();
    private final List<LevelEnd> levelEnds = new ArrayList<>();
    private final List<Fluid> fluids = new ArrayList<>();

    /**
     * Constructor for the TileMap class.
     * This class represents the game level and handles the rendering of tiles, collectables, buttons, doors, and fluids.
     *
     * @param levelData The 2D array representing the level data.
     */
    public TileMap(int[][] levelData) {
        this.levelData = levelData;

        loadImages();
        loadInteractive();
    }

    /**
     * Loads the images for the tiles, collectables, buttons, doors, and fluids.
     * This method is called in the constructor to load all necessary images for the game.
     */
    private void loadImages() {
        // Load textures of Tiles
        solidTileImage = loadImage("/images/Tiles/green_tile.png");
        platformTileImage = loadImage("/images/platform_tile.png");
        doorImage = loadImage("/images/DoorsAndButtons/PinkDoorClosed.png");
        doorOpenedImage = loadImage("/images/DoorsAndButtons/PinkDoorOpened.png");
        greenDoorImage = loadImage("/images/DoorsAndButtons/GreenDoorClosed.png");
        greenDoorOpenedImage = loadImage("/images/DoorsAndButtons/GreenDoorOpened.png");

        //collectables
        blueDiamondImage = loadImage("/images/Collectables/BlueDiamond2.png");
        redDiamondImage = loadImage("/images/Collectables/red_diamond_44.png");
        catImage = loadImage("/images/Collectables/cat.png");

        //interactive
        floorButtonImage = loadImage("/images/DoorsAndButtons/floor_button.png");
        floorButtonImagePink = loadImage("/images/DoorsAndButtons/floor_button_pink.png");
        floorButtonPressedImage = loadImage("/images/DoorsAndButtons/red_button.png");
        lavaTileImage = loadImage("/images/Tiles/lavaTile.png");
        waterTileImage = loadImage("/images/Tiles/water_tile.png");
        redEndImage = loadImage("/images/Tiles/LevelEndPurple.png");
        blueEndImage = loadImage("/images/Tiles/blue_end.png");

        backgroundImage = loadImage("/images/Scenes/testTemple.png");

    }

    /**
     * Loads the interactive elements (collectables, buttons, doors, and fluids) based on the level data.
     * This method iterates through the level data and creates the corresponding objects for each tile type.
     */
    private void loadInteractive() {
        for (int row = 0; row < levelData.length; row++) {
            for (int col = 0; col < levelData[row].length; col++) {
                int tile = levelData[row][col];
                double x = col * TILE_SIZE;
                double y = row * TILE_SIZE;

                switch (tile) {
                    case cz.fel.cvut.pjv.semestral.view.TileType.BLUE_DIAMOND:
                        diamonds.add(new Collectable(x, y, blueDiamondImage, "blue"));
                        break;
                    case cz.fel.cvut.pjv.semestral.view.TileType.RED_DIAMOND:
                        diamonds.add(new Collectable(x, y, redDiamondImage, "red"));
                        break;
                    case TileType.CAT:
                        diamonds.add(new Collectable(x, y, catImage, "any"));
                        break;
                    case TileType.DOOR_PINK:
                        doors.add(new Door(x, y, doorImage, doorOpenedImage, "pink", 1));
                        break;
                    case TileType.BUTTON_PINK:
                        buttons.add(new GameButton(x, y, floorButtonImagePink, floorButtonPressedImage, "FloorButtonPink", 1));
                        break;
                    case TileType.DOOR_GREEN:
                        doors.add(new Door(x, y, greenDoorImage, greenDoorOpenedImage, "green", 2));
                        break;
                    case TileType.BUTTON_GREEN:
                        buttons.add(new GameButton(x, y, floorButtonImage, floorButtonPressedImage, "FloorButtonGreen", 2));
                        break;
                    case TileType.RED_END:
                        levelEnds.add(new LevelEnd(x, y, redEndImage));
                        break;
                    case TileType.BLUE_END:
                        levelEnds.add(new LevelEnd(x, y, blueEndImage));
                        break;
                    case TileType.LAVA:
                        fluids.add(new Fluid(x, y, lavaTileImage, Fluid.FluidType.LAVA));
                        break;
                    case TileType.WATER:
                        fluids.add(new Fluid(x, y, waterTileImage, Fluid.FluidType.WATER));
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * Checks if the tile at the given coordinates is solid.
     * This method checks if the tile is solid and also checks for collisions with doors and fluids.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return true if the tile is solid, false otherwise.
     */
    public boolean isSolidTileAt(double x, double y) {
        int col = (int) (x / TILE_SIZE);
        int row = (int) (y / TILE_SIZE);

        double cord_x = col * TILE_SIZE;
        double cord_y = row * TILE_SIZE;

        if (row < 0 || row >= levelData.length || col < 0 || col >= levelData[0].length) {
            return false;
        }

        boolean isTileSolid = levelData[row][col] == 1;

        for (Door door : doors) {
            if (door.collidesWith(cord_x, cord_y, Player.WIDTH, Player.HEIGHT)) {
                return true;
            }
        }
        for (Fluid fluid : fluids) {
            if (fluid.collisionSolid(cord_x, cord_y, Player.WIDTH, Player.HEIGHT)) {
                return true;
            }
        }
        return isTileSolid;
    }


    /**
     * Renders the tile map on the given GraphicsContext.
     * This method draws the background, tiles, collectables, buttons, doors, and fluids on the canvas.
     *
     * @param gc The GraphicsContext to render on.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(backgroundImage, 0, 0, levelData[0].length * TILE_SIZE, levelData.length * TILE_SIZE);
        for (int row = 0; row < levelData.length; row++) {
            for (int col = 0; col < levelData[row].length; col++) {
                int tile = levelData[row][col];
                double x = col * TILE_SIZE;
                double y = row * TILE_SIZE;

                if (tile == 1 && solidTileImage != null) {
                    gc.drawImage(solidTileImage, x, y);
                } else if (tile == 2 && platformTileImage != null) {
                    gc.drawImage(platformTileImage, x, y);
                }
            }
        }

        for (Collectable d : diamonds) {
            d.render(gc);
        }
        for (GameButton button : buttons) {
            button.render(gc);
        }
        for (Door door : doors) {
            door.render(gc);
        }
        for (LevelEnd levelEnd : levelEnds) {
            levelEnd.render(gc);
        }
        for (Fluid fluid : fluids) {
            fluid.render(gc);
        }
    }
}
