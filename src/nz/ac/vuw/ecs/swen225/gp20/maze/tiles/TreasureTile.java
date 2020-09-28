package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

/**
 * Represents the tile in the game that contains a treasure
 *
 * @author Vic
 */
public class TreasureTile extends AccessibleTile {
  @Override
  public boolean isItem() {
    return true;
  }

  public String toString() { return "treasure_tile"; }
}
