package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents ta tile that the player can walk on
 *
 * @author Vic
 */
abstract public class Accessible implements Tile {

  private Entity entityHere = null; //entity that is on this tile, is null if no entity is on this tile

  public Accessible() {
    ;
  }

  /**
   * Checks if the player can walk onto this tile.
   * @return true This is always true.
   */
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
    entityHere = entity;
  }
  public Entity getEntityHere() { return entityHere; }
}