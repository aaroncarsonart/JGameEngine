package game.graphics.sprite;

import game.graphics.SpriteSheet;
import game.map.Quad;

import java.awt.Graphics2D;

import utility.Position;

/**
 * A Sprite is an image that moves about the screen. It may represent the
 * player, a creature, a terrain feature, or an item. They have a set of
 * animations associated with them.
 * <p>
 * Sprites have an associated Quad that determines it's hitbox. The hitbox
 * determines the Sprite's current position, and handles collisions with other
 * tiles or sprites .
 * <p>
 * It may or may not also cause collisions. The hitbox Quad may be offset and/or
 * a different shape from the Sprite's rendering area.
 * 
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public abstract class Sprite implements Comparable<Sprite>
{
	/** The SpriteSheet used to render this sprite. */
	public SpriteSheet	spriteSheet;
	
	/** The hit box and position quad for this Sprite. */
	public Quad			hitbox;
	
	/**
	 * The x and y offsets for this sprite, if the sprite and hitbox are
	 * different sizes.
	 */
	public Position		offset;
	
	public int			width;
	public int			height;
	
	/** if true, this sprite collides with other sprites. */
	public boolean		collides;
	
	/**
	 * Used by the Subclasses of sprites only, for custom behavior. Initialize
	 * nothing.
	 */
	protected Sprite() {
		
	}
	
	/**
	 * Create a new Sprite.
	 * 
	 * @param spriteSheet The SpriteSheet used to render this Sprite.
	 * @param hitbox The hitbox for this sprite, which includes the initial
	 *        position of the sprite, and the hitbox dimensions.
	 * @param width The width of the sprite graphic.
	 * @param height The height of the sprite graphic.
	 * @param xOffset The x offset of the sprite graphic from the hitbox.
	 * @param yOffset The y offset of the sprite graphic from the hitbox.
	 */
	public Sprite(SpriteSheet src, Quad hitbox, int xOffset, int yOffset) {
		this.spriteSheet = src;
		this.hitbox = hitbox;
		this.offset = new Position(xOffset, yOffset);
		this.width = src.getTileWidth();
		this.height = src.getTileHeight();
		this.collides = true;
	}
	
	/**
	 * Create a basic, rectangular sprite with a hitbox with the same dimension
	 * and position as the sprite.
	 * 
	 * @param spriteSheet The SpriteSheet used to render this Sprite.
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 * @param width The width of the Sprite.
	 * @param height The height of the Sprite.
	 */
	public Sprite(SpriteSheet src, int x, int y) {
		this.spriteSheet = src;
		this.offset = new Position(0, 0);
		this.width = src.getTileWidth();
		this.height = src.getTileHeight();
		this.hitbox = new Quad(x, y, width / 2.0, height / 2.0);
		this.collides = true;
	}
	
	/**
	 * Update the state of this Sprite. By default does nothing, override this
	 * method to update the state of the Sprite each tick.
	 */
	public void update() {}
	
	/**
	 * Render this Sprite to the given Graphics context.
	 * 
	 * @param g The graphics context to render to.
	 * @param xOffset The horizontal offset to be added to the Sprite's x
	 *        coordinate when rendering.
	 * @param yOffset The vertical offset to be added to the Sprite's y
	 *        coordinate when rendering.
	 */
	public abstract void render(Graphics2D g, int xOffset, int yOffset);
	
	/**
	 * Set if this Sprite collides with other Sprites and walls.
	 * 
	 * @param collides The new collides value.
	 */
	public void setCollides(boolean collides) {
		this.collides = collides;
	}
	
	/**
	 * Compare two Sprites by their y positions, or their x positions if the
	 * y positions are equal.
	 */
	@Override
	public int compareTo(Sprite sprite) {
		int yOffset = (int) (this.hitbox.y - sprite.hitbox.y);
		return yOffset == 0 ? (int) (this.hitbox.x - sprite.hitbox.x) : yOffset;
	}
}
