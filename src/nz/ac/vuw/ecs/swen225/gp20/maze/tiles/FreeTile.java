package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

public class FreeTile extends AccessibleTile {
  @Override
  public boolean isItem() {
    return false;
  }
}