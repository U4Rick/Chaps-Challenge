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
public class SoundRenderer implements LineListener {
    Map<Moves, String> filenameMap = new HashMap<>();
    boolean playCompleted;

    /**
     * A constructor that loads the .wav files into a map.
     */
    public SoundRenderer(){
        // TODO: get sounds for KEY_PICKUP, ERROR, CHAP_WIN
        for(Moves m : Moves.values()){
            filenameMap.put(m, "resources/sounds/" + m.toString() + ".wav");
        }
        // TODO: just use string concat to set filepath in playSound() instead of map?
    }

    /**
     * Play the sound correlating to the move that occurred.
     *
     * @param move the move that the sound is representing.
     */
    public void playSound(Moves move) {
        if (true){
            return; // TODO: for during testing, remove when working.
        }
        String filepath = filenameMap.get(move);

        try {
            File audioFile = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(audioStream);
            long duration = clip.getMicrosecondLength()/1000; // Convert to milliseconds.
            clip.start();

            // Wait for sound to finish playing.
            try {
                Thread.sleep(duration + 50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            /*while (!playCompleted) {
                try {
                    Thread.sleep(50); // TODO: check if this affects anything
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }*/

            clip.close();
            System.out.println("closed");

        } catch (UnsupportedAudioFileException ex) { // TODO: remove sout and merge catches?
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started."); // TODO: remove prints
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}