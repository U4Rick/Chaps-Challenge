package nz.ac.vuw.ecs.swen225.gp20.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Represents an object that is placed on top of the tiles, this is either an item that Chap can pick up or a character that moves around the map
 *
 * @author Vic
 */
abstract public class Entity extends Icon {
  Point entityPosition; //the position of the entity on the board, to avoid having to search through the board to find a specific entity

  /**
   * Constructor for entity
   * @param entityPosition x,y coordinates of entity in the board.
   */
  public Entity(Point entityPosition) {
    assert(entityPosition != null);
    this.entityPosition = entityPosition;
  }

  public Point getEntityPosition() { return entityPosition; }
  public void setEntityPosition(Point newLocation) { entityPosition = newLocation; }

  abstract public String toString();


}