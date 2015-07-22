package engine.mode;

import engine.Command;
import engine.Game;
import graphics.BasicBorderedTile;
import graphics.Images;
import graphics.Message;
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
import java.util.Iterator;
import java.util.LinkedList;
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
	private static final byte		PATH				= 0;
	private static final byte		WALL				= 1;
	
	private static final Color		WALL_COLOR			= Color.DARK_GRAY;
	private static final Color		PATH_COLOR			= Color.WHITE;
	private static final Color		PLAYER_COLOR		= Color.BLUE;
	private static final String		PLAYER_SPRITE		= WalkingSprite.MALE_WARRIOR;
	// private static final String PLAYER_SPRITE = WalkingSprite.FEMALE_WARRIOR;
	
	public static final double		UPDATE_RATIO		= 60.0 / Game.FRAME_RATE;
	public static final double		WALK_SPEED			= 1.0 * UPDATE_RATIO;
	public static final double		RUN_SPEED			= 3.0 * UPDATE_RATIO;
	
	private static final int		HUD_WIDTH			= 64;
	private static final int		WIDTH				= 100;
	private static final int		HEIGHT				= 70;
	private static final int		CA_ITERATIONS		= 3;
	
	public static final float		SOFT_LIGHT_ALPHA	= 0.85f;
	public static final boolean		DISPLAY_SOFT_LIGHT	= false;
	
	public static final String		FONT_NAME			= "Courier";
	public static final int			FONT_STYLE			= Font.PLAIN;
	public static final int			FONT_SIZE			= 12;
	public static final Font		FONT				= new Font(FONT_NAME,
																FONT_STYLE,
																FONT_SIZE);
	
	private static final String[]	HELP_MENU			= {
			"         h - help menu",
			"         m - map",
			"         t - time",
			"         r - rest",
			"         v - vitals",
			"  enter, x - action, confirm",
			"    esc, z - cancel, run",
			"" + Direction.LEFT + Direction.UP + Direction.DOWN
					+ Direction.RIGHT + ", awsd - navigation",
			"  ctrl + f - fullscreen", "  ctrl + q - quit game" };
	
	public static int				PATH_TILE			= SpriteSheet.CAVE_PATH;
	public static int				WALL_TILE			= SpriteSheet.CAVE_WALL;
	public static int				PATH_TILE_2			= SpriteSheet.CAVE_PATH_ALT;
	public static int				WALL_TILE_2			= SpriteSheet.CAVE_WALL_ALT;
	
	Entity							player;
	PlayerCharacter					hero;
	LinkedList<Message>				messages;
	
	int								width;
	int								height;
	int								tWidth;
	int								tHeight;
	int								tileSize;
	
	Maze							maze;
	Tile[][]						tiles;
	Random							random;
	
	BufferedImage					bgImage;
	BufferedImage					fgImage;
	BufferedImage					hud;
	
	private boolean					displayMap			= true;
	private boolean					displayHelp			= true;
	private boolean					displayTimer		= true;
	private boolean					displayVitals		= true;
	private boolean					displaySoftLight	= DISPLAY_SOFT_LIGHT;
	
	private BasicBorderedTile		pathRenderer = new BasicBorderedTile(Images.CAVERN_PATH_BORDER, PATH);
	private BasicBorderedTile		wallRenderer = new BasicBorderedTile(Images.CAVERN_WALL_BORDER, WALL);
	
	/**
	 * Create a new ExploreMode.
	 */
	public ExploreMode() {
		super("Explore Mode");
		tileSize = 16;
		
		//maze = Maze.generateRandomShapedRoom(WIDTH, HEIGHT, 0.70, false);
		// maze = Maze.generateRandomWalledMaze(200,200);
		// maze.setDifficulty(Difficulty.NORMAL);
		maze = Maze.generateCellularAutomataRoom(WIDTH, HEIGHT);
		maze.connectDisconnectedComponents();
		for (int i = 0; i < CA_ITERATIONS; i++) maze.cellularAutomataIteration();
		maze.connectDisconnectedComponents();
		
		
		System.out.println(maze);
		
		width = maze.getWidth();
		height = maze.getHeight();
		
		tWidth = width * tileSize;
		tHeight = height * tileSize;
		
		messages = new LinkedList<Message>();
		random = new Random();
		initializeTilesFromMaze();
		
		// get initial player position.
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
		
		// create the sprite the player will use.
		Sprite playerSprite = new WalkingSprite(PLAYER_SPRITE);
		int pWidth = 8;
		int pHeight = 8;
		int xOffset = (tileSize - pWidth) / 2;
		int yOffset = (tileSize - pHeight) / 2;
		int frameRate = 8;
		
		// create the entity that manages the player movement.
		player = new Entity(px * tileSize + pWidth, py * tileSize + pHeight,
				pWidth, pHeight, playerSprite, frameRate, xOffset, yOffset);
		player.setWalkSpeed(WALK_SPEED);
		player.setRunSpeed(RUN_SPEED);
		
		// create the Hero that contains all game stats for the hero character.
		hero = new PlayerCharacter("Michael");
		
		// initialize game graphics layers
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		bgImage = gc.createCompatibleImage(tWidth, tHeight,
				Transparency.TRANSLUCENT);
		fgImage = gc.createCompatibleImage(tWidth, tHeight,
				Transparency.TRANSLUCENT);
		
		Graphics2D bgGraphics = graphics.getBackgroundGraphics();
		bgGraphics.setColor(Color.BLACK);
		createBackgroundImage();
		createForegroundImage();
		createHudMap();
		bgGraphics.drawImage(bgImage, 0, 0, null);
		// drawForeground();
	}
	
	/**
	 * Create the bitmap for the HUD map.
	 */
	private void createHudMap() {
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		double hWidth = HUD_WIDTH;
		double hHeight = (hWidth * height / (double) width);
		hud = gc.createCompatibleImage((int) hWidth, (int) hHeight,
				Transparency.OPAQUE);
		double xChunk = width / hWidth;
		double yChunk = height / hHeight;
		for (int hx = 0; hx < (int) hWidth; hx++) {
			for (int hy = 0; hy < (int) hHeight; hy++) {
				// find the average of reach chunk of the map, and draw result.
				int p = 0, w = 0;
				for (int x = (int) (hx * xChunk); x < hx * xChunk + xChunk
						&& x < width; x++) {
					for (int y = (int) (hy * yChunk); y < hy * yChunk + yChunk
							&& y < height; y++) {
						if (maze.getCell(x, y) == Maze.PATH) p++;
						else w++;
					}
				}
				double ratio = p / (double)(p + w);
				System.out.println(255 * ratio);
				int v = (int)(255 * ratio);
				Color c = new Color(v,v,v);
				hud.setRGB(hx, hy, c.getRGB());
				/*
				if (ratio > 0.75) {
					hud.setRGB(hx, hy, new Color()Color.WHITE.getRGB());
				}
				else if (ratio > 0.50) {
					hud.setRGB(hx, hy, Color.LIGHT_GRAY.getRGB());
				}
				else if (ratio > 0.25) {
					hud.setRGB(hx, hy, Color.DARK_GRAY.getRGB());
				}
				else {
					hud.setRGB(hx, hy, Color.BLACK.getRGB());
				}
				*/
				
			}
		}
		
		// Darken HUD
		
		Graphics2D g = hud.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, hud.getWidth(), hud.getWidth());
		
	}
	
	/**
	 * Setup the Tile state from the Maze data.
	 */
	private void initializeTilesFromMaze() {
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
	}
	
	/**
	 * Create the background image. Should only be called once.
	 */
	public void createBackgroundImage() {
		Graphics2D bgGraphics = (Graphics2D) bgImage.getGraphics();
		// Color c;
		int i;

		//pathRenderer.render(bgGraphics, maze.getCells());
		//wallRenderer.render(bgGraphics, maze.getCells());
		
		// draw the tiles.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y].type == WALL){
					//SpriteSheet.WORLD.render(bgGraphics, WALL_TILE, x * tileSize, y * tileSize);
					//wallRenderer.renderTile8x8(bgGraphics, maze.getCells(), x, y, width, height);
					wallRenderer.renderTile(bgGraphics, maze.getCells(), x, y, width, height);
				}
				else{
					//pathRenderer.renderTile(bgGraphics, maze.getCells(), x, y, width, height);
					//i = random.nextBoolean() ? PATH_TILE_2 : PATH_TILE;
					//SpriteSheet.WORLD.render(bgGraphics, i, x * tileSize, y * tileSize);
					pathRenderer.renderTile(bgGraphics, maze.getCells(), x, y, width, height);
				}
				/*
				//if (tiles[x][y].type == WALL) {
					// c = WALL_COLOR;
					//i = random.nextInt(3) == 0 ? WALL_TILE_2 : WALL_TILE;
					//i = random.nextBoolean() ? WALL_TILE_2 : WALL_TILE;
					//SpriteSheet.WORLD.render(bgGraphics, i, x * tileSize, y * tileSize);
				//}
				//else {
				if(tiles[x][y].type == PATH){
					//c = PATH_COLOR;
					//i = random.nextInt(3) == 0 ? PATH_TILE_2 : PATH_TILE;
					i = random.nextBoolean() ? PATH_TILE_2 : PATH_TILE;
					SpriteSheet.WORLD.render(bgGraphics, i, x * tileSize, y * tileSize);
				}
				
				
				// bgGraphics.setColor(c);
				// bgGraphics.fillRect(x * tileSize, y * tileSize,
				// tileSize,tileSize);				 
				 */
			}
		}
		

		bgGraphics.dispose();
		
	}
	
	/**
	 * Render the foreground image.
	 */
	public void createForegroundImage() {
		Graphics2D fgGraphics = (Graphics2D) fgImage.getGraphics();
		int w = fgImage.getWidth();
		int h = fgImage.getHeight();
		int max = 40;
		int sw = Images.SHADOW_512.getWidth();
		for (int i = 0; i < max; i++) {
			fgGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			Math.min(0.15f, random.nextFloat())));
			int x = random.nextInt(Math.abs(w - sw));
			int y = random.nextInt(Math.abs(h - sw));
			fgGraphics.drawImage(Images.SHADOW_512, x, y, null);
		}
		sw = Images.SHADOW_288.getWidth();
		for (int i = 0; i < max; i++) {
			fgGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			Math.min(0.15f, random.nextFloat())));
			int x = random.nextInt(Math.abs(w - sw));
			int y = random.nextInt(Math.abs(h - sw));
			fgGraphics.drawImage(Images.SHADOW_288, x, y, null);
		}
		
		sw = Images.SHADOW_PLAYER_MASK.getWidth();
		for (int i = 0; i < max; i++) {
			fgGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			Math.min(0.15f, random.nextFloat())));
			int x = random.nextInt(Math.abs(w - sw));
			int y = random.nextInt(Math.abs(h - sw));
			fgGraphics.drawImage(Images.SHADOW_PLAYER_MASK, x, y, null);
		}
		
	}
	
	@Override
	public void update() {
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
	
	/**
	 * Update the player's position.
	 */
	private void updatePlayerNew() {
		// toggle map display on input
		if (!Command.MAP.isConsumed()) {
			displayMap = !displayMap;
			Command.MAP.consume();
		}
		
		// toggle help display on input
		if (!Command.HELP.isConsumed()) {
			displayHelp = !displayHelp;
			Command.HELP.consume();
		}
		
		// toggle display of vitals
		if (!Command.VITALS.isConsumed()) {
			displayVitals = !displayVitals;
			Command.VITALS.consume();
		}
		
		// toggle display of timer
		if (!Command.TIME.isConsumed()) {
			displayTimer = !displayTimer;
			Command.TIME.consume();
		}
		
		boolean moving = Command.moveSelected();
		if (moving && !Command.REST.isPressed()) {
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
			// System.out.printf("px: %f py: %f\n", player.x /
			// player.getWidth(), player.y / player.getHeight());
		}
		else {
			// System.out.println("stop");
			player.stop();
		}
		
		// update the player's clock if moving, or if resting.
		if(moving || Command.REST.isPressed()){
			hero.tick();
		}
		
		
		// ****************************
		// adjust for any collisions.
		// ****************************
		// keep within bounds of map.
		if (player.x < player.halfWidth) {
			player.x = player.halfWidth;
			// System.out.println("out of bounds - LEFT");
		}
		else if (player.x >= tWidth - player.halfWidth) {
			player.x = tWidth - player.halfWidth;
			// System.out.println("out of bounds - RIGHT");
		}
		if (player.y < player.halfHeight) {
			player.y = player.halfHeight;
			// System.out.println("out of bounds - UP");
		}
		else if (player.y >= tHeight - player.halfHeight) {
			player.y = tHeight - player.halfHeight;
			// System.out.println("out of bounds - DOWN");
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
		bgGraphics.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.25f));
		bgGraphics.drawImage(Images.LARGE_SHADOW, 0, 0, null);
		
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
		// sGraphics.setColor(PLAYER_COLOR);
		int px = xOffset + (int) player.getLeftX();
		int py = yOffset + (int) player.getTopY();
		int pw = (int) (player.halfWidth + player.halfWidth);
		int ph = (int) (player.halfHeight + player.halfHeight);
		
		// sGraphics.fillRect(px, py,pw, ph);
		player.render(sGraphics, px, py, px + pw, py + ph);
		
		sGraphics.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.75f));
		Iterator<Message> it = messages.iterator();
		while (it.hasNext()) {
			Message m = it.next();
			m.render(sGraphics, xOffset, yOffset);
			if (m.finished()) it.remove();
		}
		
		sGraphics.dispose();
		
		// ******************************************
		// Draw the foreground.
		// ******************************************
		
		Graphics2D fgGraphics = graphics.getForegroundGraphics();
		fgGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.CLEAR));
		fgGraphics.setColor(Color.BLACK);
		fgGraphics.fillRect(0, 0, gWidth, gHeight);
		
		// draw existing light mask.
		fgGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER));
		fgGraphics.drawImage(fgImage, 0, 0, gWidth, gHeight, sx1, sy1, sx2,
				sy2, null);
		int off;
		
		// draw lantern light mask around player.
		off = Images.SHADOW_PLAYER_MASK.getWidth() / 2;
		fgGraphics.drawImage(Images.SHADOW_PLAYER_MASK, xOffset + (int) player.x - off,
				yOffset + (int) player.y - off, null);
		
		// darken shadow areas by 75% if displaySoftLight, otherwise darken
		// completely.
		if (displaySoftLight) fgGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OUT, SOFT_LIGHT_ALPHA));
		else fgGraphics.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OUT));
		
		fgGraphics.fillRect(0, 0, gWidth, gHeight);
		
		// *******************************************
		// Map
		// *******************************************
		if (displayMap) {
			drawHudMap(fgGraphics);
		}
		
		// *******************************************
		// Timer
		// *******************************************
		if (displayTimer || Command.REST.isPressed()) {
			drawTimer(fgGraphics);
		}
		
		// *******************************************
		// Vitals
		// *******************************************
		if (displayVitals) {
			drawVitals(fgGraphics);
		}
		
		// *******************************************
		// Help
		// *******************************************
		if (displayHelp) {
			drawHelpMenu(fgGraphics);
		}
		
	}
	
	/**
	 * Draw a HUD.
	 * 
	 * @param g
	 */
	public void drawHudMap(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.50f));
		int hPad = FONT_SIZE / 2;
		g.drawImage(hud, hPad, hPad, null);
		g.setColor(Color.YELLOW);
		int hpx = (int) (player.x / player.getWidth() / width * hud.getWidth());
		int hpy = (int) (player.y / player.getHeight() / height * hud
				.getHeight());
		g.fillRect(hPad + hpx, hPad + hpy, 1, 1);
	}
	
	/**
	 * Draw a help menu.
	 * 
	 * @param g The graphics to draw to.
	 */
	public void drawHelpMenu(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.75f));
		// int fontSize = 14;
		g.setFont(FONT);
		
		g.setColor(Color.BLACK);
		int windowWidth = 26 * 12;
		int windowHeight = FONT_SIZE * (HELP_MENU.length + 2);
		int windowY = (graphics.getHeight() - windowHeight) / 2;
		int windowX = (graphics.getWidth() - windowWidth) / 2;
		g.fillRect(windowX, windowY, windowWidth, windowHeight);
		
		g.setColor(Color.WHITE);
		int yStep = 1;
		int xStep = windowX + 14;
		windowY += FONT_SIZE - 4;
		
		// write out all help menu messages.
		for (int i = 0; i < HELP_MENU.length; i++) {
			g.drawString(HELP_MENU[i], xStep, windowY + FONT_SIZE * (i + 1));
		}
	}
	
	/**
	 * Draw the vitals.
	 * 
	 * @param g
	 */
	public void drawVitals(Graphics2D g) {
		// System.out.println("vitals!");
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.50f));
		// int fontSize = 8;
		// g.setFont(new Font("Courier", Font.PLAIN, fontSize));
		g.setFont(FONT);
		int windowWidth = (int) (FONT_SIZE * 0.8 * 14);
		int windowHeight = FONT_SIZE * (4 + 1);
		
		g.setColor(Color.BLACK);
		int windowY = FONT_SIZE / 2;
		int windowX = graphics.getWidth() - windowWidth - FONT_SIZE / 2;
		g.fillRect(windowX, windowY, windowWidth, windowHeight);
		
		g.setColor(Color.WHITE);
		int yStep = 1;
		windowX += tileSize / 2;
		windowY += tileSize / (FONT_SIZE / 2);
		g.drawString(hero.getDamageVitals(), windowX, windowY + FONT_SIZE
				* yStep++);
		g.drawString(hero.getHungerVitals(), windowX, windowY + FONT_SIZE
				* yStep++);
		g.drawString(hero.getThirstVitals(), windowX, windowY + FONT_SIZE
				* yStep++);
		g.drawString(hero.getStressVitals(), windowX, windowY + FONT_SIZE
				* yStep++);
		
	}
	
	/**
	 * Draw the vitals.
	 * 
	 * @param g
	 */
	public void drawTimer(Graphics2D g) {
		// System.out.println("timer!");
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER ,0.50f));
		// int fontSize = 8;
		g.setFont(FONT);
		
		g.setColor(Color.BLACK);
		int w = (int) (FONT_SIZE * 0.8 * 7);
		int h = FONT_SIZE + FONT_SIZE / 2;
		
		int x = graphics.getWidth() - w - FONT_SIZE / 2;
		int y = graphics.getHeight() - h - FONT_SIZE / 2;
		g.fillRect(x, y, w, h);
		g.setColor(Color.WHITE);
		
		x += FONT_SIZE / 2;
		y += FONT_SIZE;
		//g.drawString(String.format("Time: %6s", hero.time / Game.FRAME_RATE), x, y);
		g.drawString(formatIntegerAsTime((int)hero.time), x, y);
	}
	
	// ***********************************************************************
	// TIME CONSTANTS
	// ***********************************************************************
	
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int SECONDS_PER_HOUR = 60 * 60;
	public static final int HOURS_PER_DAY = 24;
	public static final int NOON = 12;

	/** Start the game at a random time. **/
	public static final int START_TIME = new Random().nextInt(SECONDS_PER_HOUR * HOURS_PER_DAY);
	
	public static final String AM = "am";
	public static final String PM = "pm";
	
	/**
	 * Take an integer and represent it as seconds, minutes, hrs, etc.
	 * @param time
	 * @return
	 */
	public static String formatIntegerAsTime(int time){
		time += START_TIME;
		//int seconds = time % SECONDS_PER_MINUTE;
		int minutes = (time / MINUTES_PER_HOUR) % MINUTES_PER_HOUR;
		int hours = (time / SECONDS_PER_HOUR) % HOURS_PER_DAY;
		String period = hours >= NOON ? PM : AM;
		hours %= NOON;
		if (hours == 0) hours = NOON;
		
		//String format = String.format("%2d:%02d:%02d %s", hours, minutes, seconds, period);
		String format = String.format("%2d:%02d %s", hours, minutes, period);
		
		return format;
	}
	
}
