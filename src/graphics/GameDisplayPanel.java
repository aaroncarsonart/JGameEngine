package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.VolatileImage;
import java.util.Random;

import javax.swing.JPanel;

import utility.TpsCounter;

/**
 * Holds all data needed to render a Game to a GameWindow.  This includes
 * Three BufferedImage layers (background, sprites, and foreground), their
 * associated pixel buffers, and a composite image that scales quickly 
 * for rendering on this GameDisplay.
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class GameDisplayPanel extends JPanel
{
	
	// ********************************************************************
	// Static fields
	// ********************************************************************
	
	private static final long	serialVersionUID	= 1L;
	public static final int		WIDTH				= 16 * 16 * 2;	// 512 px
	public static final int		HEIGHT				= 16 * 9 * 2;	// 288 px
	public static final int		SCALE				= 1;
																	
	// ********************************************************************
	// instance fields
	// ********************************************************************
	
	/** Generate random numbers. **/
	private Random				random;
	
	private BufferedImage		backgroundImage;
	private BufferedImage		spriteImage;
	private BufferedImage		foregroundImage;
	
	/**For iterating over graphics layers in proper drawing order. **/
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
	private TpsCounter			tps;
	
	private int width;
	private int height;
	
	/** Ratio of the width to the height of the image. **/
	private double ratio;
	
	// ***********************************************************************
	// constructors
	// ***********************************************************************
	
	/**
	 * Create a new GameDisplay instance, which manages a set of BufferedImages
	 * representing the current image of the game.
	 */
	public GameDisplayPanel() {
		super();
		width = WIDTH;
		height = HEIGHT;
		this.setPreferredSize(new Dimension(width * SCALE, height * SCALE));
		ratio = width / (double) height;
		System.out.printf("Ratio: %f5\n",ratio);
				
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		// initialize game graphics layers
		backgroundImage = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		spriteImage    = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		foregroundImage = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		imageLayers = new BufferedImage[] {backgroundImage, spriteImage, foregroundImage};	
				
		// the lock
		imageLock = new Object();
		
		// initialize composite VolatileImage (increases update performance)
		compositeImage = gc.createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);
		compositeGraphics = compositeImage.createGraphics();
		compositeGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		compositeGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	
		// initialize pixel data buffers
		backgroundPixels = ((DataBufferInt) backgroundImage.getRaster().getDataBuffer()).getData();
		spritePixels = ((DataBufferInt) spriteImage.getRaster().getDataBuffer()).getData();
		foregroundPixels = ((DataBufferInt) foregroundImage.getRaster().getDataBuffer()).getData();
		pixelLayers = new int[][]{backgroundPixels, spritePixels, foregroundPixels};

		drawBlueSquareFractal(backgroundPixels, width, height);
		drawGreenSquareFractal(spritePixels, width, height);
		drawRedSquareFractal(foregroundPixels, width, height);
		
		// other various fields
		random = new Random();
		tps = new TpsCounter(this);
	}

	// ***********************************************************************
	// Fractals
	// ***********************************************************************
	
	/**
	 * Draws a blue square fractal to the specified pixel buffer.
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
	 * Renders the compositeImage to the GameDisplayPanel, centered with a
	 * black border at a maximum scale while preserving the aspect ratio.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		tps.tick();
		
		// the current width and height of the window.
		int pw = getWidth();
		int ph = getHeight();
		
		// *************************
		// Set the background black.
		// *************************
	    g.setColor(Color.BLACK);
	    g.fillRect(0,0, pw, ph);
	    
	    // update and draw the composite graphics.
	    
	    int dLeft, dRight, dTop, dBottom;
	    
	    // ***************************************************
	    // CASE 1: if wider than tall, calculate width first
	    // ***************************************************
	    if (pw > ph) {	    
	    	int dWidth = Math.min(pw, (int) (ph * ratio));
	    	dLeft = (pw - dWidth) / 2;
	    	dRight = (pw + dWidth) / 2;
	    	
	    	int dHeight = Math.min(ph, (int) (pw / ratio));	    	
	    	dTop = (ph - dHeight) / 2;
	    	dBottom = (ph + dHeight) / 2;
	    }
	    // ***************************************************
	    // CASE 2: if taller than wide
	    // ***************************************************
	    else{
	    	dLeft = 0;
	    	dRight = pw;
	    	int dHeight = (int) (pw / ratio);
	    	dTop = (ph - dHeight) / 2;
	    	dBottom = (ph + dHeight) / 2;	    	
	    }
	    
		// *********************************
	    // draw the Composite, synchronized.
		// *********************************
		synchronized(imageLock){
			((Graphics2D)g).drawImage(compositeImage, dLeft, dTop, dRight, dBottom, 0, 0, width, height, null);
		}
		// force update of the GameDisplay.
		repaint();
	}
	
	/**
	 * Draw each BufferedImage layer to the GameDisplay.
	 * @param g The Graphics2D context to draw to.
	 */
	public void updateCompositeGraphics(){
		// draw buffered images directly to volatile image
		int len = imageLayers.length;
		for(int i = 0; i < len; i++){
			compositeGraphics.drawImage(imageLayers[i], 0, 0, null);
		}		
	}
	
	// ************************************************************************
	// Getters
	// ************************************************************************
	
	/**
	 * Get the lock for rendering these images (needed for multi-threaded
	 * updates to display).
	 */
	public final Object getImageLock(){
		return imageLock;
	}

	/**
	 * @return the backgroundImage
	 */
	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @return the foregroundImage
	 */
	public BufferedImage getForegroundImage() {
		return foregroundImage;
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
	 * @return the SpriteImage
	 */
	public BufferedImage getSpriteImage() {
		return spriteImage;
	}
	
	/**
	 * Get the aspect ratio of the game image.
	 * @return The aspect ratio (ratio of width to height).
	 */
	public double getAspectRatio(){
		return ratio;
	}
	
	
	/**
	 * Get the width of the game image, in pixels (this value is constant).
	 * For the GameDisplayPanel width, use getWidth().
	 * @return The width of the game image, in pixels.
	 */	public int getGameWidth(){
		return width;
	}
	
	/**
	 * Get the height of the game image, in pixels (this value is constant).
	 * For the GameDisplayPanel height, use getHeight().
	 * @return The height of the game image, in pixels.
	 */
	public int getGameHeight(){
		return height;
	}
}
