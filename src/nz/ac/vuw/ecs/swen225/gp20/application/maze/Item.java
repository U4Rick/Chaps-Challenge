package src.src.nz.ac.vuw.ecs.swen225.gp20.application.maze;

import java.awt.*;

/**
 * Represents an item in the game that Chap can pick up
 *
 * @author Vic
 */
public class Item extends Entity {

  public Item(Point position) {
    super(position);
  }

  public boolean canBePickedUp() {
    return true;
  }
}
