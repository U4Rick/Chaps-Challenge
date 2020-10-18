package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Levels;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Replay;

import java.io.FileNotFoundException;


/**
 * Run the game.
 *
 * @author Keely Haskett
 */
public class Main extends GUI {

    Maze maze;
    Replay replay;
    Record record;

    /**
     * Run the game.
     * @param args Runtime arguments.
     */
    public static void main(String[] args) { Main main  =  new Main(); }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    protected void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    protected Record getRecord() {
        return record;
    }

    @Override
    protected void setRecord(Record record) {
        this.record = record;
    }

    @Override
    protected Replay getReplay() {
        return replay;
    }

    @Override
    protected void setReplay(Replay replay) {
        this.replay = replay;
    }
}
