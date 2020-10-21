
package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import javax.json.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a replay of the movements made by the player for the previous level.
 * @author Ricky McLean
 */
public class Replay{

    private List<String> recordedMoves;
    private JsonObject loadedActions;
    public int currentLevel;

    private File file;

    /**
     * Constructs the replay with relevant maze.
     *
     */
    public Replay(File file) {
        if (file != null) {
            this.file = file;
        }
    }

    /**
     * Loads in a file to play a replay from and gets the level number.
     * @param file containing replay json
     */
    public void loadFile(File file) {
        try {
            InputStream fis = new FileInputStream(file);

            JsonReader reader = Json.createReader(fis);     //reads in json replay for the level and movements
            loadedActions = reader.readObject();

            JsonArray tempArr = loadedActions.getJsonArray("levels");
            for (JsonValue jsonLevel : tempArr) {
                JsonObject levelArr = jsonLevel.asJsonObject(); //loads the level file name
                currentLevel = levelArr.getInt("level");
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the actions of the loaded json file.
     * @return list of String directions to move chap
     */
    public List<String> processActionsJson(){
        recordedMoves = new ArrayList<>();
        currentLevel = 0;

        JsonArray moves = loadedActions.getJsonArray("moves");      //load in json arrays

        for (JsonValue jsonMove : moves) {
            JsonObject move = jsonMove.asJsonObject();
            String direction = move.getString("move");  //loads the moves to make
            recordedMoves.add(direction);
        }

        return recordedMoves;
    }

}
