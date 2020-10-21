package nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding;

import java.util.HashSet;

public class Graph {

    private final HashSet<Node> nodes;

    public Graph(HashSet<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }
}
