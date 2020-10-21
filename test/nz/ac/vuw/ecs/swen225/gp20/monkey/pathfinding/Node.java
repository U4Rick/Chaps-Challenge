package nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.monkey.utilities.TileType;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Node {

    private final String nodeID;
    private LinkedList<Node> shortestPath;
    private Integer distance;
    private Map<Node, Integer> adjacentNodes;

    public Node(Tile tile, int xPos, int yPos) {
        this.nodeID = tile.getClass().getSimpleName() + "_" + xPos + "_" + yPos;
        this.shortestPath = new LinkedList<>();
        this.distance = Integer.MAX_VALUE;
        this.adjacentNodes = new HashMap<>();
    }

    public void addAdjacentNode(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public LinkedList<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return nodeID.equals(node.nodeID);
    }

    @Override
    public int hashCode() {
        return nodeID.hashCode();
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeID='" + nodeID + '\'' +
                ", shortestPath=" + shortestPath +
                ", distance=" + distance +
                ", adjacentNodes=" + adjacentNodes.size() +
                '}' + "\n";
    }
}
