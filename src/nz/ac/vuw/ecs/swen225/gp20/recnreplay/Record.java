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

    public void record() {
        try{
            FileReader fr = new FileReader(new File("temp"));

            JsonReader jr = Json.createReader(fr);
            JsonArray jArr = jr.readArray();

            for (int i = 0; i < jArr.size()-1; i++) {
                JsonObject object = jArr.getJsonObject(i).asJsonObject();
                //deal with different type of actions here e.g move/item
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String writeFile(String json){
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(new PrintWriter(stringWriter));
        jsonWriter.writeObject(obj);
        jsonWriter.close();
        return stringWriter.toString();

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
