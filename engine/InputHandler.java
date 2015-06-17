package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles inputs for the Game, and reports results in Commands.
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class InputHandler implements KeyListener
{
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		setCommandPressedFrom(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		setCommandPressedFrom(e.getKeyCode(), false);
	}
	
	/**
	 * Switches over a set of key codes, and if one matches it sets the
	 * associated Command's pressed value.
	 * @param e The KeyEvent from which to get the keycode.
	 * @param pressed
	 */
	private void setCommandPressedFrom(int keycode, boolean pressed){
		switch (keycode) {
		
		// *******************************************
		// UP
		// *******************************************
		case KeyEvent.VK_UP:
			Command.UP.setPressed(pressed);
			break;
		case KeyEvent.VK_W:
			Command.UP.setPressed(pressed);
			break;
		
		// *******************************************
		// DOWN
		// *******************************************
		case KeyEvent.VK_DOWN:
			Command.DOWN.setPressed(pressed);
			break;
		case KeyEvent.VK_S:
			Command.DOWN.setPressed(pressed);
			break;
		
		// *******************************************
		// LEFT
		// *******************************************
		case KeyEvent.VK_LEFT:
			Command.LEFT.setPressed(pressed);
			break;
		
		case KeyEvent.VK_A:
			Command.LEFT.setPressed(pressed);
			break;
		
		// *******************************************
		// RIGHT
		// *******************************************
		case KeyEvent.VK_RIGHT:
			Command.RIGHT.setPressed(pressed);
			break;
		case KeyEvent.VK_D:
			Command.RIGHT.setPressed(pressed);
			break;
		
		// *******************************************
		// OK
		// *******************************************
		case KeyEvent.VK_ENTER:
			Command.OK.setPressed(pressed);
			break;
		
		case KeyEvent.VK_X:
			Command.OK.setPressed(pressed);
			break;
			
		// *******************************************
		// CANCEL
		// *******************************************
		case KeyEvent.VK_ESCAPE:
			Command.CANCEL.setPressed(pressed);
			break;
		
		case KeyEvent.VK_Z:
			Command.CANCEL.setPressed(pressed);
			break;
		}

	}
	
}
