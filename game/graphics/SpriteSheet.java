package game.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Contains all needed info to manage a sheet of sprite tile images.
 * 
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public class SpriteSheet
{
	/**
	 * The SpriteSheet for the WorldMap tiles.
	 */
	public static final SpriteSheet	WORLD	= new SpriteSheet(Images.loadImageFrom("/res/images/landscape_tiles_spritesheet_color.png"),16);
	
	
	private static final BufferedImage ITEMS	= Images.loadImageFrom("/res/images/items.png");
	public static final SpriteSheet	ITEMS_16x16	= new SpriteSheet(ITEMS, 16);
	public static final SpriteSheet	ITEMS_8x16	= new SpriteSheet(ITEMS, 8, 16);
	public static final SpriteSheet	ITEMS_8x8	= new SpriteSheet(ITEMS, 8);

	/**
	 * Constants for World sprite tile indexes.
	 * @author Aaron Carson
	 * @version Jul 24, 2015
	 */
	public static class World{
		public static final int			WATER			= 0;
		public static final int			SAND			= 1;
		public static final int			GRASS			= 2;
		public static final int			TREES			= 3;
		public static final int			HILLS			= 4;
		public static final int			MOUNTAINS		= 5;
		public static final int			TALL_MOUNTAINS	= 6;
		public static final int			HUTS			= 7;
		public static final int			CAVE			= 8;
		public static final int			TOWN			= 9;
		public static final int			CAVE_PATH		= 10;
		public static final int			CAVE_PATH_ALT	= 11;
		public static final int			CAVE_WALL		= 12;
		public static final int			CAVE_WALL_ALT	= 13;		
	}
	
	/**
	 * Index values for for the 16 x 16 Item sprites.
	 * 
	 * @author Aaron Carson
	 * @version Jul 24, 2015
	 */
	public static class Items
	{
		/**
		 * Get the SpriteSheet for the given item index.
		 * @param index The index, use 1 for 16x16, 2 for 8x8, and 3 for 8x16.
		 * @return
		 */
		public static SpriteSheet get(int index){
			switch(index){
			case 1: return ITEMS_16x16;
			case 2: return ITEMS_8x8;
			case 3: return ITEMS_8x16;
			default: return null;
			}
		}
		
		// first row - rewards
		public static final int	SMALL_BAG			= 0;
		public static final int	LARGE_BAG			= 1;
		public static final int	COPPER_COINS		= 2;
		public static final int	COPPER_COIN_PILE	= 3;
		public static final int	SILVER_COINS		= 4;
		public static final int	SILVER_COIN_PILE	= 5;
		public static final int	GOLD_COINS			= 6;
		public static final int	GOLD_COIN_PILE		= 7;
		public static final int	PLATINUM_COINS		= 8;
		public static final int	PLATINUM_COIN_PILE	= 9;
		
		// second row - food
		public static final int	BREAD				= 10;
		public static final int	MEAT				= 11;
		
		// third row - drink / bottles
		public static final int	BOTTLE_SQUARE		= 20;
		public static final int	BOTTLE_TAPERED		= 21;
		public static final int	BOTTLE_SQUARE_ALT	= 22;
		public static final int	BOTTLE_TAPERED_ALT	= 23;
		
		// fourth row (weapons, different offsets
		// TODO: fix these
		public static final int	SWORDS				= 30;
		public static final int	AXES				= 33;
		
		// fifth row - treasure chests
		public static final int	TREASURE_CHEST_BRIGHT		= 40;
		public static final int	TREASURE_CHEST_BRIGHT_OPEN	= 41;
		public static final int	TREASURE_CHEST_DINGY		= 42;
		public static final int	TREASURE_CHEST_DINGY_OPEN	= 43;
		public static final int	TREASURE_CHEST_METAL		= 44;
		public static final int	TREASURE_CHEST_METAL_OPEN	= 45;
	}
	
	/**
	 * Index values for the 8 x 16 Weapon sprites.
	 * @author Aaron Carson
	 * @version Jul 24, 2015
	 */
	public static class Weapons{
		
		// SWORDS
		public static final int	BROAD_SWORD		= 60;
		public static final int	SCIMITAR		= 61;
		public static final int	LONG_SWORD		= 62;
		public static final int	SHORT_SWORD		= 63;
		public static final int	DAGGER			= 64;
		public static final int	KNIFE			= 65;
		
		// AXES
		public static final int	BATTLE_AXE		= 66;
		public static final int	HALBERD			= 67;
		public static final int	LARGE_AXE		= 68;
		public static final int	MEDIUM_AXE		= 69;
		public static final int	HAND_AXE		= 70;
		public static final int	THROWING_AXE	= 71;
				
	}	
	
	private BufferedImage	src;
	//private int				tileSize;
	private int				tileWidth;
	private int				tileHeight;
	private int				width;
	private int				height;
	
	/**
	 * 
	 * @param src The src image to load.
	 * @param size The size of each tile, in pixels.
	 */
	public SpriteSheet(BufferedImage src, int size) {
		this(src, size, size);
	}
	
	/**
	 * Create a new SpriteSheet.
	 * @param src The source image.
	 * @param tWidth The width of each tile.
	 * @param tHeight The height of each tile.
	 */
	public SpriteSheet(BufferedImage src, int tWidth, int tHeight){
		this.src = src;
		this.tileWidth = tWidth;
		this.tileHeight = tHeight;
		this.width = src.getWidth() / tileWidth;//srcWidth;
		this.height = src.getHeight() / tileHeight;//srcHeight;
		
	}
	
	/**
	 * Get the the width of the tiles of this SpriteSheet.
	 * @return The width of the tiles in pixels.
	 */
	public int getTileWidth(){
		return tileWidth;
	}
	
	/**
	 * Get the height of the Tiles used by this SpriteSheet.
	 * @return the height of the Tiles in pixels.
	 */
	public int getTileHeight(){
		return tileHeight;
	}
	
	/**
	 * Get the the width of this SpriteSheet in tiles.
	 * @return The width of this SpriteSheet, in tiles.
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Get the the height of this SpriteSheet in tiles.
	 * @return The height of this SpriteSheet, in tiles.
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Render the given tile to the graphics context.
	 * 
	 * @param g The Graphics2D context to render to.
	 * @param tileIndex the index of the tile to render.
	 * @param x The top-left x coordinate to begin drawing at.
	 * @param y The top-left y coordinate to begin drawing at.
	 */
	public void render(Graphics2D g, int tileIndex, int x, int y) {
		
		int xOffset = tileIndex % width;
		int yOffset = tileIndex / width;
		int sx1 = xOffset * tileWidth;
		int sx2 = sx1 + tileWidth;
		int sy1 = yOffset * tileHeight;
		int sy2 = sy1 + tileHeight;
		
		//System.out.printf("src width: %d height: %d\n", width, height);
		//System.out.printf("yOffset: %d height: %d\n", yOffset, height);
		if (yOffset < height) {
			// g.drawImage(src, xOffset, yOffset,null);
			g.drawImage(src, x, y, x + tileWidth, y + tileHeight, sx1, sy1, sx2,
					sy2, null);
		}
		else {
			//System.out.println("Error, tileIndex out of bounds");
		}
	}
		
	public BufferedImage getSrc(){
		return src;
	}
}
