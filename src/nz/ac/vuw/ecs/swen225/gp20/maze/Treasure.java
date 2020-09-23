package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

/**
 * Represents a treasure item in the game
 * TODO not currently used, delete later if still not used.
 *
 * @author Vic
 */
public class Treasure extends Item {
  public Treasure(Point treasurePos) {
    super(treasurePos);
  }

  @Override
  public boolean canBeAddedToInve() { return false; }
}
