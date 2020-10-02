package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import java.util.ArrayList;

/**
 * An AI that tries to push through walls where possible.
 *
 * @author Matt
 */
public class Gorilla extends MonkeyAI {

    //Need to store which tiles we attempted to visit and should not revisit to not get stuck in a loop.
    final ArrayList<Tile> blacklistedTiles = new ArrayList<>();

    /**
     * Instantiates a new Gorilla model MonkeyAI with preset reward weightings.
     */
    public Gorilla() {
        super(50,
                20,
                20,
                0,
                100,
                100,
                20,
                100);
    }

    @Override
    public Direction selectMove(Maze maze) {
        Direction direction = super.selectMove(maze);
        Tile destinationTile = getDestinationTile(maze, direction);

        //Add to blacklist if not accessible to prevent getting stuck in a loop
        if (!destinationTile.isAccessible()) {
            blacklistedTiles.add(destinationTile);
        }

        return direction;
    }
}
