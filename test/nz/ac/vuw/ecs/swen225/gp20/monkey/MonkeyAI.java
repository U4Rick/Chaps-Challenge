package nz.ac.vuw.ecs.swen225.gp20.monkey;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static nz.ac.vuw.ecs.swen225.gp20.maze.Maze.*;

/**
 * Abstract class representing the contractual requirements of MonkeyAI players.
 */
public abstract class MonkeyAI {

    //Reward given by the utilityFunction for different types of tiles.
    private final int accessibleReward;
    private final int inaccessibleReward;
    private final int treasureReward;
    private final int keyReward;
    private final int lockAndKeyReward;
    private final int lockNoKeyReward;
    private final int exitReward;

    //Variance to prevent AI actions from being deterministic
    static final int VARIANCE = 20;

    //Need to store which tiles we attempted to visit and should not revisit to not get stuck in a loop.
    ArrayList<Tile> blacklistedTiles;

    /**
     * Instantiates a new MonkeyAI using passed reward weightings.
     *
     * @param accessibleReward   the accessible reward.
     * @param inaccessibleReward the inaccessible reward.
     * @param treasureReward     the treasure reward.
     * @param keyReward          the key reward.
     * @param lockAndKeyReward   the lock and key reward.
     * @param lockNoKeyReward    the lock and no key reward.
     * @param exitReward         the exit reward.
     */
    public MonkeyAI(int accessibleReward, int inaccessibleReward, int treasureReward, int keyReward, int lockAndKeyReward, int lockNoKeyReward, int exitReward) {
        this.accessibleReward = accessibleReward;
        this.inaccessibleReward = inaccessibleReward;
        this.treasureReward = treasureReward;
        this.keyReward = keyReward;
        this.lockAndKeyReward = lockAndKeyReward;
        this.lockNoKeyReward = lockNoKeyReward;
        this.exitReward = exitReward;
    }

    /**
     * Assigns an numeric value to the proposed tile.
     *
     * @param tile Proposed Tile.
     * @return Expected Value of Tile.
     */
    public int utilityFunction(Tile tile){
        int reward = 0;

/*        if (tile instanceof AccessibleTile) {
            reward = accessibleReward;
        } else if (tile instanceof InaccessibleTile) {
            reward = inaccessibleReward;
        } else if (tile instanceof TreasureTile) {
            reward = treasureReward;
        } else if (tile instanceof KeyTile) {
            reward = keyReward;
        } else if (tile instanceof LockedDoorTile) {
            if (hasKey) {
                reward = lockAndKeyReward;
            } else {
                reward = lockNoKeyReward;
            }
        } else if (tile instanceof ExitTile) {
            reward = exitReward;
        }*/

        int variance = new Random().nextInt(VARIANCE);  //Calculate variance between 0 and VARIANCE

        return reward + variance;
    }

    /**
     * Checks the Expected Value of each possible move and selects the best.
     *
     * @param maze Maze of the current game.
     * @return Move with the highest Expected Value.
     */
    public Direction selectMove(Maze maze) {
        Direction bestDirection = null;
        int bestEV = Integer.MIN_VALUE;

        //For each possible move, calculate EV and select the move with the highest EV
        for (Direction direction : Direction.values()) {

            Tile tile = getDestinationTile(maze, direction);

            //If tile is not null, then move is valid
            if (tile != null) {

                //Calculate EV of this tile
                int tileEV = utilityFunction(tile);

                //If tileEV is better set bestDirection and bestEV
                if (tileEV > bestEV) {
                    bestDirection = direction;
                    bestEV = tileEV;
                }
            }
        }
        return bestDirection;
    }

    /**
     * Returns destination tile of the move in passed Direction.
     *
     * @param direction Direction of move.
     * @return Destination tile.
     */
    private Tile getDestinationTile(Maze maze, Direction direction) {
        /*Point chapPosition = maze.getChapPosition();

        //TODO add checks for out of bounds
        switch (direction) {
            case UP:
                return maze.getTileAt(chapPosition.x, chapPosition.y - 1);
            case DOWN:
                return maze.getTileAt(chapPosition.x, chapPosition.y + 1);
            case LEFT:
                return maze.getTileAt(chapPosition.x - 1, chapPosition.y);
            case RIGHT:
                return maze.getTileAt(chapPosition.x + 1, chapPosition.y);
        }*/
        return null;
    }
}
