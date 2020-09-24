package nz.ac.vuw.ecs.swen225.gp20.recnreplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Record {
    private static List<Tile> moves = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    private JsonObjectBuilder objectBuilder;
    private JsonObject obj;

    public void writeToFile() throws IOException {
        try(FileWriter fw = new FileWriter("jsonData.txt");
        JsonWriter jsonWriter = Json.createWriter(fw);) {

            for (Tile move : moves) {       //todo implement move.getDirection()
               // JsonObject jsonMove = (JsonObject) objectBuilder.add("move", move.getDirection());
            }

            for (Item item : items) {
                JsonObject jsonItem = (JsonObject) objectBuilder.add("item", item.toString());
            }

            JsonObject jsonObject = objectBuilder.build();
            jsonWriter.writeObject(jsonObject);
        }
    }



    public static List<Tile> getMoves() {
        return moves;
    }

    public static void setMoves(List<Tile> moves) {
        Record.moves = moves;
    }

    public static List<Item> getItems() {
        return items;
    }

    public static void setItems(List<Item> items) {
        Record.items = items;
    }


}
