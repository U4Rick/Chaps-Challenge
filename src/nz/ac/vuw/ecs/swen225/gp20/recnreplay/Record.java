package nz.ac.vuw.ecs.swen225.gp20.recnreplay;


import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a record and write out the players movements.
 *
 * @author Ricky McLean
 */
public class Record {

    public List<Maze.Direction> moves;

    /**
     * Constructs a record object.
     */
    public Record() {
        this.moves = new ArrayList<>();
    }

    /**
     * Writes the movements made by the player out to a file.
     */
    public void writeToFile() {
        try {
            PrintWriter pw = new PrintWriter("json_data_record.json");

            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            JsonArrayBuilder moves = Json.createArrayBuilder();

            for (Maze.Direction action : this.moves) {
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
    public List<Maze.Direction> getMoves() {
        return moves;
    }

    /**
     * Set the list of moves made by the player.
     *
     * @param moves the list of moves.
     */
    public void setMoves(List<Maze.Direction> moves) {
        this.moves = moves;
    }

    /**
     * Add a move to the current list of moves made by the player.
     *
     * @param dir the direction of the movement
     */
    public void addMove(Maze.Direction dir) {
        this.moves.add(dir);
    }

}
