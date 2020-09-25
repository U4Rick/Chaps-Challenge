package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Represents a tile in the board.
 */
public abstract class Tile {
  private Image icon = null;

  /**
   * Checks if the player can walk onto this tile.
   * @return true This is always true.
   */
  public abstract boolean isAccessible();

  /**
   * Getter for graphic representation of the tile.
   * Uses lazy initialisation.
   * @return the icon of the tile
   */
  public Image getIcon(){
    if(icon == null){
      try {
        icon = ImageIO.read(new File("./resources/" + this.toString() + ".png"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return icon;
  }
}

