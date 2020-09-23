package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.AccessibleTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private final int TILE_SIZE = 30; // pixels
    private final int BOARD_DIMENSION = 9; // rows/columns
    private Maze maze;

    public Renderer(Maze maze){
        Dimension size = new Dimension(BOARD_DIMENSION * TILE_SIZE, BOARD_DIMENSION * TILE_SIZE);
        setPreferredSize(size);
        setLayout(null);
        this.maze = maze;
    }

    public void paintComponent(Graphics g){
        // TODO: centre around chap
        for(int row = 0; row < BOARD_DIMENSION; row++){
            for(int col = 0; col < BOARD_DIMENSION; col++){
                // TODO: get tile from board, then draw tile icon
                Tile tile = maze.getBoard()[row][col];
//                g.drawImage(/*image to draw*/tile.getIcon(), col * TILE_SIZE, row * TILE_SIZE, null);

                if(tile.isAccessible()){
                    AccessibleTile accessibleTile = (AccessibleTile) tile;
                    if(accessibleTile.getEntityHere() != null){
//                        g.drawImage(/*image to draw*/accessibleTile.getEntityHere().getIcon(), col * TILE_SIZE, row * TILE_SIZE, null);
                    }
                }
            }
        }
    }
}