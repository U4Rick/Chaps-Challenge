package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import java.util.Random;

/**
 * An AI that plays based on purely random inputs.
 *
 * @author Matthew Hill 300507607
 */
public class Baboon extends MonkeyAI {

    /**
     * Instantiates a new Baboon model AI with preset reward weightings.
     */
    public Baboon() {
        super(0,
                0,
                0,
                0,
                0,
                0,
                0,
                0);
    }

    @Override
    public Direction selectMove(Maze maze) {
        int number = new Random().nextInt(Direction.values().length);   //Get random ordinal
        return Direction.values()[number];                              //Return direction from ordinal
    }
}
