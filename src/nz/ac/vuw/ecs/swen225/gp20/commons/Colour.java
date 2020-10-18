package nz.ac.vuw.ecs.swen225.gp20.commons;

import java.util.Collections;

/**
 * Possible colours for keys and doors.
 */
public enum Colour {
  RED, YELLOW, GREEN, BLUE;

  @Override
  public String toString() {
    switch(this) {
      case RED:
        return "red";
      case YELLOW:
        return "yellow";
      case GREEN:
        return "green";
      case BLUE:
        return "blue";
      default:
        return "";
    }
  }
}
