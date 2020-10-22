package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

/**
 * Represents a tile that displays information when the player walks onto it.
 *
 * @author Vic
 */
public class InfoTile extends AccessibleTile {

  String information; //information that stored in the infoField tile

  /**
   * Constructor for the InfoField object.
   * @param information The information that is stored in this tile.
   */
  public InfoTile(String information) {
    this.information = information;
  }

  /**
   * Gets the information stored in this tile.
   * @return The information stored in this tile.
   */
  public final String getInformation() {
    return information;
  }

  @Override
  public boolean isItem() {
    return false;
  }

  @Override
  public String toString() { return "info_tile"; }
}