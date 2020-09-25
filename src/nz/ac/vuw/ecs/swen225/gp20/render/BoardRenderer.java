package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import javax.swing.*;
import java.awt.*;

public class BoardRenderer extends JPanel {
//    private final int TILE_SIZE = 50; // pixels
    private final int DISPLAY_DIMENSION = 2; // rows/columns of viewer // FIXME: should be odd so Chap can be centred
    private final int BOARD_DIMENSION = 50; // rows/columns of entire board // TODO: get board dimension from maze
    private final int TILE_SIZE = 500/DISPLAY_DIMENSION; // FIXME: constant is temp
    private final Maze maze;

    public BoardRenderer(Maze maze){
        // FIXME: display and tile size relying on each other
        Dimension size = new Dimension(DISPLAY_DIMENSION * TILE_SIZE, DISPLAY_DIMENSION * TILE_SIZE);
        setPreferredSize(size);
        setLayout(null);
        this.maze = maze;
        if(maze == null){
            System.out.println("maze = null, testing with temp board"); // FIXME: for testing
        }
    }

    public void paintComponent(Graphics g){
//        Tile[][] board = maze.getBoard();

        // FIXME: temp hardcode for testing without maze
        Tile[][] board = new Tile[2][2];
        board[0][0] = new FreeTile();
        board[0][1] = new KeyTile(Maze.Colours.BLUE);
        board[1][0] = new DoorTile(Maze.Colours.BLUE);
        board[1][1] = new TreasureTile();
        ((AccessibleTile)board[0][0]).setEntityHere(new Chap(new Point(0, 0)));
        /*
        * new ExitLockTile();
        * new ExitTile();
        * */

        int startRow = 0;
        int startCol = 0;

        // TODO: test when maze/level available
        /*// Centre display around Chap.
        int startRow = maze.getChap().getEntityPosition().y - (DISPLAY_DIMENSION-1)/2;
        int startCol = maze.getChap().getEntityPosition().x - (DISPLAY_DIMENSION-1)/2;
        // Boundaries.
        if(startRow < 0){
            startRow = 0;
        } else if(startRow + DISPLAY_DIMENSION >= BOARD_DIMENSION){ // TODO: check maths >= or >
            startRow = BOARD_DIMENSION - DISPLAY_DIMENSION;
        }
        if(startCol < 0){
            startCol = 0;
        } else if(startCol + DISPLAY_DIMENSION >= BOARD_DIMENSION){ // TODO: check maths
            startCol = BOARD_DIMENSION - DISPLAY_DIMENSION;
        }*/

        // TODO: add offset variable for smooth animation

        for(int row = startRow; row < startRow + DISPLAY_DIMENSION; row++){
            for(int col = startCol; col < startCol + DISPLAY_DIMENSION; col++){
                Tile tile = board[row][col];
                System.out.printf("draw tile at (%d,%d)\n", row, col);
                Image tileIcon = tile.getIcon();
                g.drawImage(tileIcon, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

                if(tile.isAccessible()){ // Tile can have something on it
                    AccessibleTile accessibleTile = (AccessibleTile) tile;
                    if(accessibleTile.getEntityHere() != null){
                        System.out.printf("draw entity at (%d, %d)\n", row, col);
                        // TODO: animate Chap
                        Image entityIcon = accessibleTile.getEntityHere().getIcon();
                        g.drawImage(entityIcon, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }
            } // end col for loop
        } // end row for loop
    }
}