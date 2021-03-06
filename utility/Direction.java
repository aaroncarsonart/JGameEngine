package utility;
/**
 * Direction enum for abstracting directional input.
 */
public enum Direction {
	LEFT       ("\u2190"), 
	UP         ("\u2191"), 
	RIGHT      ("\u2192"), 
	DOWN       ("\u2193");
	
	private String	unicode;
	
	/**
	 * Create a new Direction with the specified arrow character.
	 * 
	 * @param unicode The unicode character string to represent this Direction.
	 */
	Direction(String unicode) {
		this.unicode = unicode;
	}
	
	@Override
	public String toString() {
		return unicode;
	}
	
	/**
	 * Gets the opposite of the input direction (for example: UP returns DOWN).
	 * @param direction The direction to get the opposite of.
	 * @return The opposite direction.
	 */
	public static Direction getOpposite(Direction direction) {
		switch (direction) {
		case LEFT:       return RIGHT;
		case RIGHT:      return LEFT;
		case UP:         return DOWN;
		case DOWN:       return UP;
		default:		return DOWN;
		}
	}
	
}
