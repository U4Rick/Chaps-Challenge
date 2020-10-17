package nz.ac.vuw.ecs.swen225.gp20.monkey.models;

import nz.ac.vuw.ecs.swen225.gp20.commons.Colour;
import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.entities.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

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
    private final int exitReward;
    private final int wallReward;
    private final int exitLockReward;
    private final int lockAndKeyReward;
    private final int lockNoKeyReward;

    //Variance to prevent AI actions from being deterministic
    static final int VARIANCE = 20;

    //Need to store which tiles we attempted to visit and should not revisit to not get stuck in a loop.
    final HashSet<Tile> blacklistedTiles = new HashSet<>();

    /**
     * Instantiates a new Monkey ai.
     *
     * @param freeReward       the free reward.
     * @param keyReward        the key reward.
     * @param treasureReward   the treasure reward.
     * @param exitReward       the exit reward.
     * @param wallReward       the wall reward.
     * @param exitLockReward   the exit lock reward.
     * @param lockAndKeyReward the lock and key reward.
     * @param lockNoKeyReward  the lock no key reward.
     */
    public MonkeyAI(int freeReward, int keyReward, int treasureReward, int exitReward,
                    int wallReward, int exitLockReward, int lockAndKeyReward, int lockNoKeyReward) {
        this.freeReward = freeReward;
        this.keyReward = keyReward;
        this.treasureReward = treasureReward;
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
     * @param maze Current maze.
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
     * @param chap Active player.
     * @param tile Proposed Tile.
     * @return Expected Value of Tile.
     */
    private int utilityFunction(Chap chap, Tile tile) {

        int reward = assignReward(chap, tile);          //Get the reward of the tile

        int variance = new Random().nextInt(VARIANCE);  //Calculate amount of variance to add

        return reward + variance;
    }

    /**
     * Checks the associated weighting for the passed tile and returns it.
     * For doors, takes into account whether the corresponding key is held or not.
     *
     * @param chap Active player.
     * @param tile Tile to get the reward of.
     * @return Reward associated with the tile.
     */
    private int assignReward(Chap chap, Tile tile) {
        //Get enum representation of the Tile's class name
        TileType tileType = TileType.valueOf(tile.getClass().getSimpleName().toUpperCase());

        if (blacklistedTiles.contains(tile)) {
            return -100;
        }

        //Return associated reward
        switch (tileType) {
            case FREETILE:
            case INFOTILE:
                return freeReward;
            case KEYTILE:
                return keyReward;
            case TREASURETILE:
                return treasureReward;
            case EXITTILE:
                return exitReward;
            case WALLTILE:
                return wallReward;
            case EXITLOCKTILE:
                return exitLockReward;
            case DOORTILE:
                DoorTile doorTile = (DoorTile) tile;
                boolean hasKey = checkMatchingKey(chap, doorTile);

                if (hasKey) {
                    return lockAndKeyReward;
                }
                return lockNoKeyReward;

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Check if Chap has the key matching the colour of passed door.
     *
     * @param chap Active player.
     * @param doorTile Door to check key for.
     * @return True if Chap has matching key, otherwise False.
     */
    private boolean checkMatchingKey(Chap chap, DoorTile doorTile) {
        Colour doorColour = doorTile.getDoorColour();
        Integer integer = chap.getKeyInventory().get(new Key(doorColour));

        System.out.println(integer);

        return false;
/*        Set<Key> keyInventory = chap.getKeyInventory().keySet();

        //For each key in chaps key inventory
        for (Key key : keyInventory) {

            //Get colours of Door and Key
            Colours doorColour = doorTile.getDoorColour();
            Colours keyColour = key.getKeyColour();

            //Check if they match
            if (keyColour.equals(doorColour)) {
                return true;
            }
        }
        return false;*/
    }

    /**
     * Enum representation of the Tile class names, used for the assignReward function.
     */
    private enum TileType {
        DOORTILE, EXITLOCKTILE, EXITTILE, FREETILE, INFOTILE, KEYTILE, TREASURETILE, WALLTILE
    }
}
