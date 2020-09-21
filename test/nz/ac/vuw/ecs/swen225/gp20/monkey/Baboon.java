package nz.ac.vuw.ecs.swen225.gp20.monkey;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import java.util.Random;

import static nz.ac.vuw.ecs.swen225.gp20.maze.Maze.*;

/**
 * An AI that plays based on purely random inputs.
 */
public class Baboon extends MonkeyAI {

    /**
     * Instantiates a new Baboon model AI with preset reward weightings.
     */
    public Baboon() {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public Direction selectMove(Maze maze) {
        int number = new Random().nextInt(Direction.values().length - 1);   //Get random ordinal
        return Direction.values()[number];                                          //Return direction from ordinal
    }
}
