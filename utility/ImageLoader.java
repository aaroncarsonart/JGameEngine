package utility;

import graphics.WalkingSprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Simple class to wrap static methods in.
 * @author Aaron Carson
 * @version Jul 19, 2015
 */
public class ImageLoader
{	
	/**
	 * Loads an image and converts it to a BufferedImage.TYPE_INT_ARGB.
	 * @param filename The name for the file to load.
	 * @return A BuffedImage.
	 */
	public static BufferedImage loadImageFrom(String filename) {
		try {
			System.out.println("filename: " + filename);
			String resource = ImageLoader.class.getResource(filename).toString();
			System.out.println("resource: " + resource);
			
			BufferedImage png = ImageIO.read(WalkingSprite.class.getResource(filename));
			return png;
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
