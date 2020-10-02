package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import static nz.ac.vuw.ecs.swen225.gp20.maze.Maze.*;

/**
 * Abstract class representing the contractual requirements of MonkeyAI players.
 *
 * @author Matt
 */
public abstract class MonkeyAI {

    //Reward given by the utilityFunction for different types of tiles.
    private final int freeReward;
    private final int keyReward;
    private final int treasureReward;
    private final int infoReward;
    private final int exitReward;
    private final int wallReward;
    private final int exitLockReward;
    private final int lockAndKeyReward;
    private final int lockNoKeyReward;

    //Variance to prevent AI actions from being deterministic
    static final int VARIANCE = 20;

    //Need to store which tiles we attempted to visit and should not revisit to not get stuck in a loop.
    final ArrayList<Tile> blacklistedTiles = new ArrayList<>();

    /**
     * Instantiates a new Monkey ai.
     *
     * @param freeReward       the free reward.
     * @param keyReward        the key reward.
     * @param treasureReward   the treasure reward.
     * @param infoReward       the info reward.
     * @param exitReward       the exit reward.
     * @param wallReward       the wall reward.
     * @param exitLockReward   the exit lock reward.
     * @param lockAndKeyReward the lock and key reward.
     * @param lockNoKeyReward  the lock no key reward.
     */
    public MonkeyAI(int freeReward, int keyReward, int treasureReward, int infoReward, int exitReward,
                    int wallReward, int exitLockReward, int lockAndKeyReward, int lockNoKeyReward) {
        this.freeReward = freeReward;
        this.keyReward = keyReward;
        this.treasureReward = treasureReward;
        this.infoReward = infoReward;
        this.exitReward = exitReward;
        this.wallReward = wallReward;
        this.exitLockReward = exitLockReward;
        this.lockAndKeyReward = lockAndKeyReward;
        this.lockNoKeyReward = lockNoKeyReward;
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

            //If tile is not null, then move must be within bounds
            if (tile != null) {

                //Calculate EV of this tile
                int tileEV = utilityFunction(maze.getChap(), tile);

                //If tileEV is better, set bestDirection and bestEV
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
    Tile getDestinationTile(Maze maze, Direction direction) {
        Point chapPosition = maze.getChapPosition();

        //TODO add checks for out of bounds
        switch (direction) {
            case UP:
                return maze.getTile(chapPosition.x, chapPosition.y - 1);
            case DOWN:
                return maze.getTile(chapPosition.x, chapPosition.y + 1);
            case LEFT:
                return maze.getTile(chapPosition.x - 1, chapPosition.y);
            case RIGHT:
                return maze.getTile(chapPosition.x + 1, chapPosition.y);
        }
        return null;
    }

    /**
     * Assigns an numeric value to the proposed tile.
     *
     * @param tile Proposed Tile.
     * @return Expected Value of Tile.
     */
    public int utilityFunction(Chap chap, Tile tile) {
        int reward = 0;

        //Typically a code smell, however there is no way to use polymorphism instead as this is not production code.
        if (tile instanceof FreeTile) {
            reward = freeReward;
        } else if (tile instanceof KeyTile) {
            reward = keyReward;
        } else if (tile instanceof TreasureTile) {
            reward = treasureReward;
        } else if (tile instanceof InfoTile) {
            reward = infoReward;
        } else if (tile instanceof ExitTile) {
            reward = exitReward;
        } else if (tile instanceof WallTile && !blacklistedTiles.contains(tile)) {
            reward = wallReward;
        } else if (tile instanceof ExitLockTile && !blacklistedTiles.contains(tile)) {
            reward = exitLockReward;
        } else if (tile instanceof DoorTile) {

            //TODO Something clever to ensure not stuck on locked doors, but must be able to enter once we get the key.
            DoorTile doorTile = (DoorTile) tile;
            boolean hasKey = checkMatchingKey(chap, doorTile);

            if (hasKey) {
                reward = lockAndKeyReward;
            } else {
                reward = lockNoKeyReward;
            }
        }

        int variance = new Random().nextInt(VARIANCE);  //Calculate variance between 0 and VARIANCE

        return reward + variance;
    }

    /**
     * Check if Chap has the key matching the colour of passed door.
     *
     * @param doorTile Door to check key for.
     * @return True if Chap has matching key, otherwise False.
     */
    private boolean checkMatchingKey(Chap chap, DoorTile doorTile) {
        Set<Key> inventory = chap.getKeyInventory();
        for (Key key : inventory) {
            Colours doorColour = doorTile.getDoorColour();
            Colours keyColour = key.getKeyColour();
            if (keyColour.equals(doorColour)) {
                return true;
            }
        }
        return false;
    }
}
