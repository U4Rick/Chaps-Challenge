package nz.ac.vuw.ecs.swen225.gp20.maze;

//importing libraries needed
import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import javax.imageio.ImageIO;

/**
 * Represents the map used in the game for each level.
 *
 * @author Vic
 */
public class Maze {

  private Tile[][] board; //2d array of tiles,
  private Point exitLocation; //where exit is located at on the map

  private Chap chap;  //it's Chap!
  private NPC npc;  //it's a man

  private int levelNumber; //name of the current maze
  private int timeAvailable;  //amount of time available to solve the maze at beginning of level
  private int timeLeft; //amount of time left to solve the maze

  private final int TREASURES_NUM;
  private int treasuresPickedUp = 0;
  private int treasuresLeft;  //for postconditions check

  private boolean chapWin = false;  //checks that Chap is on exit tile

  static private Map<Colour, Image> keyImages = null;

  /**
   * Constructs the level for the game based from the level data files.
   * @param chapLocation  The initial location of Chap.
   * @param exitLocation  The location of the exit in the level.
   * @param treasuresNum  Number of treasures in the level.
   * @param board The 2d array that represents the board for the level.
   * @throws IllegalStateException If chap is being set onto an inaccessible tile, then there is something wrong with the level.
   */
  public Maze(int levelNumber, Point chapLocation, Point exitLocation, int treasuresNum, int timeAvailable, Tile[][] board) throws IllegalStateException, IOException {
    //check that parameters are not null
    Preconditions.checkNotNull(chapLocation);
    Preconditions.checkNotNull(exitLocation);
    //check that all tiles on board exist
    for (Tile[] tiles : board) {
      Preconditions.checkNotNull(tiles);
      for (int y = 0; y < board[0].length; y++) {
        Preconditions.checkNotNull(tiles[y]);
      }
    }

    //set variables
    this.levelNumber = levelNumber;
    this.chap = new Chap(chapLocation);
    this.exitLocation = exitLocation;
    this.timeAvailable = timeAvailable;
    this.timeLeft = timeAvailable;
    TREASURES_NUM = treasuresNum;
    this.board = board;

    //set tile's entity at chap pos to chap
    if(this.board[chapLocation.x][chapLocation.y].isAccessible()) {
      ((AccessibleTile)this.board[chapLocation.x][chapLocation.y]).setEntityHere(chap);
    } else {
      throw new IllegalStateException();
    }

    //initialise key images if it doesn't exist
    if(keyImages == null) {
      keyImages = new HashMap<>();
      for (Colour c : Colour.values()) {
        Image icon = ImageIO.read(new File("./resources/" + c.toString() + "key_item.png"));
        keyImages.put(c, icon);
      }
    }
  }

  /**
   * Moves Chap with the direction specified.
   * @param direction Direction specified.
   */
  public void moveChap(Direction direction) {
      chapWin = chap.moveEntity(direction, chap, this, true);
  }

  /**
   * Moves the NPC.
   */
  public void moveNPC() {
    //TODO implement this in separate branch for NPC.
  }

  /**
   * For dealing with logic of picking up an item.
   * @param location The tile to pick the item from.
   */
  public void pickUpItem(Point location) {
    Preconditions.checkState(board[location.x][location.y] instanceof  AccessibleTile );  //check that tile is an accessibletile
    AccessibleTile accessibleTile = (AccessibleTile)this.getBoard()[location.x][location.y];
    Preconditions.checkState(board[location.x][location.y] instanceof KeyTile || board[location.x][location.y] instanceof TreasureTile); //check that tile is a keytile or treasuretile
    if(accessibleTile instanceof KeyTile) {  //check if not on a treasure tile
      Colour colour = ((KeyTile) accessibleTile).getKeyColour();
      int originalSize = chap.getKeyInventory().get(colour);
      chap.addToKeyInven(colour);
      board[location.x][location.y] = new FreeTile(); //change to free tile
      assert(chap.getKeyInventory().get(colour) == originalSize+1);  //check that value in map is incremented
    } else {  //if Chap is going to pick up treasure
      treasuresPickedUp++;
      treasuresLeft--;

      board[location.x][location.y] = new FreeTile(); //change to free tile

      //check that number of treasures picked up is not zero or negative
      assert(treasuresPickedUp > 0);
      int treasuresOnBoard = 0; //check that number of treasures on board has reduced by one
      for (Tile[] tiles : board) {
        for (int y = 0; y < board[0].length; y++) {
          if (tiles[y] instanceof TreasureTile) treasuresOnBoard++;
        }
      }
      assert(treasuresOnBoard == treasuresLeft);
      //check that total number of treasures is consistent
      assert(treasuresPickedUp+treasuresLeft == TREASURES_NUM);


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
   * Gets the image that represents the key stated in the parameter.
   * @param colour The colour of the key that the image represents.
   * @return  The image object of the key.
   */
  static public Image getKeyIcon(Colour colour) {
    Image img = keyImages.get(colour);
    assert(img != null);
    return keyImages.get(colour);
  }

  /**
   * Gets tile from board located at x,y.
   * @param x x coordinate of tile.
   * @param y y coordinate of tile.
   * @return  The tile located at x,y.
   * @throws IllegalArgumentException If the x,y coordinates are out of bounds, then is invalid coordinates.
   */
  public final Tile getTile(int x, int y) throws IllegalArgumentException {
    Preconditions.checkNotNull(board);
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
    Preconditions.checkNotNull(chap);
    return chap.entityPosition;
  }

  /**
   * Gets the board used for this level.
   * @return  The board used for this level.
   */
  public final Tile[][] getBoard() {
    Preconditions.checkNotNull(board);
    return board;
  }

  /**
   * Gets chap that is for this level.
   * @return Chap that is for this level.
   */
  public final Chap getChap() {
    Preconditions.checkNotNull(chap);
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
   * Sets the int number of treasures currently picked up by Chap.
   * @param treasuresPickedUp Number of treasures currently picked up by Chap.
   */
  public void setTreasuresPickedUp(int treasuresPickedUp) { this.treasuresPickedUp = treasuresPickedUp; }

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

  /**
   * Gets the boolean variable that checks if Chap has won.
   * @return The boolean variable that checks if Chap has won.
   */
  public boolean getChapWin() {
    return chapWin;
  }
}