package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;

import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class PersistenceTest {

    @Test
    public void Test_saveState() throws IOException {
      File levelFile = LevelsTest.createTestFile();
      Maze maze = Levels.loadLevelFromFile(levelFile);
      
      maze.getChap().setEntityPosition(new Point(5, 0));
      
      maze.setTimeLeft(43);
      
      //pick up treasure
      maze.pickUpItem(new Point(0, 8));
      //pick up key
      maze.pickUpItem(new Point(7, 2));
      //unlock door
      maze.getChap().unlockDoor(new Point(4, 3), maze);
      
      File gameStateFile = LevelsTest.tempFolder.newFile("state.json");
      Persistence.saveGameState(maze, gameStateFile);
      
      Maze maze2 = Persistence.loadGameState(gameStateFile, levelFile);
        
      assert(maze2.getBoard()[4][3] instanceof FreeTile);
        
        

    }
}