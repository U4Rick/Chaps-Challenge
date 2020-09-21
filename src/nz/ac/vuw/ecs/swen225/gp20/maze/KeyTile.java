package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents the tile in the game that contains a key
 *
 * @author Vic
 */
public class KeyTile extends Accessible  {
  private Maze.Colours keyColour;

  @Override
  public boolean isItem() {
    return true;
  }
}
