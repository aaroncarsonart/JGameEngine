package game.graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import utility.FpsCounter;
import utility.FpsThrottle;

/**
 * Holds all data needed to render a Game to a GameWindow. This includes Three
 * BufferedImage layers (background, sprites, and foreground), their associated
 * pixel buffers, and a composite image that scales quickly for rendering on
 * this GameDisplay.
 * 
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class GameDisplayPanel extends JPanel
{
	
	// ********************************************************************
	// Static fields
	// ********************************************************************
	
	private static final long	serialVersionUID	= 1L;
	
	// ********************************************************************
	// instance fields
	// ********************************************************************
	
	/** Used to count updates in the paintComponent(Graphics g) method. **/
	private FpsCounter			fpsCounter;
	private FpsThrottle			fpsThrottle;
	private GameGraphics		currentGraphics;
	private VolatileImage		compositeImage;
	private int width;
	private int height;
	private double ratio;
	//private Graphics2D			compositeGraphics;
		
	// ***********************************************************************
	// constructors
	// ***********************************************************************
	
	/**
	 * Create a new GameDisplay instance, which manages a set of BufferedImages
	 * representing the current image of the game.
	 */
	public GameDisplayPanel(GameGraphics initialGameGraphics) {
		super();
		setGameGraphics(initialGameGraphics);
		fpsCounter = new FpsCounter(this);
		fpsThrottle = new FpsThrottle(60);
		
		// Transparent 16 x 16 pixel cursor image.
		//BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		//Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		//this.setCursor(blankCursor);
	}
		
	// ***********************************************************************
	// Methods
	// ***********************************************************************
	
	/**
	 * Set the game graphics to be used with this object.
	 * @param currentGraphics
	 */
	public void setGameGraphics(GameGraphics newGameGraphics){
		this.currentGraphics = newGameGraphics;
		this.compositeImage = currentGraphics.getCompositeImage();
		this.width = currentGraphics.getWidth();
		this.height = currentGraphics.getHeight();
		this.ratio = currentGraphics.getAspectRatio();
		//this.setPreferredSize(new Dimension(Math.max(500, width), Math.max(500, height)));
		//this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width * GameGraphics.SCALE, height * GameGraphics.SCALE));
		//this.compositeGraphics = currentGraphics.getCompositeGraphics();
	}
	
	/**
	 * Renders the compositeImage to the GameDisplayPanel, centered with a black
	 * border at a maximum scale while preserving the aspect ratio.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// the current width and height of the window.
		int pw = getWidth();
		int ph = getHeight();
		
		// *************************
		// Set the background black.
		// *************************
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, pw, ph);
		
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
		else {
			dLeft = 0;
			dRight = pw;
			int dHeight = (int) (pw / ratio);
			dTop = (ph - dHeight) / 2;
			dBottom = (ph + dHeight) / 2;
		}
		
		// *********************************
		// draw the Composite, synchronized.
		// *********************************
		synchronized (currentGraphics.getImageLock()) {
			((Graphics2D) g).drawImage(compositeImage, dLeft, dTop, dRight,
					dBottom, 0, 0, width, height, null);
		}
		// force update of the GameDisplay.
		repaint();
		//fpsCounter.tick();
		fpsThrottle.throttle();
	}
	
	/**
	 * @return the compositeImage
	 */
	public VolatileImage getCompositeImage() {
		return compositeImage;
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
	 * Get the width of the game image, in pixels (this value is constant). For
	 * the GameDisplayPanel width, use getWidth().
	 * 
	 * @return The width of the game image, in pixels.
	 */
	public int getGameWidth() {
		return width;
	}
	
	/**
	 * Get the height of the game image, in pixels (this value is constant). For
	 * the GameDisplayPanel height, use getHeight().
	 * 
	 * @return The height of the game image, in pixels.
	 */
	public int getGameHeight() {
		return height;
	}
}
