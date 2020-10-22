package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * Class to create a replay of the movements made by the player for the previous level.
 *
 * @author Ricky McLean
 */
public class Replay {

    private List<String> recordedMoves;
    private JsonObject loadedActions;
    public int currentLevel;

    private File file;

    /**
     * Constructs the replay with relevant maze.
     */
    public Replay(File file) {
        if (file != null) {
            this.file = file;
        }
    }

    /**
     * Loads in a file to play a replay from and gets the level number.
     *
     * @param file containing replay json
     */
    public void loadFile(File file) {
        try {
            InputStream fis = new FileInputStream(file);

            JsonReader reader = Json.createReader(fis); //reads in json for the level and movements
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
     *
     * @return list of String directions to move chap
     */
    public List<String> processActionsJson() {
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
