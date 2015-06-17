package engine;

import graphics.GameDisplayPanel;
import graphics.GameFrame;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import sound.SoundEffects;
import utility.FpsCounter;
import utility.FpsThrottle;

/**
 * Game is the engine that updates the game state.
 * 
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class Game implements Runnable, Serializable
{
	private static final long	serialVersionUID	= 1L;
	public static final String	NAME = "JGameEngine";
	
	private GameDisplayPanel	displayPanel;
	private GameFrame			frame;
	
	private boolean				running;
	private Thread				gameThread;
	private FpsCounter			fpsCounter;
	private FpsThrottle			fpsThrottle;
	private InputHandler		inputHandler;
	
	/**
	 * Create a new Game.
	 */
	public Game() {
		running = false;
		fpsCounter = new FpsCounter(this);
		fpsThrottle = new FpsThrottle(60);
		System.out.printf("%d fps, maxFrameWait: %d ms\n", fpsThrottle.getFrameRate(), fpsThrottle.getMaximumWait());
		
		inputHandler = new InputHandler();
	}
	
	/**
	 * Start the Game, initializing all graphics and windows.
	 */
	public void start() {
		try {
			GameFrame.tryAppleLookAndFeel();
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					displayPanel = new GameDisplayPanel();
					frame = new GameFrame(NAME);
					frame.addKeyListener(inputHandler);
					frame.getContentPane().add(displayPanel, BorderLayout.CENTER);
					frame.pack();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			});
			
			// begin the game.
			resume();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Update and manage the entire game state.
	 */
	@Override
	public void run() {
		running = true;
		while (running) {
			
			// ******************************************
			// 1. update the game state
			// ******************************************
			update();
			
			// ******************************************
			// 2. update the graphics
			// ******************************************
			synchronized(displayPanel.getImageLock()){
				updateGraphics();
			}
			
			// ******************************************
			// 2. ensure frame rate
			// ******************************************
			fpsCounter.tick();
			fpsThrottle.throttle();
		}
	}
	
	/**
	 * Update the game state.  Everything that manages the game state must be
	 * called here.
	 */
	public void update(){
		
	}
	
	/**
	 * Specialty method that limits what must be synchronized on the 
	 * graphics update.
	 */
	public void updateGraphics(){
		displayPanel.updateCompositeGraphics();		
	}
	
	/**
	 * Pause the game updates.
	 */
	public void pause() {
		running = false;
	}
	
	/**
	 * Pause the game updates.
	 */
	public void resume() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
}
