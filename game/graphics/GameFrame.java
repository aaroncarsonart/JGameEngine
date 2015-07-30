package game.graphics;

import game.engine.Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
	public static final boolean BEGIN_FULLSCREEN = false;
	private static final long	serialVersionUID	= 1L;
	private Container			contentPane;
	private JMenuBar			menuBar;
	private JMenu				menu;
	
	private String				applicationName;
	
	/**
	 * Create a new instance of a GameWindow.
	 */
	public GameFrame(String applicationName) {
		super(applicationName);
		this.setLayout(new BorderLayout());
		this.applicationName = applicationName;
		//this.setCursor(getToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(0,0),null));
		
		// menu
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// file menu items
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);

		// View menu items
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);		
		fullscreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fullscreenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display();
			}
		});
		viewMenu.add(fullscreenItem);
	
	}
	
	public JMenuItem fullscreenItem = new JCheckBoxMenuItem("Full Screen", BEGIN_FULLSCREEN);
	
	/**
	 * Toggle the state of Fullscreen.
	 */
	public void toggleFullscreen(){
		fullscreenItem.setSelected(!fullscreenItem.isSelected());
		display();
	}
	
	public void display(){
		if(fullscreenItem.isSelected()){
			setToFullscreen();
		}
		else{
			setToWindowMode();			
		}
	}
	
	/**
	 * Activate fullscreen mode.
	 */
	public void setToFullscreen(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (gd.isFullScreenSupported()){
			GameFrame.this.dispose();
			//GameFrame.this.setUndecorated(true);
			gd.setFullScreenWindow(GameFrame.this);
			GameFrame.this.setVisible(true);
			
			// hide cursor
			// Transparent 16 x 16 pixel cursor image.
			//BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

			// Create a new blank cursor.
			//Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			//GameFrame.this.setCursor(blankCursor);
			//Cursor c = getToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(0,0),null);
			//System.out.println(c == null);
			//GameFrame.this.setCursor(c);
		}
		else{
			GameFrame.this.setExtendedState(JFrame.MAXIMIZED_BOTH);			
		}
	}
	
	/**
	 * switch to windowed mode.
	 */
	public void setToWindowMode(){
		GameFrame.this.dispose();
		GameFrame.this.setUndecorated(false);
		pack();
		setLocationRelativeTo(null);
		GameFrame.this.setVisible(true);
	}
	
	public static void tryAppleLookAndFeel() {
		// set look and feel
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", Game.NAME);
			//System.setProperty("apple.awt.fullscreenhidecursor","true");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}