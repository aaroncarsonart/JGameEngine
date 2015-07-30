package game.graphics;

import game.engine.Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * Game Window is the JFrame that holds the GameDisplay. It scales the display
 * of the game image that is being rendered by the Game.
 * 
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class GameFrame extends JFrame
{
	public static final boolean	BEGIN_FULLSCREEN	= false;
	private static final long	serialVersionUID	= 1L;
	private Container			contentPane;
	private JMenuBar			menuBar;
	private String				applicationName;
	private static GameFrame	CURRENT_FRAME;
	
	/**
	 * Create a new instance of a GameWindow.
	 */
	public GameFrame(String applicationName) {
		super(applicationName);
		GameFrame.CURRENT_FRAME = this;
		this.setLayout(new BorderLayout());
		this.applicationName = applicationName;
		// this.setCursor(getToolkit().createCustomCursor(new
		// BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(0,0),null));
		
		// menu
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// file menu items
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask()));
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);
		
		// View menu items
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		fullscreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fullscreenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display();
			}
		});
		viewMenu.add(fullscreenItem);
		
	}
	
	public JMenuItem	fullscreenItem	= new JCheckBoxMenuItem("Full Screen",
												BEGIN_FULLSCREEN);
	
	/**
	 * Toggle Fullscreen.
	 */
	public static void toggleFullscreen() {
		if (GameFrame.CURRENT_FRAME != null) {
			boolean b = GameFrame.CURRENT_FRAME.fullscreenItem.isSelected();
			GameFrame.CURRENT_FRAME.fullscreenItem.setSelected(!b);
			GameFrame.CURRENT_FRAME.display();
		}
	}
	
	public void display() {
		if (fullscreenItem.isSelected()) {
			setToFullscreen();
		}
		else {
			setToWindowMode();
		}
	}
	
	/**
	 * Activate fullscreen mode.
	 */
	public void setToFullscreen() {
		GraphicsDevice gd = this.getGraphicsConfiguration().getDevice();// getCurrentDevice();
		if (gd.isFullScreenSupported()) {
			System.out.println("Fullscreen mode supported");
			this.dispose();
			this.setUndecorated(true);
			this.setAlwaysOnTop(true);
			// this.getJMenuBar().setVisible(false);
			gd.setFullScreenWindow(this);
			this.setVisible(true);
			hideCursor();
			
		}
		else {
			System.out.println("Fullscreen not supported, maximizing.");
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
	

	
	/**
	 * Helper method to hide the cursor.
	 */
	private void hideCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		this.setCursor(blankCursor);
		
	}
	
	/**
	 * Helper method to show the cursor.
	 */
	private void showCursor() {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * switch to windowed mode.
	 */
	public void setToWindowMode() {
		this.dispose();
		this.setUndecorated(false);
		this.setAlwaysOnTop(false);
		this.getJMenuBar().setVisible(true);
		pack();
		
		// center window first time this is ran
		if (firstTime) {
			this.setLocationRelativeTo(null);
			firstTime = false;
		}
		this.setVisible(true);
		showCursor();
	}
	
	// used 
	private boolean	firstTime	= true;
	
	public static void tryAppleLookAndFeel() {
		// set look and feel
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name",
					Game.NAME);
			// System.setProperty("apple.awt.fullscreenhidecursor","true");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the current GraphicsDevice by measuring the area of any Device this
	 * JFrame overlaps.
	 * 
	 * @return
	 *
	 *         private GraphicsDevice getCurrentDevice() { GraphicsEnvironment
	 *         ge = GraphicsEnvironment .getLocalGraphicsEnvironment();
	 *         GraphicsDevice[] gd = ge.getScreenDevices(); Rectangle
	 *         frameBounds = this.getBounds(); List<GraphicsDevice> devices =
	 *         new ArrayList<>(gd.length);
	 * 
	 *         // 1. get intersecting devices for (int i = 0; i < gd.length;
	 *         i++) { GraphicsDevice device = gd[i]; GraphicsConfiguration gc =
	 *         device.getDefaultConfiguration(); Rectangle deviceBounds =
	 *         gc.getBounds(); if (frameBounds.intersects(deviceBounds)) {
	 *         devices.add(device); } }
	 * 
	 *         // 2. get device with greatest area. double maxArea = 0;
	 *         GraphicsDevice maxDevice = null; for (int i = 0; i <
	 *         devices.size(); i++) { GraphicsDevice device = devices.get(i);
	 *         GraphicsConfiguration gc = device.getDefaultConfiguration();
	 *         Rectangle deviceBounds = gc.getBounds(); Rectangle2D intersection
	 *         = frameBounds .createIntersection(deviceBounds); double area =
	 *         intersection.getWidth() * intersection.getHeight(); if (area >
	 *         maxArea) { maxArea = area; maxDevice = device; } }
	 * 
	 *         // error checking for when not found. if (maxDevice == null) {
	 *         System.out.println("cannot find current window, use defuault.");
	 *         maxDevice = ge.getDefaultScreenDevice(); } return maxDevice; }
	 */
}
