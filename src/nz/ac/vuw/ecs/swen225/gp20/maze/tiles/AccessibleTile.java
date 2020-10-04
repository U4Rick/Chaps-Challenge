package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

/**
 * Represents ta tile that the player can walk on
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
   * Gets the item located in this tile, usually would be null.
   * @return The item in this tile.
   */
  public Item getItemHere() { return null; }
}