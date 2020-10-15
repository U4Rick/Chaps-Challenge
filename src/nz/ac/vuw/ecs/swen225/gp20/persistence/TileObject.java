package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.Colours;

/**
 * A helper class for storing tile object data
 *
 * @author Tristan
 */
class TileObject {
	public int x;
	public int y;
	public Colours colour;

	/**
	 * Constructor to be used if the object doesn't have a colour value.
	 * @param x x value of the object.
	 * @param y y value of the object.
	 */
	public TileObject(int x, int y) {
		this.x = x;
		this.y = y;
		this.colour = null;
	}

	/**
	 * Constructor to be used if the object does have a colour value
	 * @param x x value of the object.
	 * @param y y value of the object.
	 * @param colour colour value of the object.
	 */
	public TileObject(int x, int y, Colours colour) {
		this.x = x;
		this.y = y;
		this.colour = colour;
	}
}