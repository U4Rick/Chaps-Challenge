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
        for (int row = 0, boardLength = board.length; row < boardLength; row++) {
            Tile[] tiles = board[row];
            for (int col = 0, tilesLength = tiles.length; col < tilesLength; col++) {
                Tile tile = tiles[col];

                //Check if we could potentially visit the tile, makes the graph sparser which should help traversal.
                if (tile.isAccessible() || tile instanceof DoorTile) {
                    Node newNode = new Node(tile, row, col);

                    //Add all neighbours to this node
                    addNodeNeighbours(board, row, boardLength, col, tilesLength, newNode);
                    nodes.add(newNode);
                }
            }
        }
    }

    private void addNodeNeighbours(Tile[][] board, int row, int boardLength, int col, int tilesLength, Node newNode) {

        //Add north neighbour
        if (row > 0) {
            Tile neighbourTile = board[row - 1][col];
            if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, row - 1, col);
                newNode.addAdjacentNode(neighbour, Integer.MAX_VALUE);
            }
        }

        //Add south neighbour
        if (row < boardLength - 1) {
            Tile neighbourTile = board[row + 1][col];
            if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, row + 1, col);
                newNode.addAdjacentNode(neighbour, Integer.MAX_VALUE);
            }
        }

        //Add west neighbours
        if (col > 0) {
            Tile neighbourTile = board[row][col - 1];
            if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, row, col - 1);
                newNode.addAdjacentNode(neighbour, Integer.MAX_VALUE);
            }
        }

        //add east neighbour
        if (col < tilesLength - 1) {
            Tile neighbourTile = board[row][col + 1];
            if (neighbourTile.isAccessible() || neighbourTile instanceof DoorTile) {
                Node neighbour = new Node(neighbourTile, row, col + 1);
                newNode.addAdjacentNode(neighbour, Integer.MAX_VALUE);
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
