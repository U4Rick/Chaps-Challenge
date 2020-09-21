package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccesibleTile;

public class WallTile extends InaccesibleTile {
  @Override
  public boolean isLockedDoor() { return false; }
}