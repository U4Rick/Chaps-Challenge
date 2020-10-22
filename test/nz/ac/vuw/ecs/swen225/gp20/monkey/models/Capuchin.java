package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

/**
 * An AI that tries to play the game intelligently.
 *
 * @author Matthew Hill 300507607
 */
public class Capuchin extends MonkeyAI {

    /**
     * Instantiates a new Capuchin model MonkeyAI with preset reward weightings.
     */
    public Capuchin() {
        super(40,
                80,
                80,
                100,
                0,
                0,
                90,
                0);
    }
}
