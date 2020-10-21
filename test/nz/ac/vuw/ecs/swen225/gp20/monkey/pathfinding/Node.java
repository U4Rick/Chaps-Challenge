package nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding;

import nz.ac.vuw.ecs.swen225.gp20.monkey.utilities.TileType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Node {

    private final TileType tileType;
    private final LinkedList<Node> shortestPath;
    private final Integer distance;
    private final Map<Node, Integer> adjacentNodes;

    public Node(TileType tileType) {
        this.tileType = tileType;
        this.shortestPath = new LinkedList<>();
        this.distance = Integer.MAX_VALUE;
        this.adjacentNodes = new HashMap<>();
    }
}
