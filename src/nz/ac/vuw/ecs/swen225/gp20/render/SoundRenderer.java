package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        if(move == Moves.MOVE || move == Moves.ERROR){
            // Don't play sounds for these moves as they occur too often.
            return;
        }

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
            e.printStackTrace();
        }
    }
}