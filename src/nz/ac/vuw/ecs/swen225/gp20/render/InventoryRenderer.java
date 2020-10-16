package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

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
    private Image bg;
    private Image circle;

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
            bg = ImageIO.read(new File("./resources/green.png"));
            circle = ImageIO.read(new File("./resources/circle.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        // Redraw the background.
        g.drawImage(bg,0,0,PANEL_WIDTH, PANEL_HEIGHT, null);
        for (int i = 0; i < NUM_SLOTS; i++) {
            g.drawImage(circle, i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
        }

        Map<Key, Integer> inventory = chap.getKeyInventory();

        for (Key key : inventory.keySet()) {
            int x;
            switch(key.getKeyColour()) {
                case YELLOW:
                    x = 1;
                    break;
                case GREEN:
                    x = 2;
                    break;
                case BLUE:
                    x = 3;
                    break;
                default:
                    x = 0; // RED key position.
            }

            int numKeys = inventory.get(key);
            if(numKeys > 0){
                g.drawImage(key.getIcon(), x * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
                if(numKeys > 1){
                    // Draw number on top. FIXME: currently drawing chap, needs to draw text instead
                    g.drawImage(chap.getIcon(), x * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }
}