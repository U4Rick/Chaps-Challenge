package nz.ac.vuw.ecs.swen225.gp20.maze;

//importing libraries needed
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the map used in the game for each level.
 *
 * @author Vic
 */
class Map {
  private List<List<Tile>> board; //2d array of tiles,
  private Point exitLocation; //where exit is located at on the map
  private Chap chap;  //it's Chap!

  private final int TREASURES_NUM;
  private int treasuresPickedUp = 0;

  /**
   * Constructs new map, parameter is the data parsed from the level files
   */
  public Map(Point exitLocation, int treasuresNum) {
    this.exitLocation = exitLocation;
    TREASURES_NUM = treasuresNum;
    board = new ArrayList<>();
  }

  /**
   * Moves Chap into the new position on the board.
   *
   * TO ADD: adds enum for direction instead point for parameter
   *
   * @param position  Represents the new position to move Chap to.
   */
  public void moveChap(Point position) {
    if(!chap.canMove(board.get(position.x).get(position.y))) {
      Inaccessible tile = (Inaccessible)board.get(position.x).get(position.y);
      if(tile.isLockedDoor()) { //check that tile is a locked door
        chap.unlockDoor(tile);
      }
    }

    else {    //if Chap can move onto tile
      Accessible accessibleTile = (Accessible)board.get(position.x).get(position.y);
      Point chapLocation = chap.getEntityPosition();

      //pick up item on free tile if item exists
      pickUpItem(accessibleTile);

      //reassign Chap to new tile
      ((Accessible)board.get(chapLocation.x).get(chapLocation.y)).setEntityHere(null);
      (accessibleTile).setEntityHere(chap);
      chap.setEntityPosition(new Point(position));  //to keep track of Chap's location
    }
  }

  /**
   * For dealing with logic of picking up an item
   * @param accessibleTile
   */
  private void pickUpItem(Accessible accessibleTile) {
    Entity item = accessibleTile.getEntityHere();
    if(item != null) {
      if(item.canBePickedUp()) {  //check if can be picked up
        Item tileItem = (Item)item;
        if(tileItem.canBeAddedToInve()) {  //check if can be added to inventory
          chap.addToInven((Item) item);
        } else {  //if can't be added to inventory then is treasure
          treasuresPickedUp++;
          if(treasuresPickedUp == TREASURES_NUM) {  //check if picked up all the treasures to unlock the exit
            board.get(exitLocation.x).set(exitLocation.y, new Exit());
          }
        }
        accessibleTile.setEntityHere(null); //set tile's item to null since item is picked up
      }
    }
  }

}