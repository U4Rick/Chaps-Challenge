package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents the exit tile in the level, when the player enters this tile the level is finished.
 */
public class ExitTile extends AccessibleTile {
  @Override
  public boolean isItem() {
    return false;
  }

  @Override
  public String toString() { return "exit_tile"; }
}