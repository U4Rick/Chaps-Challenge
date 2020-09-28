package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import javax.json.*;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Replay extends GUI{

    private List<String> recordedMoves;
    private List<String> recordedItems;
    private int playbackDelay;
    private JsonObject loadedActions;

//    public void getRecordedMove() {
//        for (String move : recordedMoves) {
//            switch (move) {
//                case "up":
//                    move = "up";
//                case "right":
//                    move = "right";
//                case "down":
//                    move = "down";
//                case "left":
//                    move = "left";
//                default:
//                    break;
//            }
//        }
//    }

//    public void getRecordedItem() {
//        for (String item : recordedItems) {
//            switch (item) {
//                case "key":
//                    item = "key";   //todo handle coloured keys/doors/exit lock etc
//                case "treasure":
//                    item = "treasure";
//                default:
//                    break;
//            }
//        }
//    }

    public void loadFile() {
        try {
            InputStream fis = new FileInputStream("json_data.txt");

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
                case "down":
                    super.movePlayer(Maze.Direction.DOWN);
                    break;
                case "right":
                    super.movePlayer(Maze.Direction.RIGHT);
                    break;
                case "up":
                    super.movePlayer(Maze.Direction.UP);
                    break;
                case "left":
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
    public Maze getMaze() {     //todo implement
        return null;
    }
}
