package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utility.ImageLoader;

/**
 * Contains all needed info to manage a sheet of sprite tile images.
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public class SpriteSheet
{	
	/**
	 * The SpriteSheet for the WorldMap tiles.
	 */
	public static final SpriteSheet WORLD = new SpriteSheet(ImageLoader.loadImageFrom("/res/images/landscape_tiles_spritesheet_color.png"), 16, 8 , 8);
	/*
	public static final BufferedImage LARGE_SHADOW = ImageLoader.loadImageFrom("/res/images/shadow.png");
	public static final BufferedImage MEDIUM_SHADOW = ImageLoader.loadImageFrom("/res/images/shadow_medium.png");
	public static final BufferedImage MEDIUM_SHADOW_MASK = ImageLoader.loadImageFrom("/res/images/medium_shadow_mask.png");
	public static final BufferedImage SMALL_SHADOW = ImageLoader.loadImageFrom("/res/images/shadow_5x5.png");
	public static final BufferedImage SHADOW_128 = ImageLoader.loadImageFrom("/res/images/shadow_128.png");
	public static final BufferedImage SHADOW_128_FAINT = ImageLoader.loadImageFrom("/res/images/shadow_128_faint.png");
	public static final BufferedImage SHADOW_512 = ImageLoader.loadImageFrom("/res/images/shadow_512.png");
	public static final BufferedImage SHADOW_288 = ImageLoader.loadImageFrom("/res/images/shadow_288.png");
	public static final BufferedImage SHADOW_144 = ImageLoader.loadImageFrom("/res/images/shadow_144.png");
	*/
	
	public static final int WATER = 0;
	public static final int SAND = 1;
	public static final int GRASS = 2;
	public static final int TREES = 3;
	public static final int HILLS = 4;
	public static final int MOUNTAINS = 5;
	public static final int TALL_MOUNTAINS = 6;
	public static final int HUTS = 7;
	public static final int CAVE = 8;
	public static final int TOWN = 9;
	public static final int CAVE_PATH = 10;
	public static final int CAVE_PATH_ALT = 11;
	public static final int CAVE_WALL = 12;
	public static final int CAVE_WALL_ALT = 13;

	
	private BufferedImage src;
	private int tileSize;
	private int width;
	private int height;
	
	/**
	 * 
	 * @param src The src image to load.
	 * @param size The size of each tile, in pixels.
	 * @param width The width of the spritesheet, in tiles.
	 * @param height The height of the spritesheet, in tiles.
	 */
	public SpriteSheet(BufferedImage src, int size, int width, int height){
		this.src = src;
		this.tileSize = size;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Render the given tile to the graphics context.
	 * @param g The Graphics2D context to render to.
	 * @param tileIndex the index of the tile to render.
	 */
	public void render(Graphics2D g, int tileIndex, int x, int y){

		int xOffset = tileIndex % width;
		int yOffset = tileIndex / width;
		int sx1 = xOffset * tileSize;
		int sx2 = xOffset * tileSize + tileSize;
		int sy1 = yOffset * tileSize;
		int sy2 = yOffset * tileSize + tileSize;
		
		if (yOffset < height){
			//g.drawImage(src, xOffset, yOffset,null);
			g.drawImage(src, x, y, x + tileSize, y + tileSize, sx1, sy1, sx2, sy2, null);
		}
		else{
			System.out.println("Error, tileIndex out of bounds");
		}
	}
}
