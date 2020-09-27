package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.Entity;

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
   * @param entity
   */
  public void setEntityHere(Entity entity) {
    this.entityHere = entity;
  }
  public Entity getEntityHere() { return entityHere; }
}