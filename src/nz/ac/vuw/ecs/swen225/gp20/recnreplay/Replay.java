package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import javax.json.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Replay extends GUI{



    private List<String> recordedMoves;
    private int playbackDelay = 1;
    private JsonObject loadedActions;
    private Maze maze;
    private File file;

    public Replay(File file) {
        this.file = file;
    }

    public Replay(Maze maze) {
        this.maze = maze;
    }

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
