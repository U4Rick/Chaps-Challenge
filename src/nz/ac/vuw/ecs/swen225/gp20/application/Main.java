package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Replay;


/**
 * Run the game.
 *
 * @author Keely Haskett 300473212
 */
public class Main extends GUI {

    private Maze maze;
    private Replay replay;
    private Record record;

    /**
     * Run the game.
     * @param args Runtime arguments.
     */
    public static void main(String[] args) { new Main(); }

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
