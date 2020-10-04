package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

/**
 * Represents an empty tile in the level.
 */
public class FreeTile extends AccessibleTile {

  @Override
  public boolean isItem() {
    return false;
  }

  public String toString() { return "free_tile"; }
}