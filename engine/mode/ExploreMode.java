package engine.mode;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Random;

import engine.Command;
import utility.Difficulty;
import utility.Maze;
import utility.Quad;
import utility.Tile;

/**
 * Explore a game map.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class ExploreMode extends GameMode
{
	private static final byte	PATH			= 0;
	private static final byte	WALL			= 1;
	
	private static final Color	WALL_COLOR		= Color.DARK_GRAY;
	private static final Color	PATH_COLOR		= Color.WHITE;
	private static final Color	PLAYER_COLOR	= Color.BLUE;
	public static final double  WALK_SPEED 			= 1.0;
	public static final double  RUN_SPEED 			= 5.0;
	
	Quad						player;
	double						speed;
	
	int							width;
	int							height;
	int							tWidth;
	int							tHeight;
	
	int							tileSize;
	
	Tile[][]					tiles;
	Random						random;
	
	BufferedImage bgImage;
	BufferedImage fgImage;
	
	Maze maze;
	
	
	/**
	 * Create a new ExploreMode.
	 */
	public ExploreMode() {
		super("Explore Mode");
		tileSize = 16;
		// *player at (0, 0) with width and height of 16
		player = new Quad(8, 8, 8, 8);
		speed = WALK_SPEED;
		
		//maze = Maze.generateRandomMaze(20, 30);
		//maze.setDifficulty(Difficulty.NORMAL);
		//width = maze.getWidth();
		//height = maze.getHeight();
		width = 50;
		height = 50;
		
		
		tWidth  = width * tileSize;
		tHeight = height * tileSize;
		
		random = new Random();
		initializeTiles();
		//initializeTilesForMaze();

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		// initialize game graphics layers
		bgImage = gc.createCompatibleImage(tWidth, tHeight, Transparency.TRANSLUCENT);
		fgImage = gc.createCompatibleImage(tWidth, tHeight, Transparency.TRANSLUCENT);
		
		Graphics2D bgGraphics = graphics.getBackgroundGraphics();
		bgGraphics.setColor(Color.BLACK);
		drawBackground();
		bgGraphics.drawImage(bgImage, 0, 0, null);

		//drawForeground();		
	}
	
	private void initializeTilesForMaze(){
		tiles = new Tile[width][height];
		int halfTile = tileSize / 2;
		// set them all randomly.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Quad nextQuad = new Quad(x * tileSize + halfTile, y * tileSize + halfTile, halfTile, halfTile);
				byte type = maze.getCell(x, y) == Maze.WALL ? WALL : PATH;
				tiles[x][y] = new Tile(nextQuad, type);
			}
		}	
	}
	
	private void initializeTiles(){
		tiles = new Tile[width][height];
		int halfTile = tileSize / 2;
		// set them all randomly.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Quad nextQuad = new Quad(x * tileSize + halfTile, y * tileSize + halfTile, halfTile, halfTile);
				if (random.nextInt(4) == 0) tiles[x][y] = new Tile(nextQuad, WALL);
				else tiles[x][y] = new Tile(nextQuad, PATH);
			}
		}
		
		// set a top corner to be guaranteed paths.
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tiles[x][y].type = PATH;
			}
		}
	}
	
	/**
	 * Render the background image.
	 */
	public void drawBackground() {
		Graphics2D bgGraphics = (Graphics2D) bgImage.getGraphics();
		Color c;
		
		// draw the tiles.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y].type == WALL) {
					c = WALL_COLOR;
				}
				else {
					c = PATH_COLOR;
				}				
				bgGraphics.setColor(c);
				bgGraphics.fillRect(x * tileSize, y * tileSize, tileSize,tileSize);
			}
		}
		bgGraphics.dispose();
		
	}
	
	/**
	 * Render the foreground image.
	 */
	public void drawForeground() {
		int gWidth = graphics.getWidth();
		int gHeight = graphics.getHeight();
	
		int[] fgPixels = graphics.getForegroundPixels();
	
		int fc = Color.GREEN.getRGB();

		for (int x = 0; x < gWidth; x++) {
			for (int y = 0; y < gHeight; y++) {
				
				if (random.nextInt(20) == 0) {
					fgPixels[x + y * gWidth] = fc;	
				}		
				else{
					fgPixels[x + y * gWidth] = 0;
				}
			}
		}
	}
	
	
	@Override
	public void update() {
		updatePlayer();
		updateGraphics();
	}
	
	
	
	
		
   /**
    * Check if the given position is passable.
    * @param p The position to check.
    * @return True if the position is within bounds and the associated tile is
    * a wall, otherwise returns false.
    */
    public boolean isPassable(int x, int y){
	    return 0 < x && x < width && 0 < y && y < height && tiles[x][y].type == PATH;
    }
	
	/**
	 * Update the player's position.
	 */
	private void updatePlayer() {
		
		//System.out.printf("px: %f py: %f\n", px, py);
		
		// calculate the new dx and dy.
		double dx = 0;
		double dy = 0;		
		if (Command.CANCEL.isPressed()) speed = RUN_SPEED;
		else speed = WALK_SPEED;
		if (Command.UP.isPressed()) dy -= speed;
		if (Command.DOWN.isPressed()) dy += speed;
		if (Command.LEFT.isPressed()) dx -= speed;
		if (Command.RIGHT.isPressed()) dx += speed;
		
		// move the player.
		player.x += dx;
		player.y += dy;

		// ****************************
		// adjust for any collisions.
		// ****************************
		// keep within bounds of map.
		if (player.x < player.halfWidth) player.x = player.halfWidth;
		else if (player.x >= tWidth - player.halfWidth) player.x  = tWidth - player.halfWidth;
		if (player.y < player.halfHeight) player.y = player.halfHeight;
		else if (player.y >= tHeight - player.halfHeight) player.y  = tHeight - player.halfHeight;
		
		// collisions!
		adjustForCollisions(player);
	}
	/**
	 * Adjusts the given Quad
	 * @param quad The Quad to adjust for collisions on.
	 */
	public void adjustForCollisions(Quad quad){
				
		// ********************
		// quadtrees: 
		// ********************
		// http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374		

		// ********************
		// collision detection:
		// ********************
		// http://gamedevelopment.tutsplus.com/tutorials/collision-detection-with-the-separating-axis-theorem--gamedev-169		
		// cast as ints
		int left   = (int) quad.getLeftX();
		int right  = (int) quad.getRightX();
		int top    = (int) quad.getTopY();
		int bottom = (int) quad.getBottomY();
				
		//System.out.printf("l: %d r: %d t: %d b: %d\n", left, right, top, bottom);
		
		// indices are guaranteed to be within the boundaries of the map.
		int ix = left /= tileSize;
		int iy = top /= tileSize;

		// check the four possible surrounding tiles.
		Tile t;		
		t = tiles[ix][iy];	
		if(t.type == WALL) quad.fixIfCollides(t.quad);
		boolean checkX = ix + 1 < width;
		boolean checkY = iy + 1 < height;
		
		
		if(checkX){
			t = tiles[ix+1][iy];	
			if(t.type == WALL) quad.fixIfCollides(t.quad);
		}
		if(checkY){
		t = tiles[ix][iy+1];	
		if(t.type == WALL) quad.fixIfCollides(t.quad);
		}
		if(checkX && checkY){
			t = tiles[ix+1][iy+1];	
			if(t.type == WALL) quad.fixIfCollides(t.quad);
		}
		
		// check any sprites.
		
		
		//Position result = new Position(left, top);
		//LinkedList<Quad> quads = new LinkedList<Quad>();
		
		
	}

	
	
	@Override
	public void updateGraphics() {
		//updateGraphicsStatic();
		updateGraphicsDynamic();
	}
	
	/**
	 * Keep the camera stationary as the player moves.
	 */
	public void updateGraphicsStatic(){
		int gWidth = graphics.getWidth();
		int gHeight = graphics.getHeight();
		
		
		// ***********************************************************
		// Sprites
		// ***********************************************************
		BufferedImage sImage = graphics.getSpriteImage();
		Graphics2D sGraphics = (Graphics2D) sImage.getGraphics();
		//int[] sPixels = graphics.getSpritePixels();
		
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR);
		sGraphics.setComposite(composite);
		sGraphics.setColor(Color.BLACK);
		sGraphics.fillRect(0, 0, gWidth, gHeight);
		sGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			
		sGraphics.setColor(PLAYER_COLOR);
		sGraphics.fillRect((int) player.getLeftX(), (int) player.getTopY(), 
				(int) player.getWidth(), (int) player.getHeight());
		sGraphics.dispose();
		
	}
	
	
	// move the camera with the player.
	public void updateGraphicsDynamic(){
		int gWidth = graphics.getWidth();
		int gHeight = graphics.getHeight();
		int hgw = gWidth / 2;
		int hgh = gHeight / 2;
		
		// ***********************************************************
		// Background
		// ***********************************************************
		Graphics2D bgGraphics = graphics.getBackgroundGraphics();
		
		// fill black
		bgGraphics.fillRect(0, 0, gWidth, gHeight);
		
		// draw bgImage to bgGraphics.
		// calculate the center of the camera.
		int xCamera, yCamera, xOffset, yOffset;
		if(tWidth < gWidth){
			xCamera = tWidth / 2;
			xOffset = xCamera;
		}
		else if (player.x < hgw){
			xCamera = hgw;
			xOffset = 0;
		}
		else if (player.x > tWidth - hgw){
			xCamera = tWidth - hgw;
			xOffset = - tWidth + gWidth;
		}
		else{
			xCamera = (int) player.x;
			xOffset = (int) -player.x + hgw;
		}
		yOffset = 0;
		
		if(tHeight < gHeight){
			yCamera = tHeight / 2;
			yOffset = yCamera;
		}
		else if (player.y < hgh){
			yCamera = hgh;
			yOffset = 0;
		}
		else if (player.y > tHeight - hgh){
			yCamera = tHeight - hgh;
			yOffset = -tHeight + gHeight;
		}
		else{
			yCamera = (int) player.y;	
			yOffset = (int) - player.y + hgh;
		}

		int sx1 = xCamera - hgw;
		int sx2 = xCamera + hgw;
		int sy1 = yCamera - hgh;
		int sy2 = yCamera + hgh;
				
		bgGraphics.drawImage(bgImage, 0, 0, gWidth, gHeight, sx1, sy1, sx2, sy2, null);
		
		// ***********************************************************
		// Sprites
		// ***********************************************************
		BufferedImage sImage = graphics.getSpriteImage();
		Graphics2D sGraphics = (Graphics2D) sImage.getGraphics();
		
		// clear image
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR);
		sGraphics.setComposite(composite);
		sGraphics.setColor(Color.BLACK);
		sGraphics.fillRect(0, 0, gWidth, gHeight);
		sGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		
		sGraphics.setColor(PLAYER_COLOR);
		sGraphics.fillRect(xOffset + (int)player.getLeftX(), yOffset + (int) player.getTopY(),
				(int) (player.halfWidth + player.halfWidth), (int) (player.halfHeight + player.halfHeight));
		sGraphics.dispose();
		
		// draw background
	}
	
}
