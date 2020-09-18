package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * An AI that avoids keys and treasures but still tries to open doors and reach the exit.
 */
public class Lemur extends MonkeyAI {

    /**
     * Instantiates a new Lemur model MonkeyAI with preset reward weightings.
     */
    public Lemur() {
        super(20, 0, 0, 0, 0, 100, 100);
    }
}
