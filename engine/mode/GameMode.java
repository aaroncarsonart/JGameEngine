package engine.mode;

import graphics.GameGraphics;

/**
 * Game mode controls how the game functions.  Each GameMode has it's own 
 * state updated each frame, draws unique graphics, and handles Commands 
 * differently.
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public abstract class GameMode
{	
	/**
	 * The GameGraphics for this GameMode.  Won't be rendered to the screen
	 * unless this is set as the currentGameMode in Game.
	 */
	protected GameGraphics graphics;
	
	private String name;
	
	/**
	 * Create a new GameMode.
	 * @param name the name of the game mode.
	 */
	public GameMode(String name){
		this.name = name;
		graphics = new GameGraphics();
	}
	
	/**
	 * Create a new GameMode, with the given name and graphics.
	 * @param name
	 * @param graphics
	 */
	public GameMode(String name, GameGraphics graphics){
		this.name = name;
		this.graphics = graphics;
	}
	
	/**
	 * Get the GameGraphics of this GameMode.
	 * @return The GameGraphics
	 */
	public GameGraphics getGameGraphics(){
		return graphics;
	}
	
	/**
	 * Get the name of this GameMode.
	 * @return
	 */
	public final String getName(){
		return name;
	}
		
	/**
	 * Update the game state.  This is called each time the Game updates.
	 */
	public abstract void update();
	
	public abstract void updateGraphics();

}
