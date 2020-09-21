package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

public class InfoTile extends AccessibleTile {

  String information; //information that stored in the infoField tile

  @Override
  public boolean isItem() {
    return false;
  }
}