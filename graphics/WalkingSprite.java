package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A WalkingSprite represents a walking animation used by a player or enemy.
 * All WalkingSprites use 4x4 SpriteSheets to cycle through the poses.
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public class WalkingSprite extends Sprite
{
	/** Path to the image file for a male warrior walk-cycle. */
	public static final String MALE_WARRIOR = "/res/images/male_warrior_spritesheet.png";
	
	/** Path to the image file for a female warrior walk-cycle. */
	public static final String FEMALE_WARRIOR = "/res/images/female_warrior_spritesheet.png";
	
	/**
	 * Create a new WalkingSprite.
	 * @param path The path to the image to load the graphics from.
	 */
	public WalkingSprite(String path) {
		super(loadImageFrom(path), 16, 4, 4);
	}		
	
	/**
	 * Loads an image and converts it to a BufferedImage.TYPE_INT_ARGB.
	 * @param filename The name for the file to load.
	 * @return A BuffedImage.
	 */
	public static BufferedImage loadImageFrom(String filename) {
		try {
			System.out.println("filename: " + filename);
			String resource = WalkingSprite.class.getResource(filename).toString();
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
