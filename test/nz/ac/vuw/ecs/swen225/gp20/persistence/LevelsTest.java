package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LevelsTest {
    @Test
    public void Test_success_walls() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board[0][0] instanceof WallTile);
        assertTrue(board[1][0] instanceof WallTile);
    }

    @Test
    public void Test_success_size() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board.length == 11);
        assertTrue(board[0].length == 12);
    }

    @Test
    public void Test_success_lockedDoor() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board[4][3] instanceof DoorTile);
        assertTrue(((DoorTile)board[4][3]).getDoorColour() == Maze.Colours.BLUE);
    }

    @Test
    public void Test_success_keys() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board[7][2] instanceof KeyTile);
        assertTrue(((KeyTile)board[7][2]).getKeyColour() == Maze.Colours.BLUE);
    }

    @Test
    public void Test_success_variousTiles() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board[10][0] instanceof ExitTile);
        assertTrue(board[9][0] instanceof ExitLockTile);
        assertTrue(board[6][5] instanceof InfoTile);
    }

    @Test
    public void Test_success_chapPos() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board[5][5] instanceof FreeTile);
        assertTrue(((FreeTile)board[5][5]).getEntityHere() instanceof Chap);

        Maze testMaze = Levels.loadLevelFromFile(testFile);
        assert testMaze != null;
        assertTrue(testMaze.getChapPosition().equals(new Point(5, 5)));
    }

    @Test
    public void Test_success_treasures() {
        File testFile = createTestFile();
        Tile[][] board = getBoard(testFile);

        assertTrue(board[0][8] instanceof TreasureTile);
        assertTrue(board[1][8] instanceof TreasureTile);
    }

    /**
     *
     * @param file
     * @return
     */
    private Tile[][] getBoard(File file) {

        Maze testMaze = Levels.loadLevelFromFile(file);

        assert testMaze != null;
        Tile[][] board = testMaze.getBoard();

        return board;
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
                    "   \"number\": 1," +
                    "   \"width\": 11," +
                    "   \"height\": 12," +
                    "   \"time\": 90," +
                    "   \"walls\": [" +
                    "      {\"x\": 0, \"y\": 0}," +
                    "      {\"x\": 1, \"y\": 0}" +
                    "   ]," +
                    "   \"locked_doors\": [{\"x\": 4, \"y\": 3, \"colour\": \"blue\"}]," +
                    "   \"keys\": [{\"x\": 7, \"y\": 2, \"colour\": \"blue\"}]," +
                    "   \"exit\": {\"x\": 10, \"y\": 0}," +
                    "   \"starting_position\": {\"x\": 5, \"y\": 5}," +
                    "   \"exit_lock\": {\"x\": 9, \"y\": 0}," +
                    "   \"info\": {\"x\": 6, \"y\": 5}," +
                    "   \"treasures\": [" +
                    "      {\"x\": 0, \"y\": 8}," +
                    "      {\"x\": 1, \"y\": 8}" +
                    "   ]" +
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
