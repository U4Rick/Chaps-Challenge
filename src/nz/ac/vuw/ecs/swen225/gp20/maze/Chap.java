package nz.ac.vuw.ecs.swen225.gp20.maze;

//importing libraries needed
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import java.awt.Point;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents Chap who is the hero of the game. Chap can move, interact with entities and is controlled by the player.
 *
 * @author Vic
 */
public class Chap extends Entity {
  private Set<Key> inventory; //stores the objects that Chap has

  /**
   * Constructor for the Chap object.
   * @param chapsLocation Where Chap is located on the board.
   */
  public Chap(Point chapsLocation) {
    super(chapsLocation);
    inventory = new HashSet<>();
  }

  /**
   * Checks if door can be unlocked by Chap and if so unlocks the door
   * @param door  Door that is unlocked
   */
  public void unlockDoor(Tile door) {
    //check if have correct key for door
    for(Key key : inventory) {
      if(key.getKeyColour() == ((DoorTile)door).getDoorColour()) {
        //unlock door
        door = new FreeTile();
      }
    }
  }

  /**
   * For checking if Chap can move to the new tile.
   * @param tile The tile to check if Chap can move into.
   */
  public boolean canMove(Tile tile) {
    return tile.isAccessible();
  }

  /**
   * Adds a key to the inventory
   * @param key Key to add to inventory.
   */
  public void addToInven(Key key) {
    inventory.add(key);
  }

  @Override
  public boolean canBePickedUp() {
    return false;
  }

  public Set<Key> getInventory() { return Collections.unmodifiableSet(inventory);  }

  public String toString() { return "chap"; }
}