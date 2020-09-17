package nz.ac.vuw.ecs.swen225.gp20.monkey;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class BaboonTest {

    static MonkeyAI baboon;

    @BeforeAll
    static void beforeAll() {
        baboon = new Baboon();
    }

    @BeforeEach
    void setUp() {
        //Set up new maze
    }

    @Test
    void pass() {
        assertTrue(true);
    }
}