package engine;

import graphics.GameDisplayPanel;
import graphics.GameFrame;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utility.TpsCounter;

/**
 * Game is the engine that updates the game state.
 * 
 * @author Aaron Carson
 * @version Jun 15, 2015
 */
public class Game implements Runnable, Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private GameDisplayPanel	displayPanel;
	private JFrame				frame;
	
	private boolean				running;
	private Thread				gameThread;
	private TpsCounter			tps;
	
	private long				prevUpdate;
	
	private int					fps;
	private int                 fpsWaitMillis;
	
	/**
	 * Create a new Game.
	 */
	public Game() {
		running = false;
		tps = new TpsCounter(this);
		prevUpdate = System.currentTimeMillis();
		fps = 60;
		fpsWaitMillis = 1000 / fps;
		System.out.printf("%d fps, maxFrameWait: %d ms\n", fps, fpsWaitMillis);
	}
	
	/**
	 * Start the Game, initializing all graphics and windows.
	 */
	public void start() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					//frame.getContentPane().add(displayPanel, BorderLayout.CENTER);
					displayPanel = new GameDisplayPanel();
					
					frame = new JFrame();
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
			tps.tick();
			
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
			try {
				Thread.sleep(Math.max(0, fpsWaitMillis - (System.currentTimeMillis() - prevUpdate)));
				prevUpdate = System.currentTimeMillis();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
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
