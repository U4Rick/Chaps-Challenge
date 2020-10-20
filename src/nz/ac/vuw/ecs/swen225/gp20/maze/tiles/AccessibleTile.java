package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

import java.awt.*;

/**
 * Represents ta tile that the player can walk on.
 *
 * @author Vic
 */
abstract public class AccessibleTile extends Tile {

  private Entity entityHere = null; //entity that is on this tile, is null if no entity is on this tile

  @Override
  public boolean isAccessible() {
    return true;
  }

  /**
   * Checks if the free tile contains an item that can be picked up
   * @return true if is an item, false if not
   */
  abstract public boolean isItem();

  /**
   * Moves an entity to this tile
   * @param entity  Entity to move into this tile.
   */
  public void setEntityHere(Entity entity) {
    this.entityHere = entity;
  }

  /**
   * Gets the entity located in this tile.
   * @return The entity in this tile.
   */
  public Entity getEntityHere() {
    return entityHere;
  }

  /**
   * Gets the item located in this tile, usually would be null unless tile represents a tile with an item on it. Then would return the item.
   * @return The item in this tile.
   */
  public Item getItemHere() { return null; }

  @Override
  public void inMove(Maze maze, Point position, boolean isChap, Entity entity, Direction direction) {
    Preconditions.checkArgument(maze.getBoard()[position.x][position.y] instanceof AccessibleTile);

    Point entityLocation = entity.getEntityPosition();

    //pick up item on tile if is an item tile
    if(isChap) {
      Preconditions.checkArgument(entity instanceof Chap);  //make sure that entity is Chap
      if(isItem()) {
        maze.pickUpItem(position);
      }
    }

    //reassign entity to new tile
    ((AccessibleTile)maze.getBoard()[entityLocation.x][entityLocation.y]).setEntityHere(null);
    ((AccessibleTile)maze.getBoard()[position.x][position.y]).setEntityHere(entity);
    entity.setEntityPosition(new Point(position));  //to keep track of entity's location
    entity.setLastMove(direction); //update last move variable
  }
}