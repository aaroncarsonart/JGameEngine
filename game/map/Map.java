package game.map;

import game.graphics.sprite.ItemSprite;
import game.graphics.sprite.Sprite;
import game.item.Item;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.Position;
import utility.Quad;

/**
 * Map represents all data contained in
 * 
 * @author Aaron Carson
 * @version Jul 25, 2015
 */
public abstract class Map
{
	public static final byte	PATH			= 0;
	public static final byte	WALL			= 1;
	public static final byte	OUT_OF_BOUNDS	= 2;
	
	// ************************************************************************
	// Fields
	// ************************************************************************
	
	protected byte[][]			cells;
	protected Quad[][]			quads;
	
	protected Sprite[][]	    spriteGrid;
	protected List<Sprite>		sprites;
		
	private String				name;
	
	protected int				width;
	protected int				height;
	protected int				tileSize;
	protected int				widthInPixels;
	protected int				heightInPixels;
	
	protected Random			random;
	
	// ************************************************************************
	// Constructors
	// ************************************************************************
	
	/**
	 * Create a new, blank Map of square tiles.
	 * 
	 * @param name The name of this map.
	 * @param width The width in cells.
	 * @param height The height in cells.
	 * @param tileSize The dimension of each tile, in pixels.
	 */
	public Map(String name, int width, int height, int tileSize) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.widthInPixels = this.width * tileSize;
		this.heightInPixels = this.height * tileSize;
		this.random = new Random();

		this.sprites = new ArrayList<Sprite>();
		this.spriteGrid = new Sprite[width][height];
		
		// initialize the Quads.
		this.quads = new Quad[width][height];
		double ht = tileSize / 2;
		// initialize the values.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Quad q = new Quad(x * tileSize + ht, y * tileSize + ht, ht, ht);
				quads[x][y] = q;
			}
		}
	}
	
	// ************************************************************************
	// Methods
	// ************************************************************************
		
	/**
	 * Adds the Sprite.  If the sprite collides, then it must occupy a unique
	 * slot in the spriteGrid.
	 * @param sprite The Sprite to add.
	 * @return True, if the Sprite was added.
	 */
	public boolean addSprite(Sprite sprite){
		int x = (int)(sprite.hitbox.x / tileSize);
		int y = (int)(sprite.hitbox.y / tileSize);
		return addSprite(sprite, x, y);
	}
	
	/**
	 * Adds the Sprite.  If the sprite collides, then it must occupy a unique
	 * slot in the spriteGrid.
	 * @param sprite The Sprite to add.
	 * @param x The x coordinate to insert at (for speed)
	 * @param y The y coordinate to insert at (for speed)
	 * @return True, if the Sprite was added.
	 */
	public boolean addSprite(Sprite sprite, int x, int y){
		if(sprite.collides){
			
			// if sprite collides, and the new slot is occupied.
			if(spriteGrid[x][y] != null){
				return false;
			}
			
			// the slot is open, and the sprite could be added.
			else if (sprites.add(sprite)){
				spriteGrid[x][y] = sprite;
				return true;
			}
			
			// the add failed.
			else{
				return false;
			}
		}	
		
		// the sprite does not collide.
		return sprites.add(sprite);
	}
	
	
	/**
	 * Get the name of this map.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the width of this Map, in tiles.
	 * 
	 * @return The width in tiles.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of this Map, in tiles.
	 * 
	 * @return The height in tiles.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get the tileSize.
	 * 
	 * @return the tile size, in pixels (width and height).
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * Get the width of this Map, in pixels.
	 * 
	 * @return
	 */
	public int getWidthInPixels() {
		return widthInPixels;
	}
	
	/**
	 * Get the Height of this Map in pixels.
	 * 
	 * @return
	 */
	public int getHeightInPixels() {
		return heightInPixels;
	}
	
	/**
	 * Check if the given coordinate is within bounds, so x is from 0 inclusive
	 * to width exclusive, and y is from 0 inclusive and height inclusive.
	 * 
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return True, if the cell coordinate of (x, y) is within bounds.
	 */
	public boolean withinBounds(int x, int y) {
		return 0 <= x && x < width && 0 <= y && y < height;
	}
	
	/**
	 * Check if the given coordinate is passable (does a within bounds check;
	 * any coordinate outside of the Map boundaries is considered not passable)
	 * 
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return True, if the cell at (x, y) is passable.
	 */
	public boolean isPassable(int x, int y) {
		return withinBounds(x, y) && cells[x][y] == PATH;
	}

	/**
	 * Check if the given coordinate is occupied.
	 * 
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return True, if the cell at (x, y) is occupied.
	 */
	public boolean isOccupied(int x, int y) {
		return cells[x][y] == WALL || spriteGrid[x][y] != null;
	}

	
	/**
	 * Get the value of the cell at the given x and y coordinates.
	 * 
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return The byte value of the cell at (x, y).
	 */
	public byte getCell(int x, int y) {
		return withinBounds(x, y) ? cells[x][y] : OUT_OF_BOUNDS;
	}
	
	/**
	 * Get the cells byte[][] storing WALL/PATH data of this Map.
	 * 
	 * @return The cells byte[][].
	 */
	public byte[][] getCells() {
		return cells;
	}
	
	public Quad[][] getQuads() {
		return quads;
	}
	
	/**
	 * Get a list of sprites that were added to this list.
	 * @return
	 */
	public List<Sprite> getSprites(){
		return sprites;
	}
	
	/**
	 * Find a randomly selected, empty position on this map.
	 * 
	 * @return An empty position
	 */
	public Position getEmptyGridPosition() {
		int x, y;
		// find an empty position.
		do {
			x = random.nextInt(width);
			y = random.nextInt(height);
		} while (!isPassable(x, y));
		
		return new Position(x, y);
	}
	
	public Position getUnoccupiedGridPosition(){
		int x, y;
		// find an empty position.
		do {
			x = random.nextInt(width);
			y = random.nextInt(height);
		} while (isOccupied(x, y));
		
		return new Position(x, y);
	}
	
	/**
	 * Add The given item to the Map.  Automatically creates the ItemSprite
	 * from the Item's properties.
	 * @param item The item to add.
	 */
	public void addItem(Item item, boolean sparkle){
		Position p = getUnoccupiedGridPosition();
		int ix = p.x() * tileSize + tileSize/2;
		int iy = p.y() * tileSize + tileSize/2;
		
		addSprite(new ItemSprite(item, ix, iy, sparkle), p.x(), p.y());		
	}
	
	/**
	 * Find a randomly selected, empty position on this map.
	 * 
	 * @return An empty position
	 */
	public Position getEmptyAbsolutePosition() {
		Position p = getEmptyGridPosition();
		p.set(p.x() * tileSize, p.y() * tileSize);
		return p;
	}
	
	/**
	 * Adjust the given Quad for colliding with the Map boundaries.
	 * 
	 * @param hitbox
	 */
	public void adjustForMapBoundaries(Quad hitbox) {
		// ****************************
		// adjust for any collisions.
		// ****************************
		// keep within bounds of map.
		if (hitbox.x < hitbox.halfWidth) {
			hitbox.x = hitbox.halfWidth;
			// System.out.println("out of bounds - LEFT");
		}
		else if (hitbox.x >= widthInPixels - hitbox.halfWidth) {
			hitbox.x = widthInPixels - hitbox.halfWidth;
			// System.out.println("out of bounds - RIGHT");
		}
		if (hitbox.y < hitbox.halfHeight) {
			hitbox.y = hitbox.halfHeight;
			// System.out.println("out of bounds - UP");
		}
		else if (hitbox.y >= heightInPixels - hitbox.halfHeight) {
			hitbox.y = heightInPixels - hitbox.halfHeight;
			// System.out.println("out of bounds - DOWN");
		}
	}
	
	/**
	 * Adjusts the given Quad
	 * 
	 * @param quad The Quad to adjust for collisions on.
	 */
	public void adjustForCollisions(Quad quad) {
		
		// ********************
		// quadtrees:
		// ********************
		// http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
		
		// ********************
		// collision detection:
		// ********************
		// http://gamedevelopment.tutsplus.com/tutorials/collision-detection-with-the-separating-axis-theorem--gamedev-169
		// cast as ints
		int left = (int) quad.getLeftX();
		int right = (int) quad.getRightX();
		int top = (int) quad.getTopY();
		int bottom = (int) quad.getBottomY();
		
		// System.out.printf("l: %d r: %d t: %d b: %d\n", left, right, top,
		// bottom);
		
		// indices are guaranteed to be within the boundaries of the map.
		int ix = left /= tileSize;
		int iy = top /= tileSize;
		
		// check the four possible surrounding tiles.
		Quad t = quads[ix][iy];
		byte b = cells[ix][iy];
		if (b == WALL) quad.fixIfCollides(t);
		boolean checkX = ix + 1 < width;
		boolean checkY = iy + 1 < height;
		
		if (checkX) {
			t = quads[ix + 1][iy];
			b = cells[ix + 1][iy];
			if (b == WALL) quad.fixIfCollides(t);
		}
		if (checkY) {
			t = quads[ix][iy + 1];
			b = cells[ix][iy + 1];
			if (b == WALL) quad.fixIfCollides(t);
		}
		if (checkX && checkY) {
			t = quads[ix + 1][iy + 1];
			b = cells[ix + 1][iy + 1];
			if (b == WALL) quad.fixIfCollides(t);
		}
		
		
		//QuadTree tree = new QuadTree();
		for(Sprite s : sprites){
			//tree.insert(s);
			quad.fixIfCollides(s.hitbox);
		}		
		//List<Sprite> matches = new ArrayList<>();
		//tree.getSprites(quad);
		//for(Sprite s : matches){
		//	quad.fixIfCollides(s.hitbox);
		//}

	}
	
	/**
	 * Create a pre-rendered background image for the entire map.
	 * 
	 * @return A pre-rendered image for the entire background graphic.
	 */
	public abstract BufferedImage createBackgroundImage();
	
	/**
	 * Create a pre-rendered background image for the entire foreground.
	 * 
	 * @return A pre-rendered image for the foreground graphic.
	 */
	public abstract BufferedImage createForegroundImage();
	
	/**
	 * Create a pre-rendered lighting image for the entire map.
	 * 
	 * @return A pre-rendered image for the entire Map that is a lighting mask.
	 *         The alpha value of each pixel determines how light and shadow
	 *         falls; an alpha of zero will be rendered as black (not visible),
	 *         while an alpha of 255 will be the rendered the original tile
	 *         color (completely visible).
	 */
	public abstract BufferedImage createLightMaskImage();
	
}
