package game.graphics;

import game.menu.Message;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts
{
	//public static Font	DELUXE_FONT;
	//public static Font	KONG_FONT;
	//public static Font	MANASPACE_FONT;
	//public static Font	PRESS_START_2P_FONT;
	//public static Font	PRESS_START_FONT;
	//public static Font	PRESS_START_K_FONT;
	//public static Font	RETURN_OF_GANON_FONT;
	public static Font	PROFONT;
	//public static Font	PROFONT = new Font("ProFont", Font.PLAIN, 9);
	//public static Font	PROFONT_X = new Font("ProFontX", Font.PLAIN, 9);
	//public static final String	FONT_NAME	= "ProFont";
	public static Font	FONT;
	
	static {
		try {
			//DELUXE_FONT = loadFont("/res/fonts/dlxfont.ttf", 8);
			//KONG_FONT = loadFont("/res/fonts/kongtext.ttf", 8);
			//MANASPACE_FONT = loadFont("/res/fonts/manaspc.ttf", 12);
			//PRESS_START_2P_FONT = loadFont("/res/fonts/PressStart2P.ttf", 8);
			//PRESS_START_FONT = loadFont("/res/fonts/prstart.ttf", 16);
			//PRESS_START_K_FONT = loadFont("/res/fonts/prstartk.ttf", 8);
			//RETURN_OF_GANON_FONT = loadFont("/res/fonts/retganon.ttf", 16);
			//PROFONT = loadFont(Font.TYPE1_FONT, "/res/fonts/profont-2.2-charmap.png", 9);
			//PROFONT = loadFont(Font.TYPE1_FONT, "/res/fonts/jbpr08.fnt", 8);
			PROFONT = loadFont("/res/fonts/ProFont.ttf", 9);
			//PROFONT = new Font("ProFont", Font.PLAIN, 9);
			FONT = PROFONT;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load a font, registers it with the graphics environment, and returns it.
	 * 
	 * @param filename The local filename and path to the font file.
	 * @param size The intended size of the font.
	 * @return A new font.
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private static Font loadFont(String filename, int size) throws IOException,
			FontFormatException {
		return loadFont(Font.TRUETYPE_FONT, filename, size);
	}
	
	
	/**
	 * Load a font, registers it with the graphics environment, and returns it.
	 * 
	 * @param fontFormat Font.TRUETYPE_FONT or TYPE1_FONT
	 * @param filename The local filename and path to the font file.
	 * @param size The intended size of the font.
	 * @return A new font.
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private static Font loadFont(int fontFormat, String filename, int size) throws IOException,
			FontFormatException {
		Font font = Font.createFont(fontFormat,
				Font.class.getResourceAsStream(filename)).deriveFont(
				Font.PLAIN, size);
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		System.out.printf("loaded font %s\n", font.getName());
		return font;
	}
	
	
	
}
