package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * An AI that tries to play the game intelligently.
 */
public class Capuchin implements MonkeyAI{

    @Override
    public int utilityFunction(Object move) {
        //assign high EV to treasures and exit tile
        return 0;
    }

    @Override
    public Object selectMove(Object currentPosition) {
        return null;
    }
}
