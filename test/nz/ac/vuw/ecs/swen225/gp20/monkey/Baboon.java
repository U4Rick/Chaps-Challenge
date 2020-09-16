package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * An AI that plays based on purely random inputs.
 */
public class Baboon implements MonkeyAI{

    @Override
    public int utilityFunction(Object move) {
        return (int) (Math.random() * 100);
    }

    @Override
    public Object selectMove(Object currentPosition) {
        return null;
    }
}
