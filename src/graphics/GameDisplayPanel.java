package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	// Fields
	// ********************************************************************
	
	private VolatileImage	compositeImage;
	private Graphics2D		compositeGraphics;
	private int				width	= WIDTH;
	private int				height	= HEIGHT;
	private double			ratio	= width / (double) height;
	
	// ********************************************************************
	// Constructor
	// ********************************************************************
	
	/**
	 * Create a new Render Area of the specified size.
	 * 
	 * @param width The width, in pixels.
	 * @param height The height, in pixels.
	 */
	public GameDisplayPanel() {
		super();
		setPreferredSize(new Dimension(width, height));
		
		setSize(getPreferredSize());
		// setBackground(Color.BLACK);
		
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		compositeImage = gc.createCompatibleVolatileImage(width, height,
				Transparency.TRANSLUCENT);
		compositeGraphics = compositeImage.createGraphics();
		compositeGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		compositeGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
	}
	
	// ********************************************************************
	// Methods
	// ********************************************************************
	
	/**
	 * PaintComponent updates the component by drawing the data of this
	 * RenderArea's BufferedImage directly to the component.
	 * <p>
	 * Note 1: overwrite paint(Graphics g) to increase performance, but no added
	 * components are drawn.
	 * <p>
	 * Note 2: overwrite paintComponent(Graphics g) to draw all extra layers.
	 * 
	 * @see javax.swing.JComponent#paintComponent(Graphics g)
	 */
	// public void paint(Graphics g) {
	// super.paint(g);
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}
	
	/**
	 * Called by paintComponent(Graphics g) to update this FractalMaker. This
	 * method needs to draw something to the Graphics2D parameter, or nothing
	 * will show.
	 * <p>
	 * For experimentation purposes, change which methods are called in the
	 * first section of the method to view different fractals in the GUI.
	 * 
	 * @param g The Graphics2D object to update.
	 */
	public void render(Graphics2D g) {
		
		// the current width and height of the window.
		int pw = getWidth();
		int ph = getHeight();
		
		// set the background black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, pw, ph);
		
		// update and draw the composite graphics.
		
		int dLeft, dRight, dTop, dBottom;
		
		// if wider than tall, calculate width first
		if (pw > ph) {
			int dWidth = Math.min(pw, (int) (ph * ratio));
			dLeft = (pw - dWidth) / 2;
			dRight = (pw + dWidth) / 2;
			
			int dHeight = Math.min(ph, (int) (pw / ratio));
			dTop = (ph - dHeight) / 2;
			dBottom = (ph + dHeight) / 2;
		}
		// if taller than wide
		else {
			dLeft = 0;
			dRight = pw;
			int dHeight = (int) (pw / ratio);
			dTop = (ph - dHeight) / 2;
			dBottom = (ph + dHeight) / 2;
			
		}
		System.out
				.printf("panelWidth: %d panelHeight: %d dLeft: %d dRight: %d dTop: %d dBottom: %d\n",
						pw, ph, dLeft, dRight, dTop, dBottom);
		
		// g.drawImage(compositeImage, 0, 0, panelWidth, panelHeight, 0, 0,
		// width, height, null);
		g.drawImage(compositeImage, dLeft, dTop, dRight, dBottom, 0, 0, width,
				height, null);
		repaint();
	}
	
	// *******************************************************************
	// Static Testing Methods
	// *******************************************************************
	
	/**
	 * Create a new DrawPanel that renders teh given DrawObject.
	 * 
	 * @param drawObject The object to draw in the panel.
	 */
	public static void createDisplay() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameDisplayPanel panel = new GameDisplayPanel();
				JFrame frame = new JFrame("RenderArea Test");
				frame.getContentPane().add(panel, BorderLayout.CENTER);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
	
	// *******************************************************************
	// MAIN
	// *******************************************************************
	
	/**
	 * Test the render area in a JFrame. Run this method to view the results
	 * using this code only.
	 * 
	 * @param args Does nothing.
	 */
	public static void main(String[] args) {
		createDisplay();
	}
	
}
