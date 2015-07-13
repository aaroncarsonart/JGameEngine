package engine;

import engine.mode.ElevationMapMode;
import engine.mode.ExploreMode;
import engine.mode.GameMode;
import engine.mode.LineGraphMode;
import graphics.GameDisplayPanel;
import graphics.GameFrame;
import graphics.GameGraphics;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import terrain.TerrainGenerator;
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
	public static final int FRAME_RATE = 60;
	
	private GameFrame			frame;
	private GameDisplayPanel	displayPanel;
	private GameMode			exploreMode;
	private GameMode			currentMode;
	private GameGraphics		currentGraphics;
	
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
		fpsThrottle = new FpsThrottle(FRAME_RATE);
		System.out.printf("%d fps, maxFrameWait: %d ms\n", fpsThrottle.getFrameRate(), fpsThrottle.getMaximumWait());	
		inputHandler = new InputHandler();
		
		//testExploreMode();
		//testSubdivideWithGraphMode();
		testElevationMapMode();
	}
	
	/**
	 * Use GraphMode for the Game.
	 */
	private void testElevationMapMode(){
		double[][] values = TerrainGenerator.diamondSquareAlgorithm(2, -100, 100, 0.5);
		setGameMode(new ElevationMapMode(values));
	}
	
	/**
	 * Use GraphMode for the Game.
	 */
	private void testSubdivideWithGraphMode(){
		double[] values = TerrainGenerator.testSubdividing();
		setGameMode(new LineGraphMode(values));
	}
	
	/**
	 * Use ExploreMode for the Game.
	 */
	private void testExploreMode(){
		setGameMode(new ExploreMode());
	}

	/**
	 * Set the current GameMode to be used for this game.
	 * @param mode The new GameMode.
	 */
	public void setGameMode(GameMode mode){
		currentMode = mode;
		currentGraphics = mode.getGameGraphics();
		if (frame != null){
			frame.setName(getGameName());
		}
		
	}
	
	/**
	 * Start the Game, initializing all graphics and windows.
	 */
	public void start() {
		try {
			GameFrame.tryAppleLookAndFeel();
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					displayPanel = new GameDisplayPanel(currentMode.getGameGraphics());
					frame = new GameFrame(getGameName());
					//frame.setUndecorated(true);
					frame.addKeyListener(inputHandler);
					frame.getContentPane().add(displayPanel, BorderLayout.CENTER);
					frame.pack();
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
	 * Get the current name of the game (to be displayed in the Frame).
	 * @return The name.
	 */
	public final String getGameName(){
		return NAME + " - " + currentMode.getName();
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
			currentMode.update();
			
			// ******************************************
			// 2. update the graphics
			// ******************************************
			synchronized(currentGraphics.getImageLock()){
				currentGraphics.updateComposite();
			}
			
			// ******************************************
			// 2. ensure frame rate
			// ******************************************
			//fpsCounter.tick();
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
