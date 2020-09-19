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
public class Maze {
  public enum Direction {
    UP, DOWN, LEFT, RIGHT;
  }

  private List<List<Tile>> board; //2d array of tiles,
  private Point exitLocation; //where exit is located at on the map
  private Chap chap;  //it's Chap!

  private final int TREASURES_NUM;
  private int treasuresPickedUp = 0;

  /**
   * Constructs new map, parameter is the data parsed from the level files
   */
  public Maze(Point exitLocation, int treasuresNum) {
    this.exitLocation = exitLocation;
    TREASURES_NUM = treasuresNum;
    board = new ArrayList<>();
  }

  /**
   * Moves Chap in the direction stated by one tile on the board.
   *
   * @param direction Represents the direction to move Chap
   * @throws ArrayIndexOutOfBoundsException if going to new direction will cause Chap to go out of bounds, will throw an ArrayIndexOutOfBoundsException.
   */
  public void moveChap(Direction direction) throws ArrayIndexOutOfBoundsException {
    Point chapLocation = chap.getEntityPosition();
    //get new position to move Chap to
    Point position;
    switch(direction) {
      case UP:
        position = new Point(chapLocation.x, chapLocation.y-1);
        break;
      case DOWN:
        position = new Point(chapLocation.x, chapLocation.y+1);
        break;
      case LEFT:
        position = new Point(chapLocation.x-1, chapLocation.y);
        break;
      default:  //move right
        position = new Point(chapLocation.x+1, chapLocation.y);
    }

    //check that new position is in the bounds of the board, if not throw exception
    if(position.x >= board.size() || position.y >= board.get(0).size()) {
      throw new ArrayIndexOutOfBoundsException();
    }


    if(!chap.canMove(board.get(position.x).get(position.y))) {
      Inaccessible tile = (Inaccessible)board.get(position.x).get(position.y);
      if(tile.isLockedDoor()) { //check that tile is a locked door
        chap.unlockDoor(tile);
      }
    }

    else {    //if Chap can move onto tile
      Accessible accessibleTile = (Accessible)board.get(position.x).get(position.y);

      //pick up item on tile if is an item tile
      if(accessibleTile.isItem()) {
        pickUpItem(accessibleTile);
      }

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