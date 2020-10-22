package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

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

  @Override
  public String toString() { return "treasure_tile"; }
}
