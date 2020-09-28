package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Record {
    private static List<Maze.Direction> moves;

    private JsonObjectBuilder objectBuilder;
    private JsonObject obj;


    public void writeToFile() throws IOException {
        try {
            PrintWriter pw = new PrintWriter("json_data.txt");

            JsonArrayBuilder jArr = Json.createArrayBuilder();
//            JsonObject jsonMove = null;

            for (Maze.Direction action : moves) {     //should be jsonArray with objects
                switch (action) {
                    case RIGHT:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build()); //todo should be "move", the direction
                    case DOWN:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build()); //todo should be "move", the direction
                    case LEFT:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build()); //todo should be "move", the direction
                    case UP:
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action.toString())
                                .build()); //todo should be "move", the direction
                    default:
                        break;
                }
            }

//            for (String move : moves) {       //todo remove later
//               // JsonObject jsonMove = (JsonObject) objectBuilder.add("move", move.getDirection());
//            }
//
//            for (Item item : items) {
//                JsonObject jsonItem = (JsonObject) objectBuilder.add("item", item.toString());
//            }

//            JsonObject jsonObject = objectBuilder.build();
           pw.write(jArr.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Maze.Direction> getMoves() {
        return moves;
    }

    public static void setMoves(List<Maze.Direction> moves) {
        Record.moves = moves;
    }


//    public static List<Item> getItems() {
//        return items;
//    }
//
//    public static void setItems(List<Item> items) {
//        Record.items = items;
//    }


}
