package nz.ac.vuw.ecs.swen225.gp20.maze;

public class InfoField extends AccessibleTile {

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