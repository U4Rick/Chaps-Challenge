package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

/**
 * Represents the tile in the game that contains a key.
 *
 * @author Vic
 */
public class KeyTile extends AccessibleTile {
  private Colour keyColour;

  /**
   * Constructor for key tile.
   * @param keyColour The colour associated with this door, key to unlock this door will have the same colour.
   */
  public KeyTile(Colour keyColour) {
    this.keyColour = keyColour;
  }

  @Override
  public boolean isItem() {
    return true;
  }

  public Colour getKeyColour() { return keyColour; }

  @Override
  public Item getItemHere() {
    return new Key(keyColour);
  }

  @Override
  public String toString() { return keyColour.toString().toLowerCase()+"_key_tile"; }
}
