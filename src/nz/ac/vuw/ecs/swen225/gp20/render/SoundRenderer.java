package nz.ac.vuw.ecs.swen225.gp20.render;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundRenderer implements LineListener {
    Map<String, String> filenameMap = new HashMap<>();
    boolean playCompleted;

    public SoundRenderer(){
        // TODO: load filenames into map, mapped to something related to when it needs to be called.
        filenameMap.put("move", "resources/sounds/Move.wav");
        filenameMap.put("treasure", "resources/sounds/Treasure_Pick_Up.wav");
        filenameMap.put("unlock", "resources/sounds/Unlock_Door.wav");
        filenameMap.put("unlocked", "resources/sounds/Exit_Unlocked.wav");
        filenameMap.put("death", "resources/sounds/Death.wav");
    }

    public void play(/*String action*/){
        // TODO: get the sound that needs to be played from map?
//        playSound(filenameMap.get(action));
        playSound(filenameMap.get("move"));
//        playSound(filenameMap.get("treasure"));
//        playSound(filenameMap.get("unlock"));
//        playSound(filenameMap.get("unlocked"));
//        playSound(filenameMap.get("death"));
    }

    private void playSound(String filepath) {
        File audioFile = new File(filepath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(audioStream);
            clip.start();

            // Wait for sound to finish playing.
            while (!playCompleted) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            clip.close();

        } catch (UnsupportedAudioFileException ex) {
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
            System.out.println("Playback started.");
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}