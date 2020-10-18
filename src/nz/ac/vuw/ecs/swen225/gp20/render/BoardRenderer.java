package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

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
    private Image infoIcon;

    /**
     * Constructs a new renderer to display the current board.
     *
     * @param importMaze the maze containing the board to be drawn.
     * @param size the dimensions of the board.
     */
    public BoardRenderer(Maze importMaze, Dimension size){
        maze = importMaze;
        BOARD_WIDTH = maze.getBoard().length;
        BOARD_HEIGHT = maze.getBoard()[0].length;
        TILE_SIZE = size.width/DISPLAY_DIMENSION;

        // Set as actual display size in case size parameter did not divide well. FIXME: good code style?
        setPreferredSize(new Dimension(DISPLAY_DIMENSION * TILE_SIZE, DISPLAY_DIMENSION * TILE_SIZE));
        setLayout(null);

        // Info display background.
        try {
            infoIcon = ImageIO.read(new File("./resources/info_scroll.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setFont(new Font("Calibri", Font.BOLD, 32)); // TODO: choose right font/size for info display.
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

        // If chap is on info tile, display info.
        if(maze.getTile(maze.getChapPosition().x, maze.getChapPosition().y) instanceof InfoTile){
            String infoText = "YOU STEPPED ON THE INFO TILE."; // TODO: get text from maze/level.
            g.drawImage(infoIcon, 25, 300, 445, 165, null);
            g.drawString(infoText, 30, 350); // FIXME: check position when game can run.
        }
    }
}