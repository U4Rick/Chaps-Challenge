package nz.ac.vuw.ecs.swen225.gp20.maze;

//importing libraries needed
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.awt.Point;

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
  private NPC npc;  //it's a man

  private int levelNumber; //name of the current maze
  private int timeAvailable;  //amount of time available to solve the maze at beginning of level
  private int timeLeft; //amount of time left to solve the maze

  private final int TREASURES_NUM;
  private int treasuresPickedUp = 0;

  /**
   * Constructs the level for the game based from the level data files.
   * @param chapLocation  The initial location of Chap.
   * @param exitLocation  The location of the exit in the level.
   * @param treasuresNum  Number of treasures in the level.
   * @param board The 2d array that represents the board for the level.
   * @throws IllegalStateException If chap is being set onto an inaccessible tile, then there is something wrong with the level.
   */
  public Maze(int levelNumber, Point chapLocation, Point exitLocation, int treasuresNum, int timeAvailable, Tile[][] board) throws IllegalStateException{
    this.levelNumber = levelNumber;
    this.chap = new Chap(chapLocation);
    this.exitLocation = exitLocation;
    this.timeAvailable = timeAvailable;
    this.timeLeft = timeAvailable;
    TREASURES_NUM = treasuresNum;
    this.board = board;

    //set tile's entity at chap pos to chap
    if(board[chapLocation.x][chapLocation.y].isAccessible()) {
      ((AccessibleTile)board[chapLocation.x][chapLocation.y]).setEntityHere(chap);
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * Moves Chap with the direction specified.
   * @param direction Direction specified.
   */
  public void moveChap(Direction direction) {
    chap.moveEntity(direction, chap, this, true);
  }

  /**
   * Moves the NPC.
   */
  public void moveNPC() {
    //TODO implement this in separate branch for NPC.
  }

  /**
   * For dealing with logic of picking up an item
   * @param accessibleTile The tile to pick the item from.
   */
  public void pickUpItem(AccessibleTile accessibleTile) {
    assert(accessibleTile instanceof KeyTile || accessibleTile instanceof TreasureTile); //check that tile is a keytile or treasuretile
    if(!(accessibleTile instanceof TreasureTile)) {  //check if not on a treasure tile
      Item item = accessibleTile.getItemHere();
      if(item != null) {
        int originalSize = chap.getKeyInventory().size();
        chap.addToKeyInven((Key) item);
        accessibleTile = new FreeTile();  //change to free tile
        assert(chap.getKeyInventory().size() == (originalSize+1) && chap.getKeyInventory().contains(item));  //check that key is in inventory
      }
    } else {  //if Chap is going to pick up treasure
      treasuresPickedUp++;
      if(treasuresPickedUp == TREASURES_NUM) {  //check if picked up all the treasures to unlock the exit
        //find all exit locks and change them into free tiles once all treasures have been picked up.
        for(int x = 0; x < board.length; x++) {
          for(int y = 0; y < board[0].length; y++) {
            if(board[x][y] instanceof ExitLockTile) {
              board[x][y] = new FreeTile();
            }
          }
        }
        board[exitLocation.x][exitLocation.y] = new ExitTile();
      }
    }
  }

  //getters and setters

  /**
   * Gets tile from board located at x,y.
   * @param x x coordinate of tile.
   * @param y y coordinate of tile.
   * @return  The tile located at x,y.
   * @throws IllegalArgumentException If the x,y coordinates are out of bounds, then is invalid coordinates.
   */
  public final Tile getTile(int x, int y) throws IllegalArgumentException {
    assert(board != null);
    if(x < 0 || x > board.length || y < 0 || y > board[0].length) {
      throw new IllegalArgumentException();
    }
    return board[x][y];
  }

  /**
   * Gets coordinates of chap's position on the board.
   * @return  The coordinates of chap's position on the board.
   */
  public final Point getChapPosition() {
    assert(chap != null);
    return chap.entityPosition;
  }

  /**
   * Gets the board used for this level.
   * @return  The board used for this level.
   */
  public final Tile[][] getBoard() {
    assert(board != null);
    return board;
  }

  /**
   * Gets chap that is for this level.
   * @return Chap that is for this level.
   */
  public final Chap getChap() {
    assert(chap != null);
    return chap;
  }

  /**
   * Gets the name of this level, for the persistence package.
   * @return The name of this level.
   */
  public final int getLevelNumber() {
    return levelNumber;
  }

  /**
   * Gets the amount of time available to solve the maze at beginning of level.
   * @return The amount of time available to solve the maze at beginning of level.
   */
  public final int getTimeAvailable() {
    return timeAvailable;
  }

  /**
   * Sets the amount of time left to solve the maze.
   * @param timeLeft  The amount of time left to solve the maze.
   */
  public void setTimeLeft(int timeLeft) {
    this.timeLeft = timeLeft;
  }

  /**
   * Gets the amount of time left to solve the maze.
   * @return  The amount of time left to solve the maze.
   */
  public final int getTimeLeft() {
    return timeLeft;
  }

  /**
   * Gets the int number of treasures currently picked up by Chap.
   * @return Number of treasures currently picked up by Chap.
   */
  public final int getTreasuresPickedUp() { return treasuresPickedUp; }

  /**
   * Gets the total number of treasures on the level.
   * @return  The total number of treasures on the level.
   */
  public final int getTREASURES_NUM() { return TREASURES_NUM; }
}