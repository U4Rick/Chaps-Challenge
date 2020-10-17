package nz.ac.vuw.ecs.swen225.gp20.maze.items;

import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;

/**
 * Represents the key item in the game
 *
 * @author Vic
 */
public class Key extends Item {
  private Colour keyColour;

  /**
   * Constructor for the Key object.
   * @param keyColour The colour of the key, is associated with a door in the level
   */
  public Key(Colour keyColour) {
    this.keyColour = keyColour;
  }

  /**
   * Gets the colour of this key
   * @return  The colour of this key
   */
  public Colour getKeyColour() { return keyColour; }

  @Override
  public String toString() {
    return keyColour.toString().toLowerCase()+"_key_item";
  }
}