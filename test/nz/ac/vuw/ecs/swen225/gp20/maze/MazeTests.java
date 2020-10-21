package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.commons.Moves;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static nz.ac.vuw.ecs.swen225.gp20.commons.Colour.GREEN;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    private Main main;

    //setting up level 1 to test stuff
    @BeforeEach
    void setUp() {
        main = new Main();
        main.setDebugMode(true);
    }


    //testing chap moving to accessible tile
    @Test
    public void moveTest_up() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.UP);
        assert(maze.getChapPosition().equals(new Point(1,0)));
    }

    @Test
    public void moveTest_left() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.LEFT);
        assert(maze.getChapPosition().equals(new Point(0,1)));
    }

    @Test
    public void moveTest_right() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.RIGHT);
        assert(maze.getChapPosition().equals(new Point(2,1)));
    }

    @Test
    public void moveTest_down() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.RIGHT);
        assert(maze.getChapPosition().equals(new Point(2,1)));
    }

    //check if go out of bounds
    @Test
    public void moveTest_outOfBounds() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.RIGHT);
        assertThrows(IllegalStateException.class, () -> maze.moveChap(Direction.RIGHT));
    }

    //winning the game
    @Test
    public void moveTest_win() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(1,2);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.DOWN);
        assert(maze.getChapWin());
        assert(!maze.getChapLose());
    }

    //picking up a key
    @Test
    public void moveTest_pickUpKey() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        board[0][0] = new KeyTile(GREEN);
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.UP);
        maze.moveChap(Direction.LEFT);

        assert(maze.getChap().getKeyInventory().get(GREEN) == 1);
    }

    //picking up a treasure
    @Test
    public void moveTest_pickUpTreasure() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        board[1][0] = new TreasureTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 1, 30, board, null);
        maze.moveChap(Direction.UP);

        assert(maze.getTreasuresPickedUp() == 1);
        assert(maze.getBoard()[1][0] instanceof FreeTile);
    }

    //unlocking door
    @Test
    public void moveTest_unlockDoor() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        board[0][0] = new KeyTile(GREEN);
        board[1][0] = new DoorTile(GREEN);
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.LEFT);
        maze.moveChap(Direction.UP);
        maze.moveChap(Direction.RIGHT);
    }

    //unlocking exit lock
    @Test
    public void moveTest_unlockExitLock() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        board[1][0] = new TreasureTile();
        board[2][0] = new ExitLockTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 1, 30, board, null);
        assert(maze.getBoard()[2][0] instanceof ExitLockTile);
        maze.moveChap(Direction.UP);
        assert(maze.getBoard()[2][0] instanceof FreeTile);
    }

    //check if chap done correct move
    @Test
    public void moveTest_move() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[exitLocation.x][exitLocation.y] = new ExitTile();
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.UP);
        assert(maze.getChapCurrentMove() == Moves.MOVE);
    }

    //check that info tile storing correct info
    @Test
    public void tileTest_checkInfo() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[0][1] = new InfoTile("HALLO!");
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.UP);
        assert(((InfoTile)maze.getBoard()[0][1]).getInformation().equals("HALLO!"));
    }

    //test decay tile
    @Test
    public void tileTest_decayTile() throws IOException {
        //create 3x3 board of just free tiles and one exit tile
        Tile[][] board = new Tile[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = new FreeTile();
            }
        }
        Point chapLocation = new Point(1,1);
        Point exitLocation = new Point(0,0);
        board[1][0] = new DecayTile(2);
        Maze maze = new Maze(0, chapLocation, exitLocation, 0, 30, board, null);
        maze.moveChap(Direction.UP);
        assert(((DecayTile)maze.getBoard()[1][0]).getDecayLevel() == 1);
        maze.moveChap(Direction.LEFT);
        maze.moveChap(Direction.RIGHT);
        assert(((DecayTile)maze.getBoard()[1][0]).getDecayLevel() == 0);
        maze.moveChap(Direction.LEFT);
        assert(maze.getBoard()[1][0] instanceof WallTile);
    }
}