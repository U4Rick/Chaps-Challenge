package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;

/**
 * Represents a tile that is a locked door that the player cannot go through
 *
 * @author Vic
 */
public class DoorTile extends InaccessibleTile {
  private Colour doorColour; //the colour of the door, 0 for red, 1 for blue, 2 for yellow

  /**
   * Constructor for DoorTile.
   * @param doorColour The colour of the door, this colour is used for doors and keys.
   */
  public DoorTile(Colour doorColour) {
    this.doorColour = doorColour;
  }

  @Override
  public boolean isLockedDoor() { return true; }

  /**
   * Gets the colour of the door, associated with a key on the level.
   * @return The colour of the door.
   */
  public final Colour getDoorColour() { return doorColour; }

  @Override
  public String toString() { return doorColour.toString().toLowerCase()+"_door_tile"; }
}