package engine;

/**
 * Game mode controls how the game functions.  Each GameMode has it's own 
 * state updated each frame, draws unique graphics, and handles Commands 
 * differently.
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public interface GameMode
{	
	public void update();
	public void updateGraphics();
	
}
