package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import javax.swing.*;
import java.awt.*;

public class BoardRenderer extends JPanel {
    private final int DISPLAY_DIMENSION = 9; // Number of rows/columns shown (should be odd so Chap can be centred).
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    private final int TILE_SIZE = 495/DISPLAY_DIMENSION; // FIXME: set value in constructor
    private final Maze maze;

    public BoardRenderer(Maze importMaze/*, Dimension size*/){
        setPreferredSize(new Dimension(495, 495)); // FIXME: use parameter dimension
        setLayout(null);
        maze = importMaze;
        BOARD_WIDTH = maze.getBoard().length;
        BOARD_HEIGHT = maze.getBoard()[0].length;
//        TILE_SIZE = size.width/DISPLAY_DIMENSION;
    }

    public void paintComponent(Graphics g){
        Tile[][] board = maze.getBoard();

        // Centre display around Chap.
        int startX = maze.getChapPosition().x - (DISPLAY_DIMENSION-1)/2;
        int startY = maze.getChapPosition().y - (DISPLAY_DIMENSION-1)/2;
        // Boundaries.
        if(startX < 0){
            startX = 0;
        } else if(startX + DISPLAY_DIMENSION > BOARD_WIDTH){
            startX = BOARD_WIDTH - DISPLAY_DIMENSION;
        }
        if(startY < 0){
            startY = 0;
        } else if(startY + DISPLAY_DIMENSION > BOARD_HEIGHT){ // TODO: check maths >= or >
            startY = BOARD_HEIGHT - DISPLAY_DIMENSION;
        }

        // TODO: use for animation?
//        int xOffset = _0/1/-1_ * TILE_SIZE;
//        int yOffset = _0/1/-1_ * TILE_SIZE;

        for(int xPos = startX; xPos < startX + DISPLAY_DIMENSION; xPos++){
            for(int yPos = startY; yPos < startY + DISPLAY_DIMENSION; yPos++){
                Tile tile = board[xPos][yPos];
                Image tileIcon = tile.getIcon();
                g.drawImage(tileIcon, (xPos - startX) * TILE_SIZE, (yPos - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

                if(tile.isAccessible()){ // Tile can have something on it
                    AccessibleTile accessibleTile = (AccessibleTile) tile;

                    if(accessibleTile.getEntityHere() != null){
                        // TODO: animate Chap
                        Image entityIcon = accessibleTile.getEntityHere().getIcon();
                        g.drawImage(entityIcon, (xPos - startX) * TILE_SIZE, (yPos - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }
            }
        }
    }
}