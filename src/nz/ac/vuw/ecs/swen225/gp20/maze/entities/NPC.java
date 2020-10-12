package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import java.awt.*;

/**
 * Represents a nonChap entity in the level.
 *
 * @author Vic
 */
public class NPC extends Entity {
  /**
   * Used to represent the behaviour the NPC needs to do, TODO possibly will not be used, delete if not used
   */
  public enum behaviourMode {
    MEAN, NICE;
  }

  /**
   * Constructor for npc
   * @param npcLocation x,y coordinates of npc in the board.
   */
  NPC(Point npcLocation) {
    super(npcLocation);
  }

  /**
   * Chooses a random direction that the NPC will move.
   * @return The random direction that the NPC will move.
   */
  public Maze.Direction moveRandom() {
    //choose random direction
    Maze.Direction direction = Maze.Direction.LEFT;
    int directionValue = (int)(Math.random()*4);
    assert(directionValue > -1 && directionValue < 4);
    switch(directionValue) {
      case 0:
        direction = Maze.Direction.LEFT;
        break;
      case 1:
        direction = Maze.Direction.RIGHT;
        break;
      case 2:
        direction = Maze.Direction.UP;
        break;
      case 3:
        direction = Maze.Direction.DOWN;
        break;
    }
    return direction;
  }

  @Override
  public final String toString() { return "npc"; }
}
