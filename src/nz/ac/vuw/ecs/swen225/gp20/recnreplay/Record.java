package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a record and write out the players movements.
 * @author Ricky McLean
 */
public class Record {

    public List<Maze.Direction> moves;

    /**
     * Constructs a record object.
     */
    public Record() {
        this.moves = getMoves();
    }

    /**
     * Writes the movements made by the player out to a file.
     * @throws IOException if file error occurs
     */
    public void writeToFile() throws IOException {
        try {
            PrintWriter pw = new PrintWriter("json_data.json");

            JsonArrayBuilder jArr = Json.createArrayBuilder();

            for (Maze.Direction action : moves) {
                switch (action) {
                    case RIGHT:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    case DOWN:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    case LEFT:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    case UP:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    default:
                        break;
                }
            }

            pw.write(jArr.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of moves made by the player.
     * @return the list of moves
     */
    public List<Maze.Direction> getMoves() {
        return moves;
    }

    /**
     * Set the list of moves made by the player.
     * @param moves the list of moves.
     */
    public void setMoves(List<Maze.Direction> moves) {
        this.moves = moves;
    }

    /**
     * Add a move to the current list of moves made by the player.
     * @param dir the direction of the movement
     */
    public void addMove(Maze.Direction dir) {
        this.moves.add(dir);
    }

}
