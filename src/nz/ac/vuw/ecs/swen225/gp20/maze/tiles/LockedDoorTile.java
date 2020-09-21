package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccesibleTile;

public class LockedDoorTile extends InaccessibleTile {
  private int doorColour; //the colour of the door, 0 for red, 1 for blue, 2 for yellow

  public LockedDoor(Maze.Colours doorColour) {
    this.doorColour = doorColour;
  }

  @Override
  public boolean isLockedDoor() { return true; }

  public Maze.Colours  getDoorColour() { return doorColour; }
}