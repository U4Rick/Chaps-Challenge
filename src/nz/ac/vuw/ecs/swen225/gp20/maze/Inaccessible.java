package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents ta tile that the player can walk on
 *
 * @author Vic
 */
abstract public class Inaccessible implements Tile {
  abstract public boolean isLockedDoor();

  @Override
  public boolean isAccessible() { return false; }
}