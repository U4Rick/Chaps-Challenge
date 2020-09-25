package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private final int TILE_SIZE = 30; // pixels
    private final int DISPLAY_DIMENSION = 9; // rows/columns of viewer - should be odd so Chap can be centred
    private final int BOARD_DIMENSION = 50; // rows/columns of entire board
    private final Maze maze;

    public Renderer(Maze maze){
        Dimension size = new Dimension(DISPLAY_DIMENSION * TILE_SIZE, DISPLAY_DIMENSION * TILE_SIZE);
        setPreferredSize(size);
        setLayout(null);
        this.maze = maze;
    }

    public void paintComponent(Graphics g){
        Tile[][] board = maze.getBoard();

//        int startRow = 0;
//        int startCol = 0;

        // Centre display around Chap.
        int startRow = maze.getChap().getEntityPosition().y - (DISPLAY_DIMENSION-1)/2;
        int startCol = maze.getChap().getEntityPosition().x - (DISPLAY_DIMENSION-1)/2;
        // Boundaries.
        if(startRow < 0){
            startRow = 0;
        } else if(startRow + DISPLAY_DIMENSION >= BOARD_DIMENSION){ // TODO: check maths
            startRow = BOARD_DIMENSION - DISPLAY_DIMENSION;
        }
        if(startCol < 0){
            startCol = 0;
        } else if(startCol + DISPLAY_DIMENSION >= BOARD_DIMENSION){ // TODO: check maths
            startCol = BOARD_DIMENSION - DISPLAY_DIMENSION;
        }

        for(int row = startRow; row < startRow + DISPLAY_DIMENSION; row++){
            for(int col = startCol; col < startCol + DISPLAY_DIMENSION; col++){
                // TODO: get tile from board, then draw tile icon
                Tile tile = board[row][col];
//                Image tileIcon = tile.getIcon();
                // TODO: add offset variable for smoother animation
//                g.drawImage(tileIcon, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                System.out.printf("draw tile at (%d, %d)", row, col);

                if(tile.isAccessible()){
                    AccessibleTile accessibleTile = (AccessibleTile) tile;
                    // TODO: animate entities moving
                    if(accessibleTile.getEntityHere() != null){
//                        Image entityIcon = accessibleTile.getEntityHere().getIcon();
//                        g.drawImage(entityIcon, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        System.out.printf("draw entity at (%d, %d)", row, col);
                    }
                }
            }
        }
    }
}