package nz.ac.vuw.ecs.swen225.gp20.monkey.pathfinding;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

import java.awt.*;
import java.util.HashSet;

public class Graph {

    private final HashSet<Node> nodes;

    public Graph(Tile[][] board) {
        nodes = new HashSet<>();

        //For each tile in the board, create a node and set it's adjacent nodes
        for (int i = 0, boardLength = board.length; i < boardLength; i++) {
            Tile[] tiles = board[i];
            for (int j = 0, tilesLength = tiles.length; j < tilesLength; j++) {
                Tile tile = tiles[j];

                //Check if we could potentially visit the tile, makes the graph sparser which should help traversal.
                if (tile.isAccessible() || tile instanceof DoorTile) {
                    Node newNode = new Node(tile, i, j);

                    //Add all neighbours to this node
                    addNodeNeighbours(board, i, boardLength, j, tilesLength, newNode);
                }
            }
        }
    }

    private void addNodeNeighbours(Tile[][] board, int i, int boardLength, int j, int tilesLength, Node newNode) {
        if (i > 0) {
            Tile neighbourTile = board[i - 1][j];
            if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, i - 1, j);
                newNode.addAdjacentNode(neighbour, 1);
            }
        }
        if (i < boardLength - 1) {
            Tile neighbourTile = board[i - 1][j];
            if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, i + 1, j);
                newNode.addAdjacentNode(neighbour, 1);
            }
        }
        if (j > 0) {
            Tile neighbourTile = board[i - 1][j];if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, i, j - 1);
                newNode.addAdjacentNode(neighbour, 1);
            }
        }
        if (j < tilesLength - 1) {
            Tile neighbourTile = board[i - 1][j];if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, i, j + 1);
                newNode.addAdjacentNode(neighbour, 1);
            }
        }
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }
}
