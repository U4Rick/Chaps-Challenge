package nz.ac.vuw.ecs.swen225.gp20.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents an object in the maze that will be drawn by the renderer.
 *
 * @author Vic
 */
abstract public class Icon {
  private Image icon = null;
  
  /**
   * Getter for graphic representation of the entity.
   * Uses lazy initialisation.
   * @return the icon of the entity
   */
  public Image getIcon() throws IOException{
    if(icon == null){
      try {
        icon = ImageIO.read(new File("./resources/" + this.toString() + ".png"));
      } catch (IOException e) {
        throw new IOException();
      }
    }
    return icon;
  }

  /**
   * ToString method, used by the renderer.
   * @return String of tile, item or entity.
   */
  abstract public String toString();
}
