package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.Gorilla;
import nz.ac.vuw.ecs.swen225.gp20.monkey.models.MonkeyAI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Testing suite for the Gorilla model AI, which tries to push through doors and walls.
 *
 * @author Matt
 */
public class GorillaTest {

    private static MonkeyAI monkeyAI;
    private Main main;

    @BeforeAll
    static void beforeAll() {
        monkeyAI = new Gorilla();
    }

    @BeforeEach
    void setUp() {
        main = new Main();
        main.setDebugMode(true);
    }

    @Test
    void exampleTest() {
        for (int i = 0; i < 1_000_000; i++) {
            Direction direction = monkeyAI.selectMove(main.getMaze());
            main.movePlayer(direction);
        }
    }
}

