package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents ta tile that the player can walk on
 *
 * @author Vic
 */
abstract class Accessible implements Tile {

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
   * Moves an entity to this tile
   * @param entity
   */
  public void setEntityHere(Entity entity) {
    entityHere = entity;
  }
}