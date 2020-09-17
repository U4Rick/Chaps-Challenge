package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * An AI that tries to push through walls where possible.
 */
public class Gorilla extends MonkeyAI {

    /**
     * Instantiates a new Gorilla model MonkeyAI with preset reward weightings.
     */
    public Gorilla() {
        super(20, 100, 0, 0, 100, 10);
    }
}
