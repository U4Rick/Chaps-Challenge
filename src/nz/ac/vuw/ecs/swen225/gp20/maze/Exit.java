package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents the exit tile in the level, when the player enters the tile the level is finished.
 */
public class Exit extends AccessibleTile {
  @Override
  public boolean isItem() {
    return false;
  }
}