package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import nz.ac.vuw.ecs.swen225.gp20.maze.Icon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Represents an object that is a character that moves around the map.
 *
 * @author Vic
 */
abstract public class Entity extends Icon {
  public Point entityPosition; //the position of the entity on the board, to avoid having to search through the board to find a specific entity

  /**
   * Constructor for entity
   * @param entityPosition x,y coordinates of entity in the board.
   */
  public Entity(Point entityPosition) {
    assert(entityPosition != null);
    this.entityPosition = entityPosition;
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
}