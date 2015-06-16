package graphics;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * Game Window is the JFrame that holds the GameDisplay.  It scales the display
 * of the game image that is being rendered by the Game.
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class GameFrame extends JFrame
{	
	private static final long serialVersionUID = 1L;
	private Container contentPane;
	private JMenuBar menuBar;
	private JMenu menu;	
	
	/**
	 * Create a new instance of a GameWindow.
	 */
	public GameFrame(){
		super("Aaron's Game");
		contentPane = this.getContentPane();
	}
		
		
	
}
