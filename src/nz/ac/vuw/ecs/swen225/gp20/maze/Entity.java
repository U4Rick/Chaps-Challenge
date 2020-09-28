package nz.ac.vuw.ecs.swen225.gp20.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Represents an object that is placed on top of the tiles, this is either an item that Chap can pick up or a character that moves around the map
 *
 * @author Vic
 */
abstract public class Entity {
  private Image icon = null;
  Point entityPosition; //the position of the entity on the board, to avoid having to search through the board to find a specific entity

  /**
   * Constructor for entity
   * @param entityPosition x,y coordinates of entity in the board.
   */
  public Entity(Point entityPosition) {
    this.entityPosition = entityPosition;
  }

  /**
   * Checks if the entity can be picked up by Chap, items can be picked up, Chap and npcs cannot.
   * @return true if Chap can pick up this item, otherwise false.
   */
  abstract public boolean canBePickedUp();

  public Point getEntityPosition() { return entityPosition; }
  public void setEntityPosition(Point newLocation) { entityPosition = newLocation; }

  abstract public String toString();

  /**
   * Getter for graphic representation of the entity.
   * Uses lazy initialisation.
   * @return the icon of the entity
   */
  public Image getIcon(){
    if(icon == null){
      try {
        icon = ImageIO.read(new File("./resources/" + this.toString() + ".png"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return icon;
  }
}