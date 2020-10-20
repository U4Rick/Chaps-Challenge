package nz.ac.vuw.ecs.swen225.gp20.persistence;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ServiceLoader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParsingException;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.ExitTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InfoTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.KeyTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.TreasureTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;

/**
 * A class for loading levels from the json files.
 *
 * @author Tristan
 */
public class Levels {

  /**
   * Loads a level from number.
   * 
   * @param levelNum the number of the level to load.
   * @return the maze loaded.
   */
  public static Maze loadLevel(int levelNum) throws FileNotFoundException {
    String levelName = "levels/level" + levelNum + ".json";

    File levelFile = new File(levelName);

    if (!levelFile.exists()) {
      throw new FileNotFoundException("Level file doesn't exist");
    }

    return loadLevelFromFile(levelFile);
  }

  /**
   * Reads the maze from a file.
   * 
   * @param levelFile The json file containing the level.
   * @return The maze read in from the file.
   */
  public static Maze loadLevelFromFile(File levelFile) {

    try {
      JsonReader reader = Json.createReader(new FileReader(levelFile));

      JsonObject level = reader.readObject();

      final int width = level.getInt("width");
      final int height = level.getInt("height");
      
      final int levelNum = level.getInt("number");

      final int levelTime = level.getInt("time");

      Tile[][] levelArray = new Tile[width][height];

      // fill array with accessibles first
      for (int x = 0; x < levelArray.length; x++) {
        for (int y = 0; y < levelArray[x].length; y++) {
          levelArray[x][y] = new FreeTile();
        }
      }

      // load walls
      TileObject[] wallTiles = Persistence.getObjectValues(level, "walls", false);

      for (TileObject wall : wallTiles) {
        levelArray[wall.x][wall.y] = new WallTile();
      }

      // load locked doors
      TileObject[] lockedDoorTiles = Persistence.getObjectValues(level, "locked_doors", true);

      for (TileObject door : lockedDoorTiles) {
        levelArray[door.x][door.y] = new DoorTile(door.colour);
      }

      // load keys
      TileObject[] keyTiles = Persistence.getObjectValues(level, "keys", true);

      for (TileObject key : keyTiles) {
        levelArray[key.x][key.y] = new KeyTile(key.colour);
      }

      // load treasures
      TileObject[] treasureTiles = Persistence.getObjectValues(level, "treasures", false);

      for (TileObject treasure : treasureTiles) {
        levelArray[treasure.x][treasure.y] = new TreasureTile();
      }

      // load actors
      List<NPC> actors = new ArrayList<NPC>();
      NPC actorBase = loadActor(levelNum);
      if (actorBase != null) {
        Constructor<NPC> actorConstruct = (Constructor<NPC>) actorBase.getClass()
            .getConstructor(Point.class, Direction[].class);

        JsonArray actorArray = level.getJsonArray("actors");

        for (JsonValue actorValue : actorArray) {
          JsonObject actor = actorValue.asJsonObject();

          int x = actor.getInt("x");
          int y = actor.getInt("y");

          Direction[] actorPath = getActorPath(actor.getJsonArray("path"));

          NPC newActor = actorConstruct.newInstance(new Point(x, y), actorPath);

          actors.add(newActor);

          ((FreeTile) levelArray[x][y]).setEntityHere(newActor);

        }
      }
      // load exit tile
      JsonObject exit = level.getJsonObject("exit");
      Point exitPos = new Point(exit.getInt("x"), exit.getInt("y"));
      levelArray[exitPos.x][exitPos.y] = new ExitTile();

      // load starting position
      JsonObject startPos = level.getJsonObject("starting_position");
      Point chapPos = new Point(startPos.getInt("x"), startPos.getInt("y"));

      // load exit lock
      JsonObject exitLock = level.getJsonObject("exit_lock");
      levelArray[exitLock.getInt("x")][exitLock.getInt("y")] = new ExitLockTile();

      //get info text from the level
      String infoText = level.getString("info_text");

      // load info tile
      JsonObject infoTile = level.getJsonObject("info");
      levelArray[infoTile.getInt("x")][infoTile.getInt("y")] = new InfoTile(infoText);

      // make maze
      return new Maze(levelNum, chapPos, exitPos, treasureTiles.length, levelTime, levelArray, actors);
    } catch (FileNotFoundException e) {
      // file was not found - maybe display something to user?
    } catch (ClassCastException | NullPointerException | InputMismatchException | JsonParsingException e) {
      // error in the file
      e.printStackTrace();
    } catch (InstantiationException | IllegalStateException | IllegalAccessException | InvocationTargetException
        | NoSuchMethodException e) {
      // reflection error, most likely an issue with the NPC
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // if error, return null
    return null;
  }
  
  /**
   * Gets the path of directions the actor should take.
   * 
   * @param path the path passed in as a JsonArray.
   * @return the array of Directions giving the path taken.
   */
  private static Direction[] getActorPath(JsonArray path) {
    Direction[] directions = new Direction[path.size()];

    for (int i = 0; i < path.size(); i++) {
      directions[i] = getDirectionFromString(path.getString(i));
    }

    return directions;
  }
  
  /**
   * Gets the direction from the string inputted in the file.
   * 
   * @param dir the direction as a string.
   * @return the direction of the string.
   */
  private static Direction getDirectionFromString(String dir) {
    switch (dir) {
      case "left":
        return Direction.LEFT;
      case "right":
        return Direction.RIGHT;
      case "up":
        return Direction.UP;
      case "down":
        return Direction.DOWN;
      default:
        return null;
    }
  }

  /**
   * Loads the actor for the level.
   * 
   * @param levelNum the level number's actor to be loaded.
   * @return the actor loaded.
   */
  private static NPC loadActor(int levelNum) {
    File actorFile = new File("levels/level" + levelNum + ".jar");

    try {
      URL[] url = new URL[] { actorFile.toURI().toURL() };

      URLClassLoader classLoader = new URLClassLoader(url);

      ServiceLoader<NPC> serviceLoader = ServiceLoader.load(NPC.class, classLoader);

      for (NPC npc : serviceLoader) {
        return npc;
      }

    } catch (MalformedURLException e) {
      // error with the url
    }

    return null;
  }

}
