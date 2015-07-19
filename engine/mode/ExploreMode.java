package engine.mode;

import engine.Command;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.WalkingSprite;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Stack;

import party.PlayerCharacter;
import utility.Direction;
import utility.Entity;
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
	public static final double	WALK_SPEED		= 1.0;
	public static final double	RUN_SPEED		= 5.0;
	
	private static final int HUD_WIDTH = 64;
	private static final int MAP_WIDTH = 500;
	private static final int MAP_HEIGHT = 300;
	
	private static final String[] HELP_MENU = {
		"         h - help menu",
		"         m - map",
		"         t - time",
		"         r - rest",
		"  enter, x - action, confirm",
		"    esc, z - cancel, run",
		"" + Direction.LEFT  + Direction.UP + Direction.DOWN + Direction.RIGHT + ", awsd - navigation",
		"  ctrl + f - fullscreen",
		"  ctrl + q - quit game"
};
	
	
	
	public static int			PATH_TILE		= SpriteSheet.CAVE_PATH;
	public static int			WALL_TILE		= SpriteSheet.CAVE_WALL;
	
	Entity						player;
	PlayerCharacter				hero;
	
	int							width;
	int							height;
	int							tWidth;
	int							tHeight;
	
	int							tileSize;
	
	Tile[][]					tiles;
	Random						random;
	
	BufferedImage				bgImage;
	BufferedImage				fgImage;
	
	BufferedImage				hud;
	
	Maze						maze;
	
	/**
	 * Create a new ExploreMode.
	 */
	public ExploreMode() {
		super("Explore Mode");
		tileSize = 16;
		
		maze = Maze.generateRandomShapedRoom(MAP_WIDTH, MAP_HEIGHT, 0.70, false);
		// maze = Maze.generateRandomWalledMaze(200,200);
		// maze.setDifficulty(Difficulty.NORMAL);
		width = maze.getWidth();
		height = maze.getHeight();
		
		tWidth = width * tileSize;
		tHeight = height * tileSize;
		
		random = new Random();
		// initializeTiles();
		initializeTilesForMaze();
		
		int px = 0, py = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (maze.getCell(x, y) == Maze.PATH) {
					px = x;
					py = y;
					break;
				}
			}
		}
		
		Sprite playerSprite = new WalkingSprite(WalkingSprite.MALE_WARRIOR);
		int pWidth = 8;
		int pHeight = 8;
		int xOffset = (tileSize - pWidth) / 2;
		int yOffset = (tileSize - pHeight) / 2;
		int frameRate = 8;
		player = new Entity(px * tileSize + pWidth, py * tileSize + pHeight, pWidth, pHeight, playerSprite, frameRate,
				xOffset, yOffset);
		player.setRunSpeed(6.0);	
		hero = new PlayerCharacter("Michael");
		
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		// initialize game graphics layers
		bgImage = gc.createCompatibleImage(tWidth, tHeight,
				Transparency.TRANSLUCENT);
		fgImage = gc.createCompatibleImage(tWidth, tHeight,
				Transparency.TRANSLUCENT);
				
		Graphics2D bgGraphics = graphics.getBackgroundGraphics();
		bgGraphics.setColor(Color.BLACK);
		drawBackground();
		createHudMap();
		bgGraphics.drawImage(bgImage, 0, 0, null);
		// drawForeground();
	}
	
	private void createHudMap(){
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		double hWidth = HUD_WIDTH;
		double hHeight = (hWidth * height / (double) width);
		hud = gc.createCompatibleImage((int)hWidth, (int)hHeight, Transparency.OPAQUE);
		double xChunk =  width  / hWidth;
		double yChunk =  height / hHeight;
		for(int hx = 0; hx < (int)hWidth; hx++){
			for (int hy = 0; hy < (int)hHeight; hy++){
				//find the average of reach chunk of the map, and draw result.
				int p = 0, w = 0;
				for(int x = (int)(hx * xChunk); x < hx * xChunk + xChunk && x < width; x++){
					for(int y = (int)(hy * yChunk); y < hy * yChunk + yChunk && y < height; y++){
						//System.out.println(maze.getCell(x,y));
						if(maze.getCell(x,y) == Maze.PATH) p++;
						else w ++;
					}			
				}
				// more paths
				//if (p > w) hud.setRGB(hWidth - 1 - hx, hHeight - 1 - hy, 0x88ffffff);
				if (p > 0){
					//System.out.println("p");
					hud.setRGB(hx, hy, Color.DARK_GRAY.getRGB());
				}
				// more walls
				//else hud.setRGB(hWidth - 1 - hx, hHeight - 1 - hy, 0x88000000);
				else{
					//System.out.println("w");
					hud.setRGB(hx, hy, Color.BLACK.getRGB());
				}
			}
		}
	}
	
	private void initializeTilesForMaze() {
		tiles = new Tile[width][height];
		int halfTile = tileSize / 2;
		// set them all randomly.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Quad nextQuad = new Quad(x * tileSize + halfTile, y * tileSize
						+ halfTile, halfTile, halfTile);
				byte type = maze.getCell(x, y) == Maze.WALL ? WALL : PATH;
				tiles[x][y] = new Tile(nextQuad, type);
			}
		}
		/*
		// set a top corner to be guaranteed paths.
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tiles[x][y].type = PATH;
			}
		}
		*/
	}
	
	private void initializeTiles() {
		tiles = new Tile[width][height];
		int halfTile = tileSize / 2;
		// set them all randomly.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Quad nextQuad = new Quad(x * tileSize + halfTile, y * tileSize
						+ halfTile, halfTile, halfTile);
				if (random.nextInt(4) == 0) tiles[x][y] = new Tile(nextQuad,
						WALL);
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
		// Color c;
		int i;
		
		// draw the tiles.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y].type == WALL) {
					// c = WALL_COLOR;
					i = WALL_TILE;
				}
				else {
					// c = PATH_COLOR;
					i = PATH_TILE;
				}
				
				SpriteSheet.WORLD.render(bgGraphics, i, x * tileSize, y
						* tileSize);
				// bgGraphics.setColor(c);
				// bgGraphics.fillRect(x * tileSize, y * tileSize,
				// tileSize,tileSize);
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
				else {
					fgPixels[x + y * gWidth] = 0;
				}
			}
		}
	}
	
	@Override
	public void update() {
		// updatePlayer();
		updatePlayerNew();
		updateGraphics();
	}
	
	/**
	 * Check if the given position is passable.
	 * 
	 * @param p The position to check.
	 * @return True if the position is within bounds and the associated tile is
	 *         a wall, otherwise returns false.
	 */
	public boolean isPassable(int x, int y) {
		return 0 < x && x < width && 0 < y && y < height
				&& tiles[x][y].type == PATH;
	}
	
	/**
	 * Update the player's position.
	 */
	private void updatePlayer() {
		
		// System.out.printf("px: %f py: %f\n", px, py);
		
		// calculate the new dx and dy.
		double dx = 0;
		double dy = 0;
		double speed;
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
		else if (player.x >= tWidth - player.halfWidth)
			player.x = tWidth - player.halfWidth;
		if (player.y < player.halfHeight) player.y = player.halfHeight;
		else if (player.y >= tHeight - player.halfHeight)
			player.y = tHeight - player.halfHeight;
		
		// collisions!
		adjustForCollisions(player);
	}
	
	private boolean displayMap = false;
	private boolean displayHelp = true;
	
	/**
	 * Update the player's position.
	 */
	private void updatePlayerNew() {
		// toggle map display on input
		if (!Command.MAP.isConsumed()){
			displayMap = !displayMap;
			Command.MAP.consume();
		}
		
		// toggle help display on input
		if (!Command.HELP.isConsumed()){
			displayHelp = !displayHelp;
			Command.HELP.consume();
		}
		
		if (Command.moveSelected()) {
			Stack<Direction> directions = new Stack<Direction>();
			
			if (Command.CANCEL.isPressed()) player.setRunning(true);
			else player.setRunning(false);
			
			if (Command.UP.isPressed()) directions.push(Direction.UP);
			if (Command.DOWN.isPressed()) directions.push(Direction.DOWN);
			if (Command.LEFT.isPressed()) directions.push(Direction.LEFT);
			if (Command.RIGHT.isPressed()) directions.push(Direction.RIGHT);
			if (directions.isEmpty()) directions.push(Direction.DOWN);
			
			player.setDirectionStack(directions);
			player.update();
			hero.tick();
			//System.out.printf("px: %f py: %f\n", player.x / player.getWidth(), player.y / player.getHeight());
		}
		else {
			//System.out.println("stop");
			player.stop();
		}
		
		// ****************************
		// adjust for any collisions.
		// ****************************
		// keep within bounds of map.
		if (player.x < player.halfWidth) {
			player.x = player.halfWidth;
			//System.out.println("out of bounds - LEFT");
		}
		else if (player.x >= tWidth - player.halfWidth) {
			player.x = tWidth - player.halfWidth;
			//System.out.println("out of bounds - RIGHT");
		}
		if (player.y < player.halfHeight) {
			player.y = player.halfHeight;
			//System.out.println("out of bounds - UP");
		}
		else if (player.y >= tHeight - player.halfHeight) {
			player.y = tHeight - player.halfHeight;
			//System.out.println("out of bounds - DOWN");
		}
		
		// collisions!
		adjustForCollisions(player);
		
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
		Tile t;
		t = tiles[ix][iy];
		if (t.type == WALL) quad.fixIfCollides(t.quad);
		boolean checkX = ix + 1 < width;
		boolean checkY = iy + 1 < height;
		
		if (checkX) {
			t = tiles[ix + 1][iy];
			if (t.type == WALL) quad.fixIfCollides(t.quad);
		}
		if (checkY) {
			t = tiles[ix][iy + 1];
			if (t.type == WALL) quad.fixIfCollides(t.quad);
		}
		if (checkX && checkY) {
			t = tiles[ix + 1][iy + 1];
			if (t.type == WALL) quad.fixIfCollides(t.quad);
		}
		
		// check any sprites.
		
		// Position result = new Position(left, top);
		// LinkedList<Quad> quads = new LinkedList<Quad>();
		
	}
	
	@Override
	public void updateGraphics() {
		// updateGraphicsStatic();
		updateGraphicsDynamic();
	}
	
	/**
	 * Keep the camera stationary as the player moves.
	 */
	public void updateGraphicsStatic() {
		int gWidth = graphics.getWidth();
		int gHeight = graphics.getHeight();
		
		// ***********************************************************
		// Sprites
		// ***********************************************************
		BufferedImage sImage = graphics.getSpriteImage();
		Graphics2D sGraphics = (Graphics2D) sImage.getGraphics();
		// int[] sPixels = graphics.getSpritePixels();
		
		AlphaComposite composite = AlphaComposite
				.getInstance(AlphaComposite.CLEAR);
		sGraphics.setComposite(composite);
		sGraphics.setColor(Color.BLACK);
		sGraphics.fillRect(0, 0, gWidth, gHeight);
		sGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER));
		
		sGraphics.setColor(PLAYER_COLOR);
		sGraphics.fillRect((int) player.getLeftX(), (int) player.getTopY(),
				(int) player.getWidth(), (int) player.getHeight());
		sGraphics.dispose();
		
	}
	
	// move the camera with the player.
	public void updateGraphicsDynamic() {
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
		if (tWidth < gWidth) {
			xCamera = tWidth / 2;
			xOffset = xCamera;
		}
		else if (player.x < hgw) {
			xCamera = hgw;
			xOffset = 0;
		}
		else if (player.x > tWidth - hgw) {
			xCamera = tWidth - hgw;
			xOffset = -tWidth + gWidth;
		}
		else {
			xCamera = (int) player.x;
			xOffset = (int) -player.x + hgw;
		}
		yOffset = 0;
		
		if (tHeight < gHeight) {
			yCamera = tHeight / 2;
			yOffset = yCamera;
		}
		else if (player.y < hgh) {
			yCamera = hgh;
			yOffset = 0;
		}
		else if (player.y > tHeight - hgh) {
			yCamera = tHeight - hgh;
			yOffset = -tHeight + gHeight;
		}
		else {
			yCamera = (int) player.y;
			yOffset = (int) -player.y + hgh;
		}
		
		int sx1 = xCamera - hgw;
		int sx2 = xCamera + hgw;
		int sy1 = yCamera - hgh;
		int sy2 = yCamera + hgh;
		
		bgGraphics.drawImage(bgImage, 0, 0, gWidth, gHeight, sx1, sy1, sx2,
				sy2, null);
		
		// ***********************************************************
		// Sprites
		// ***********************************************************
		BufferedImage sImage = graphics.getSpriteImage();
		Graphics2D sGraphics = (Graphics2D) sImage.getGraphics();
		
		// clear image
		AlphaComposite composite = AlphaComposite
				.getInstance(AlphaComposite.CLEAR);
		sGraphics.setComposite(composite);
		sGraphics.setColor(Color.BLACK);
		sGraphics.fillRect(0, 0, gWidth, gHeight);
		sGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER));
		
		// draw player
		sGraphics.setColor(PLAYER_COLOR);
		int px = xOffset + (int) player.getLeftX();
		int py = yOffset + (int) player.getTopY();
		int pw = (int) (player.halfWidth + player.halfWidth);
		int ph = (int) (player.halfHeight + player.halfHeight);
		
		// sGraphics.fillRect(px, py,pw, ph);
		player.render(sGraphics, px, py, px + pw, py + ph);
		
		// *******************************************
		// Draw the HUD map
		// *******************************************
		if(displayMap){
			drawHudMap(sGraphics);
			/*
		sGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		int hPad = 4;
		sGraphics.drawImage(hud, hPad, hPad, null);
		sGraphics.setColor(Color.YELLOW);
		int hpx = (int) (player.x / player.getWidth() / width * hud.getWidth());
		int hpy = (int) (player.y / player.getHeight() / height * hud.getHeight());
		//int hpx = (int) (width  / (double) (hud.getWidth())  * (player.x / width));
		//int hpy = (int) (height / (double) (hud.getHeight()) * (player.y / height));
		//System.out.printf("hpx: %d hpy: %d\n", hpx, hpy);
		sGraphics.fillRect(hPad + hpx, hPad + hpy, 1,1);
		*/
		}
		
		// *******************************************
		// Draw the Help Menu
		// *******************************************
		if (displayHelp){
			
			drawHelpMenu(sGraphics);
			/*
			sGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			int fontSize = 16;
			sGraphics.setFont(new Font("Courier", Font.PLAIN, fontSize)); 
			
			sGraphics.setColor(Color.BLACK);
			int windowWidth = 26 * 12;
			int windowHeight = fontSize * (HELP_MENU.length	 + 2);
			int windowY = (gHeight - windowHeight) / 2;
			int windowX = (gWidth - windowWidth) / 2;
			sGraphics.fillRect(windowX,  windowY, windowWidth, windowHeight);
		
			sGraphics.setColor(Color.WHITE);
			int yStep = 1;
			int xStep = windowX + 14;
			windowY += fontSize - 4;
			
			// write out all help menu messages.
			for ( int i = 0; i < HELP_MENU.length; i++){
				sGraphics.drawString(HELP_MENU[i], xStep, windowY + fontSize * (i + 1));	
			}
			*/
			
		}
		sGraphics.dispose();
				
		// ***********************************************************
		// Foreground
		// ***********************************************************
	}
	
	/**
	 * Draw a HUD.
	 * @param g
	 */
	public void drawHudMap(Graphics2D g){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		int hPad = 4;
		g.drawImage(hud, hPad, hPad, null);
		g.setColor(Color.YELLOW);
		int hpx = (int) (player.x / player.getWidth() / width * hud.getWidth());
		int hpy = (int) (player.y / player.getHeight() / height * hud.getHeight());
		g.fillRect(hPad + hpx, hPad + hpy, 1,1);
	}
	
	/**
	 * Draw a help menu.
	 * @param g The graphics to draw to.
	 */
	public void drawHelpMenu(Graphics2D g){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		int fontSize = 15;
		g.setFont(new Font("Courier", Font.PLAIN, fontSize)); 
		
		g.setColor(Color.BLACK);
		int windowWidth = 26 * 12;
		int windowHeight = fontSize * (HELP_MENU.length	 + 2);
		int windowY = (graphics.getHeight() - windowHeight) / 2;
		int windowX = (graphics.getWidth() - windowWidth) / 2;
		g.fillRect(windowX,  windowY, windowWidth, windowHeight);
	
		g.setColor(Color.WHITE);
		int yStep = 1;
		int xStep = windowX + 14;
		windowY += fontSize - 4;
		
		// write out all help menu messages.
		for ( int i = 0; i < HELP_MENU.length; i++){
			g.drawString(HELP_MENU[i], xStep, windowY + fontSize * (i + 1));	
		}
	}
	
}
