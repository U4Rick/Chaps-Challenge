package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;

import java.awt.*;

/**
 * Represents a nonChap entity in the level.
 *
 * @author Vic
 */
public class NPC extends Entity {

  /**
   * Constructor for npc
   * @param npcLocation x,y coordinates of npc in the board.
   */
  NPC(Point npcLocation) {
    super(npcLocation);
  }

  @Override
  public final String toString() { return "npc"; }
}
