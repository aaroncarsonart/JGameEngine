package game.map;

/**
 * A tile matches a quad with passability data.
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class Tile
{	
	/** The quad used to determine the hitbox/space used by this tile. */
	public final Quad quad;
	public final boolean passable;
	
	public Tile(Quad quad, boolean passable){
		this.quad = quad;
		this.passable = passable;
	}
}
