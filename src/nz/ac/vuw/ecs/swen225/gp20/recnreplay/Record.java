package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Record {

    public List<Maze.Direction> moves;

    public Record() {
        this.moves = new ArrayList<>();
    }

    public void writeToFile() throws IOException {
        try {
            PrintWriter pw = new PrintWriter("json_data.json");

            JsonArrayBuilder moves = Json.createArrayBuilder();

            for (Maze.Direction action : this.moves) {
                switch (action) {
                    case RIGHT:
                        moves.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    case DOWN:
                        moves.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    case LEFT:
                        moves.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    case UP:
                        moves.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build());
                    default:
                        break;
                }
            }

            pw.write(moves.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Maze.Direction> getMoves() {
        return moves;
    }

    public void setMoves(List<Maze.Direction> moves) {
        this.moves = moves;
    }

    public void addMove(Maze.Direction dir) {
        this.moves.add(dir);
    }

}
