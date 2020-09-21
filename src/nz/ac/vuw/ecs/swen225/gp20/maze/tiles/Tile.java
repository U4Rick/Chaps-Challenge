package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents a tile in the board.
 */
public interface Tile {
  /**
   * Checks if the player can walk onto this tile.
   * @return true This is always true.
   */
  public boolean isAccessible();
}

