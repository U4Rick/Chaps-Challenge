package nz.ac.vuw.ecs.swen225.gp20.persistence;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParsingException;

import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Colours;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

public class Persistence {
  
  /**
   * Loads a level number.
   * @param levelNum the number of the level to load.
   * @return the maze loaded.
   */
  public static Maze loadLevel(int levelNum) {
    String levelName = "levels/level" + levelNum + ".json";
    
    File levelFile = new File(levelName);
    
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

      int width = level.getInt("width");
      int height = level.getInt("height");

      Tile[][] levelArray = new Tile[width][height];

      // fill array with accessibles first
      for (int x = 0; x < levelArray.length; x++) {
        for (int y = 0; y < levelArray[x].length; y++) {
          levelArray[x][y] = new FreeTile();
        }
      }

      // load walls
      JsonArray wallTiles = level.getJsonArray("walls");

      for (JsonValue wallValue : wallTiles) {
        JsonObject wall = wallValue.asJsonObject();

        int wallX = wall.getInt("x");
        int wallY = wall.getInt("y");

        levelArray[wallX][wallY] = new WallTile();
      }

      // load locked doors
      JsonArray lockedDoorTiles = level.getJsonArray("locked_doors");

      for (JsonValue doorValue : lockedDoorTiles) {
        JsonObject door = doorValue.asJsonObject();

        int doorX = door.getInt("x");
        int doorY = door.getInt("y");

        Colours colour = getColourFromString(door.getString("colour"));

        levelArray[doorX][doorY] = new DoorTile(colour);
      }

      // load keys
      JsonArray keyTiles = level.getJsonArray("keys");

      for (JsonValue keyValue : keyTiles) {
        JsonObject key = keyValue.asJsonObject();

        int keyX = key.getInt("x");
        int keyY = key.getInt("y");

        Colours colour = getColourFromString(key.getString("colour"));

        levelArray[keyX][keyY] = new KeyTile(colour);
      }

      // load treasures
      JsonArray treasureTiles = level.getJsonArray("treasures");

      for (JsonValue treasureValue : treasureTiles) {
        JsonObject treasure = treasureValue.asJsonObject();

        int treasureX = treasure.getInt("x");
        int treasureY = treasure.getInt("y");

        levelArray[treasureX][treasureY] = new TreasureTile();
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

      // load info tile
      JsonObject infoTile = level.getJsonObject("info");
      levelArray[infoTile.getInt("x")][infoTile.getInt("y")] = new InfoTile("");

      // make maze
      return new Maze(chapPos, exitPos, treasureTiles.size(), levelArray);
    } catch (FileNotFoundException e) {
      // file was not found - maybe display something to user?
    } catch (ClassCastException | NullPointerException | InputMismatchException | JsonParsingException e) {
      // error in the file
    }
    // if error, return null
    return null;
  }

  /**
   * Gets the colour from the string provided.
   * 
   * @param colour The string representing the name of the colour.
   * @return The colour obtained.
   */
  private static Colours getColourFromString(String colour) {

    switch (colour) {
      case "red":
        return Colours.RED;
      case "yellow":
        return Colours.YELLOW;
      case "green":
        return Colours.GREEN;
      case "blue":
        return Colours.BLUE;
      default:
        // invalid colour, throw error
        throw new InputMismatchException("Invalid colour");
    }
  }

  /**
   * Gets the colour from the string provided.
   * 
   * @param colour The string representing the name of the colour.
   * @return The colour obtained.
   */
  private static String getColourNameFromColour(Colours colour) {

    switch (colour) {
      case RED:
        return "red";
      case YELLOW:
        return "yellow";
      case GREEN:
        return "green";
      case BLUE:
        return "blue";
      default:
        // invalid colour, throw error
        throw new InputMismatchException("Invalid colour");
    }

  }

  /**
   * Saves the game state to a json file for loading later. Needs to save all the
   * things that can change.
   * @param maze the maze to be saved.
   * @param file the file to be saved to.
   */
  public static void saveGameState(Maze maze, File file) {
    // generate board arrays first

    JsonArrayBuilder lockedDoorsBuilder = Json.createArrayBuilder();
    JsonArrayBuilder keysBuilder = Json.createArrayBuilder();
    JsonArrayBuilder treasuresBuilder = Json.createArrayBuilder();

    Tile[][] board = maze.getBoard();

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] instanceof DoorTile) {
          DoorTile lockedDoor = (DoorTile) board[i][j];

          JsonObject lockedDoorJson = Json.createObjectBuilder()
              .add("x", i)
              .add("y", j)
              .add("colour", getColourNameFromColour(lockedDoor.getDoorColour()))
              .build();
          
          lockedDoorsBuilder.add(lockedDoorJson);
        } else if (board[i][j] instanceof KeyTile) {
          KeyTile key = (KeyTile) board[i][j];

          JsonObject keyJson = Json.createObjectBuilder()
    		  .add("x", i)
    		  .add("y", j)
              .add("colour", getColourNameFromColour(key.getKeyColour()))
              .build();

          keysBuilder.add(keyJson);
        } else if (board[i][j] instanceof TreasureTile) {
          JsonObject treasureJson = Json.createObjectBuilder()
              .add("x", i)
              .add("y", j)
              .build();

          treasuresBuilder.add(treasureJson);
        }
      }
    }

    JsonObject chapPosition = Json.createObjectBuilder()
        .add("x", maze.getChapPosition().getX())
        .add("y", maze.getChapPosition().getY())
        .build();

    JsonArrayBuilder chapInventoryArray = Json.createArrayBuilder();
    for (Item item : maze.getChap().getKeyInventory()) {

      JsonObject object = null;
      if (item instanceof Key) {
        object = Json.createObjectBuilder()
            .add("item_type", "key")
            .add("colour", getColourNameFromColour(((Key) item).getKeyColour()))
            .build();
      }
      if (object != null) {
        chapInventoryArray.add(object);
      }
    }

    JsonObject chap = Json.createObjectBuilder()
        .add("position", chapPosition)
        .add("inventory", chapInventoryArray)
        .build();

    JsonObject level = Json.createObjectBuilder()
        .add("level_name", "level1")
        .add("locked_doors", lockedDoorsBuilder.build())
        .add("keys", keysBuilder.build())
        .add("treasures", treasuresBuilder.build())
        .add("chap", chap)
        .build();

    try {
      PrintWriter writer = new PrintWriter(file);

      writer.write(level.toString());

      writer.close();
    } catch (FileNotFoundException e) {
      // invalid file
    }
  }

  /**
   * Loads a game state from a file.
   * 
   * @param gameStateFile the state file to load.
   * @return the maze loaded from the game state.
   */
  public static Maze loadGameState(File gameStateFile) {
    try {
      JsonReader reader = Json.createReader(new FileReader(gameStateFile));

      JsonObject gameState = reader.readObject();

      String levelName = "levels/" + gameState.getString("level_name") + ".json";

      File levelFile = new File(levelName);

      Maze maze = loadLevelFromFile(levelFile);

      Map<Point, TreasureTile> treasures = new HashMap<>();
      Map<Point, KeyTile> keys = new HashMap<>();
      Map<Point, DoorTile> doors = new HashMap<>();

      JsonArray treasuresArray = gameState.getJsonArray("treasures");

      for (JsonValue treasureValue : treasuresArray) {
        JsonObject treasure = treasureValue.asJsonObject();

        int treasureX = treasure.getInt("x");
        int treasureY = treasure.getInt("y");

        treasures.put(new Point(treasureX, treasureY), new TreasureTile());
      }

      JsonArray keysArray = gameState.getJsonArray("keys");

      for (JsonValue keyValue : keysArray) {
        JsonObject key = keyValue.asJsonObject();

        int keyX = key.getInt("x");
        int keyY = key.getInt("y");

        Colours keyColour = getColourFromString(key.getString("colour"));

        keys.put(new Point(keyX, keyY), new KeyTile(keyColour));
      }

      JsonArray lockedDoorsArray = gameState.getJsonArray("locked_doors");

      for (JsonValue doorValue : lockedDoorsArray) {
        JsonObject door = doorValue.asJsonObject();

        int doorX = door.getInt("x");
        int doorY = door.getInt("y");

        Colours doorColour = getColourFromString(door.getString("colour"));

        doors.put(new Point(doorX, doorY), new DoorTile(doorColour));
      }

      Tile[][] board = maze.getBoard();

      for (int x=0;x<board.length;x++) {
        for (int y=0;y<board[x].length;y++) {
          if (board[x][y] instanceof TreasureTile) {
            if (!treasures.containsKey(new Point(x, y))) {
              board[x][y] = new FreeTile();
            }
          } else if (board[x][y] instanceof KeyTile) {
            if (!keys.containsKey(new Point(x, y))) {
              board[x][y] = new FreeTile();
            }
          } else if (board[x][y] instanceof DoorTile) {
            if (!doors.containsKey(new Point(x, y))) {
              board[x][y] = new FreeTile();
            }
          }
        }
      }

      //move chap to correct position
      Chap chap = maze.getChap();
      Point chapStartPos = chap.getEntityPosition();

      ((AccessibleTile)board[chapStartPos.x][chapStartPos.y]).setEntityHere(null);

      JsonObject chapObject = gameState.getJsonObject("chap");
      JsonObject chapPosObj = chapObject.getJsonObject("position");

      Point chapPos = new Point(chapPosObj.getInt("x"), chapPosObj.getInt("y"));

      ((AccessibleTile)board[chapPos.x][chapPos.y]).setEntityHere(chap);
      chap.setEntityPosition(chapPos);

      JsonArray inventory = chapObject.getJsonArray("inventory");
      for (JsonValue keyValue : inventory) {
        Key key = new Key(getColourFromString(keyValue.asJsonObject().getString("colour")));
        chap.addToKeyInven(key);
      }

      return maze;

    } catch (FileNotFoundException e) {
      // error reading file
    }
    return null;
  }
}
