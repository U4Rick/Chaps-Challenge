package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccessibleTile;

/**
 * Represents the wall tile in the level.
 *
 * @author Vic
 */
public class WallTile extends InaccessibleTile {
  @Override
  public boolean isLockedDoor() { return false; }

  public String toString() { return "walltile"; }
}