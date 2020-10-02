package nz.ac.vuw.ecs.swen225.gp20.maze;

//importing libraries needed
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.awt.Point;

//TODO rewrite Javadoc

/**
 * Represents the map used in the game for each level.
 *
 * @author Vic
 */
public class Maze {
  /**
   * Used to represent the direction for the movement of Chap
   */
  public enum Direction {
    UP, DOWN, LEFT, RIGHT
  }

  /**
   * Used to represent the colours of the keys and the doors
   */
  public enum Colours {
    RED, YELLOW, GREEN, BLUE
  }

  private Tile[][] board; //2d array of tiles,
  private Point exitLocation; //where exit is located at on the map
  private Chap chap;  //it's Chap!

  private final int TREASURES_NUM;
  private int treasuresPickedUp = 0;

  /**
   * Constructs the level for the game based from the level data files.
   * @param chapLocation  The initial location of Chap.
   * @param exitLocation  The location of the exit in the level.
   * @param treasuresNum  Number of treasures in the level.
   * @param board The 2d array that represents the board for the level.
   */
  public Maze(Point chapLocation, Point exitLocation, int treasuresNum, Tile[][] board) throws IllegalStateException{
    this.chap = new Chap(chapLocation);
    this.exitLocation = exitLocation;
    TREASURES_NUM = treasuresNum;
    this.board = board;

    //set tile's entity at chap pos to chap
    //TODO for code contracts, could violate condition of Chap not allowed in inaccessible tiles
    if(board[chapLocation.x][chapLocation.y].isAccessible()) {
      ((AccessibleTile)board[chapLocation.x][chapLocation.y]).setEntityHere(chap);
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * Moves Chap in the direction stated by one tile on the board.
   *
   * @param direction Represents the direction to move Chap
   * @throws ArrayIndexOutOfBoundsException if going to new direction will cause Chap to go out of bounds, will throw an ArrayIndexOutOfBoundsException.
   */
  public void moveChap(Direction direction) throws IllegalStateException, IllegalArgumentException {
    assert(chap != null && board != null);
    Point chapLocation = chap.getEntityPosition();
    //get new position to move Chap to
    Point position = null;
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
      case RIGHT:
        position = new Point(chapLocation.x+1, chapLocation.y);
        break;
      default:  //if not any of the move cases, then is not a valid move
        throw new IllegalArgumentException();
    }

    assert(position != null);
    //check that new position is in the bounds of the board, if not throw exception
    if(position.x >= board.length || position.y >= board[0].length) {
      throw new IllegalStateException();
    }


    if(!chap.canMove(board[position.x][position.y])) {
      InaccessibleTile tile = (InaccessibleTile)board[position.x][position.y];
      if(tile.isLockedDoor()) { //check that tile is a locked door
        chap.unlockDoor(tile);
      }
    }

    else {    //if Chap can move onto tile
      AccessibleTile accessibleTile = (AccessibleTile)board[position.x][position.y];

      //pick up item on tile if is an item tile
      if(accessibleTile.isItem()) {
        pickUpItem(accessibleTile);
      }

      //reassign Chap to new tile
      ((AccessibleTile)board[chapLocation.x][chapLocation.y]).setEntityHere(null);
      (accessibleTile).setEntityHere(chap);
      chap.setEntityPosition(new Point(position));  //to keep track of Chap's location
    }
    assert(board[chapLocation.x][chapLocation.y] instanceof AccessibleTile); //check that chap is not on an invalid tile
  }

  /**
   * For dealing with logic of picking up an item
   * @param accessibleTile The tile to pick the item from.
   */
  public void pickUpItem(AccessibleTile accessibleTile) throws IllegalStateException {
    //check if treasure tile
    if(!(accessibleTile instanceof TreasureTile)) {  //check if not on a treasure tile
      Item item = accessibleTile.getItemHere();
      if(item != null) {
        chap.addToKeyInven((Key) item);
        accessibleTile.setItemHere(null); //set tile's item to null since item is picked up
      }
    } else {  //if Chap is going to pick up treasure
      treasuresPickedUp++;
      if(treasuresPickedUp == TREASURES_NUM) {  //check if picked up all the treasures to unlock the exit
        board[exitLocation.x][exitLocation.y] = new ExitTile();
      }
    }
  }

  //getters and setters
  public Tile getTile(int x, int y) throws IllegalStateException, IllegalArgumentException {
    assert(board != null);
    if(x < 0 || x > board.length || y < 0 || y > board[0].length) {
      throw new IllegalArgumentException();
    }
    return board[x][y];
  }

  public Point getChapPosition() {
    assert(chap != null);
    return chap.entityPosition;
  }

  public Tile[][] getBoard() {
    assert(board != null);
    return board;
  }

  public void setBoard(Tile[][] board) {
    this.board = board;
  }

  public Chap getChap() {
    assert(chap != null);
    return chap;
  }

  public int getTreasuresPickedUp() { return treasuresPickedUp; }

}