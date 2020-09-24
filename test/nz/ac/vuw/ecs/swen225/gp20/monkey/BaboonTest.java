package nz.ac.vuw.ecs.swen225.gp20.monkey;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Direction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaboonTest {

    static MonkeyAI baboon;
    Main main;

    @BeforeAll
    static void beforeAll() {
        baboon = new Baboon();
    }

    @BeforeEach
    void setUp() {
        //Set up new maze
        main = new Main();
        /*
        main.createMaze();
        */
    }

    @Test
    void exampleTest() {
        for (int i = 0; i < 10_000; i++) {
            //TODO check game still valid somehow
            while (true) {
                Direction direction = baboon.selectMove(main.getMaze());
                main.movePlayer(direction);
            }
        }
    }
}