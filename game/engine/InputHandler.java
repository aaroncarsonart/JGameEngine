package game.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles inputs for the Game, and reports results in Commands.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public class InputHandler implements KeyListener
{
	
	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("keyTyped: " + e.getKeyCode());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("keyPressed: " + e.getKeyCode());
		setCommandPressedFrom(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		setCommandPressedFrom(e.getKeyCode(), false);
	}
	
	/**
	 * Switches over a set of key codes, and if one matches it sets the
	 * associated Command's pressed value.
	 * 
	 * @param e The KeyEvent from which to get the keycode.
	 * @param pressed
	 */
	private void setCommandPressedFrom(int keycode, boolean pressed) {
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
		case KeyEvent.VK_K:
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
		case KeyEvent.VK_J:
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
		case KeyEvent.VK_H:
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
		case KeyEvent.VK_L:
			Command.RIGHT.setPressed(pressed);
			break;
		
		// *******************************************
		// OK
		// *******************************************
		case KeyEvent.VK_ENTER:
			Command.OK.setPressed(pressed);
			System.out.println("enter " + (pressed ? "pressed" : "released"));
			break;
		
		case KeyEvent.VK_SPACE:
			System.out.println("space " + (pressed ? "pressed" : "released"));
			Command.OK.setPressed(pressed);
			break;
		
		case KeyEvent.VK_X:
			System.out.println("x " + (pressed ? "pressed" : "released"));
			Command.OK.setPressed(pressed);
			break;
		
		// *******************************************
		// CANCEL
		// *******************************************
		
		case KeyEvent.VK_Z:
			Command.CANCEL.setPressed(pressed);
			break;
		case KeyEvent.VK_SHIFT:
			Command.CANCEL.setPressed(pressed);
			break;
		
		// *******************************************
		// MENU
		// *******************************************
		case KeyEvent.VK_ESCAPE:
			Command.MENU.setPressed(pressed);
			break;
		case KeyEvent.VK_TAB:
			Command.MENU.setPressed(pressed);
			break;
		
		// *******************************************
		// INVENTORY
		// *******************************************
		case KeyEvent.VK_I:
			Command.INVENTORY.setPressed(pressed);
			break;
		
		// *******************************************
		// MAP
		// *******************************************
		case KeyEvent.VK_M:
			Command.MAP.setPressed(pressed);
			break;
		
		// *******************************************
		// HELP
		// *******************************************
		/*
		 * case KeyEvent.VK_H: Command.HELP.setPressed(pressed); break;
		 */
		
		// *******************************************
		// TIME
		// *******************************************
		case KeyEvent.VK_T:
			Command.TIME.setPressed(pressed);
			break;
		
		// *******************************************
		// REST
		// *******************************************
		case KeyEvent.VK_R:
			Command.REST.setPressed(pressed);
			break;
		
		// *******************************************
		// REST
		// *******************************************
		case KeyEvent.VK_V:
			Command.VITALS.setPressed(pressed);
			break;
		}
	}
	
}
