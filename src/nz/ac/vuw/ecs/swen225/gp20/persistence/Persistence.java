package nz.ac.vuw.ecs.swen225.gp20.persistence;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

public class Persistence {

  /**
   * Reads the maze from a file.
   * @param levelFile The json file containing the level.
   * @return The maze read in from the file.
   */
  public static Maze readLevel(File levelFile) {
    
    try {
      JsonReader reader = Json.createReader(new FileReader(levelFile));

      JsonObject level = reader.readObject();
      
      int width = level.getInt("width");
      int height = level.getInt("height");
      
      Tile[][] levelArray = new Tile[width][height];

      //fill array with accessibles first
      for (int x=0;x<levelArray.length;x++) {
        for (int y=0;y<levelArray[x].length;y++) {
          levelArray[x][y] = new FreeTile();
        }
      }

      //load walls
      JsonArray wallTiles = level.getJsonArray("walls");
      
      for (JsonValue wallValue : wallTiles) {
        JsonObject wall = wallValue.asJsonObject();
        
        int wallX = wall.getInt("x");
        int wallY = wall.getInt("y");
        
        levelArray[wallX][wallY] = new WallTile();
      }

      //load locked doors
      JsonArray lockedDoorTiles = level.getJsonArray("locked_doors");

      for (JsonValue doorValue : lockedDoorTiles) {
        JsonObject door = doorValue.asJsonObject();

        int doorX = door.getInt("x");
        int doorY = door.getInt("y");

        levelArray[doorX][doorY] = new LockedDoorTile(0);
      }

      //load keys
      JsonArray keyTiles = level.getJsonArray("keys");

      for (JsonValue keyValue : keyTiles) {
        JsonObject key = keyValue.asJsonObject();

        int keyX = key.getInt("x");
        int keyY = key.getInt("y");

        Point keyPos = new Point(keyX, keyY);

        levelArray[keyX][keyY] = new KeyTile();
      }

      //load treasures
      JsonArray treasureTiles = level.getJsonArray("treasures");

      for (JsonValue treasureValue : treasureTiles) {
        JsonObject treasure = treasureValue.asJsonObject();

        int treasureX = treasure.getInt("x");
        int treasureY = treasure.getInt("y");

        levelArray[treasureX][treasureY] = new TreasureTile();
      }

      //load exit tile
      JsonObject exit = level.getJsonObject("exit");
      Point exitPos = new Point(exit.getInt("x"), exit.getInt("y"));
      levelArray[exitPos.x][exitPos.y] = new ExitTile();

      //load starting position
      JsonObject startPos = level.getJsonObject("starting_position");
      Point chapPos = new Point(startPos.getInt("x"), startPos.getInt("y"));

      //load exit lock
      JsonObject exitLock = level.getJsonObject("exit_lock");
      levelArray[exitLock.getInt("x")][exitLock.getInt("y")] = new ExitLockTile();

      //load info tile
      JsonObject infoTile = level.getJsonObject("info");
      levelArray[infoTile.getInt("x")][infoTile.getInt("y")] = new InfoTile();

      //make maze
      //Maze maze = new Maze(chapPos, exitPos, treasureTiles.size(), levelArray);
      return null;
    } catch (FileNotFoundException e) {
      // file was not found - maybe display something to user?
    } catch (ClassCastException | NullPointerException e) {
      //error in the file
    }
    //if error, return null
    return null;
  }

  /**
   * Saves the game state to a json file for loading later.
   * Needs to save all the things that can change.
   */
  public static void saveGameState() {
    JsonObject level = Json.createObjectBuilder()
        .add("levelname", "level1")
        .build();

  }
}
