package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.Baboon;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.MonkeyAI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class BaboonTest {

    static MonkeyAI baboon;
    static Main main;

    @BeforeAll
    static void beforeAll() {
        baboon = new Baboon();
    }

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    void exampleTest() {
        //TODO check game still valid somehow
        for (int i = 0; i < 100_000; i++) {
            Direction direction = baboon.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }

    @Test
    void exampleTimeDelayTest() {
        //TODO check game still valid somehow
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Direction direction = baboon.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }
}