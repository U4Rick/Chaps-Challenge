package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Icon;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.ExitTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InaccessibleTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Represents an object that is a character that moves around the map.
 *
 * @author Vic
 */
abstract public class Entity extends Icon {
  public Point entityPosition; //the position of the entity on the board, to avoid having to search through the board to find a specific entity
  private Direction lastMove = null; //the direction of the last move that the entity has done
  private Map<Direction, Image> iconMap = new HashMap<>();

  /**
   * Constructor for entity
   * @param entityPosition x,y coordinates of entity in the board.
   */
  public Entity(Point entityPosition) {
    Preconditions.checkNotNull(entityPosition);
    this.entityPosition = entityPosition;
  }

  /**
   * Moves Chap in the direction stated by one tile on the board.
   *
   * @param direction Represents the direction to move Chap.
   * @param entity The entity to move.
   * @param maze The maze where the entity is moved.
   * @param isChap For checking if the entity being moved is Chap so the extra Chap logic is applied to entity.
   * @throws IllegalStateException If going to new direction will cause Chap to go out of bounds, will throw an IllegalStateException.
   * @throws IllegalArgumentException If the direction provided is not left, right, up or down, then is an invalid direction.
   * @return True if Chap has reached the exit tile and won, false is not.
   */
  public boolean moveEntity(Direction direction, Entity entity, Maze maze, boolean isChap) throws IllegalStateException, IllegalArgumentException {
    //checking that parameters are not null.
    Preconditions.checkNotNull(maze);
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(maze.getBoard());

    Chap chap = null;
    /*if(isChap) {
      Preconditions.checkArgument(entity instanceof Chap);  //make sure that entity is Chap
      chap = (Chap)entity;
    }*/
    Point entityLocation = entity.getEntityPosition();
    //get new position to move entity to
    Point position;
    switch(direction) {
      case UP:
        position = new Point(entityLocation.x, entityLocation.y-1);
        break;
      case DOWN:
        position = new Point(entityLocation.x, entityLocation.y+1);
        break;
      case LEFT:
        position = new Point(entityLocation.x-1, entityLocation.y);
        break;
      case RIGHT:
        position = new Point(entityLocation.x+1, entityLocation.y);
        break;
      default:  //if not any of the move cases, then is not a valid move
        throw new IllegalArgumentException();
    }

    //check that new position is in the bounds of the board, if not throw exception
    if(position.x >= maze.getBoard().length || position.y >= maze.getBoard()[0].length) {
      throw new IllegalStateException();
    }

    maze.getBoard()[position.x][position.y].inMove(maze, position, isChap, entity, direction);

    //check if chap is on exit tile
    if(maze.getBoard()[position.x][position.y] instanceof ExitTile) {
      return true;
    }

    assert(maze.getBoard()[entityLocation.x][entityLocation.y] instanceof AccessibleTile);  //check that entity is not on an invalid tile
    return false;
  }



  /**
   * For checking if entity can move to the new tile.
   * @param tile The tile to check if entity can move into.
   */
  public boolean canMove(Tile tile) {
    Preconditions.checkNotNull(tile);
    return tile.isAccessible();
  }

  /**
   * Gets the entity's x,y coordinates on the map.
   * @return Entity's x,y coordinates on the map.
   */
  public Point getEntityPosition() { return entityPosition; }

  /**
   * Gets the entity's x,y coordinates on the map.
   * @param newLocation The new x,y coordinates.
   */
  public void setEntityPosition(Point newLocation) { entityPosition = newLocation; }

  /**
   * Gets the direction of the last move done by this entity.
   * @return  Direction of the last move done by this entity.
   */
  public Direction getLastMove() { return lastMove; }

  /**
   * Sets the direction of the last move done by this entity.
   * @param direction Direction of the last move done by this entity.
   */
  public void setLastMove(Direction direction) { this.lastMove = direction;}

  @Override
  public Image getIcon(){
    if(iconMap.isEmpty()){
      // Load icons on first call.
      try{
        iconMap.put(Direction.UP, ImageIO.read(new File("./resources/" + this.toString() + "_up.png")));
        iconMap.put(Direction.DOWN, ImageIO.read(new File("./resources/" + this.toString() + "_down.png")));
        iconMap.put(Direction.LEFT, ImageIO.read(new File("./resources/" + this.toString() + "_left.png")));
        iconMap.put(Direction.RIGHT, ImageIO.read(new File("./resources/" + this.toString() + "_right.png")));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return iconMap.get(Direction.UP);
    } else {
      if(lastMove == null){
        return iconMap.get(Direction.UP);
      }
      return iconMap.get(lastMove);
    }
  }
}