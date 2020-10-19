package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * A renderer to visually display the player's inventory.
 *
 * @author Cherie
 */
public class InventoryRenderer extends JPanel {
    private final int NUM_SLOTS = 4;
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int TILE_SIZE;
    private final Chap chap;
    private Image bg, circle;

    /**
     * Constructs a new renderer to display the inventory.
     *
     * @param importMaze the maze containing Chap.
     * @param width the width of the panel in pixels (should be divisible by number of slots).
     */
    public InventoryRenderer(Maze importMaze, int width){
        PANEL_WIDTH = width;
        TILE_SIZE = width/NUM_SLOTS;
        PANEL_HEIGHT = TILE_SIZE;
        Dimension size = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        setPreferredSize(size);
        setLayout(null);
        chap = importMaze.getChap();

        try {
            // Background.
            bg = ImageIO.read(new File("./resources/green.png"));
            circle = ImageIO.read(new File("./resources/circle.png"));
        } catch (Exception e) {
            e.printStackTrace(); // TODO: throw an error?
        }
        this.setFont(new Font("Calibri", Font.BOLD, 32));
    }

    @Override
    public void paintComponent(Graphics g){
        // Redraw the background.
        g.drawImage(bg,0,0,PANEL_WIDTH, PANEL_HEIGHT, null);
        // Draw inventory slots.
        for (int i = 0; i < NUM_SLOTS; i++) {
            g.drawImage(circle, i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
        }

        Map<Colour, Integer> inventory = chap.getKeyInventory(); // Get Chap's keys.
        for (Colour keyColour : inventory.keySet()) {
            int xOffset;
            Image icon = Maze.getKeyIcon(keyColour);

            // Have set inventory position for each key colour.
            switch(keyColour) {
                case YELLOW:
                    xOffset = 1;
                    break;
                case GREEN:
                    xOffset = 2;
                    break;
                case BLUE:
                    xOffset = 3;
                    break;
                default:
                    xOffset = 0; // RED key.
            }

            int numKeys = inventory.get(keyColour);
            if(numKeys > 0){
                g.drawImage(icon, xOffset * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
                if(numKeys > 1){ // FIXME: always display number?
                    // Display number of keys if more than one.
                    g.drawString(String.valueOf(numKeys), (xOffset * TILE_SIZE) + (int) (0.3 * TILE_SIZE), (int) (0.75 * TILE_SIZE));
                }
            }
        }
    }
}