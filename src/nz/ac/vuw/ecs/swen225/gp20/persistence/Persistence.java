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
import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.KeyTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.TreasureTile;

/**
 * A class for the saving and loading of the game state for the game.
 *
 * @author Tristan
 */
public class Persistence {

  /**
   * A helper method for getting an array of the TileObjects from a json
   * levelobject.
   *
   * @param levelObject The object containing the jsonarray of object.
   * @param levelKey    The name/key corresponding to the name of the objects.
   * @param colour      Whether the tileobject has colour.
   * @return An array of TileObjects.
   */
  protected static TileObject[] getObjectValues(JsonObject levelObject, String levelKey, boolean colour) {
    // load keys
    JsonArray objects = levelObject.getJsonArray(levelKey);

    TileObject[] objArray = new TileObject[objects.size()];

    for (int i = 0; i < objects.size(); i++) {
      JsonObject object = objects.getJsonObject(i);

      int objectX = object.getInt("x");
      int objectY = object.getInt("y");

      if (colour) {
        Colour objColour = getColourFromString(object.getString("colour"));
        objArray[i] = new TileObject(objectX, objectY, objColour);
      } else {
        objArray[i] = new TileObject(objectX, objectY);
      }
    }

    return objArray;
  }

  /**
   * Gets the colour from the string provided.
   * 
   * @param colour The string representing the name of the colour.
   * @return The colour obtained.
   */
  protected static Colour getColourFromString(String colour) {

    switch (colour) {
      case "red":
        return Colour.RED;
      case "yellow":
        return Colour.YELLOW;
      case "green":
        return Colour.GREEN;
      case "blue":
        return Colour.BLUE;
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
  protected static String getColourNameFromColour(Colour colour) {

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
   * Saves the current game state to a json file for loading later. Needs to save
   * all the things that can change in the game.
   *
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

          JsonObject lockedDoorJson = Json.createObjectBuilder().add("x", i).add("y", j)
              .add("colour", getColourNameFromColour(lockedDoor.getDoorColour())).build();

          lockedDoorsBuilder.add(lockedDoorJson);
        } else if (board[i][j] instanceof KeyTile) {
          KeyTile key = (KeyTile) board[i][j];

          JsonObject keyJson = Json.createObjectBuilder().add("x", i).add("y", j)
              .add("colour", getColourNameFromColour(key.getKeyColour())).build();

          keysBuilder.add(keyJson);
        } else if (board[i][j] instanceof TreasureTile) {
          JsonObject treasureJson = Json.createObjectBuilder().add("x", i).add("y", j).build();

          treasuresBuilder.add(treasureJson);
        }
      }
    }

    JsonObject chapPosition = Json.createObjectBuilder().add("x", maze.getChapPosition().getX())
        .add("y", maze.getChapPosition().getY()).build();

    JsonArrayBuilder chapInventoryArray = Json.createArrayBuilder();
    for (Colour key : maze.getChap().getKeyInventory().keySet()) {

      JsonObject object = Json.createObjectBuilder()
          .add("key_colour", getColourNameFromColour(key))
          .add("number", maze.getChap().getKeyInventory().get(key))
          .build();

      chapInventoryArray.add(object);
    }

    JsonObject chap = Json.createObjectBuilder()
        .add("position", chapPosition)
        .add("inventory", chapInventoryArray)
        .build();
    
    JsonArrayBuilder actorArray = Json.createArrayBuilder();

    for (NPC npc : maze.getNpcs()) {
      JsonObject npcObj = Json.createObjectBuilder()
        .add("x", npc.entityPosition.x)
        .add("y", npc.entityPosition.y)
        .add("pathIndex", npc.getCurrentMoveIndex())
        .build();
      
      actorArray.add(npcObj);
    }

    JsonObject level = Json.createObjectBuilder()
        .add("level_number", maze.getLevelNumber())
        .add("locked_doors", lockedDoorsBuilder.build())
        .add("keys", keysBuilder.build())
        .add("treasures", treasuresBuilder.build())
        .add("actors", actorArray.build())
        .add("chap", chap)
        .add("time_left", maze.getTimeLeft())
        .build();

    try {
      PrintWriter writer = new PrintWriter(file);

      writer.write(level.toString());

      writer.close();
    } catch (FileNotFoundException e) {
      // invalid file
      e.printStackTrace();
    }
  }
  
  /**
   * Loads a game state from the game state file, which contains which number level file to be used as a base.
   * 
   * @param gameStateFile the state file to load.
   * @return the maze loaded from the game state.
   */
  public static Maze loadGameState(File gameStateFile) {
    
      JsonReader reader;
      try {
        reader = Json.createReader(new FileReader(gameStateFile));
      
        int levelNum = reader.readObject().getInt("level_number");
        
        File mazeFile = new File("levels/level" + levelNum + ".json");
        
        return loadGameState(gameStateFile, mazeFile);
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      return null;
  }

  /**
   * Loads a game state from the game state file and the base maze file.
   * 
   * @param gameStateFile the state file to load.
   * @param baseMazeFile the base maze to be altered from.
   * @return the maze loaded from the game state.
   */
  public static Maze loadGameState(File gameStateFile, File baseMazeFile) {
    try {
      JsonReader reader = Json.createReader(new FileReader(gameStateFile));

      JsonObject gameState = reader.readObject();

      Map<Point, TreasureTile> treasures = new HashMap<>();
      Map<Point, KeyTile> keys = new HashMap<>();
      Map<Point, DoorTile> doors = new HashMap<>();

      // load treasures still on map
      TileObject[] treasureTiles = getObjectValues(gameState, "treasures", false);

      for (TileObject treasure : treasureTiles) {
        treasures.put(new Point(treasure.x, treasure.y), new TreasureTile());
      }

      // load keys still on map
      TileObject[] keyTiles = getObjectValues(gameState, "keys", true);

      for (TileObject key : keyTiles) {
        keys.put(new Point(key.x, key.y), new KeyTile(key.colour));
      }

      // load doors still on map
      TileObject[] doorTiles = getObjectValues(gameState, "locked_doors", true);

      for (TileObject door : doorTiles) {
        doors.put(new Point(door.x, door.y), new DoorTile(door.colour));
      }

      int treasuresPickedUp = 0;
      
      Maze maze = Levels.loadLevelFromFile(baseMazeFile);

      Tile[][] board = maze.getBoard();

      for (int x = 0; x < board.length; x++) {
        for (int y = 0; y < board[x].length; y++) {
          if (board[x][y] instanceof TreasureTile) {
            if (!treasures.containsKey(new Point(x, y))) {
              board[x][y] = new FreeTile();
              // this treasure originally on the level has been picked up
              treasuresPickedUp++;
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

      // move chap to correct position
      Chap chap = maze.getChap();
      Point chapStartPos = chap.getEntityPosition();

      ((AccessibleTile) board[chapStartPos.x][chapStartPos.y]).setEntityHere(null);

      JsonObject chapObject = gameState.getJsonObject("chap");
      JsonObject chapPosObj = chapObject.getJsonObject("position");

      Point chapPos = new Point(chapPosObj.getInt("x"), chapPosObj.getInt("y"));

      ((AccessibleTile) board[chapPos.x][chapPos.y]).setEntityHere(chap);
      chap.setEntityPosition(chapPos);

      JsonArray inventory = chapObject.getJsonArray("inventory");
      for (JsonValue keyValue : inventory) {
        Colour key = getColourFromString(keyValue.asJsonObject().getString("key_colour"));
        int keyNum = keyValue.asJsonObject().getInt("number");
        for (int i=0;i<keyNum;i++) {
          chap.addToKeyInven(key);
        }
      }
      
      JsonArray actors = gameState.getJsonArray("actors");
      for (int i=0;i<actors.size();i++) {
        JsonObject actorObj = actors.get(i).asJsonObject();
        
        int x = actorObj.getInt("x");
        int y = actorObj.getInt("y");
        
        int pathIndex = actorObj.getInt("pathIndex");
        
        NPC npc = maze.getNpcs().get(i);
        
        npc.setEntityPosition(new Point(x, y));
        npc.setCurrentMoveIndex(pathIndex);
      }

      int timeLeft = gameState.getInt("time_left");
      maze.setTimeLeft(timeLeft);

      maze.setTreasuresPickedUp(treasuresPickedUp);

      return maze;

    } catch (FileNotFoundException e) {
      // error reading file
      e.printStackTrace();
    }
    return null;
  }
}
