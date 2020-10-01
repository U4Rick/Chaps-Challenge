package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import javax.swing.*;
import java.awt.*;

/**
 * A renderer to visually display the state of the game board.
 *
 * @author Cherie
 */
public class BoardRenderer extends JPanel {
    private final int DISPLAY_DIMENSION = 9; // Should be odd so Chap can be centred.
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    private final int TILE_SIZE;
    private final Maze maze;

    /**
     * Constructs a new renderer to display the current board.
     *
     * @param importMaze the maze containing the board to be drawn.
     */
    public BoardRenderer(Maze importMaze, Dimension size){
        setPreferredSize(size);
        setLayout(null);
        maze = importMaze;
        BOARD_WIDTH = maze.getBoard().length;
        BOARD_HEIGHT = maze.getBoard()[0].length;
        TILE_SIZE = size.width/DISPLAY_DIMENSION;
    }

    @Override
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
        } else if(startY + DISPLAY_DIMENSION > BOARD_HEIGHT){
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

                if(tile.isAccessible()){ // Tile might have something on it
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