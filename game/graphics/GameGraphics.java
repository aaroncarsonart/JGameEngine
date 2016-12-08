package game.graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.VolatileImage;
import java.io.Serializable;
import java.util.Random;

/**
 * GameGraphics manages a set of image layers and associated pixel buffers.
 * These are flattened into a composite, which is then drawn to the screen.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class GameGraphics implements Serializable
{
	
	// ********************************************************************
	// Static fields
	// ********************************************************************

	public static final int FULLSCREEN_WIDTH = 1280;
	public static final int FULLSCREEN_HEIGHT = 720;

	private static final long	serialVersionUID	= 1L;
	
	// preferred dimensions: 512 * 288, 426 * 240
	
	public static final int		WIDTH				= 426;//FULLSCREEN_WIDTH / 3; //512; // 256;//384;
															// //(int)(16 * 16 *
															// 2); //24 * 16 ;
															// // 512 px
	public static final int		HEIGHT				=240;// FULLSCREEN_HEIGHT / 3; //288; // 144;//221;
															// //(int)(16 * 9 *
															// 2); //24 * 9 ; //
															// 288 px
	public static final int		SCALE				= 2;
	
	
	// ********************************************************************
	// instance fields
	// ********************************************************************
	
	/** Generate random numbers. **/
	private Random				random;
	
	private BufferedImage		backgroundImage;
	private BufferedImage		spriteImage;
	private BufferedImage		foregroundImage;
	private BufferedImage		lightingImage;
	private BufferedImage		overlayImage;
	
	
	/** For iterating over graphics layers in proper drawing order. **/
	private BufferedImage[]		imageLayers;
	
	/** The composite image of all graphics layers (speeds up scaling). **/
	private VolatileImage		compositeImage;
	private Graphics2D			compositeGraphics;
	
	private final Object		imageLock;
	
	private int[]				backgroundPixels;
	private int[]				spritePixels;
	private int[]				foregroundPixels;
	
	/** Convenience array for iterating over the pixel buffers. **/
	private int[][]				pixelLayers;
	
	/** Used to count updates in the paintComponent(Graphics g) method. **/
	private final int			width;
	private final int			height;
	
	/** Ratio of the width to the height of the image. **/
	private final double		ratio;
	
	// ***********************************************************************
	// constructors
	// ***********************************************************************
	
	/**
	 * Create a new GameGraphics object of default WIDTH and HEIGHT.
	 */
	public GameGraphics() {
		this(WIDTH, HEIGHT);
	}
	
	/**
	 * Create a new GameDisplay instance, which manages a set of BufferedImages
	 * representing the current image of the game.
	 * 
	 * @param width The width of the graphics, in pixels.
	 * @param width The height of the graphics, in pixels.
	 */
	public GameGraphics(int width, int height) {
		this.width = width;
		this.height = height;
		ratio = width / (double) height;
		System.out.printf("Ratio: %f5\n", ratio);
		
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		// initialize game graphics layers
		backgroundImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		spriteImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		foregroundImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		lightingImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		overlayImage = gc.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		imageLayers = new BufferedImage[] {
				backgroundImage,
				spriteImage,
				//foregroundImage, 
				lightingImage, 
				//overlayImage 
				};
		
		// the lock
		imageLock = new Object();
		
		// initialize composite VolatileImage (increases update performance)
		compositeImage = gc.createCompatibleVolatileImage(width, height,
				Transparency.TRANSLUCENT);
		//compositeGraphics = compositeImage.createGraphics();
		//compositeGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		//		RenderingHints.VALUE_ANTIALIAS_ON);
		//compositeGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,
		//		RenderingHints.VALUE_RENDER_QUALITY);
		
		// initialize pixel data buffers
		backgroundPixels = ((DataBufferInt) backgroundImage.getRaster()
				.getDataBuffer()).getData();
		spritePixels = ((DataBufferInt) spriteImage.getRaster().getDataBuffer())
				.getData();
		foregroundPixels = ((DataBufferInt) foregroundImage.getRaster()
				.getDataBuffer()).getData();
		pixelLayers = new int[][] { backgroundPixels, spritePixels,
				foregroundPixels };
		
		// drawBlueSquareFractal(backgroundPixels, width, height);
		// drawGreenSquareFractal(spritePixels, width, height);
		// drawRedSquareFractal(foregroundPixels, width, height);
		
		// other various fields
		random = new Random();
	}
	
	// ***********************************************************************
	// Fractals
	// ***********************************************************************
	
	/**
	 * Draws a blue square fractal to the specified pixel buffer.
	 * 
	 * @param pixels The pixel array to draw to.
	 * @param width The width of the pixel buffer.
	 * @param height The height of the pixel buffer.
	 */
	public void drawBlueSquareFractal(int[] pixels, int width, int height) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x + y * width] = (x ^ y) | 0xff000000;
			}
		}
	}
	
	/**
	 * Draws a green square fractal to the specified pixel buffer.
	 * 
	 * @param pixels The pixel array to draw to.
	 * @param width The width of the pixel buffer.
	 * @param height The height of the pixel buffer.
	 */
	public void drawGreenSquareFractal(int[] pixels, int width, int height) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x + y * width] = (x ^ y) << 8 | 0xff000000;
			}
		}
	}
	
	/**
	 * Draws a red square fractal to the specified pixel buffer.
	 * 
	 * @param pixels The pixel array to draw to.
	 * @param width The width of the pixel buffer.
	 * @param height The height of the pixel buffer.
	 */
	public void drawRedSquareFractal(int[] pixels, int width, int height) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x + y * width] = (x ^ y) << 16 | 0xff000000;
			}
		}
	}
	
	// ***********************************************************************
	// Methods
	// ***********************************************************************
	
	/**
	 * Draw each BufferedImage layer to the GameDisplay.
	 * 
	 * @param g The Graphics2D context to draw to.
	 */
	public void updateComposite() {
		// draw buffered images directly to volatile image
		compositeGraphics = compositeImage.createGraphics();
	    compositeGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		compositeGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
		
		int len = imageLayers.length;
		for (int i = 0; i < len; i++) {
			compositeGraphics.drawImage(imageLayers[i], 0, 0, null);
		}
		
		compositeGraphics.dispose();
	}
	
	// ************************************************************************
	// Getters
	// ************************************************************************
	
	/**
	 * Get the lock for rendering these images (needed for multi-threaded
	 * updates to display).
	 */
	public final Object getImageLock() {
		return imageLock;
	}
	
	/**
	 * @return the backgroundImage
	 */
	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}
	
	/**
	 * @return the SpriteImage
	 */
	public BufferedImage getSpriteImage() {
		return spriteImage;
	}
	
	/**
	 * @return the foregroundImage
	 */
	public BufferedImage getForegroundImage() {
		return foregroundImage;
	}
	
	/**
	 * Get the image used to render lighting effects.
	 * @return
	 */
	public BufferedImage getLightingImage(){
		return lightingImage;
	}
	
	/**
	 * Get the image used to render overlay text and menus.
	 * @return
	 */
	public BufferedImage getOverlayImage(){
		return overlayImage;
	}
	
	/**
	 * @return the imageLayers
	 */
	public BufferedImage[] getImageLayers() {
		return imageLayers;
	}
	
	/**
	 * @return the compositeImage
	 */
	public VolatileImage getCompositeImage() {
		return compositeImage;
	}
	
	/**
	 * @return the compositeGraphics
	 */
	public Graphics2D getCompositeGraphics() {
		return compositeGraphics;
	}
	
	/**
	 * @return the backgroundPixels
	 */
	public int[] getBackgroundPixels() {
		return backgroundPixels;
	}
	
	/**
	 * @return the spritePixels
	 */
	public int[] getSpritePixels() {
		return spritePixels;
	}
	
	/**
	 * @return the foregroundPixels
	 */
	public int[] getForegroundPixels() {
		return foregroundPixels;
	}
	
	/**
	 * @return the pixelLayers
	 */
	public int[][] getPixelLayers() {
		return pixelLayers;
	}
	
	/**
	 * Get the aspect ratio of the game image.
	 * 
	 * @return The aspect ratio (ratio of width to height).
	 */
	public double getAspectRatio() {
		return ratio;
	}
	
	/**
	 * Get the width of the game image, in pixels (this value is constant).
	 * 
	 * @return The width of the game image, in pixels.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the game image, in pixels (this value is constant).
	 * 
	 * @return The height of the game image, in pixels.
	 */
	public int getHeight() {
		return height;
	}
	
	
	
	public static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration();
	}
}
