package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents ta tile that the player can walk on
 *
 * @author Vic
 */
abstract public class InaccessibleTile implements Tile {
  /**
   * Checks if the tile is a locked door.
   * @return true if the tile is a locked door.
   */
  abstract public boolean isLockedDoor();

  @Override
  public boolean isAccessible() { return false; }
}