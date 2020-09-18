package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

/**
 * Represents an item in the game that Chap can pick up
 *
 * @author Vic
 */
abstract public class Item extends Entity {

  public Item(Point position) {
    super(position);
  }

  abstract public boolean canBeAddedToInve();

  @Override
  public boolean canBePickedUp() { return true; }
}
