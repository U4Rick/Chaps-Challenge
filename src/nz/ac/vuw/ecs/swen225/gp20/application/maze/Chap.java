package nz.ac.vuw.ecs.swen225.gp20.application.maze;

//importing libraries needed
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents Chap who is the hero of the game. Chap can move, interact with entities and is controlled by the player.
 *
 * @author Vic
 */
class Chap implements Entity {
  private Point chapsLocation; //stores location of Chap so map doesn't need to keep searching for Chap
  private List<Entity> inventory; //stores the objects that Chap has

  public Chap(Point chapsLocation) {
    this.chapsLocation = chapsLocation;
    inventory = new ArrayList<Entity>();
  }

  /**
   * For checking if Chap can move to the new tile.
   * @param tile The tile to check if Chap can move into.
   */
  private boolean canMove(Tile tile) {
    if(tile.isAccessible()) {
      return true;
    }
    return false;
  }

  public Point getChapsLocation() { return chapsLocation; }
  public void setChapsLocation(Point newLocation) { chapsLocation = newLocation; }
}