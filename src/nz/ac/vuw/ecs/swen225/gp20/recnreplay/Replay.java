package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

public class Replay {

    private List<String> recordedMoves;
    private List<String> recordedItems;
    private int playbackDelay;

    public void getRecordedMove() {
        for (String move : recordedMoves) {
            switch (move) {
                case "up":
                    move = "up";
                case "right":
                    move = "right";
                case "down":
                    move = "down";
                case "left":
                    move = "left";
                default:
                    break;
            }
        }
    }

    public void getRecordedItem() {
        for (String item : recordedItems) {
            switch (item) {
                case "key":
                    item = "key";   //todo handle coloured keys/doors/exit lock etc
                case "treasure":
                    item = "treasure";
                default:
                    break;
            }
        }
    }

    public void loadFile(String jsonString) throws FileNotFoundException {
        InputStream fis = new FileInputStream("jsonData.txt");

        JsonReader reader = Json.createReader(fis);     //
        JsonObject Object = reader.readObject();

        reader.close();

    }

    public int getPlaybackDelay() {
        return playbackDelay;
    }

    public void setPlaybackDelay(int playbackDelay) {
        this.playbackDelay = playbackDelay;
    }
}
