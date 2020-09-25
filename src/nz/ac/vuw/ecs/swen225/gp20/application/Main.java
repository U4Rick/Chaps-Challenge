package nz.ac.vuw.ecs.swen225.gp20.application;


import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import java.io.File;

import static nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence.readLevel;

public class Main extends GUI {

    Maze maze;

    private void run() {
        while (true) {

        }

    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Main main  =  new Main();
    }

    @Override
    public Maze getMaze() {
        if(maze == null){
            createMaze();
        }
        return maze;
    }

    public void createMaze() {
        maze = readLevel(new File("levels/level1.json"));
    }
}
