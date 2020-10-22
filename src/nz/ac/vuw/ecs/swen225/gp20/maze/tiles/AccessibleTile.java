package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;

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

  @Override
  public Moves inMove(Maze maze, Point position, boolean isChap, Entity entity, Direction direction) {
    Preconditions.checkArgument(maze.getBoard()[position.x][position.y] instanceof AccessibleTile);

    Point entityLocation = entity.getEntityPosition();
    Moves move = Moves.MOVE;

    //pick up item on tile if is an item tile
    if(isChap) {
      Preconditions.checkArgument(entity instanceof Chap);  //make sure that entity is Chap
      if(isItem()) {
        move = maze.pickUpItem(position);
      }
    }


    //check if NPC is in new tile
    if (((AccessibleTile) maze.getBoard()[position.x][position.y]).getEntityHere() != null) {
      maze.setChapLose(true);
      move = Moves.DEATH;
    }

    //reassign entity to new tile
    ((AccessibleTile)maze.getBoard()[entityLocation.x][entityLocation.y]).setEntityHere(null);
    ((AccessibleTile)maze.getBoard()[position.x][position.y]).setEntityHere(entity);
    entity.setEntityPosition(new Point(position));  //to keep track of entity's location
    entity.setLastMove(direction); //update last move variable

    //check if previous tile was a decayTile, if so change to wallTile
    if(maze.getBoard()[entityLocation.x][entityLocation.y] instanceof DecayTile) {
      if(((DecayTile)maze.getBoard()[entityLocation.x][entityLocation.y]).getDecayLevel() == 0) {
        maze.getBoard()[entityLocation.x][entityLocation.y] = new WallTile();
      }
    }

    return move;
  }
}