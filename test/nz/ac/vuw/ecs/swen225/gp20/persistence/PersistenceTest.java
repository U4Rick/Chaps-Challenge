package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceTest {


    @BeforeEach
    void setUp() {
    }


    @Test
    public void Test_success_01() {
        File testFile = createTestFile();

        Maze testMaze = Persistence.loadLevelFromFile(testFile);

        assert testMaze != null;
        Tile[][] board = testMaze.getBoard();

        assertTrue(board[0][0] instanceof WallTile);
    }

    /**
     * Creates a test file with a level for testing
     * @return the file created
     */
    private File createTestFile() {
        File testFile = new File("testLevel.json");
        try {
            FileWriter fw = new FileWriter(testFile);

            fw.write("{" +
                         "   \"name\": \"testLevel\"," +
                         "   \"width\": 11," +
                         "   \"height\": 11," +
                         "   \"walls\": [" +
                         "      {\"x\": 0, \"y\": 0}" +
                         "   ]," +
                         "   \"locked_doors\": []," +
                         "   \"keys\": []," +
                         "   \"exit\": {\"x\": 10, \"y\": 0}," +
                         "   \"starting_position\": {\"x\": 5, \"y\": 5}," +
                         "   \"exit_lock\": {\"x\": 9, \"y\": 0}," +
                         "   \"info\": {\"x\": 6, \"y\": 5}," +
                         "   \"treasures\": []" +
                         "}");

            fw.flush();

            fw.close();

            return testFile;

        } catch (IOException e) {
            fail();
        }

        return null;
    }
}