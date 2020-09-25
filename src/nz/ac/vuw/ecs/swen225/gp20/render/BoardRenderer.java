package nz.ac.vuw.ecs.swen225.gp20.render;

// FIXME: remove un-allowed imports
import nz.ac.vuw.ecs.swen225.gp20.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import javax.swing.*;
import java.awt.*;

public class BoardRenderer extends JPanel {
//    private final int TILE_SIZE = 50; // pixels
    private final int DISPLAY_DIMENSION = 9; // rows/columns of viewer - should be odd so Chap can be centred
    private final int BOARD_HEIGHT; // rows
    private final int BOARD_WIDTH; // columns
    private final int TILE_SIZE = 450/DISPLAY_DIMENSION; // FIXME: constant is temp
    private final Maze maze;

    public BoardRenderer(Maze importMaze){
        // FIXME: display and tile size relying on each other
        Dimension size = new Dimension(DISPLAY_DIMENSION * TILE_SIZE, DISPLAY_DIMENSION * TILE_SIZE);
        setPreferredSize(size);
        setLayout(null);
        maze = importMaze;
        BOARD_HEIGHT = maze.getBoard().length;
        BOARD_WIDTH = maze.getBoard()[0].length;
    }

    public void paintComponent(Graphics g){
        Tile[][] board = maze.getBoard();

        // Centre display around Chap.
        int startRow = maze.getChap().getEntityPosition().y - (DISPLAY_DIMENSION-1)/2;
        int startCol = maze.getChap().getEntityPosition().x - (DISPLAY_DIMENSION-1)/2;
        // Boundaries.
        if(startRow < 0){
            startRow = 0;
        } else if(startRow + DISPLAY_DIMENSION > BOARD_HEIGHT){ // TODO: check maths >= or >
            startRow = BOARD_HEIGHT - DISPLAY_DIMENSION;
        }
        if(startCol < 0){
            startCol = 0;
        } else if(startCol + DISPLAY_DIMENSION > BOARD_WIDTH){
            startCol = BOARD_WIDTH - DISPLAY_DIMENSION;
        }

        // TODO: add animation offset variable for smoother animation
        int rowOffset = startRow * TILE_SIZE;
        int colOffset = startCol * TILE_SIZE;

        for(int row = startRow; row < startRow + DISPLAY_DIMENSION; row++){
            for(int col = startCol; col < startCol + DISPLAY_DIMENSION; col++){
                Tile tile = board[row][col];
                Image tileIcon = tile.getIcon();
                g.drawImage(tileIcon, col * TILE_SIZE - colOffset, row * TILE_SIZE - rowOffset, TILE_SIZE, TILE_SIZE, null);

                if(tile.isAccessible()){ // Tile can have something on it
                    AccessibleTile accessibleTile = (AccessibleTile) tile;

                    // FIXME: remove when maze is working
                    if(row == startRow + 4 && col == startCol + 4){
                        accessibleTile.setEntityHere(new Chap(new Point(col, row)));
                    }

                    if(accessibleTile.getEntityHere() != null){
                        // TODO: animate Chap
                        Image entityIcon = accessibleTile.getEntityHere().getIcon();
                        g.drawImage(entityIcon, col * TILE_SIZE - colOffset, row * TILE_SIZE - rowOffset, TILE_SIZE, TILE_SIZE, null);
                    }
                }
            } // end col for loop
        } // end row for loop
    }
}