package nz.ac.vuw.ecs.swen225.gp20.maze.entities;

import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;

import java.awt.*;

public class Actor extends NPC {

    Direction[] path;
    int pathIndex;

    public Actor() {
        super(new Point(0, 0));

    }

    public Actor(Point startingPoint, Direction[] path) {
        super(startingPoint);
        this.path = path;
        this.pathIndex = 0;
    }

    @Override
    public Direction getNextMove() {
        Direction dir = path[pathIndex];
        pathIndex = (pathIndex + 1) % path.length;
        return dir;
    }

    @Override
    public int getCurrentMoveIndex() {
      return pathIndex;
    }

    @Override
    public void setCurrentMoveIndex(int index) {
      pathIndex = index;
    }
}
