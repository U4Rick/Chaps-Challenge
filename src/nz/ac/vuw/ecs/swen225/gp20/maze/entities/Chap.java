package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

//importing libraries needed
import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.DoorTile;

import java.awt.Point;
import java.util.*;

/**
 * Represents Chap who is the hero of the game. Chap can move, interact with entities and is controlled by the player.
 *
 * @author Vic
 */
public class Chap extends Entity {
  private Map<Colour, Integer> keyInventory; //stores the objects that Chap has, keys are the key part and number of keys are the value part

  /**
   * Constructor for the Chap object.
   * @param chapsLocation Where Chap is located on the board.
   */
  public Chap(Point chapsLocation) {
    super(chapsLocation);
    keyInventory = new HashMap<>();

    //add all the possible keys
    for(Colour colour : Colour.values()) {
      keyInventory.put(colour, 0);
    }
  }



  /**
   * Checks if door can be unlocked by Chap and if so unlocks the door
   * @param location  x,y coordinates of door.
   * @param maze The level in this maze.
   */
  public boolean unlockDoor(Point location, Maze maze) {
    Preconditions.checkArgument(maze.getBoard()[location.x][location.y] instanceof DoorTile);

    //check if have correct key for door
    Colour colour = ((DoorTile)maze.getBoard()[location.x][location.y]).getDoorColour();
    //check that have more than zero keys to unlock door
    if(keyInventory.get(colour) > 0) {
      //unlock door
      maze.getBoard()[location.x][location.y] = new FreeTile();
      int originalValue = keyInventory.get(colour);
      keyInventory.replace(colour, keyInventory.get(colour) - 1);  //remove key
      assert(keyInventory.get(colour) >= 0); //check that there are not less than 0 keys
      assert (keyInventory.get(colour) == originalValue-1); //check that number of keys is smaller by one
      return true;
    }
    return false;
  }

  /**
   * Adds a key to the inventory
   * @param colour Key to add to inventory.
   */
  public void addToKeyInven(Colour colour) {
    keyInventory.replace(colour, keyInventory.get(colour)+1);  //add key
  }

  /**
   * Gets the key inventory from Chap.
   * @return Key inventory from Chap.
   */
  public Map<Colour, Integer> getKeyInventory() {
    return Collections.unmodifiableMap(keyInventory);
  }

  @Override
  public final String toString() { return "chap"; }
}