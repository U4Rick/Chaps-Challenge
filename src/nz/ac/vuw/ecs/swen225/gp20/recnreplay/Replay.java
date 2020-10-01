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
    private int playbackDelay = 1;
    private JsonObject loadedActions;
    private File file;
    private Maze maze;

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
     * Loads in a file to play a replay from.
     * @param file containing replay json
     */
    public void loadFile(File file) {
        try {
            InputStream fis = new FileInputStream(file);

            JsonReader reader = Json.createReader(fis);     //reads in json
            loadedActions = reader.readObject();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Processes the actions of the loaded json file.
     */
    public List<String> processActionsJson(){


        JsonArray moves = loadedActions.getJsonArray("moves");

        for (JsonValue jsonMove : moves) {
            JsonObject move = jsonMove.asJsonObject();
            String direction = move.getString("move");
            recordedMoves.add(direction);
        }
        return recordedMoves;

    }

    /**
     * Get the delay speed for replaying actions.
     * @return the delay
     */
    public int getPlaybackDelay() {
        return playbackDelay;
    }

    /**
     * Set the delay speed for replaying actions.
     * @param playbackDelay
     */
    public void setPlaybackDelay(int playbackDelay) {
        this.playbackDelay = playbackDelay;
    }

}
