package nz.ac.vuw.ecs.swen225.gp20.monkey.tests;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding.DijkstraPathfinding;
import nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding.Graph;
import nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;


/**
 * Testing suite for the Capuchin model AI, which tries to play the game properly.
 *
 * @author Matt
 */
public class DijkstraTest {

    private Main main;
    private Graph graph;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        main = new Main();
        main.setDebugMode(true);
        graph = new Graph(main.getMaze().getBoard());
    }

    @Test
    void exampleTest() {
        Point chapPosition = main.getMaze().getChapPosition();
        Tile tile = main.getMaze().getTile(chapPosition.x, chapPosition.y);

        Node chapsPosition = new Node(tile, chapPosition.x, chapPosition.y);

        Graph shortestPath = DijkstraPathfinding.calculateShortestPath(this.graph, chapsPosition);

        for (int i = 0; i < 1_000_000; i++) {
            if (main.getMaze().getChapWin()) {
                break;
            }
        }
    }
}

