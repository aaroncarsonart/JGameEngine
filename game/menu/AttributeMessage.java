package game.menu;

import game.graphics.GameGraphics;
import game.graphics.WindowBorderRenderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * Handles Popup Messages that appear above a character or item in the game and
 * display with attributes.
 * 
 * TODO look into this
 * http://docs.oracle.com/javase/7/docs/api/java/awt/font/TextLayout.html
 * 
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class AttributeMessage implements Message
{
	private AttributedString[]	aStrings;
	private Color				color;
	private int					alignment;
	private boolean				drawBorder;
	private BufferedImage		image;
	private int					textHeight;
	private int					fontAscent;
	private int					lineSpacing;
	private int					width;
	private int					height;
	private int					xInset;
	private int					yInset;
	
	/**
	 * Create a message to be rendered to the screen.
	 * 
	 * @param text A single String to render.
	 * @param color the color of the text to render.
	 * @param alignment The alignment.
	 * @param border If true, a border is rendered.
	 */
	public AttributeMessage(String text, Color color, int alignment,
			boolean border, int width, int height) {
		initializeFromConstructor(getAttributedStrings(text), color, alignment,
				border, width, height);
		initialize();
	}

	/**
	 * Create a message to be rendered to the screen.
	 * 
	 * @param text An array of Strings to render.
	 * @param color the color of the text to render.
	 * @param alignment The alignment.
	 * @param border If true, a border is rendered.
	 */
	public AttributeMessage(String[] text, Color color, int alignment,
			boolean border, int width, int height) {
		initializeFromConstructor(getAttributedStrings(text), color, alignment,
				border, width, height);
		initialize();
	}

	
	
	/**
	 * Create a message to be rendered to the screen.
	 * 
	 * @param text A single AttributedString to render.
	 * @param color the color of the text to render.
	 * @param alignment The alignment.
	 * @param border If true, a border is rendered.
	 */
	public AttributeMessage(AttributedString text, Color color, int alignment,
			boolean border, int width, int height) {
		AttributedString[] as = { text };
		initializeFromConstructor(as, color, alignment, border, width, height);
		initialize();
	}
	
	/**
	 * Create a message to be rendered to the screen.
	 * 
	 * @param text An array of AttributedStrings to render.
	 * @param color the base color of the text to render.
	 * @param alignment The alignment.
	 * @param border If true, a border is rendered.
	 */
	public AttributeMessage(AttributedString[] text, Color color,
			int alignment, boolean border, int width, int height) {
		initializeFromConstructor(text, color, alignment, border, width, height);
		initialize();
	}
	
	/**
	 * Create an array of AttributedStrings from the given input text with the
	 * default font.
	 * 
	 * @param text The input text.
	 * @return An array of AttributeStrings, split by newline chars.
	 */
	public static AttributedString[] getAttributedStrings(String text) {
		String[] s = text.split("\n");
		AttributedString[] a = new AttributedString[s.length];
		for (int i = 0; i < s.length; i++) {
			a[i] = new AttributedString(s[i]);
			a[i].addAttribute(TextAttribute.FONT, Message.FONT);
		}
		return a;
	}
	
	/**
	 * Create an array of AttributedStrings from the given input text with the
	 * default font.
	 * 
	 * @param text The input text.
	 * @return An array of AttributeStrings, split by newline chars.
	 */
	public static AttributedString[] getAttributedStrings(String[] text) {
		AttributedString[] a = new AttributedString[text.length];
		for (int i = 0; i < text.length; i++) {
			a[i] = new AttributedString(text[i]);
			a[i].addAttribute(TextAttribute.FONT, Message.FONT);
		}
		return a;
	}
	
	/**
	 * Initialize immediate values from the constructor.
	 * 
	 * @param strings
	 * @param color
	 * @param alignment
	 * @param border
	 */
	private void initializeFromConstructor(AttributedString[] strings,
			Color color, int alignment, boolean border, int width, int height) {
		this.aStrings = strings;
		this.color = color;
		this.alignment = alignment;
		this.drawBorder = border;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Initialize this this message and the backing image.
	 */
	private final void initialize() {
		
		// create temp image for font metrics.
		GraphicsConfiguration gc = GameGraphics.getGraphicsConfiguration();
		BufferedImage tmp = gc.createCompatibleImage(1, 1,
				Transparency.TRANSLUCENT);
		Graphics2D g = tmp.createGraphics();
		g.setFont(FONT);
		
		// calculate height of a single line of text.
		FontMetrics m = g.getFontMetrics();
		textHeight = m.getHeight();
		fontAscent = m.getAscent();
		
		// spacing between each line.
		this.lineSpacing = X_PADDING / 2;
		
		// inner dimensions
		int innerHeight = (textHeight + lineSpacing) * aStrings.length
				- lineSpacing;
		int innerWidth = Message.getRenderedWidth(m, aStrings[0]);
		for (int i = 1; i < aStrings.length; i++) {
			int next = Message.getRenderedWidth(m, aStrings[1]);
			innerWidth = Math.max(innerWidth, next);
		}
		g.dispose();
		
		// *****************************************
		// Create the background image for the menu.
		// *****************************************
		WindowBorderRenderer borderRenderer = new WindowBorderRenderer();
		
		int tileWidth = borderRenderer.getTileWidth();
		int tileHeight = borderRenderer.getTileHeight();
		
		this.xInset = X_PADDING + tileWidth;
		this.yInset = Y_PADDING + tileHeight;
		System.out.printf("xInset: %d yInset: %d\n", xInset, yInset);
		
		// ensure proper dimensions.
		if (this.width == 0) this.width = innerWidth + 2 * xInset + X_FIX;
		if (this.height == 0) this.height = innerHeight + 2 * yInset + Y_FIX;
		
		System.out.printf("w: %d h: %d inner: %d\n", width, height, innerWidth);
		
		image = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// render message window background.
		g.setColor(Color.BLACK);
		int rx, ry, rw, rh;
		int txOff = tileWidth / 2;
		int tyOff = tileHeight / 2;
		rx = txOff;
		ry = tyOff;
		rw = width - txOff * 2;
		rh = height - tyOff * 2;
		
		// render the menu borders
		// *****************************************
		// Create the background image for the menu.
		// *****************************************
		if (drawBorder) {
			System.out.println("should be true: " + drawBorder);
			g.fillRect(rx, ry, rw, rh);
			borderRenderer.render(g, 0, 0, width, height);
		}
		else {
			System.out.println("should be false: " + drawBorder);
			g.fillRoundRect(rx, ry, rw, rh, 8, 8);
		}
		g.dispose();
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Render the given text to the screen.
	 * 
	 * @param g The text to render.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param transparency the alpha value for the image.
	 */
	@Override
	public final void render(Graphics2D g, int x, int y, float alpha) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		
		// draw the background image.
		g.drawImage(image, x, y, null);
		
		// draw the menu items.
		g.setFont(FONT);
		g.setColor(color);
		
		int lx = x + xInset;
		int ly;
		
		for (int i = 0; i < aStrings.length; i++) {
			// get the current y text position.
			ly = y + yInset + fontAscent + (textHeight + lineSpacing) * i;
			
			// draw the menu item.
			g.drawString(aStrings[i].getIterator(), lx, ly);
		}
		
	}
	
}
