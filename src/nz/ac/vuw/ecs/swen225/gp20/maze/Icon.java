package nz.ac.vuw.ecs.swen225.gp20.maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

abstract public class Icon {
  private Image icon = null;
  
  /**
   * Getter for graphic representation of the entity.
   * Uses lazy initialisation.
   * @return the icon of the entity
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
