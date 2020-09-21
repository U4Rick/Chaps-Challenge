package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents an empty tile in the level.
 */
public class FreeTile extends Accessible {

  @Override
  public boolean isItem() {
    return false;
  }
}