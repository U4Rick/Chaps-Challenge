package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

/**
 * An AI that tries to push through walls where possible.
 */
public class Gorilla extends MonkeyAI {

    /**
     * Instantiates a new Gorilla model MonkeyAI with preset reward weightings.
     */
    public Gorilla() {
        super(50, 20, 20, 20, 0, 100, 100, 20, 100);
    }
}
