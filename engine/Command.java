package engine;

import java.awt.event.KeyEvent;

/**
 * Represents inputs by the user. if Pressed(), then the command is active.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public enum Command {
	UP, DOWN, LEFT, RIGHT, OK, CANCEL, QUIT;
	
	private boolean	pressed;
	private boolean consumed;
	
	/**
	 * Press this command.
	 */
	public void press() {
		if (!pressed) {
			pressed = true;
			consumed = false;
			System.out.printf("%s pressed\n", this.name());
		}
	}
	
	/**
	 * Release this command.
	 */
	public void release() {
		if(pressed){
			pressed = false;
			consumed = true;
			System.out.printf("%s released\n", this.name());
		}
	}
	
	/**
	 * Check if this command is pressed.
	 * 
	 * @return True if pressed.
	 */
	public boolean isPressed() {
		return pressed;
	}
	
	/**
	 * Set a new pressed value.
	 * 
	 * @param pressed The new value.
	 */
	public void setPressed(boolean pressed) {
		if (pressed) press();
		else release();
	}
	
	/**
	 * Switch the value of pressed.
	 */
	public void toggle() {
		if (pressed) release();
		else press();
	}
	
	/**
	 * Returns false if the command has not been released, and has not yet been
	 * consumed.  In all other cases, it returns true.
	 * @return True, if the Command has been consumed.
	 */
	public boolean isConsumed(){
		return consumed;
	}
	
	/**
	 * "Consume" the event, causing further calls to isConsumed() to return
	 * false, until the command is pressed again.
	 */
	public void consume(){
		consumed = true;
	}
	
}
