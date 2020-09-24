package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.io.StringReader;
import java.util.List;

public class Replay {

    private List<String> recordedMoves;
    private int playbackDelay;

    public void getRecordedMoves(){
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



    public void loadFile(String jsonString){
        JFileChooser jfc = new JFileChooser();
        jfc.showSaveDialog(null);

        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();
    }

    public int getPlaybackDelay() {
        return playbackDelay;
    }

    public void setPlaybackDelay(int playbackDelay) {
        this.playbackDelay = playbackDelay;
    }
}
