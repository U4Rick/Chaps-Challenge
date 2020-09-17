package nz.ac.vuw.ecs.swen225.gp20.monkey;

import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class representing the contractual requirements of MonkeyAI players.
 */
public abstract class MonkeyAI {

    //Reward given by the utilityFunction for different types of tiles.
    int accessibleReward;
    int inaccessibleReward;
    int treasureReward;
    int keyReward;
    int lockReward;
    int exitReward;

    //Variance to prevent AI actions from being deterministic
    int variance = 5;

    //Need to store which tiles we attempted to visit and should not revisit to not get stuck in a loop.
    ArrayList<Object> blacklistedTiles; //FIXME use correct generic type

    /**
     * Instantiates a new MonkeyAI with default constructor.
     */
    public MonkeyAI() {

    }

    /**
     * Instantiates a new MonkeyAI using passed reward weightings.
     *
     * @param accessibleReward   the accessible reward.
     * @param inaccessibleReward the inaccessible reward.
     * @param treasureReward     the treasure reward.
     * @param keyReward          the key reward.
     * @param lockReward         the lock reward.
     * @param exitReward         the exit reward.
     */
    public MonkeyAI(int accessibleReward, int inaccessibleReward, int treasureReward, int keyReward, int lockReward, int exitReward) {
        this.accessibleReward = accessibleReward;
        this.inaccessibleReward = inaccessibleReward;
        this.treasureReward = treasureReward;
        this.keyReward = keyReward;
        this.lockReward = lockReward;//TODO needs to check if has key or not
        this.exitReward = exitReward;
    }

    /**
     * Assigns an numeric value to the proposed move.
     *
     * @param move Proposed move.
     * @return Expected Value.
     */
    int utilityFunction(Object move){   //TODO use more specific param type, either tile or move

        Random random = new Random();
        int v = random.nextInt(variance);

        //switch on type of tile being moved to

        //return reward + variance
        return 0;
    }

    /**
     * Checks the Expected Value of each possible move and selects the best.
     *
     * @param currentPosition Current position on the board.
     * @return Move with the highest Expected Value.
     */
    Object selectMove(Object currentPosition) { //TODO use more specific param type
        //For each possible move, calculate EV and select the move with the highest EV
        Object move = new Object();
        Object bestSoFar = move;

        if (utilityFunction(move) > utilityFunction(bestSoFar)) {
            bestSoFar = move;
        }

        return bestSoFar;
    }
}
