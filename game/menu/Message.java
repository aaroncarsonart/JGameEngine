package game.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public interface Message
{
	public static final int		FONT_SIZE		= 16;
	public static final String	FONT_NAME		= "Courier";
	public static final Font	FONT			= new Font(FONT_NAME,
														Font.PLAIN, FONT_SIZE);
	public static final Color	DEFAULT_COLOR	= Color.WHITE;
	public static final float	OPAQUE			= 1.0f;
	public static final float	TRANSLUCENT		= 0.60f;
	
	public static final int		LEFT			= 1;
	public static final int		RIGHT			= 2;
	public static final int		CENTER			= 3;
	
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
	public static int getMaximumRenderedWidth(FontRenderContext context, String[] text) {
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
	public static int getRenderedWidth(FontMetrics metrics, String text) {
		return metrics.stringWidth(text);
	}
	
}
