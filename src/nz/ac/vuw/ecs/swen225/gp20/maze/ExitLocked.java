package nz.ac.vuw.ecs.swen225.gp20.maze;

class ExitLocked extends Inaccessible {
  @Override
  public boolean isLockedDoor() {
    return false;
  }
}