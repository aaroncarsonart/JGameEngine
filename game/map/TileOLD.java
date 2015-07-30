package game.map;


/**
 * A tile is an object that represents collision info and rendering data for
 * a given tile.
 * @author Aaron Carson
 * @version Jul 3, 2015
 */
public final class TileOLD
{	
	/** The quad used to determine the hitbox/space used by this tile. */
	public final Quad quad;
	
	/** The sprite used to represent this Tile. */
	public int sprite; 

	/**
	 * Create a new Tile.
	 * @param quad The quad.
	 * @param sprite The sprite index.
	 */
	public TileOLD(Quad quad, int sprite){
		this.quad = quad;
		this.sprite = sprite;
	}
}
