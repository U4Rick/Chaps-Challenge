package nz.ac.vuw.ecs.swen225.gp20.recnreplay;


import nz.ac.vuw.ecs.swen225.gp20.commons.Direction;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a record and write out the players movements.
 *
 * @author Ricky McLean
 */
public class Record {

    public List<Direction> moves;
    public int levelNumber;

    /**
     * Constructs a record object.
     */
    public Record(int levelNum) {
        this.moves = new ArrayList<>();
        this.levelNumber = levelNum;
    }

    /**
     * Writes the movements made by the player out to a file.
     * @param replayFile
     */
    public void writeToFile(File replayFile) {
        try {
            PrintWriter pw = new PrintWriter(replayFile);

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            JsonArrayBuilder moves = Json.createArrayBuilder();

            JsonArrayBuilder levels = Json.createArrayBuilder();
            //todo write the current level to a jsonArray and make object "level : level1" etc

            for (Direction action : this.moves) {
                switch (action) {
                    case RIGHT:
                    case DOWN:
                    case LEFT:
                    case UP:
                        moves.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                        break;
                    default:
                        break;
                }
            }
            jsonObject.add("moves", moves);
            JsonObject JsonObjectMain = jsonObject.build();

            pw.write(JsonObjectMain.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of moves made by the player.
     *
     * @return the list of moves
     */
    public List<Direction> getMoves() {
        return moves;
    }

    /**
     * Set the list of moves made by the player.
     *
     * @param moves the list of moves.
     */
    public void setMoves(List<Direction> moves) {
        this.moves = moves;
    }

    /**
     * Add a move to the current list of moves made by the player.
     *
     * @param dir the direction of the movement
     */
    public void addMove(Direction dir) {
        this.moves.add(dir);
    }

}
