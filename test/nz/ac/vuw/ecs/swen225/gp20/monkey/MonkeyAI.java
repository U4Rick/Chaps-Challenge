package nz.ac.vuw.ecs.swen225.gp20.monkey;

/**
 * Abstract class representing the contractual requirements of MonkeyAI players.
 */
public interface MonkeyAI {

    /**
     * Assigns an numeric value to the proposed move.
     *
     * @param move Proposed move.
     * @return Expected Value.
     */
    int utilityFunction(Object move); //TODO use more specific param type

    /**
     * Checks the Expected Value of each possible move and selects the best.
     *
     * @param currentPosition Current position on the board.
     * @return Move with the highest Expected Value.
     */
    Object selectMove(Object currentPosition); //TODO use more specific param type
}
