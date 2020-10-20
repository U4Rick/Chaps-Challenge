package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents a tile that decays each time Chap walks out of it, becomes a WallTile when Chap walks over it enough times.
 *
 * @author Vic
 */
public class DecayTile extends AccessibleTile {

  int decayLevel; //current state of decay level of this tile, if it becomes

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
    return "decay_tile_"+decayLevel;
  }
}
