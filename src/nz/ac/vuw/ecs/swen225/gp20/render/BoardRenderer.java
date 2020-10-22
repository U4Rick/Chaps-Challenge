package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Entity;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.InfoTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A renderer to visually display the state of the game board.
 *
 * @author Cherie Deng 300477224
 */
public class BoardRenderer extends JPanel {
    private static final int DISPLAY_DIMENSION = 9; // Should be odd so Chap can be centred.
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    private final int TILE_SIZE;
    private final Maze maze;
    private BufferedImage infoIcon;

    /**
     * Constructs a new renderer to display the current board.
     *
     * @param importMaze the maze containing the board to be drawn.
     * @param size       the dimensions of the board.
     */
    public BoardRenderer(Maze importMaze, Dimension size) {
        maze = importMaze;
        BOARD_WIDTH = maze.getBoard().length;
        BOARD_HEIGHT = maze.getBoard()[0].length;
        TILE_SIZE = size.width / DISPLAY_DIMENSION;

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
    public void paintComponent(Graphics g) {
        Tile[][] board = maze.getBoard();
        int chapX = maze.getChapPosition().x;
        int chapY = maze.getChapPosition().y;

        // Centre display around Chap.
        int startX = chapX - (DISPLAY_DIMENSION - 1) / 2;
        int startY = chapY - (DISPLAY_DIMENSION - 1) / 2;

        // Boundaries.
        if (startX < 0) {
            startX = 0;
        } else if (startX + DISPLAY_DIMENSION > BOARD_WIDTH) {
            startX = BOARD_WIDTH - DISPLAY_DIMENSION;
        }
        if (startY < 0) {
            startY = 0;
        } else if (startY + DISPLAY_DIMENSION > BOARD_HEIGHT) {
            startY = BOARD_HEIGHT - DISPLAY_DIMENSION;
        }

        // Draw tiles.
        for (int xPos = startX; xPos < startX + DISPLAY_DIMENSION; xPos++) {
            for (int yPos = startY; yPos < startY + DISPLAY_DIMENSION; yPos++) {
                Tile tile = board[xPos][yPos];
                Image tileIcon = tile.getIcon();
                g.drawImage(tileIcon, (xPos - startX) * TILE_SIZE, (yPos - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }

        // Draw entities.
        for (Entity e : maze.getNpcs()) {
            g.drawImage(e.getIcon(), (e.getEntityPosition().x - startX) * TILE_SIZE, (e.getEntityPosition().y - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
        // Draw Chap.
        g.drawImage(maze.getChap().getIcon(), (chapX - startX) * TILE_SIZE, (chapY - startY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

        // If Chap is on info tile, display info.
        Tile chapTile = maze.getTile(chapX, chapY);
        if (chapTile instanceof InfoTile) {
            g.drawImage(infoIcon, 25, 300, 445, 165, null); // Scroll image.
            drawMultilineString(g, ((InfoTile) chapTile).getInformation(), 75, 330); // Level info text.
        }
    }

    /**
     * Draws a string on multiple lines (if it contains \n character).
     *
     * @param g    the graphics object to draw on.
     * @param text the text to be drawn.
     * @param x    the x position where the text starts.
     * @param y    the y position of the top of the text.
     */
    void drawMultilineString(Graphics g, String text, int x, int y) {
        int lineHeight = g.getFontMetrics().getHeight();
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += lineHeight);
        }
    }
}