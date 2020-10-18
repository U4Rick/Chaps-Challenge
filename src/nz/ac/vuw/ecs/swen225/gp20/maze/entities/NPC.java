package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

/**
 * Represents a nonChap entity in the level.
 *
 * @author Vic
 */
public class NPC extends Entity {

  int currentMovement;  //current index position in movementList
  List<Direction> movementList; //stores list of movements
  /**
   * Constructor for npc
   * @param npcLocation x,y coordinates of npc in the board.
   */
  public NPC(Point npcLocation, List<Direction> movementList) {
    super(npcLocation);
    currentMovement = 0;
    this.movementList = new ArrayList<>();
    this.movementList.addAll(movementList);
  }

  public Direction getNextMove() {
    if(currentMovement == movementList.size()) {
      currentMovement = 0;
    }
    return movementList.get(currentMovement++);
  }

  /**
   * Chooses a random direction that the NPC will move.
   * @return The random direction that the NPC will move.
   */
  public Direction moveRandom() {
    //choose random direction
    Direction direction = Direction.LEFT;
    int directionValue = (int)(Math.random()*4);
    assert(directionValue > -1 && directionValue < 4);
    switch(directionValue) {
      case 0:
        direction = Direction.LEFT;
        break;
      case 1:
        direction = Direction.RIGHT;
        break;
      case 2:
        direction = Direction.UP;
        break;
      case 3:
        direction = Direction.DOWN;
        break;
    }
    return direction;
  }

  @Override
  public final String toString() { return "npc"; }
}
