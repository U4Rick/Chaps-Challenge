package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

/**
 * Represents the tile in the game that contains a key.
 *
 * @author Vic
 */
public class KeyTile extends AccessibleTile {
  private Maze.Colours keyColour;

  /**
   * Constructor for key tile.
   * @param keyColour The colour associated with this door, key to unlock this door will have the same colour.
   */
  public KeyTile(Maze.Colours keyColour) {
    this.keyColour = keyColour;
  }

  @Override
  public boolean isItem() {
    return true;
  }

  public Maze.Colours getKeyColour() { return keyColour; }

  public String toString() { return keyColour.toString().toLowerCase()+"_key_tile"; }
}
