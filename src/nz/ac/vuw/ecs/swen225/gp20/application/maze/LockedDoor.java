package src.src.nz.ac.vuw.ecs.swen225.gp20.application.maze;

class LockedDoor extends Inaccessible {
  int doorColour; //the colour of the door, 0 for red, 1 for blue, 2 for yellow

  LockedDoor(int doorColour) {
    this.doorColour = doorColour;
  }
}