package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;

class PersistenceTest {

    @Test
    public void Test_saveState() {
        Maze maze = setupMaze();

        File file = new File("test");
        Persistence.saveGameState(maze, file);


    }

    private Maze setupMaze() {
        File file = LevelsTest.createTestFile();
        return Levels.loadLevelFromFile(file);
    }
}