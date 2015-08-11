package game.menu;

import game.graphics.Fonts;
import game.graphics.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;

public interface Message
{
	//public static final int		FONT_SIZE	= 9;
	//public static final String	FONT_NAME	= "ProFont";
	//public static final Font	FONT		= loadFontFrom("fonts/ProFont");
	//public static final Font	FONT		= new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	public static final Font	FONT		= Fonts.FONT;
	public static final int		FONT_SIZE	= FONT.getSize();
	/**
	 * Loads an image.
	 * 
	 * @param filename The name for the file to load.
	 * @return A BuffedImage.
	 */
	public static Font loadFontFrom(String filename) {
		try {
			// System.out.println("filename: " + filename);
			// String resource = Images.class.getResource(filename).toString();
			// System.out.println("resource: " + resource);
			Font font = Font.createFont(Font.TYPE1_FONT,
					Message.class.getResourceAsStream(filename));
			// GraphicsEnvironment ge =
			// GraphicsEnvironment.getLocalGraphicsEnvironment();
			// ge.registerFont(font);
			return font;
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static final Color	DEFAULT_COLOR	= Color.WHITE;
	public static final float	OPAQUE			= 1.0f;
	public static final float	TRANSLUCENT		= 0.60f;
	
	public static final int		LEFT			= 1;
	public static final int		RIGHT			= 2;
	public static final int		CENTER			= 3;
	
	public static final int		X_PADDING		= 3;
	public static final int		Y_PADDING		= 2;
	public static final int		X_FIX			= 1;
	public static final int		Y_FIX			= 1;
	
	/**
	 * Render the given text to the screen using the default TRANSLUCENT
	 * transparency value.
	 * 
	 * @param g The text to render.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	default void render(Graphics2D g, int x, int y) {
		render(g, x, y, TRANSLUCENT);
	}
	
	/**
	 * Render the given text to the screen.
	 * 
	 * @param g The text to render.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param transparency the alpha value for the image.
	 */
	void render(Graphics2D g, int x, int y, float transparency);
	
	/**
	 * Get the maximum rendered width for an array of strings.
	 * 
	 * @param context The FontRenderContext used to eventually render this text.
	 * @param text The array of strings to measure.
	 * @return The greatest width.
	 */
	public static int getMaximumRenderedWidth(FontRenderContext context,
			String[] text) {
		// get the width of the first string.
		int max = getRenderedWidth(context, text[0]);
		
		// check the other strings.
		for (int i = 1; i < text.length; i++) {
			int next = getRenderedWidth(context, text[i]);
			if (next > max) max = next;
		}
		
		return max;
		
	}
	
	/**
	 * Get the maximum rendered width for an array of strings.
	 * 
	 * @param context The FontRenderContext used to eventually render this text.
	 * @param text The array of strings to measure.
	 * @return The greatest width.
	 */
	public static int getMaximumRenderedWidth(FontMetrics metrics, String[] text) {
		// get the width of the first string.
		int max = getRenderedWidth(metrics, text[0]);
		
		// check the other strings.
		for (int i = 1; i < text.length; i++) {
			int next = getRenderedWidth(metrics, text[i]);
			if (next > max) max = next;
		}
		
		return max;
		
	}
	
	/**
	 * Get the rendered width of the given String.
	 * 
	 * @param context The context in which the String is being rendered.
	 * @param text The text to measure.
	 * @return The width of the rendered text.
	 */
	public static int getRenderedWidth(FontRenderContext context, String text) {
		return (int) new TextLayout(text, FONT, context).getBounds().getWidth();
	}
	
	/**
	 * Get the rendered width of the given String.
	 * 
	 * @param context The context in which the String is being rendered.
	 * @param text The text to measure.
	 * @return The width of the rendered text.
	 */
	public static int getRenderedWidth(FontMetrics metrics, String text) {
		return metrics.stringWidth(text);
	}
	
	/**
	 * Get the rendered width of the given String.
	 * 
	 * @param context The context in which the String is being rendered.
	 * @param text The text to measure.
	 * @return The width of the rendered text.
	 */
	public static int getRenderedWidth(FontMetrics metrics,
			AttributedString text) {
		// build the String so it can be measured, ignoring the null terminal.
		StringBuilder sb = new StringBuilder();
		AttributedCharacterIterator it = text.getIterator();
		sb.append(it.current());
		while (it.getIndex() < it.getEndIndex() - 1) {
			sb.append(it.next());
		}
		String s = sb.toString();
		System.out.printf("attributedString raw: '%s'\n", s);
		
		return metrics.stringWidth(s);
	}
	
}
