package src.src.nz.ac.vuw.ecs.swen225.gp20.application.maze;

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
  private Chap chap;  //it's Chap!

  /**
   * Constructs new map, parameter is the data parsed from the level files
   */
  public Map() {
    board = new ArrayList<>();
  }

  public void unlockExit() {
    ;
  }

  public void unlockDoor() {
    ;
  }

  /**
   * Moves Chap into the new position on the board.
   * @param position  Represents the new position to move Chap to.
   */
  public void moveChap(Point position) {
    //check if Chap can move onto tile
    if(chap.canMove(board.get(position.x).get(position.y))) {
      Point chapLocation = chap.getEntityPosition();
      //reassign Chap to new tile
      ((Accessible)board.get(chapLocation.x).get(chapLocation.y)).setEntityHere(null);
      ((Accessible)board.get(position.x).get(position.y)).setEntityHere(chap);
      chap.setEntityPosition(new Point(position));  //to keep track of Chap's location
    }
  }
}