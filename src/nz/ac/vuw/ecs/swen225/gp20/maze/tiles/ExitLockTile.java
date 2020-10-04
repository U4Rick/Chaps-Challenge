package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccessibleTile;

/**
 * Represents the locked exit tile in the level,is only unlocked when Chap moves to the door while holding a key with the same colour.
 */
public class ExitLockTile extends InaccessibleTile {
  @Override
  public boolean isLockedDoor() {
    return false;
  }

  @Override
  public String toString() { return "exit_lock_tile"; }
}