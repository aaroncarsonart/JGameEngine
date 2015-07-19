package graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import utility.Quad;

/**
 * A Sprite is an image that moves about the screen.  It may represent the
 * player, a creature, a terrain feature, or an item.  They have a set
 * of animations associated with them.
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public abstract class Sprite
{		
	protected BufferedImage spriteSheet;
	protected int tileSize;
	protected int sheetWidth;
	protected int sheetHeight;
	
	/**
	 * Create a new sprite.
	 * @param type The type of the sprite.
	 * @param src The image for the sprite graphics.
	 * @param tileSize
	 */
	public Sprite(BufferedImage src, int tileSize, int width, int height){
		// create compatible image and draw the source image to it.
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		this.spriteSheet = gc.createCompatibleImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
		spriteSheet.getGraphics().drawImage(src, 0, 0, null);
		this.tileSize = tileSize;
		this.sheetWidth = width;
		this.sheetHeight = height;
	}
		
	/**
	 * Render the tile witht the given index to the graphics context.
	 * @param g The Graphics2D context to render to.
	 * @param tileIndex the index of the tile to render.
	 * @param x The top left x Coordinate to render the image to.
	 * @param y The top left y Coordinate to render the image to.
	 */
	private void render(Graphics2D g, int tileIndex, int x, int y){
		int xOffset = tileIndex % sheetWidth;
		int yOffset = tileIndex / sheetWidth;
		int sx1 = xOffset * tileSize;
		int sx2 = xOffset * tileSize + tileSize;
		int sy1 = yOffset * tileSize;
		int sy2 = yOffset * tileSize + tileSize;
		g.drawImage(spriteSheet, x, y, x + tileSize, y + tileSize, sx1, sy1, sx2, sy2, null);
		
		/*
		if (yOffset < sheetHeight){
			//g.drawImage(spriteSheet, xOffset, yOffset,null);
			
		}
		else{
			System.out.println("Error, tileIndex out of bounds");
		}
		*/
	}

	/**
	 * @return the spriteSheet
	 */
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	/**
	 * @return the tileSize
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * @return the sheetWidth
	 */
	public int getSheetWidth() {
		return sheetWidth;
	}

	/**
	 * @return the sheetHeight
	 */
	public int getSheetHeight() {
		return sheetHeight;
	}
	
}
