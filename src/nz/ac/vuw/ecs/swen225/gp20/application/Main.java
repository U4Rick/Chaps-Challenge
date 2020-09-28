package nz.ac.vuw.ecs.swen225.gp20.application;


import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import java.io.File;

import static nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence.loadLevel;

public class Main extends GUI {

    Maze maze;

    private void run() {
        createMaze();
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
        if(this.maze == null){
            createMaze();
        }
        return maze;
    }

    public void createMaze() {
        this.maze = loadLevel(1);
    }
}
