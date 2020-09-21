package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

/**
 * Represents an item in the game that Chap can pick up
 *
 * @author Vic
 */
abstract public class Item extends Entity {

  /**
   * Constructor for the Item object.
   * @param position The x,y coordinates of the item
   */
  public Item(Point position) {
    super(position);
  }

  /**
   * Checks that the item can be added to Chap's inventory.
   * @return true if item can be added to Chap's inventory.
   */
  abstract public boolean canBeAddedToInve();

  @Override
  public boolean canBePickedUp() { return true; }
}
