package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents the locked exit tile in the level,is only unlocked when the .
 */
public class ExitLocked extends Inaccessible {
  @Override
  public boolean isLockedDoor() {
    return false;
  }
}