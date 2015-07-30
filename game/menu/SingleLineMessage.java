package game.menu;

import game.graphics.GameGraphics;
import game.graphics.Images;
import game.graphics.SpriteSheet;
import game.graphics.WindowBorderRenderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * Handles Popup Messages that appear above a character or item in the game.
 * 
 * TODO look into this
 * http://docs.oracle.com/javase/7/docs/api/java/awt/font/TextLayout.html
 * 
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class SingleLineMessage implements Message
{	
	private String				text;
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
	public SingleLineMessage(String text, Color color, int alignment, boolean border) {
		this.text = text;
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
	public SingleLineMessage(String text, boolean border) {
		this(text, DEFAULT_COLOR, CENTER, border);
	}
	
	public boolean			rendered	= false;
	public BufferedImage	windowImage;

	/**
	 * Render the given text to the screen.
	 * 
	 * @param g The text to render.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param transparency the alpha value for the image.
	 */
	public final void render(Graphics2D g, int x, int y, float transparency) {
		g.setFont(FONT);
		TextLayout tl = new TextLayout(text, FONT, g.getFontRenderContext());
		Rectangle2D bounds = tl.getBounds();
		
		float xOff;
		switch (alignment) {
		case RIGHT:
			xOff = (float) bounds.getWidth();
			break;
		case CENTER:
			xOff = (float) bounds.getWidth() / 2;
			break;
		case LEFT:
		default:
			xOff = 0;
			break;
		}
		
		int xPadding = 8;
		int yPadding = 8;
		/*
		 * bounds.setRect(bounds.getX() + x,// - xOff, bounds.getY() + y,//+
		 * yOff, bounds.getWidth(), bounds.getHeight() );
		 */
		if (!rendered) {
			rendered = true;
			windowImage = createBorderImage((int) bounds.getWidth(),
					(int) bounds.getHeight(), xPadding, yPadding);
			Graphics2D wg = windowImage.createGraphics();
			wg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			//wg.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			
			int xPad = spriteSheet.getTileWidth();
			int yPad = spriteSheet.getTileHeight();
			wg.setColor(Color.BLACK);
			
			// draw a square black rectangle.
			if(drawBorder){
			wg.fillRect(xPad, yPad, windowImage.getWidth() - xPad * 2,
					windowImage.getHeight() - yPad * 2);
			}
			
			// draw a rounded, black rectangle.
			else{
				wg.fillRoundRect(xPad, yPad, windowImage.getWidth() - xPad * 2,
				windowImage.getHeight() - yPad * 2, 8, 8);
			}
			//int cwx = windowImage.getWidth() / 2;
			//int cwy = windowImage.getHeight() / 2;
			// wg.setColor(Color.WHITE);
			// tl.draw(wg, cwx - xOff, cwy + tl.getDescent());
			wg.dispose();
		}
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				transparency));
		g.drawImage(windowImage, x - windowImage.getWidth() / 2, y
				- windowImage.getHeight() / 2, null);
		g.setColor(color);
		//tl.draw(g, x - xOff, y + tl.getDescent());
		AttributedString as1 = new AttributedString(text);
		 as1.addAttribute(TextAttribute.FONT, Message.FONT);
		int cStart = text.indexOf('"');
		int cEnd = cStart != -1 && text.length() > cStart ? text.indexOf('"', cStart + 1) : -1;
		if(cStart != -1 /* && cEnd != -1*/){
			as1.addAttribute(TextAttribute.FOREGROUND, Color.GREEN, cStart, text.length());
		}
		//System.out.printf("cStart: %d cEnd: %d\n", cStart, cEnd);
		//g.drawString(text, x - xOff, y + tl.getDescent());
		g.drawString(as1.getIterator(), x - xOff, y + tl.getDescent());
		// g.setColor(Color.BLACK);
		// g.fill(bounds);
		// g.setColor(Color.WHITE);
		// tl.draw(g, x - xOff, y + yOff);
	}
	
	/**
	 * Create a new BufferedImage aroudn the given quad.
	 * 
	 * @param xPadding
	 * @param yPadding
	 * @return
	 */
	public BufferedImage createBorderImage(int w, int h, int xPadding,
			int yPadding) {
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		int tileWidth = spriteSheet.getTileWidth();
		int tileHeight = spriteSheet.getTileWidth();
		// calculate the dimensions of the image.
		int width = (xPadding + tileWidth) * 2 + w;
		int height = (yPadding + tileHeight) * 2 + h;
		
		BufferedImage image = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		if (drawBorder) {
			Graphics2D g = image.createGraphics();
			WindowBorderRenderer windowBorder = new WindowBorderRenderer();
			windowBorder.render(g, 0, 0, width, height);
			g.dispose();
		}
		return image;
	}
	
}
