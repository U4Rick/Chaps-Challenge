package nz.ac.vuw.ecs.swen225.gp20.monkey;

import java.util.Random;

/**
 * An AI that plays based on purely random inputs.
 */
public class Baboon extends MonkeyAI {

    @Override
    public int utilityFunction(Object move) {
        return new Random().nextInt(100);
    }
}
