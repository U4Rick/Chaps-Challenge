package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents the locked exit tile in the level,is only unlocked when Chap moves to the door while holding a key with the same colour.
 */
public class ExitLockTile extends InaccessibleTile {

  @Override
  public String toString() { return "exit_lock_tile"; }
}