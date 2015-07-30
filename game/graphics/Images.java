package game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Holds a reference to many Images, and has a convenience method for loading
 * them.
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class Images
{	
	public static final BufferedImage LARGE_SHADOW = loadImageFrom("/res/images/shadow.png");
	public static final BufferedImage SHADOW_128 = loadImageFrom("/res/images/shadow_128.png");
	public static final BufferedImage SHADOW_128_FAINT = loadImageFrom("/res/images/shadow_128_faint.png");
	public static final BufferedImage SHADOW_512 = loadImageFrom("/res/images/shadow_512.png");
	public static final BufferedImage SHADOW_288 = loadImageFrom("/res/images/shadow_288.png");
	public static final BufferedImage SHADOW_PLAYER_MASK = loadImageFrom("/res/images/shadow_mask_player.png");
	public static final BufferedImage CAVERN_WALL_BORDER = loadImageFrom("/res/images/cavern_wall_border.png");
	public static final BufferedImage CAVERN_PATH_BORDER = loadImageFrom("/res/images/cavern_path_border.png");
	public static final BufferedImage MENU_BORDER = loadImageFrom("/res/images/WindowBorder8x8.png");
	public static final BufferedImage ANIMATIONS = loadImageFrom("/res/images/animations.png");

	/**
	 * Loads an image.
	 * @param filename The name for the file to load.
	 * @return A BuffedImage.
	 */
	public static BufferedImage loadImageFrom(String filename) {
		try {
			//System.out.println("filename: " + filename);
			//String resource = Images.class.getResource(filename).toString();
			//System.out.println("resource: " + resource);			
			BufferedImage png = ImageIO.read(Images.class.getResource(filename));
			return png;
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
