package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

/**
 * Represents the key item in the game
 *
 * @author Vic
 */
public class Key extends Item {
  private Maze.Colours keyColour;

  /**
   * Constructor for the Key object.
   * @param keyColour The colour of the key, is associated with a door in the level
   * @param keyPos  The x,y coordinates of the key in the level
   */
  public Key(Maze.Colours keyColour, Point keyPos) {
    super(keyPos);
    this.keyColour = keyColour;
  }

  @Override
  public boolean canBeAddedToInve() { return true; }

  /**
   * Gets the colour of this key
   * @return  The colour of this key
   */
  public Maze.Colours getKeyColour() { return keyColour; }

  public String toString() {
    return keyColour.toString().toLowerCase()+"_key_item";
  }
}