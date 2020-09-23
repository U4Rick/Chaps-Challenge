package nz.ac.vuw.ecs.swen225.gp20.application;


import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

public class Main extends GUI {

    Maze maze;

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
}
