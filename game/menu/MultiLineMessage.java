package game.menu;

import game.graphics.GameGraphics;
import game.graphics.Images;
import game.graphics.SpriteSheet;
import game.graphics.WindowBorderRenderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Handles Popup Messages that appear above a character or item in the game.
 * 
 * TODO look into this
 * http://docs.oracle.com/javase/7/docs/api/java/awt/font/TextLayout.html
 * 
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class MultiLineMessage implements Message
{	
	private String[]			text;
	private Color				color;
	private int					alignment;
	private boolean				drawBorder;
	private SpriteSheet			spriteSheet;
	
	/**
	 * Create a message to be rendered to the screen.
	 * 
	 * @param text The text to render.
	 * @param color the color of the text to render.
	 */
	public MultiLineMessage(String text, Color color, int alignment, boolean border) {
		this.text = text.split("\n");
		this.color = color;
		this.alignment = alignment;
		this.drawBorder = border;
		this.spriteSheet = new SpriteSheet(Images.MENU_BORDER, 8);

	}
	
	/**
	 * Create a new Text that renders in white, centered, with a border.
	 * 
	 * @param text
	 */
	public MultiLineMessage(String text) {
		this(text, DEFAULT_COLOR, CENTER, true);
	}
	
	public boolean rendered = false;
	public BufferedImage windowImage;

	
	/**
	 * Render the given text to the screen.
	 * 
	 * @param g The text to render.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param transparency the alpha value to render at.
	 */
	public void render(Graphics2D g, int x, int y, float transparency) {
		g.setFont(FONT);
		TextLayout[] layouts = new TextLayout[text.length];
		TextLayout tl = new TextLayout(text[0], FONT, g.getFontRenderContext());
		layouts[0] = tl;
		Rectangle2D bounds = tl.getBounds();
		for(int i = 1; i < text.length; i++){
			TextLayout next = new TextLayout(text[i], FONT, g.getFontRenderContext());			
			layouts[i] = next;
			Rectangle2D nBounds = next.getBounds();
			if (nBounds.getWidth() > bounds.getWidth()){
				tl = next;
				bounds = nBounds;
			}
		}
		
		float xOff;
		switch (alignment) {
		case RIGHT:
			xOff = (float)bounds.getWidth();
			break;
		case CENTER:
			xOff = (float)bounds.getWidth() / 2;
			break;
		case LEFT:
		default:
			xOff = 0;
			break;			
		}

				
		int xPadding = 8;
		int yPadding = 8;
		int lineSpacing = 4;
		/*
		bounds.setRect(bounds.getX() + x,// - xOff, 
				bounds.getY() + y,//+ yOff,
				bounds.getWidth(), 
				bounds.getHeight()
				);
		*/
		if(!rendered){
			rendered = true;
			float tHeight = tl.getAscent() + tl.getDescent() + lineSpacing;
			System.out.printf("boundsHeight: %f text.length: %d\n", bounds.getHeight(), text.length);
			windowImage = createBorderImage((int)bounds.getWidth(), (int) tHeight * text.length - lineSpacing, xPadding, yPadding);	
			Graphics2D wg = windowImage.createGraphics();
			
			int xPad = spriteSheet.getTileWidth();
			int yPad = spriteSheet.getTileHeight();
			wg.setColor(Color.BLACK);
			wg.fillRect(xPad, yPad, windowImage.getWidth() - xPad * 2, windowImage.getHeight() - yPad * 2);	
			int cwx = windowImage.getWidth() / 2;
			int cwy = windowImage.getHeight() / 2;
			wg.setColor(color);
			for(int i = 0; i < layouts.length; i++){
				//Rectangle2D nBounds = layouts[i].getBounds();
				layouts[i].draw(wg, cwx - xOff, 8 + yPadding + tl.getAscent() + tHeight * i );		
			}
			wg.dispose();
			}
		//g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g.drawImage(windowImage, x - windowImage.getWidth() / 2, y - windowImage.getHeight() / 2, null);

		//g.setColor(Color.BLACK);
		//g.fill(bounds);
		//g.setColor(Color.WHITE);
		//tl.draw(g, x - xOff, y + yOff);		
	}
	
	/**
	 * Create a BufferedImage bordered by this WindowBorder, that encloses
	 * the given width and height, and has the given padding.
	 * @param w The width to enclose
	 * @param h The height to enclose
	 * @param xPadding
	 * @param yPadding
	 * @return
	 */
	public BufferedImage createBorderImage(int w, int h, int xPadding, int yPadding) {
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		int tileWidth = spriteSheet.getTileWidth();
		int tileHeight = spriteSheet.getTileWidth();
		// calculate the dimensions of the image.
		int width = (xPadding + tileWidth) * 2 + w;
		int height = (yPadding + tileHeight) * 2 + h;

		BufferedImage image = gc.createCompatibleImage(width, height,Transparency.TRANSLUCENT);
		Graphics2D g = image.createGraphics();
		WindowBorderRenderer windowBorder = new WindowBorderRenderer();
		windowBorder.render(g, 0,0, width, height);
		/*
		//g.setColor(Color.PINK);
		//g.fillRect(0, 0, width, height);
		
		int top = 1;
		int bottom = 1 + spriteSheet.getWidth() * 2;
		int left = 0 + spriteSheet.getWidth() * 1;
		int right = 2 + spriteSheet.getWidth() * 1;
		
		int topLeft = 0;
		int topRight = 2;
		int bottomLeft = 0 + spriteSheet.getWidth() * 2;
		int bottomRight = 2 + spriteSheet.getWidth() * 2;
		
		
		// draw the edges.
		//int widthInTiles = image.getWidth() / tileWidth;
		//int heightInTiles = image.getHeight() / tileHeight;
		int x, y;
		int xMax = width - tileWidth;
		int yMax = height - tileHeight;

		// draw along the top and bottom.
		for (x = tileWidth; x < width - tileWidth; x += tileWidth) {
			y = 0;
			spriteSheet.render(g, top, x, y);
			y = yMax;
			spriteSheet.render(g, bottom, x, y);
		}

		// draw along the left and right.
		for (y = tileWidth; y < height - tileHeight; y += tileHeight) {
			x = 0;
			spriteSheet.render(g, left, x, y);
			x = xMax;
			spriteSheet.render(g, right, x, y);
		}
		
		// draw the four corners.
		spriteSheet.render(g, topLeft, 0, 0);
		spriteSheet.render(g, topRight, xMax, 0);
		spriteSheet.render(g, bottomLeft, 0, yMax);
		spriteSheet.render(g, bottomRight, xMax, yMax);
		*/
		g.dispose();
		return image;
	}	

}
