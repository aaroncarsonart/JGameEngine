package game.graphics.sprite;

import game.item.Item;

import java.awt.Graphics2D;
import java.util.Random;

/**
 * ItemSprites are sprites that represent an item. It shows the Item on the
 * Screen, and if the player walks up do the Item, he can pick it up with
 * "CONFIRM" and add it to his inventory. This removes the Item Sprite from the
 * screen.
 * 
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class ItemSprite extends StaticSprite
{
	public final Item		item;
	private boolean			sparkle;
	private AnimationSprite	sparkleSprite;
	private static Random	RANDOM	= new Random();
	
	/**
	 * Create a new ItemSprite for the given Item. If Tile-based x and y
	 * coordinates are to be used, the x and y values for this Item need to be
	 * calculated based on the sprite's width and heights.
	 * 
	 * @param item The item to add.
	 * @param x The pixel-perfect x coordinate for this Sprite.
	 * @param y The pixel-perfect y coordinate for this Sprite.
	 */
	public ItemSprite(Item item, int x, int y, boolean sparkle) {
		super(item.spriteSheet, x, y, item.tileIndex);
		this.item = item;
		this.sparkle = sparkle;
		if (sparkle) {
			int sx = (int) this.hitbox.x;
			int sy = (int) this.hitbox.y;
			int holdFrames = AnimationSprite.getRandomHoldFramesValue(20);
			sparkleSprite = AnimationSprite.getSparkle(sx, sy, holdFrames);
		}
	}
	
	@Override
	public void update() {
		if (sparkle) sparkleSprite.update();
	}
	
	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		super.render(g, xOffset, yOffset);
		if (sparkle) sparkleSprite.render(g, xOffset, yOffset);
	}
	
}
