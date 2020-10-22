package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;

import java.awt.*;

/**
 * Represents ta tile that the player can walk on
 *
 * @author Vic
 */
abstract public class InaccessibleTile extends Tile {
  @Override
  public boolean isAccessible() { return false; }

  @Override
  public Moves inMove(Maze maze, Point position, boolean isChap, Entity entity, Direction direction) {
    Preconditions.checkArgument(maze.getBoard()[position.x][position.y] instanceof InaccessibleTile);
    return Moves.ERROR;
  }
}