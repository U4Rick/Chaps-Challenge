package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tile that decays each time Chap walks out of it, becomes a WallTile when Chap walks over it enough times.
 *
 * @author Vic
 */
public class DecayTile extends AccessibleTile {

  int decayLevel; //current state of decay level of this tile, if it becomes equal to 0, it becomes a wall tile
  private Map<Integer, Image> iconMap = new HashMap<>();

  /**
   * Constructor for the DecayTile object.
   * @param decayLevel The level of decay this tile should be initialised with.
   */
  public DecayTile(int decayLevel) {
    this.decayLevel = decayLevel;
  }

  @Override
  public boolean isItem() {
    return false;
  }

  @Override
  public String toString() {
    return "decay_tile_";
  }

  @Override
  public Moves inMove(Maze maze, Point position, boolean isChap, Entity entity, Direction direction) {
    Moves move = super.inMove(maze, position, isChap, entity, direction);
    //if chap was originally on a decay tile, need to decrement decay value
    decayLevel--;
    return move;
  }

  /**
   * Returns the decay level of this tile.
   * @return  The decay level of this tile.
   */
  public int getDecayLevel() { return decayLevel; }

  @Override
  public Image getIcon(){
    if(iconMap.isEmpty()){
      // Load icons on first call.
      try{
        iconMap.put(0, ImageIO.read(new File("./resources/" + toString() + "1" + ".png")));
        iconMap.put(1, ImageIO.read(new File("./resources/" + toString() + "1" + ".png")));
        iconMap.put(2, ImageIO.read(new File("./resources/" + toString() + "2" + ".png")));
        iconMap.put(3, ImageIO.read(new File("./resources/" + toString() + "3" + ".png")));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return iconMap.get(3);
    } else {
      return iconMap.get(decayLevel);
    }
  }
}
