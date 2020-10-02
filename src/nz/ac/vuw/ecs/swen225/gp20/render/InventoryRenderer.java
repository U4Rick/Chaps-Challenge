package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    }

    @Override
    public void paintComponent(Graphics g){
        Map<Key, Integer> inventory = new HashMap<>();

        // FIXME: temp hard code
        inventory.put(new Key(Maze.Colours.BLUE), 2);
        inventory.put(new Key(Maze.Colours.GREEN), 1);
        inventory.put(new Key(Maze.Colours.YELLOW), 1);
        inventory.put(new Key(Maze.Colours.RED), 1);

        int x = 0;
        for (Key key : inventory.keySet()) {
            int numKeys = inventory.get(key);
            if(numKeys > 0){
                g.drawImage(key.getIcon(), x * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
                if(numKeys > 1){
                    // Draw number on top.
                    g.drawImage(chap.getIcon(), x * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null); // FIXME: temp
                }
            }
            x++;
        }
    }
}