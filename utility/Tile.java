package utility;

/**
 * A tile is an object that encapsulates all data 
 * @author Aaron Carson
 * @version Jul 3, 2015
 */
public final class Tile
{	
	public final Quad quad;
	public byte type;

	/**
	 * Create a new Tile.
	 * @param quad The quad.
	 * @param type The type.
	 */
	public Tile(Quad quad, byte type){
		this.quad = quad;
		this.type = type;
	}
}
