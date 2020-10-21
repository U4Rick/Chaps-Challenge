package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;

import java.awt.Point;

/**
 * Represents a nonChap entity in the level.
 *
 * @author Vic
 */
abstract public class NPC extends Entity {

  /**
   * Constructor for npc
   * @param npcLocation x,y coordinates of npc in the board.
   */
  public NPC(Point npcLocation) {
    super(npcLocation);
  }

  /**
   * Gets next move for NPC from persistence.
   * @return  The next move to do for the NPC.
   */
  abstract public Direction getNextMove();
  
  /**
   * Gets the current index of the NPC's move list.
   * @return The move index the NPC is currently on.
   */
  abstract public int getCurrentMoveIndex();
  
  /**
   * Sets the current index of the NPC's move list.
   * @param index the index to be set to.
   */
  abstract public void setCurrentMoveIndex(int index);

  @Override
  public final String toString() { return "npc"; }
}
