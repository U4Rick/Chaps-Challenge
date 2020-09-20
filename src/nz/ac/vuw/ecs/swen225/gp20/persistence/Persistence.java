package nz.ac.vuw.ecs.swen225.gp20.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;

public class Persistence {
  public void readLevel(File levelFile) {
    
    try {
      JsonReader reader = Json.createReader(new FileReader(levelFile));

      JsonObject level = reader.readObject();
      
      int width = level.getInt("width");
      int height = level.getInt("height");
      
      Tile[][] levelArray = new Tile[width][height];
      
      JsonArray wallTiles = level.getJsonArray("walls");
      
      for (JsonValue wallValue : wallTiles) {
        JsonObject wall = wallValue.asJsonObject();
        
        int wallX = wall.getInt("x");
        int wallY = wall.getInt("y");
        
        //levelArray[wallX][wallY] = new Wall();
      }

    } catch (FileNotFoundException e) {
      // file was not found - maybe display something to user?
    } catch (ClassCastException | NullPointerException e) {
      //error in the file
    }

  }
}
