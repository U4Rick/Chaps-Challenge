package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;
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

        // Set as actual display size in case size parameter did not divide well.
        setPreferredSize(new Dimension(DISPLAY_DIMENSION * TILE_SIZE, DISPLAY_DIMENSION * TILE_SIZE));
        setLayout(null);

        // Info display background.
        try {
            infoIcon = ImageIO.read(new File("./resources/info_scroll.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Display setup.
        this.setFont(new Font("Calibri", Font.BOLD, 32));
        this.setForeground(Color.white);
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

                // TODO: remove if not using
                /*if(tile.isAccessible()){ // Tile might have something on it
                    AccessibleTile accessibleTile = (AccessibleTile) tile;

                    if(accessibleTile.getEntityHere() != null){
                        // TODO: animate Chap
                        Image entityIcon = accessibleTile.getEntityHere().getIcon();
                        g.drawImage(entityIcon, (xPos - startX) * TILE_SIZE, (yPos - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }*/
            }
        }

        // TODO: check if always works
        // Draw entities.
        for (Entity e : maze.getNpcs()) {
            g.drawImage(e.getIcon(), (e.getEntityPosition().x - startX) * TILE_SIZE, (e.getEntityPosition().y - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
        g.drawImage(maze.getChap().getIcon(), (maze.getChapPosition().x - startX) * TILE_SIZE, (maze.getChapPosition().y - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

        // If Chap is on info tile, display info.
        Tile chapTile = maze.getTile(maze.getChapPosition().x, maze.getChapPosition().y);
        if(chapTile instanceof InfoTile){
            g.drawImage(infoIcon, 25, 300, 445, 165, null);
            drawMultilineString(g, ((InfoTile)chapTile).getInformation(), 75, 330);
        }
    }

    /**
     * Draws a string containing the \n character on multiple lines.
     *
     * @param g the graphics object to draw on.
     * @param text the text to be drawn.
     * @param x the x position where the text starts.
     * @param y the y position of the top of the text.
     */
    void drawMultilineString(Graphics g, String text, int x, int y) {
        int lineHeight = g.getFontMetrics().getHeight();
        for (String line : text.split("\n")){
            g.drawString(line, x, y += lineHeight);
        }
    }
}