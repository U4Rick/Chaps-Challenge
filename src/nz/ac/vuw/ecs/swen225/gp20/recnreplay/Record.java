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
        try (FileWriter fw = new FileWriter("json_data.json");
             JsonWriter jsonWriter = Json.createWriter(fw);) {
            JsonObject jsonMove = null;
            JsonObject jsonItem = null;


            for (String action : actions) {
                switch (action) {
                    case "right":
                         jsonMove = (JsonObject) objectBuilder.add("move", action); //todo should be "move", the direction
                    case "down":
                         jsonMove = (JsonObject) objectBuilder.add("move", action); //todo should be "move", the direction
                    case "left":
                         jsonMove = (JsonObject) objectBuilder.add("move", action); //todo should be "move", the direction
                    case "up":
                         jsonMove = (JsonObject) objectBuilder.add("move", action); //todo should be "move", the direction
                    case "treasure":
                        jsonItem = (JsonObject) objectBuilder.add("item", action); //todo should be "treasure"/ where it was
                    case "key":
                        jsonItem = (JsonObject) objectBuilder.add("item", action); //todo should be "key"/ where it was
                }
            }

//            for (String move : moves) {       //todo implement move.getDirection()
//               // JsonObject jsonMove = (JsonObject) objectBuilder.add("move", move.getDirection());
//            }
//
//            for (Item item : items) {
//                JsonObject jsonItem = (JsonObject) objectBuilder.add("item", item.toString());
//            }

            JsonObject jsonObject = objectBuilder.build();
            jsonWriter.writeObject(jsonObject);
        }
    }


    public static List<String> getMoves() {
        return moves;
    }

    public static void setMoves(List<String> moves) {
        Record.moves = moves;
    }

    public static List<Item> getItems() {
        return items;
    }

    public static void setItems(List<Item> items) {
        Record.items = items;
    }


}
