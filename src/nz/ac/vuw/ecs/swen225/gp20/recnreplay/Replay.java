package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.io.StringReader;

public class Replay {
    private int playbackDelay;

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
