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
public class Replay extends GUI{

    private List<String> recordedMoves;
    private int playbackDelay = 1;
    private JsonObject loadedActions;
    private Maze maze;

    /**
     * Constructs the replay with relevant maze.
     * @param maze of the associated level
     */
    public Replay(Maze maze) {
        this.maze = maze;
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
    public void processActionsJson(){
        JsonArray moves = loadedActions.getJsonArray("move");

        for (JsonValue jsonMove : moves) {
            JsonObject move = jsonMove.asJsonObject();
            String direction = move.getString("move");
            switch (direction) {
                case "DOWN":
                    super.movePlayer(Maze.Direction.DOWN);
                    break;
                case "RIGHT":
                    super.movePlayer(Maze.Direction.RIGHT);
                    break;
                case "UP":
                    super.movePlayer(Maze.Direction.UP);
                    break;
                case "LEFT":
                    super.movePlayer(Maze.Direction.LEFT);
                    break;
            }

        }

    }

    public int getPlaybackDelay() {
        return playbackDelay;
    }

    public void setPlaybackDelay(int playbackDelay) {
        this.playbackDelay = playbackDelay;
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }
}
