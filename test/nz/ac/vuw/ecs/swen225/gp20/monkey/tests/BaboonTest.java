package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.Baboon;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.MonkeyAI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaboonTest {

    static MonkeyAI baboon;
    static Main main;

    @BeforeAll
    static void beforeAll() {
        baboon = new Baboon();
    }

    @BeforeEach
    void setUp() {
        //Set up new maze
        main = new Main();
        main.createMaze();
    }

    @Test
    void exampleTest() {
        //TODO check game still valid somehow
        for (int i = 0; i < 10; i++) {
            Direction direction = baboon.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }
}