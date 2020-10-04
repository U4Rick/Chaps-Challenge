package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.Icon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Represents a tile in the board.
 */
public abstract class Tile extends Icon {
  /**
   * Checks if the player can walk onto this tile.
   * @return true This is always true.
   */
  public abstract boolean isAccessible();
}

