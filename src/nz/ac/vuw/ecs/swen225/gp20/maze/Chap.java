package nz.ac.vuw.ecs.swen225.gp20.maze;

//importing libraries needed
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccesibleTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.LockedDoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import java.awt.Point;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents Chap who is the hero of the game. Chap can move, interact with entities and is controlled by the player.
 *
 * @author Vic
 */
public class Chap extends Entity {
  private Set<Item> inventory; //stores the objects that Chap has

  public Chap(Point chapsLocation) {
    super(chapsLocation);
    inventory = new HashSet<Item>();
  }

  /**
   * Checks if door can be unlocked by Chap and if so unlocks the door
   * @param door  Door that is unlocked
   */
  public void unlockDoor(Tile door) {
    //check if have correct key for door
    for(Item item : inventory) {
      //TO DO: SEARCH INVENTORY FOR KEY
/*      if(((Key)item).getKeyColour() == ((LockedDoorTile)(InaccesibleTile) door).getDoorColour()) {
        //unlock door
        door = new FreeTile();
      }*/
    }
  }

  /**
   * For checking if Chap can move to the new tile.
   * @param tile The tile to check if Chap can move into.
   */
  public boolean canMove(Tile tile) {
    if(tile.isAccessible()) {
      return true;
    }
    return false;
  }

  /**
   * Adds an item to the inventory
   * @param item
   */
  public void addToInven(Item item) {
    inventory.add(item);
  }

  @Override
  public boolean canBePickedUp() {
    return false;
  }
}