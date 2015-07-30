package game.map;

import game.graphics.BorderedTileRenderer;
import game.graphics.GameGraphics;
import game.graphics.Images;
import game.item.Item;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.List;

import utility.Maze;
import utility.Position;

/**
 * CavernMaps are simple cave interiors which use cellular automata to create a
 * randomly generated room.
 * 
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class CavernMap extends Map
{
	public static final int	TILE_SIZE	= 16;
	
	/**
	 * Create A new CavernMap.
	 * 
	 * @param name The name.
	 * @param width The width, in tiles.
	 * @param height The height, in tiles.
	 * @param tileSize The
	 */
	public CavernMap(String name, int width, int height) {
		super(name, width, height, TILE_SIZE);
		this.cells = generateCavernMap();
		this.addItems();
	}
	
	/**
	 * Helper method to generate a byte[][] of paths and walls.
	 * 
	 * @return a 2d byte array.
	 */
	private byte[][] generateCavernMap() {
		Maze maze = Maze.generateCellularAutomataRoom(width, height);
		maze.connectDisconnectedComponents();
		for (int i = 0; i < 3; i++)
			maze.cellularAutomataIteration();
		maze.connectDisconnectedComponents();
		return maze.getCells();
	}
	
	/**
	 * Add some items to the map.
	 */
	public void addItems(){
		List<Item> items = Item.readItemsFromTextFile();
		for(Item item : items){
			int amount = random.nextInt(3);
			for(int i = 0; i < amount; i++){
				addItem(item, true);
			}
		}
	}	
	
	/**
	 * Render the background as a cavern.
	 * TODO: add in cave walls to south wall borders, and draw cavern walls to
	 * fg image.
	 */
	@Override
	public BufferedImage createBackgroundImage() {
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		BufferedImage bgImage = gc.createCompatibleImage(widthInPixels,
				heightInPixels, Transparency.TRANSLUCENT);
		
		// create the assets to be used to render the background image.
		BorderedTileRenderer pathRenderer = new BorderedTileRenderer(
				Images.CAVERN_PATH_BORDER, Map.PATH);
		BorderedTileRenderer wallRenderer = new BorderedTileRenderer(
				Images.CAVERN_WALL_BORDER, Map.WALL);
		
		Graphics2D g = (Graphics2D) bgImage.getGraphics();
		
		// draw the tiles.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (getCell(x, y) == Map.WALL) {
					wallRenderer.renderTile(g, cells, x, y);
				}
				else {
					pathRenderer.renderTile(g, cells, x, y);
				}
			}
		}
		
		g.dispose();
		return bgImage;
	}
	
	/**
	 * Render the foreground image.
	 */
	@Override
	public BufferedImage createLightMaskImage() {
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		BufferedImage lightMaskImage = gc.createCompatibleImage(widthInPixels,
				heightInPixels, Transparency.TRANSLUCENT);
		Graphics2D lmGraphics = (Graphics2D) lightMaskImage.getGraphics();
		int w = lightMaskImage.getWidth();
		int h = lightMaskImage.getHeight();
		int max = 40;
		int sw = Images.SHADOW_512.getWidth();
		for (int i = 0; i < max; i++) {
			lmGraphics.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER,
					Math.min(0.15f, random.nextFloat())));
			int x = random.nextInt(Math.abs(w - sw));
			int y = random.nextInt(Math.abs(h - sw));
			lmGraphics.drawImage(Images.SHADOW_512, x, y, null);
		}
		sw = Images.SHADOW_288.getWidth();
		for (int i = 0; i < max; i++) {
			lmGraphics.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER,
					Math.min(0.15f, random.nextFloat())));
			int x = random.nextInt(Math.abs(w - sw));
			int y = random.nextInt(Math.abs(h - sw));
			lmGraphics.drawImage(Images.SHADOW_288, x, y, null);
		}
		
		sw = Images.SHADOW_PLAYER_MASK.getWidth();
		for (int i = 0; i < max; i++) {
			lmGraphics.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER,
					Math.min(0.15f, random.nextFloat())));
			int x = random.nextInt(Math.abs(w - sw));
			int y = random.nextInt(Math.abs(h - sw));
			lmGraphics.drawImage(Images.SHADOW_PLAYER_MASK, x, y, null);
		}
		return lightMaskImage;
	}
	
	/**
	 * Create a
	 * 
	 * @return
	 */
	@Override
	public BufferedImage createForegroundImage() {
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		BufferedImage fgImage = gc.createCompatibleImage(widthInPixels,
				heightInPixels, Transparency.TRANSLUCENT);
		Graphics2D fgGraphics = (Graphics2D) fgImage.getGraphics();
		
		fgGraphics.dispose();
		return fgImage;
		
	}
	
	// ***********************************************************************
	// Old Stuff, save for later
	// ***********************************************************************
	
	// protected List<TileOLD>[][] backgroundTiles;
	// protected List<TileOLD>[][] foregroundTiles;
	// @SuppressWarnings("unchecked")
	// private void initializeTiles() {
	// this.backgroundTiles = (List<TileOLD>[][]) new List[width][height];
	// this.foregroundTiles = (List<TileOLD>[][]) new List[width][height];
	
	// initialize the values.
	// for (int x = 0; x < width; x++) {
	// for (int y = 0; y < height; y++) {
	// backgroundTiles[x][y] = new ArrayList<TileOLD>();
	// foregroundTiles[x][y] = new ArrayList<TileOLD>();
	// }
	// }
	// }
	
}
