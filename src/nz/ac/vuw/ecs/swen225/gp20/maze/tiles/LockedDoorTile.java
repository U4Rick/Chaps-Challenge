package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccesibleTile;

public class LockedDoorTile extends InaccesibleTile {
  private int doorColour; //the colour of the door, 0 for red, 1 for blue, 2 for yellow

  public LockedDoorTile(int doorColour) {
    this.doorColour = doorColour;
  }

  @Override
  public boolean isLockedDoor() { return true; }

  public int getDoorColour() { return doorColour; }
}