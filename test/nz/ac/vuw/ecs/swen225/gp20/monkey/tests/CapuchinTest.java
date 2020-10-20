package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.Capuchin;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.MonkeyAI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


/**
 * Testing suite for the Capuchin model AI, which tries to play the game properly.
 *
 * @author Matt
 */
public class CapuchinTest {

    private static MonkeyAI monkeyAI;
    private Main main;

    @BeforeAll
    static void beforeAll() {
        monkeyAI = new Capuchin();
    }

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    void exampleTest() {
        for (int i = 0; i < 1_000_000; i++) {
            Direction direction = monkeyAI.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }
}

