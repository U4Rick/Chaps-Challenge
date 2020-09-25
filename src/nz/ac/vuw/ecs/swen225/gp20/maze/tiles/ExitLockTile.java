package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccessibleTile;

/**
 * Represents the locked exit tile in the level,is only unlocked when the .
 */
public class ExitLockTile extends InaccessibleTile {
  @Override
  public boolean isLockedDoor() {
    return false;
  }

  public String toString() { return "exit_lock_tile"; }
}