package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.Baboon;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.MonkeyAI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * Testing suite for the Baboon model AI, which uses purely random movement to play the game.
 *
 * @author Matt
 */
class BaboonTest {

    private static MonkeyAI monkeyAI;
    private Main main;

    @BeforeAll
    static void beforeAll() {
        monkeyAI = new Baboon();
    }

    @BeforeEach
    void setUp() {
        main = new Main();
        main.setDebugMode(true);
    }

    @Test
    void singleLongGame() {
        for (int i = 0; i < 100_000; i++) {
            if (main.getMaze().getChapWin()) {
                break;
            }
            Direction direction = monkeyAI.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }

    @Test
    void multipleShortGames() {
        for (int i = 0; i < 10; i++) {
            main = new Main();
            for (int j = 0; j < 10_000; j++) {
                if (main.getMaze().getChapWin()) {
                    break;
                }
                Direction direction = monkeyAI.selectMove(main.getMaze());
                main.movePlayer(direction);
            }
        }
    }

    @Test
    void visualTest() {
        for (int i = 0; i < 100; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Direction direction = monkeyAI.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }
}