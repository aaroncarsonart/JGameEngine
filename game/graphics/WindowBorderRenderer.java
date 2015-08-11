package game.graphics;

import java.awt.Graphics2D;

/**
 * Renders WindowBorders out to a given graphics context.
 * 
 * @author Aaron Carson
 * @version Jul 27, 2015
 */
public class WindowBorderRenderer
{
	private SpriteSheet	spriteSheet;
	private int top;
	private int bottom;
	private int left;
	private int right;
	private int topLeft;
	private int topRight;
	private int bottomLeft;
	private int bottomRight;
	/**
	 * Create a new WindowBorder.
	 * 
	 * @param quad The quad defining what size to render.
	 */
	public WindowBorderRenderer() {
		this.spriteSheet = new SpriteSheet(Images.MENU_BORDER_2, 8);
		top = 1;
		bottom = 1 + spriteSheet.getWidth() * 2;
		left = 0 + spriteSheet.getWidth() * 1;
		right = 2 + spriteSheet.getWidth() * 1;
		
		topLeft = 0;
		topRight = 2;
		bottomLeft = 0 + spriteSheet.getWidth() * 2;
		bottomRight = 2 + spriteSheet.getWidth() * 2;

	}
	
	
	/**
	 * Render a WindowBorder out to the given graphics context.
	 * @param g The Graphics2D context to render to.
	 * @param x The top left x coordinate.
	 * @param y The top left y coordinate.
	 * @param width The width of the border to render.
	 * @param height The height of the border to render.
	 */
	public void render(Graphics2D g, int x, int y, int width, int height){		
		// draw the edges.
		int tileWidth = spriteSheet.getTileWidth();
		int tileHeight = spriteSheet.getTileWidth();

		int tx, ty;
		int xMax = x + width - tileWidth;
		int yMax = y + height - tileHeight;
		
		// ********************************
		// draw along the top and bottom.	
		// ********************************
		for (tx = x + tileWidth; tx < x + width - (tileWidth * 2); tx += tileWidth) {
			ty = y;
			spriteSheet.render(g, top, tx, ty);
			ty = yMax;
			spriteSheet.render(g, bottom, tx, ty);
		}
		// draw last tile offset, to prevent overlapping
		spriteSheet.render(g, top, x + width - (tileWidth * 2), y);
		spriteSheet.render(g, bottom, x + width - (tileWidth * 2), yMax);
		

		// ********************************
		// draw along the left and right.
		// ********************************
		for (ty = y + tileWidth; ty < y + height - (tileHeight * 2); ty += tileHeight) {
			tx = x;
			spriteSheet.render(g, left, tx, ty);
			tx = xMax;
			spriteSheet.render(g, right, tx, ty);
		}
		
		// draw last tile offset, to prevent overlapping.
		spriteSheet.render(g, left, x, y + height - (tileHeight * 2));
		spriteSheet.render(g, right, xMax, y + height - (tileHeight * 2));
		
		
		// ********************************
		// draw the four corners.
		// ********************************
		spriteSheet.render(g, topLeft, x, y);
		spriteSheet.render(g, topRight, xMax, y);
		spriteSheet.render(g, bottomLeft, x, yMax);
		spriteSheet.render(g, bottomRight, xMax, yMax);
	}
	
	public int getTileWidth(){
		return spriteSheet.getTileWidth();
	}

	public int getTileHeight(){
		return spriteSheet.getTileHeight();
	}
}
