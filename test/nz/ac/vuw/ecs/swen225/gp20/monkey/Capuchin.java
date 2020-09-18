package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * An AI that tries to play the game intelligently.
 */
public class Capuchin extends MonkeyAI {

    /**
     * Instantiates a new Capuchin model MonkeyAI with preset reward weightings.
     */
    public Capuchin() {
        super(40, 0, 70, 70, 70, 0, 100);
    }
}
