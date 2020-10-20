package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.*;

/**
 * A class to handle playing game sounds.
 *
 * @author Cherie
 */
public class SoundRenderer {
    Map<Moves, File> fileMap = new HashMap<>();

    /**
     * A constructor that loads the .wav files into a map with the moves they represent.
     */
    public SoundRenderer(){
        // TODO: get sounds for KEY_PICKUP, ERROR, CHAP_WIN
        for(Moves move : Moves.values()){
            fileMap.put(move, new File("resources/sounds/" + move.toString() + ".wav"));
        }
    }

    /**
     * Play the sound correlating to the move that occurred.
     *
     * @param move the move that the sound is representing.
     */
    public void playSound(Moves move) {
        try {
            // Create a clip from the file.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(fileMap.get(move));
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(audioIn);
            clip.start(); // Play the clip.
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP){
                    clip.close(); // Prevent memory leak.
                }
            });
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace(); // TODO: throw error?
        }
    }
}