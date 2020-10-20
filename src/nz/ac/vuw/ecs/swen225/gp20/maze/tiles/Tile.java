package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;
import nz.ac.vuw.ecs.swen225.gp20.maze.Icon;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;

import java.awt.*;

/**
 * Represents a tile in the board.
 */
public abstract class Tile extends Icon {
  /**
   * Checks if the player can walk onto this tile.
   * @return true This is always true.
   */
  public abstract boolean isAccessible();

  /**
   * For moveEntity, the logic of what happens in the tile when Chap moves onto it.
   * @param maze The maze where the entity is moved.
   * @param position The new position Chap moves to.
   * @param isChap For checking if the entity being moved is Chap so the extra Chap logic is applied to entity.
   * @param entity The entity to move.
   */
  public abstract Moves inMove(Maze maze, Point position, boolean isChap, Entity entity, Direction direction);
}

