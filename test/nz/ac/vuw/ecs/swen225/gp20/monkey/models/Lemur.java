package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

/**
 * An AI that picks up keys but ignores treasures, while trying to reach the exit.
 *
 * @author Matthew Hill 300507607
 */
public class Lemur extends MonkeyAI {

    /**
     * Instantiates a new Lemur model MonkeyAI with preset reward weightings.
     */
    public Lemur() {
        super(50,
                100,
                0,
                100,
                0,
                60,
                100,
                0);
    }
}
