package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

//importing libraries needed
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
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
  private Set<Key> keyInventory; //stores the objects that Chap has

  /**
   * Constructor for the Chap object.
   * @param chapsLocation Where Chap is located on the board.
   */
  public Chap(Point chapsLocation) {
    super(chapsLocation);
    keyInventory = new HashSet<>();
  }



  /**
   * Checks if door can be unlocked by Chap and if so unlocks the door
   * @param door  Door that is unlocked
   */
  public void unlockDoor(Tile door) {
    assert(door instanceof DoorTile);

    //check if have correct key for door
    for(Key key : keyInventory) {
      if(key.getKeyColour() == ((DoorTile)door).getDoorColour()) {
        //unlock door
        door = new FreeTile();
        break;
      }
    }
  }

  /**
   * Adds a key to the inventory
   * @param key Key to add to inventory.
   */
  public void addToKeyInven(Key key) {
    assert(key != null);
    keyInventory.add(key);
  }

  /**
   * Gets the key inventory from Chap.
   * @return Key inventory from Chap.
   */
  public Set<Key> getKeyInventory() {
    return Collections.unmodifiableSet(keyInventory);
  }

  @Override
  public final String toString() { return "chap"; }
}