package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Testing for saving/loading game state.
 * @author Tristan
 *
 */
class PersistenceTest {

    @Test
    public void Test_saveState(@TempDir Path path) throws IOException {
      File levelFile = LevelsTest.createTestFile(path);
      Maze maze = Levels.loadLevelFromFile(levelFile);
      
      maze.getChap().setEntityPosition(new Point(5, 0));
      
      maze.setTimeLeft(43);
      
      //pick up treasure
      maze.pickUpItem(new Point(0, 8));
      //pick up key
      maze.pickUpItem(new Point(7, 2));
      //unlock door
      maze.getChap().unlockDoor(new Point(4, 3), maze);
      
      File gameStateFile = File.createTempFile("gameState", ".json", path.toFile());
      Persistence.saveGameState(maze, gameStateFile);
      
      Maze maze2 = Persistence.loadGameState(gameStateFile, levelFile);
        
      assert(maze2.getBoard()[4][3] instanceof FreeTile);
        
        

    }
}