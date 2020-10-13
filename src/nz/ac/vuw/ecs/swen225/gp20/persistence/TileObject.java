package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Colours;

/**
 * 
 * @author Tristan
 *
 */
class TileObject {
	public int x;
	public int y;
	public Colours colour;
	
	public TileObject(int x, int y) {
		this.x = x;
		this.y = y;
		this.colour = null;
	}
	public TileObject(int x, int y, Colours colour) {
		this.x = x;
		this.y = y;
		this.colour = colour;
	}
}