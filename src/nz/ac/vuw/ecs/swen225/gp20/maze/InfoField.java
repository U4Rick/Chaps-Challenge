package nz.ac.vuw.ecs.swen225.gp20.maze;

public class InfoField extends Accessible {

  String information; //information that stored in the infoField tile

  @Override
  public boolean isItem() {
    return false;
  }
}