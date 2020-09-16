package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * An AI that tries to push through walls where possible.
 */
public class Gorilla implements MonkeyAI {

    @Override
    public int utilityFunction(Object move) {
        //If move ends on inaccessible tile assign high weighting
        return 0;
    }

    @Override
    public Object selectMove(Object currentPosition) {
        return null;
    }
}
