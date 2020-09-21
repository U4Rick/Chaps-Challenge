package nz.ac.vuw.ecs.swen225.gp20.maze;

public class LockedDoor extends Inaccessible {
  private int doorColour; //the colour of the door, 0 for red, 1 for blue, 2 for yellow

  public LockedDoor(int doorColour) {
    this.doorColour = doorColour;
  }

  @Override
  public boolean isLockedDoor() { return true; }

  public int getDoorColour() { return doorColour; }
}