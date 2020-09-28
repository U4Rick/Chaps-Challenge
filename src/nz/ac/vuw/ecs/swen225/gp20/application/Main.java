package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnreplay.Replay;

import static nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence.loadLevel;

public class Main extends GUI {

    Maze maze;
    Replay replay;
    Record record;

    private void run() {
        while (true) {

        }

    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Main main  =  new Main();
        main.run();
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    public void createMaze() {
        this.maze = loadLevel(1);
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
