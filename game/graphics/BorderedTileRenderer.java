package game.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * BasicBorderedTile is used to facilitate easy drawing of a tile that has
 * borders.
 * <p>
 * Consider the following tile pattern:
 * 
 * <pre>
 * <code>
 * +---  ----  ---+  .#.A  .#.B    
 * |#.#  .#.#  .#.|  .#.#  .#.# 
 * 
 * |#.#  .#.#  .#.|  .#.#  .#.#
 * |#.#  .#.#  .#.|  .#.+  +#.#
 * 
 * |#.#  .#.#  .#.|  .#.+  +#.#
 * +---  ----  ---+  .#.#  .#.#
 * </code>
 * </pre>
 * <p>
 * Access these tiles as 16x16 blocks with the following indices:
 * 
 * <pre>
 * <code>
 *    0     1     2     3     4
 * 
 *    5     6     7     8     9
 *  
 *   10    11    12    13    14
 * </code>
 * </pre>
 * <p>
 * Alternatively, they can be accessed in 8x8 blocks:
 * 
 * <pre>
 * <code>
 * +-  --  --  --  --  -+  .#  .A  .#  .B    
 * 
 * |#  .#  .#  .#  .#  .|  .#  .#  .#  .# 
 * 
 * |#  .#  .#  .#  .#  .|  .#  .#  .#  .#
 * 
 * |#  .#  .#  .#  .#  .|  .#  .+  +#  .#
 * 
 * |#  .#  .#  .#  .#  .|  .#  .+  +#  .#
 * 
 * +-  --  --  --  --  -+  .#  .#  .#  .#
 * </code>
 * </pre>
 * <p>
 * Access these tiles as 16x16 blocks with the following indices:
 * 
 * <pre>
 * <code>
 *  0   1   2   3   4   5   6   7   8   9 
 * 
 * 10  11  12  13  14  15  16  17  18  19 
 * 
 * 20  21  22  23  24  25  26  27  28  29 
 * 
 * 30  31  32  33  34  35  36  37  38  39 
 * 
 * 40  41  42  43  44  45  46  47  48  49 
 * 
 * 50  51  52  53  54  55  56  57  58  59 
 * </code>
 * </pre>
 * <p>
 * These tiles would be indexed and be used to draw the tile in a number of
 * combinations.
 * 
 * @author Aaron Carson
 * @version Jul 20, 2015
 */
public class BorderedTileRenderer
{
	private SpriteSheet		tiles_16;
	private SpriteSheet		tiles_8;
	private Random			random				= new Random();
	private byte			cellType;
	private int				border;
	private int				center;
	
	public static final int	T_SIZE_16			= 16;
	public static final int	T_WIDTH_16			= 5;
	public static final int	T_HEIGHT_16			= 3;
	
	public static final int	T_SIZE_8			= T_SIZE_16 / 2;
	public static final int	T_WIDTH_8			= T_WIDTH_16 * 2;
	public static final int	T_HEIGHT_8			= T_HEIGHT_16 * 2;	;
		
	/**
	 * Consider the following tile pattern:
	 * 
	 * <pre>
	 * <code>
	 * +---  ----  ---+  .#.A  .#.B    
	 * |#.#  .#.#  .#.|  .#.#  .#.# 
	 * 
	 * |#.#  .#.#  .#.|  .#.#  .#.#
	 * |#.#  .#.#  .#.|  .#.+  +#.#
	 * 
	 * |#.#  .#.#  .#.|  .#.+  +#.#
	 * +---  ----  ---+  .#.#  .#.#
	 * </code>
	 * </pre>
	 * <p>
	 * Access these tiles with the following indices:
	 * 
	 * <pre>
	 * <code>
	 *    0     1     2     3     4
	 * 
	 *    5     6     7     8     9
	 *  
	 *   10    11    12    13    14
	 * </code>
	 * </pre>
	 */
	
	/**
	 * Creat a new BasicBorderedTile.
	 * 
	 * @param src The source bitmap.
	 * @param cellType The key used for which cell type to render.
	 * @param border The id of the tile
	 * @param center
	 */
	public BorderedTileRenderer(BufferedImage src, byte cellType) {
		this.tiles_16 = new SpriteSheet(src, T_SIZE_16);
		this.tiles_8 = new SpriteSheet(src, T_SIZE_8);
		this.cellType = cellType;
	}
	
	/**
	 * Convert a byte pattern to an integer representation.
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getStringFromPattern(byte[] pattern, int cellType) {
		int length = pattern.length;
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(pattern[i] == cellType ? '1' : '0');
		}
		return sb.toString();
	}
	
	/**
	 * Get the base 10 integer representation of a binary string.
	 * 
	 * @param pattern The binary (0's and 1's) string.
	 * @return The integer form of the binary string.
	 */
	public static int getIntFromBinaryString(String pattern) {
		return Integer.parseInt(pattern, 2);
	}
	
	/**
	 * Get the base 10 integer representation of a binary string.
	 * 
	 * @param pattern The binary (0's and 1's) string.
	 * @return The integer form of the binary string.
	 */
	public static byte getByteFromBinaryString(String pattern) {
		return Byte.parseByte(pattern, 2);
	}
	
	/**
	 * Get the binary string representation of the given integer.
	 * 
	 * @param i The integer to convert.
	 * @return The binary string representation, padded to
	 */
	public static String getBinaryStringfromInt(int i) {
		String.format("%1$9s", Integer.toBinaryString(i)).replace(' ', '0');
		return null;
	}
	
	public static byte[] getPatternFromInt(int i) {
		return null;
	}
	
	/**
	 * Check if the given coordinates are contained within this maze (counting
	 * by cell)
	 * 
	 * @param x The horizontal coordinate.
	 * @param y The vertical coordinate.
	 * @param width The width to be within.
	 * @param height The height to be within.
	 * @return True, if the coordinates are within the maze.
	 */
	public static boolean withinBounds(int x, int y, int width, int height) {
		return 0 <= x && x < width && 0 <= y && y < height;
	}
	
	/**
	 * Get a String Pattern for the given
	 * 
	 * @param cells The 2D array of cells to check.
	 * @param cellType The value of the cell type to get a pattern for.
	 * @param x The x position of the cell to check.
	 * @param y The y position of the cell to check.
	 * @param w The width of the 2d cell array.
	 * @param h The height of the 2d cell array.
	 * @return A unique, 9 character string pattern representing the neighboring
	 *         cell contents.
	 */
	public static String getPattern(byte[][] cells, byte cellType, int x,
			int y, int w, int h) {
		StringBuilder sb = new StringBuilder(9);
		for (int y2 = y - 1; y2 < y + 2; y2++) {
			for (int x2 = x - 1; x2 < x + 2; x2++) {
				sb.append(withinBounds(x2, y2, w, h)
						&& cells[x2][y2] == cellType ? '1' : '0');
			}
		}
		return sb.toString();
	}
	
	/**
	 * Render the BorderedTile to an image.
	 * 
	 * @param g The tiles.
	 * @param tiles the 2d tile array from which to get data.
	 * @param key The value of the tiles to render.
	 */
	public void render(Graphics2D g, byte[][] tiles) {
		int height = tiles[0].length;
		int width = tiles.length;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// if a tile to render.
				//renderTile(g, tiles, x, y, width, height);
				renderTile(g, tiles, x, y);
				
				// if (tiles[x][y] == key) {
				// }
			}
		}
	}
	
	/**
	 * Get a 4 character string pattern from a 9 character 3x3 string pattern.
	 * 
	 * @param s The string to read from.
	 * @param i0 The index of the 1st character
	 * @param i1 The index of the 2nd character
	 * @param i2 The index of the 3rd character
	 * @param i3 The index of the 4th character
	 * @return A specific substring of length 4.
	 */
	public final String getPattern(String s, int i0, int i1, int i2, int i3) {
		return new StringBuilder().append(s.charAt(i0)).append(s.charAt(i1))
				.append(s.charAt(i2)).append(s.charAt(i3)).toString();
	}
	
	/**
	 * Render the tile based on 8x8 tiles.
	 * 
	 * @param g The Graphics2D to render to.
	 * @param cells The cell array containing tile data.
	 * @param x The x position of the tile to render in the cell array.
	 * @param y The y position of the tile to render in the cell array.
	 * @param width The width of the cell array.
	 * @param height The height of the cell array.
	 */
	public void renderTile8x8(Graphics2D g, byte[][] cells, int x, int y,
			int width, int height) {
		// get the cell moore neighbors pattern.
		String sPattern = getPattern(cells, cellType, x, y, width, height);
		
		// check the four 2 x 2 tile blocks within the 3x3 block.
		byte tLeft = getByteFromBinaryString(getPattern(sPattern, 0, 1, 3, 4));
		byte tRight = getByteFromBinaryString(getPattern(sPattern, 1, 2, 4, 5));
		byte bLeft = getByteFromBinaryString(getPattern(sPattern, 3, 4, 6, 7));
		byte bRight = getByteFromBinaryString(getPattern(sPattern, 4, 5, 7, 8));
		
		byte switchBit;
		int xOffset, yOffset, x2, y2;
		
		// ***************************************************************
		// 1. render top-left of cube.
		// ***************************************************************
		switchBit = tLeft;
		xOffset = 0;
		yOffset = 0;
		// no offset.
		x2 = x * T_SIZE_16 + xOffset;
		y2 = y * T_SIZE_16 + yOffset;
		int tIndex = -1;
		
		// Render X * portion of center tile using * * portion of 2x2 section.
		// * * * x
		switch (switchBit) {
		
		// 0 0
		// 0 0
		case BytePattern.CLEAR:
			
			// 1 1
			// 1 1
		case BytePattern.FULL:
			tIndex = Index8x8.CENTER_0;
			tiles_8.render(g, Index8x8.CENTER_0, x2, y2);
			break;
		
		// 0 0
		// 0 1
		case BytePattern.TOP_LEFT:
			tIndex = Index8x8.TOP_LEFT;
			tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			break;
		
		// 0 0
		// 1 0
		case BytePattern.TOP_RIGHT:
			// tIndex = Index8x8.TOP_RIGHT;
			// tiles_8.render(g, Index8x8.TOP_RIGHT, x2, y2);
			break;
		
		// 0 1
		// 0 0
		case BytePattern.BOTTOM_LEFT:
			// tIndex = Index8x8.BOTTOM_LEFT;
			// tiles_8.render(g, Index8x8.BOTTOM_LEFT, x2, y2);
			break;
		
		// 1 0
		// 0 0
		case BytePattern.BOTTOM_RIGHT:
			// tIndex = Index8x8.BOTTOM_RIGHT;
			// tiles_8.render(g, Index8x8.BOTTOM_RIGHT, x2, y2);
			break;
		
		// 0 0
		// 1 1
		case BytePattern.TOP:
			tIndex = Index8x8.TOP_0;
			tiles_8.render(g, Index8x8.TOP_0, x2, y2);
			
			// 1 1
			// 0 0
		case BytePattern.BOTTOM:
			break;
		
		// 0 1
		// 0 1
		case BytePattern.LEFT:
			tIndex = Index8x8.LEFT_0;
			tiles_8.render(g, Index8x8.LEFT_0, x2, y2);
			
			// 1 0
			// 1 0
		case BytePattern.RIGHT:
			break;
		
		// 1 1
		// 1 0
		case BytePattern.INSIDE_TOP_LEFT:
			break;
		
		// 1 1
		// 0 1
		case BytePattern.INSIDE_TOP_RIGHT:
			tIndex = Index8x8.LEFT_0;
			tiles_8.render(g, Index8x8.LEFT_0, x2, y2);
			break;
		
		// 1 0
		// 1 1
		case BytePattern.INSIDE_BOTTOM_LEFT:
			tIndex = Index8x8.TOP_0;
			tiles_8.render(g, Index8x8.TOP_0, x2, y2);
			break;
		
		// 0 1
		// 1 1
		case BytePattern.INSIDE_BOTTOM_RIGHT:
			tIndex = Index8x8.INSIDE_BOTTOM_RIGHT;
			tiles_8.render(g, Index8x8.INSIDE_BOTTOM_RIGHT, x2, y2);
			break;
		
		// 0 1
		// 1 0
		case BytePattern.DIAGONAL_UP:
			break;
		
		// 1 0
		// 0 1
		case BytePattern.DIAGONAL_DOWN:
			//tIndex = Index8x8.TOP_LEFT;
			//tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			tIndex = Index8x8.DIAGONAL_TOP_LEFT;
			tiles_8.render(g, Index8x8.DIAGONAL_TOP_LEFT, x2, y2);
			break;
		}
		/*
		 * g.setFont(new Font("Arial", Font.PLAIN, 8)); g.setColor(Color.WHITE);
		 * g.drawString("" + switchBit, x2, y2); g.setColor(Color.GREEN);
		 * g.drawString("" + tIndex, x2, y2 + 8);
		 */
		
		// ***************************************************************
		// 2. render top-right of cube.
		// ***************************************************************
		switchBit = tRight;
		xOffset = 8;
		yOffset = 0;
		// no offset.
		x2 = x * T_SIZE_16 + xOffset;
		y2 = y * T_SIZE_16 + yOffset;
		tIndex = -1;
		
		// Render * X portion of center tile using * * portion of 2x2 section.
		// * * x *
		switch (switchBit) {
		
		// 0 0
		// 0 0
		case BytePattern.CLEAR:
			break;
		
		// 1 1
		// 1 1
		case BytePattern.FULL:
			tIndex = Index8x8.CENTER_1;
			tiles_8.render(g, Index8x8.CENTER_1, x2, y2);
			break;
		
		// 0 0
		// 0 1
		case BytePattern.TOP_LEFT:
			// tIndex = Index8x8.TOP_LEFT;
			// tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			break;
		
		// 0 0
		// 1 0
		case BytePattern.TOP_RIGHT:
			tIndex = Index8x8.TOP_RIGHT;
			tiles_8.render(g, Index8x8.TOP_RIGHT, x2, y2);
			break;
		
		// 0 1
		// 0 0
		case BytePattern.BOTTOM_LEFT:
			// tIndex = Index8x8.BOTTOM_LEFT;
			// tiles_8.render(g, Index8x8.BOTTOM_LEFT, x2, y2);
			break;
		
		// 1 0
		// 0 0
		case BytePattern.BOTTOM_RIGHT:
			// tIndex = Index8x8.BOTTOM_RIGHT;
			// tiles_8.render(g, Index8x8.BOTTOM_RIGHT, x2, y2);
			break;
		
		// 0 0
		// 1 1
		case BytePattern.TOP:
			tIndex = Index8x8.TOP_1;
			tiles_8.render(g, Index8x8.TOP_1, x2, y2);
			
			// 1 1
			// 0 0
		case BytePattern.BOTTOM:
			break;
		
		// 0 1
		// 0 1
		case BytePattern.LEFT:
			// tIndex = Index8x8.LEFT_0;
			// tiles_8.render(g, Index8x8.LEFT_0, x2, y2);
			break;
		
		// 1 0
		// 1 0
		case BytePattern.RIGHT:
			tIndex = Index8x8.RIGHT_0;
			tiles_8.render(g, Index8x8.RIGHT_0, x2, y2);
			break;
		
		// 1 1
		// 1 0
		case BytePattern.INSIDE_TOP_LEFT:
			tIndex = Index8x8.RIGHT_0;
			tiles_8.render(g, Index8x8.RIGHT_0, x2, y2);
			break;
		
		// 1 1
		// 0 1
		case BytePattern.INSIDE_TOP_RIGHT:
			// tIndex = Index8x8.LEFT_0;
			// tiles_8.render(g, Index8x8.LEFT_0, x2, y2);
			break;
		
		// 1 0
		// 1 1
		case BytePattern.INSIDE_BOTTOM_LEFT:
			tIndex = Index8x8.INSIDE_BOTTOM_LEFT;
			tiles_8.render(g, Index8x8.INSIDE_BOTTOM_LEFT, x2, y2);
			break;
		
		// 0 1
		// 1 1
		case BytePattern.INSIDE_BOTTOM_RIGHT:
			tIndex = Index8x8.TOP_1;
			tiles_8.render(g, Index8x8.TOP_1, x2, y2);
			break;
		
		// 0 1
		// 1 0
		case BytePattern.DIAGONAL_UP:
			//tIndex = Index8x8.TOP_RIGHT;
			//tiles_8.render(g, Index8x8.TOP_RIGHT, x2, y2);
			tIndex = Index8x8.DIAGONAL_TOP_RIGHT;
			tiles_8.render(g, Index8x8.DIAGONAL_TOP_RIGHT, x2, y2);
			break;
		
		// 1 0
		// 0 1
		case BytePattern.DIAGONAL_DOWN:
			// tIndex = Index8x8.TOP_LEFT;
			// tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			break;
		}
		
		// g.setFont(new Font("Arial", Font.PLAIN, 8));
		// g.setColor(Color.WHITE);
		// g.drawString("" + switchBit, x2, y2);
		// g.setColor(Color.GREEN);
		// g.drawString("" + tIndex, x2, y2 + 8);
		
		// ***************************************************************
		// 3. render bottom-left of cube.
		// ***************************************************************
		switchBit = bLeft;
		xOffset = 0;
		yOffset = 8;
		// no offset.
		x2 = x * T_SIZE_16 + xOffset;
		y2 = y * T_SIZE_16 + yOffset;
		tIndex = -1;
		
		// Render * * portion of center tile using * X portion of 2x2 section.
		// X * * *
		switch (switchBit) {
		
		// 0 0
		// 0 0
		case BytePattern.CLEAR:
			break;
		
		// 1 1
		// 1 1
		case BytePattern.FULL:
			tIndex = Index8x8.CENTER_2;
			tiles_8.render(g, Index8x8.CENTER_2, x2, y2);
			break;
		
		// 0 0
		// 0 1
		case BytePattern.TOP_LEFT:
			// tIndex = Index8x8.TOP_LEFT;
			// tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			break;
		
		// 0 0
		// 1 0
		case BytePattern.TOP_RIGHT:
			// tIndex = Index8x8.TOP_RIGHT;
			// tiles_8.render(g, Index8x8.TOP_RIGHT, x2, y2);
			break;
		
		// 0 1
		// 0 0
		case BytePattern.BOTTOM_LEFT:
			tIndex = Index8x8.BOTTOM_LEFT;
			tiles_8.render(g, Index8x8.BOTTOM_LEFT, x2, y2);
			break;
		
		// 1 0
		// 0 0
		case BytePattern.BOTTOM_RIGHT:
			// tIndex = Index8x8.BOTTOM_RIGHT;
			// tiles_8.render(g, Index8x8.BOTTOM_RIGHT, x2, y2);
			break;
		
		// 0 0
		// 1 1
		case BytePattern.TOP:
			// tIndex = Index8x8.TOP_0;
			// tiles_8.render(g, Index8x8.TOP_0, x2, y2);
			break;
		// 1 1
		// 0 0
		case BytePattern.BOTTOM:
			tIndex = Index8x8.BOTTOM_0;
			tiles_8.render(g, Index8x8.BOTTOM_0, x2, y2);
			break;
		
		// 0 1
		// 0 1
		case BytePattern.LEFT:
			tIndex = Index8x8.LEFT_1;
			tiles_8.render(g, Index8x8.LEFT_1, x2, y2);
			break;
		
		// 1 0
		// 1 0
		case BytePattern.RIGHT:
			// tIndex = Index8x8.RIGHT_0;
			// tiles_8.render(g, Index8x8.RIGHT_0, x2, y2);
			break;
		
		// 1 1
		// 1 0
		case BytePattern.INSIDE_TOP_LEFT:
			tIndex = Index8x8.BOTTOM_0;
			tiles_8.render(g, Index8x8.BOTTOM_0, x2, y2);
			break;
		
		// 1 1
		// 0 1
		case BytePattern.INSIDE_TOP_RIGHT:
			tIndex = Index8x8.INSIDE_TOP_RIGHT;
			tiles_8.render(g, Index8x8.INSIDE_TOP_RIGHT, x2, y2);
			break;
		
		// 1 0
		// 1 1
		case BytePattern.INSIDE_BOTTOM_LEFT:
			// tIndex = Index8x8.INSIDE_BOTTOM_LEFT;
			// tiles_8.render(g, Index8x8.INSIDE_BOTTOM_LEFT, x2, y2);
			break;
		
		// 0 1
		// 1 1
		case BytePattern.INSIDE_BOTTOM_RIGHT:
			tIndex = Index8x8.LEFT_1;
			tiles_8.render(g, Index8x8.LEFT_1, x2, y2);
			break;
		
		// 0 1
		// 1 0
		case BytePattern.DIAGONAL_UP:
			//tIndex = Index8x8.BOTTOM_LEFT;
			//tiles_8.render(g, Index8x8.BOTTOM_LEFT, x2, y2);
			tIndex = Index8x8.DIAGONAL_BOTTOM_LEFT;
			tiles_8.render(g, Index8x8.DIAGONAL_BOTTOM_LEFT, x2, y2);
			break;
		
		// 1 0
		// 0 1
		case BytePattern.DIAGONAL_DOWN:
			// tIndex = Index8x8.TOP_LEFT;
			// tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			break;
		}
		
		// g.setFont(new Font("Arial", Font.PLAIN, 8));
		// g.setColor(Color.WHITE);
		// g.drawString("" + switchBit, x2, y2);
		// g.setColor(Color.GREEN);
		// g.drawString("" + tIndex, x2, y2 + 8);
		
		// ***************************************************************
		// 4. render bottom-right of cube.
		// ***************************************************************
		switchBit = bRight;
		xOffset = 8;
		yOffset = 8;
		// no offset.
		x2 = x * T_SIZE_16 + xOffset;
		y2 = y * T_SIZE_16 + yOffset;
		tIndex = -1;
		
		// Render * * portion of center tile using * X portion of 2x2 section.
		// X * * *
		switch (switchBit) {
		
		// 0 0
		// 0 0
		case BytePattern.CLEAR:
			break;
		
		// 1 1
		// 1 1
		case BytePattern.FULL:
			tIndex = Index8x8.CENTER_3;
			tiles_8.render(g, Index8x8.CENTER_3, x2, y2);
			break;
		
		// 0 0
		// 0 1
		case BytePattern.TOP_LEFT:
			// tIndex = Index8x8.TOP_LEFT;
			// tiles_8.render(g, Index8x8.TOP_LEFT, x2, y2);
			break;
		
		// 0 0
		// 1 0
		case BytePattern.TOP_RIGHT:
			// tIndex = Index8x8.TOP_RIGHT;
			// tiles_8.render(g, Index8x8.TOP_RIGHT, x2, y2);
			break;
		
		// 0 1
		// 0 0
		case BytePattern.BOTTOM_LEFT:
			// tIndex = Index8x8.BOTTOM_LEFT;
			// tiles_8.render(g, Index8x8.BOTTOM_LEFT, x2, y2);
			break;
		
		// 1 0
		// 0 0
		case BytePattern.BOTTOM_RIGHT:
			tIndex = Index8x8.BOTTOM_RIGHT;
			tiles_8.render(g, Index8x8.BOTTOM_RIGHT, x2, y2);
			break;
		
		// 0 0
		// 1 1
		case BytePattern.TOP:
			// tIndex = Index8x8.TOP_0;
			// tiles_8.render(g, Index8x8.TOP_0, x2, y2);
			break;
		// 1 1
		// 0 0
		case BytePattern.BOTTOM:
			tIndex = Index8x8.BOTTOM_1;
			tiles_8.render(g, Index8x8.BOTTOM_1, x2, y2);
			break;
		
		// 0 1
		// 0 1
		case BytePattern.LEFT:
			// tIndex = Index8x8.LEFT_0;
			// tiles_8.render(g, Index8x8.LEFT_0, x2, y2);
			break;
		
		// 1 0
		// 1 0
		case BytePattern.RIGHT:
			tIndex = Index8x8.RIGHT_1;
			tiles_8.render(g, Index8x8.RIGHT_1, x2, y2);
			break;
		
		// 1 1
		// 1 0
		case BytePattern.INSIDE_TOP_LEFT:
			tIndex = Index8x8.INSIDE_TOP_LEFT;
			tiles_8.render(g, Index8x8.INSIDE_TOP_LEFT, x2, y2);
			break;
		
		// 1 1
		// 0 1
		case BytePattern.INSIDE_TOP_RIGHT:
			tIndex = Index8x8.BOTTOM_1;
			tiles_8.render(g, Index8x8.BOTTOM_1, x2, y2);
			break;
		
		// 1 0
		// 1 1
		case BytePattern.INSIDE_BOTTOM_LEFT:
			tIndex = Index8x8.RIGHT_1;
			tiles_8.render(g, Index8x8.RIGHT_1, x2, y2);
			break;
		
		// 0 1
		// 1 1
		case BytePattern.INSIDE_BOTTOM_RIGHT:
			// tIndex = Index8x8.LEFT_0;
			// tiles_8.render(g, Index8x8.LEFT_0, x2, y2);
			break;
		
		// 0 1
		// 1 0
		case BytePattern.DIAGONAL_UP:
			// tIndex = Index8x8.BOTTOM_LEFT;
			// tiles_8.render(g, Index8x8.BOTTOM_LEFT, x2, y2);
			break;
		
		// 1 0
		// 0 1
		case BytePattern.DIAGONAL_DOWN:
			//tIndex = Index8x8.BOTTOM_RIGHT;
			//tiles_8.render(g, Index8x8.BOTTOM_RIGHT, x2, y2);
			tIndex = Index8x8.DIAGONAL_BOTTOM_RIGHT;
			tiles_8.render(g, Index8x8.DIAGONAL_BOTTOM_RIGHT, x2, y2);
			break;
		}
		
		// g.setFont(new Font("Arial", Font.PLAIN, 8));
		// g.setColor(Color.WHITE);
		// g.drawString("" + switchBit, x2, y2);
		// g.setColor(Color.GREEN);
		// g.drawString("" + tIndex, x2, y2 + 8);
		
	}
	
	/**
	 * Represents a 2x2 grid of bits, stored as a byte.
	 * 
	 * @author Aaron Carson
	 * @version Jul 22, 2015
	 */
	public static final class BytePattern
	{
		// full patterns.
		public static final byte	CLEAR				= 0b00_00;
		public static final byte	FULL				= 0b11_11;
		
		// outside corners
		public static final byte	TOP_LEFT			= 0b00_01;
		public static final byte	TOP_RIGHT			= 0b00_10;
		public static final byte	BOTTOM_LEFT			= 0b01_00;
		public static final byte	BOTTOM_RIGHT		= 0b10_00;
		
		// sides
		public static final byte	TOP					= 0b0011;
		public static final byte	BOTTOM				= 0b1100;
		public static final byte	LEFT				= 0b0101;
		public static final byte	RIGHT				= 0b1010;
		
		// inside corners
		public static final byte	INSIDE_TOP_LEFT		= 0b11_10;
		public static final byte	INSIDE_TOP_RIGHT	= 0b11_01;
		public static final byte	INSIDE_BOTTOM_LEFT	= 0b10_11;
		public static final byte	INSIDE_BOTTOM_RIGHT	= 0b01_11;
		
		// diagonals
		public static final byte	DIAGONAL_UP			= 0b01_10;
		public static final byte	DIAGONAL_DOWN		= 0b10_01;
	}
	
	/**
	 * Represents a 2x2 grid of bits, stored as a byte.
	 * 
	 * @author Aaron Carson
	 * @version Jul 22, 2015
	 */
	public static final class Index8x8
	{
		// full pattern.
		public static final int	CENTER_0			= 6;
		public static final int	CENTER_1			= 7;
		public static final int	CENTER_2			= 16;
		public static final int	CENTER_3			= 17;
		
		// outside corners
		public static final int	TOP_LEFT			= 0;
		public static final int	TOP_RIGHT			= 5;
		public static final int	BOTTOM_LEFT			= 50;
		public static final int	BOTTOM_RIGHT		= 55;
		
		// sides
		public static final int	TOP_0				= 2;
		public static final int	TOP_1				= 3;
		public static final int	BOTTOM_0			= 52;
		public static final int	BOTTOM_1			= 53;
		public static final int	LEFT_0				= 20;
		public static final int	LEFT_1				= 30;
		public static final int	RIGHT_0				= 25;
		public static final int	RIGHT_1				= 35;
		
		// inside corners
		public static final int	INSIDE_TOP_LEFT		= 37;
		public static final int	INSIDE_TOP_RIGHT	= 38;
		public static final int	INSIDE_BOTTOM_LEFT	= 47;
		public static final int	INSIDE_BOTTOM_RIGHT	= 48;
		
		//asdf22, 23, 32, 33
		// diagonals
		public static final int	DIAGONAL_BOTTOM_RIGHT	= 22;
		public static final int	DIAGONAL_BOTTOM_LEFT	= 23;
		public static final int	DIAGONAL_TOP_RIGHT		= 32;
		public static final int	DIAGONAL_TOP_LEFT		= 33;
		
	}
	
	/**
	 * Contains indexes for tile positions.
	 * 
	 * @author Aaron Carson
	 * @version Jul 22, 2015
	 */
	public static final class Index16x16
	{
		// full pattern.
		/** Not a valid tile. **/
		public static final int	CLEAR				= -1;
		
		public static final int	FULL				= 3;
		public static final int	FULL_ALT			= 4;
		
		// outside corners
		public static final int	TOP_LEFT			= 0;
		public static final int	TOP_RIGHT			= 2;
		public static final int	BOTTOM_LEFT			= 10;
		public static final int	BOTTOM_RIGHT		= 12;
		
		// sides
		public static final int	TOP					= 1;
		public static final int	BOTTOM				= 5;
		public static final int	LEFT				= 7;
		public static final int	RIGHT				= 11;
		
		// inside corners
		public static final int	INSIDE_TOP_LEFT		= 8;
		public static final int	INSIDE_TOP_RIGHT	= 9;
		public static final int	INSIDE_BOTTOM_LEFT	= 13;
		public static final int	INSIDE_BOTTOM_RIGHT	= 14;
	}
	
	/**
	 * Render the tile at the specified x and y coordinate, based on its
	 * neighboring tiles.
	 * 
	 * @param g The Graphics2D context to render to.
	 * @param cells The array of tiles containing the tile data.
	 * @param cellType The id of the tile being rendered.
	 * @param x The x coordinate of where to render the tile.
	 * @param y The y coordinate of where to render the tile.
	 * @param width The width of the tile grid.
	 * @param height The height of the tile grid.
	 */
	public void renderTile(Graphics2D g, byte[][] cells, int x, int y) {
		int width = cells.length;
		int height = cells[0].length;
		String sPattern = getPattern(cells, cellType, x, y, width, height);
		int iPattern = getIntFromBinaryString(sPattern);
		int x2 = x * T_SIZE_16;
		int y2 = y * T_SIZE_16;
		int tile;
		
		// TODO: add patterns, all 512! Once it's done, it's done.
		// System.out.printf("sPattern: %s iPattern: %d\n", sPattern, iPattern);
		switch (iPattern) {
		
		// ******************************************************************
		// EMPTY
		// ******************************************************************
		// render nothing.
		case Patterns.P_0:
			break;
		
		// ******************************************************************
		// CENTER
		// ******************************************************************
		case Patterns.P_511:
			tile = random.nextBoolean() ? Index16x16.FULL : Index16x16.FULL_ALT;
			tiles_16.render(g, tile, x2, y2);
			break;
		
		// ******************************************************************
		// UNSPECIFIED
		// ******************************************************************
		
		// edge cases: render an unknown edge piece.
		default:
			renderTile8x8(g,cells,x,y,width,height);
			break;
		}
		// src.render(g, SpriteSheet.CAVE_PATH, x * 16, y * 16);
	}
	
	public static void print512Values() {
		char c = '0';
		String s = "0101";
		String format = String.format("%1$9s", s).replace(' ', '0');
		
		int len = (int) Math.pow(2, 9);
		// System.out.println("public class Patterns{\nprivate Patterns(){}\n");
		for (int i = 0; i < len; i++) {
			String pattern = new StringBuffer(String.format("%1$9s",
					Integer.toBinaryString(i)).replace(' ', '0'))
					.insert(8, ',').insert(7, ',').insert(6, '\n')
					.insert(6, ',').insert(5, ',').insert(4, ',')
					.insert(3, '\n').insert(3, ',').insert(2, ',')
					.insert(1, ',').toString();
			System.out.printf("/**\n<code>\n%s\n</code>\n**/\n", pattern);
			System.out
					.printf("public static final int P_%d = getIntFromBinaryString(getStringFromPattern(new byte[]{\n%s\n}));\n\n",
							i, pattern);
		}
		// System.out.println("}");
		
	}
	
	public static void testGetPattern() {
		byte[][] cells = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, };
		byte[][] cells2 = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 0 }, };
		byte cellType = 1;
		int x = 1;
		int y = 1;
		int width = 3;
		int height = 3;
		String sPattern = getPattern(cells2, cellType, x, y, width, height);
		int iPattern = getIntFromBinaryString(sPattern);
		
		// System.out.printf("sPattern: %s iPattern: %d\n", sPattern, iPattern);
	}
	
	public static final int	P_0	= getIntFromBinaryString(getStringFromPattern(
										new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
										1));
	
	public static void printAllConstantDeclarations() {
		byte c = 1;
		String s = getStringFromPattern(
				new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, c);
		System.out.println(s);
	}
	
	// public static void main(String[] args) {
	// print512Values();
	// testGetPattern();
	// printAllConstantDeclarations();
	// }
}
