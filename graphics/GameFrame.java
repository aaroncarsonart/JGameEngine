package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.VK_CONTROL));
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);
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
