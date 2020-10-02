package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

/**
 * Represents the exit tile in the level, when the player enters the tile the level is finished.
 */
public class ExitTile extends AccessibleTile {
  @Override
  public boolean isItem() {
    return false;
  }

  public String toString() { return "exit_tile"; }

  @Override
  public Item getItemHere() {
    return null;
  }
}