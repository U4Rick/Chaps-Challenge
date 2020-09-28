package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.Gorilla;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.MonkeyAI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class GorillaTest {
    static MonkeyAI gorilla;
    static Main main;

    @BeforeAll
    static void beforeAll() {
        gorilla = new Gorilla();
    }

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    void exampleTest() {
        //TODO check game still valid somehow
        for (int i = 0; i < 100_000; i++) {
            Maze.Direction direction = gorilla.selectMove(main.getMaze());
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
            Maze.Direction direction = gorilla.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }
}

