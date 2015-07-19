package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import engine.Game;

/**
 * Game Window is the JFrame that holds the GameDisplay. It scales the display
 * of the game image that is being rendered by the Game.
 * 
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class GameFrame extends JFrame
{
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
		JCheckBoxMenuItem fullscreenItem = new JCheckBoxMenuItem("Full Screen", false);
		fullscreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fullscreenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fullscreenItem.isSelected()){
					GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
					if (gd.isFullScreenSupported()){
						GameFrame.this.dispose();
						//GameFrame.this.setUndecorated(true);
						gd.setFullScreenWindow(GameFrame.this);
						GameFrame.this.setVisible(true);
						
						// hide cursor
						// Transparent 16 x 16 pixel cursor image.
						BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

						// Create a new blank cursor.
						Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
						    cursorImg, new Point(0, 0), "blank cursor");

						// Set the blank cursor to the JFrame.
						GameFrame.this.setCursor(blankCursor);
					}
					else{
						GameFrame.this.setExtendedState(JFrame.MAXIMIZED_BOTH);			
					}
				}
				else{
					GameFrame.this.dispose();
					GameFrame.this.setUndecorated(false);
					pack();
					setLocationRelativeTo(null);
					GameFrame.this.setVisible(true);
					
				}
			}
		});
		viewMenu.add(fullscreenItem);
	
	}
	
	public static void tryAppleLookAndFeel() {
		// set look and feel
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", Game.NAME);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
