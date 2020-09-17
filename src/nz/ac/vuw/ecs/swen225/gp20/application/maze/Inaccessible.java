package nz.ac.vuw.ecs.swen225.gp20.application.maze;

/**
 * Represents ta tile that the player can walk on
 *
 * @author Vic
 */
abstract class Inaccessible implements Tile {
  /**
   * Checks if the player can walk onto this tile.
   * @return false This is always false.
   */
  public boolean isAccessible() {
    return false;
  }
}