package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents an empty tile in the level.
 */
public class FreeTile extends AccessibleTile {

  @Override
  public boolean isItem() {
    return false;
  }

  @Override
  public String toString() { return "free_tile"; }
}