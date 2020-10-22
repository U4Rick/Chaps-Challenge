package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents the wall tile in the level.
 *
 * @author Vic
 */
public class WallTile extends InaccessibleTile {
  @Override
  public boolean isLockedDoor() { return false; }

  @Override
  public String toString() { return "wall_tile"; }
}