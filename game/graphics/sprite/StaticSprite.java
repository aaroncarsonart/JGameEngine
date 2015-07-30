package game.graphics.sprite;

import java.awt.Graphics2D;

import game.graphics.SpriteSheet;
import utility.Position;

/**
 * A StaticSprite is the most simple implementation of a Sprite. It renders one
 * tile to the map and never changes. It's hitbox matches it's image dimensions.
 * This Sprite is not updated, and by default causes collisions.
 * 
 * @author Aaron Carson
 * @version Jul 25, 2015
 */
public class StaticSprite extends Sprite
{
	private int	tile;
	
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
	public StaticSprite(SpriteSheet src, int x, int y, int tile) {
		super(src, x, y);
		this.tile = tile;
	}
	
	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		int startX = xOffset + offset.x() - width + (int) hitbox.getRightX();
		int startY = yOffset + offset.y() - height + (int) hitbox.getBottomY();
		spriteSheet.render(g, tile, startX, startY);
	}
	
}
