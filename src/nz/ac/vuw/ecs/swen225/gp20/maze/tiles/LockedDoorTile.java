package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

public class LockedDoorTile extends InaccessibleTile {
  private Maze.Colours doorColour; //the colour of the door, 0 for red, 1 for blue, 2 for yellow

  public LockedDoorTile(Maze.Colours doorColour) {
    this.doorColour = doorColour;
  }

  @Override
  public boolean isLockedDoor() { return true; }

  public Maze.Colours  getDoorColour() { return doorColour; }
}