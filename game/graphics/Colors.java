package game.graphics;

import java.awt.Color;

/**
 * Commonly used colors in the Game.
 * @author Aaron Carson
 * @version Aug 3, 2015
 */
public class Colors
{
	private static final int	MAX			= 255;
	private static final int	HI			= 200;
	private static final int	MID			= 140;
	private static final int	LOW			= 70;
	private static final int	MIN			= 0;
	public static final Color	LIGHT_BLUE	= new Color(MID, MID, HI);
	public static final Color	LIGHT_GREEN	= new Color(HI, 255, HI);
	public static final Color	LIGHT_RED	= new Color(255, HI, HI);
	public static final Color	WARNING	= Color.RED;
	public static final Color	NEW_ITEM	= new Color(80, MAX, 80);
}
