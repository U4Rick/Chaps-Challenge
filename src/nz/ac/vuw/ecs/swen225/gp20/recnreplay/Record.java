package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Record {
    private static List<String> moves = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    private static List<String> actions = new ArrayList<>();

    private JsonObjectBuilder objectBuilder;
    private JsonObject obj;


    public void writeToFile() throws IOException {
        try {
            PrintWriter pw = new PrintWriter("json_data.txt");

            JsonArrayBuilder jArr = Json.createArrayBuilder();
//            JsonObject jsonMove = null;

            for (String action : actions) {     //should be jsonArray with objects
                switch (action) {
                    case "right":
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action)
                                .build()); //todo should be "move", the direction
                    case "down":
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action)
                                .build()); //todo should be "move", the direction
                    case "left":
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action)
                                .build()); //todo should be "move", the direction
                    case "up":
                        jArr.add(Json.createObjectBuilder()
                                .add("move", action)
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

            JsonObject jsonObject = objectBuilder.build();
           pw.write(jArr.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static List<String> getMoves() {
        return moves;
    }

    public static void setMoves(List<String> moves) {
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
