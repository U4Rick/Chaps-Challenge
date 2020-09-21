package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

/**
 * Represents an object that is placed on top of the tiles, this is either an item that Chap can pick up or a character that moves around the map
 *
 * @author Vic
 */
abstract public class Entity {
  Point entityPosition; //the position of the entity on the board, to avoid having to search through the board to find a specific entity

  /**
   * Constructor for entity
   * @param entityPosition
   */
  public Entity(Point entityPosition) {
    this.entityPosition = entityPosition;
  }

  /**
   * Checks if the entity can be picked up by Chap, items can be picked up, Chap and npcs cannot.
   * @return
   */
  abstract public boolean canBePickedUp();

  public Point getEntityPosition() { return entityPosition; }
  public void setEntityPosition(Point newLocation) { entityPosition = newLocation; }
}