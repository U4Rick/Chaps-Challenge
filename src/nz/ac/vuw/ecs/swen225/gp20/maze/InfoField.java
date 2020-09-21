package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Represents the infofield tile in the level, this tile stores tips which is displayed when Chap walks onto the tile
 */
public class InfoField extends Accessible {

  String information; //information that stored in the infoField tile

  /**
   * Constructor for the InfoField object.
   * @param information The information that is stored in this tile.
   */
  public InfoField(String information) {
    this.information = information;
  }

  /**
   * Gets the information stored in this tile.
   * @return The information stored in this tile.
   */
  public String getInformation() {
    return information;
  }

  @Override
  public boolean isItem() {
    return false;
  }
}