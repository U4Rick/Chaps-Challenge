package nz.ac.vuw.ecs.swen225.gp20.maze;

public class KeyTile extends Accessible  {
  private Maze.Colours keyColour;

  @Override
  public boolean isItem() {
    return true;
  }
}
