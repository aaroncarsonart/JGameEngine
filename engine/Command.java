package engine;

import java.util.Stack;

import utility.Direction;

/**
 * Represents inputs by the user. if Pressed(), then the command is active.
 * 
 * @author Aaron Carson
 * @version Jun 17, 2015
 */
public enum Command {
	UP, DOWN, LEFT, RIGHT, OK, CANCEL, MAP, HELP, TIME, REST, QUIT;
	
	private boolean	pressed;
	private boolean	consumed;
	
	/**
	 * Press this command.
	 */
	public void press() {
		if (!pressed) {
			pressed = true;
			consumed = false;
			if (this.ordinal() < 4) moves.push(this);
			// System.out.printf("%s pressed\n", this.name());
		}
	}
	
	/**
	 * Release this command.
	 */
	public void release() {
		if (pressed) {
			pressed = false;
			consumed = true;
			if (this.ordinal() < 4) moves.remove(this);
			// System.out.printf("%s released\n", this.name());
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
	 * consumed. In all other cases, it returns true.
	 * 
	 * @return True, if the Command has been consumed.
	 */
	public boolean isConsumed() {
		return consumed;
	}
	
	/**
	 * "Consume" the event, causing further calls to isConsumed() to return
	 * false, until the command is pressed again.
	 */
	public void consume() {
		consumed = true;
	}
	
	/** Used to track the most recently pressed direcitonal button. */
	private static Stack<Command>	moves		= new Stack<Command>();
	private static Command			lastMove	= Command.DOWN;
	
	/**
	 * Get the last pressed move that is still being pressed.
	 * 
	 * @return The last move.
	 */
	public static Command mostRecent() {
		if (moves.size() == 0) return lastMove;
		lastMove = moves.peek();
		return lastMove;
	}
	
	/**
	 * Get the last pressed move that is still being pressed.
	 * 
	 * @return The last move.
	 */
	public static Direction mostRecentDirection() {
		return getDirectionFor(mostRecent());
	}
	
	/**
	 * Get the Direction associated with the given Command.
	 * @param command The input command.
	 * @return An associated Direction.  Defaults to down if invalid input.
	 */
	public static Direction getDirectionFor(Command command){
		switch (command) {
		case UP:
			return Direction.UP;
		case DOWN:
			return Direction.DOWN;
		case LEFT:
			return Direction.LEFT;
		case RIGHT:
			return Direction.RIGHT;
		default:
			return Direction.DOWN;
		}
	}
	/**
	 * Check if any moves are selected.
	 * 
	 * @return True, if at least one move is selected.
	 */
	public static boolean moveSelected() {
		return !moves.empty();
	}
	
	/**
	 * Get all currently selected move directions by the player.
	 * @return the moves.
	 */
	public static Stack<Command> selectedMoveCommands(){
		return moves;		
	}
}
