package utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Dungeon
{
    public static final byte PATH = 0;
    public static final byte WALL = 1;
    public static final byte OUT_OF_BOUNDS = 2;

    private int width;
    private int height;
    private byte[][] cells;
    
    /**
     * Builder Class with static types only.
     * @param width The width in cells.
     * @param height The height in cells.
     */
	private Dungeon(int width, int height){
		cells = new byte[width][height];
	}
	
	
	/**
	 * Create a new Dungeon with the given parameters.
	 * @param width The width in tiles.
	 * @param height The height in tiles.
	 * @param rooms The number of rectangular rooms to randomly generate in the
	 * space.
	 * @return a new dungeon of given size with the given number of
	 * interconnected rooms.
	 */
	public static final Dungeon createWalledDungeon(int width, int height, int rooms){

		// 0. make dungeon with room for an outer wall.
		Dungeon d = new Dungeon(width + 2, height + 2);
		Random random = new Random();
		
		// 1. set entire dungeon to walls.
		for (int x = 0; x < width; x++){
			for (int y = 0; y < width; y++){
				d.cells[x][y] = WALL;
			}
		}
		
		List<Quad> quads = new LinkedList<Quad>();
		
		// 2. remove rooms and make as paths and visited.
		for(int i = 0; i < rooms; i++){
			int y1  = 1 + random.nextInt(height);
			int y2  = 1 + random.nextInt(height);
			int x1  = 1 + random.nextInt(width);
			int x2  = 1 + random.nextInt(width);
			double xSize = Math.abs(x1 - x2);
			double ySize = Math.abs(y1 - y2);
			double hw = xSize / 2;
			double hh = xSize / 2;
			double cx = Math.min(x1, x2) + hw;
			double cy = Math.min(y1, y2) + hh;
			
			Quad q = new Quad(cx, cy, hw, hh);
			
		}
		
		// 3. Add 1 - 2 unvisited paths on the perimeter of each room.
		// 4. Depth first search through the dungeon like the maze.
		// 5. Done.
		return d;		
	}
}

