package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

public class Key extends Item {
  private Maze.Colours keyColour;  //the colour of the key, 0 for red, 1 for blue, 2 for yellow

  public Key(Maze.Colours keyColour, Point keyPos) {
    super(keyPos);
    this.keyColour = keyColour;
  }

  @Override
  public boolean canBeAddedToInve() { return true; }

  public Maze.Colours getKeyColour() { return keyColour; }
}
