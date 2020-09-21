package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccessibleTile;

public class WallTile extends InaccessibleTile {
  @Override
  public boolean isLockedDoor() { return false; }
}