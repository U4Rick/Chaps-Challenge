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
    private static final int NUM_SLOTS = 4;
    private final int TILE_SIZE;
    private final Chap chap;
    private Image slotIcon;
    // TODO: checkstyle?

    /**
     * Constructs a new renderer to display the inventory.
     *
     * @param importMaze the maze containing Chap.
     * @param width the width of the panel in pixels.
     */
    public InventoryRenderer(Maze importMaze, int width){
        TILE_SIZE = width/NUM_SLOTS;
        Dimension size = new Dimension(width, TILE_SIZE);
        setPreferredSize(size);
        setLayout(null);
        chap = importMaze.getChap();

        try {
            slotIcon = ImageIO.read(new File("./resources/circle.png"));
        } catch (Exception e) {
            e.printStackTrace(); // TODO: throw an error?
        }

        this.setFont(new Font("Calibri", Font.BOLD, 32));
    }

    @Override
    public void paintComponent(Graphics g){
        // Draw inventory slots.
        for (int i = 0; i < NUM_SLOTS; i++) {
            g.drawImage(slotIcon, i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
        }

        for (Map.Entry<Colour, Integer> entry : chap.getKeyInventory().entrySet()) {
            Colour keyColour = entry.getKey();
            Image icon = Maze.getKeyIcon(keyColour);

            // Have set inventory position for each key colour.
            int slot;
            switch(keyColour) {
                case YELLOW:
                    slot = 1;
                    break;
                case GREEN:
                    slot = 2;
                    break;
                case BLUE:
                    slot = 3;
                    break;
                default:
                    slot = 0; // RED key slot.
            }

            int numKeys = entry.getValue(); // TODO: remove local variable if no if statement for >1 key
            if(numKeys > 0){
                g.drawImage(icon, slot * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
                g.drawString(String.valueOf(numKeys), (slot * TILE_SIZE) + (int) (0.3 * TILE_SIZE), (int) (0.75 * TILE_SIZE));
            }
        }
    }
}