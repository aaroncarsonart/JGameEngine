package game.graphics.sprite;

import game.graphics.Images;
import game.graphics.SpriteSheet;

import java.awt.Graphics2D;
import java.util.Random;

import utility.Quad;

/**
 * Animation Sprites are Sprites with a custom
 * 
 * @author Aaron Carson
 * @version Jul 29, 2015
 */
public class AnimationSprite extends Sprite
{
	/**
	 * A Shared SpriteSheet for 8x8 animations.
	 */
	private static SpriteSheet	Sprites8x8	= new SpriteSheet(
													Images.ANIMATIONS, 8);
	private static Random		RANDOM		= new Random();
	private int[]				frames;
	private int					frameRate;
	private int					frame;
	private int					counter;
	private boolean				loop;
	private int					holdFrames;
	private int					animationLength;
	
	/**
	 * Create an Animation sprite
	 * 
	 * @param x The x position.
	 * @param y The y position.
	 * @param frames an array of tile indexes to display for each frame.
	 * @param frameRate the frame rate, in update calls.
	 * @param loop if true, the animation loops.
	 * @param framesBetweenLoops if positive, this number of frames is always
	 *        used to wait between animations. if zero, then there is no pause.
	 *        if negative, then a random number is generated when the animation
	 *        finishes each time, with the absolute value of framesBetweenLoops
	 *        being the random number bound.
	 */
	public AnimationSprite(int x, int y, int[] frames, int frameRate,
			boolean loop, int holdFrames) {
		super(Sprites8x8, getDefaultHitBox(x, y, Sprites8x8), 0, 0);
		this.frames = frames;
		this.frameRate = frameRate;
		this.loop = loop;
		this.holdFrames = holdFrames;
		
		// case 1: not looping, or no frames between loops.
		if (!loop || holdFrames == 0) {
			this.animationLength = frames.length;
		}
		
		// case 2: positive framesBetweenLoops value.
		else if (holdFrames > 0) {
			this.animationLength = frames.length + holdFrames;
		}
		
		// otherwise, random value each loop.
		else {
			calculateRandomAnimationLength();
		}
	}
	
	/**
	 * Calculate the next animationLength, with a random additional value.
	 */
	private void calculateRandomAnimationLength() {
		int extraFrames = RANDOM.nextInt(-holdFrames) - holdFrames / 4;
		
		this.animationLength = frames.length + extraFrames;
	}
	
	@Override
	public void update() {
		// handle looping animations.
		if (loop) {
			counter++;
			if (counter == frameRate) {
				frame = (frame + 1) % animationLength;
				counter = 0;
				
				// recalculate frameLength, if random
				if (frame == 0 && holdFrames < 0) {
					calculateRandomAnimationLength();
				}
			}
		}
		
		// handle single-play animations.
		else if (frame < frames.length) {
			counter++;
			if (counter == frameRate) {
				frame += 1;
				counter = 0;
			}
		}
	}
	
	/**
	 * Check if the animation is finished playing.
	 * 
	 * @return
	 */
	public boolean finished() {
		return !loop && frame >= frames.length;
	}
	
	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		if (!finished()) {
			int sx = xOffset + offset.x() - width + (int) hitbox.getRightX();
			int sy = yOffset + offset.y() - height + (int) hitbox.getBottomY();
			int index = frame < frames.length ? frame : frames.length - 1;
			spriteSheet.render(g, frames[index], sx, sy);
		}
	}
	
	/**
	 * Get a default quad for a sprite from the given SpriteSheet.
	 * 
	 * @param x The x position.
	 * @param y The y position.
	 * @param sheet The backing SpriteSheet.
	 * @return A quad of size equal to the tile size for the SpriteSheet.
	 */
	private static Quad getDefaultHitBox(int x, int y, SpriteSheet sheet) {
		return new Quad(x, y, sheet.getTileWidth() / 2,
				sheet.getTileHeight() / 2);
	}
	
	/**
	 * Create a Sparkle animation.
	 * 
	 * @param x the x position.
	 * @param y the y position.
	 * @param holdFrames the number of frames to hold between each animation.
	 * @return A new looping Sparkle AnimationSprite.
	 */
	public static AnimationSprite getSparkle(int x, int y, int holdFrames) {
		int[] frames = { 0, 1, 2, 1, 1, 4, 1, 3, 2, 1, 0 };
		int frameRate = 8;
		boolean loop = true;
		return new AnimationSprite(x, y, frames, frameRate, loop, holdFrames);
	}
	
	/**
	 * Helper method to get a random holdFrames value for an animation.
	 * 
	 * @param valueBound random value will be from valueBound inclusive to
	 *        valueBound * 2 exclusive.
	 * @param chanceBound THe chance of 
	 * @return
	 */
	public static int getRandomHoldFramesValue(int valueBound) {
		int bound = valueBound;
		int chance = RANDOM.nextInt(20);
		int value = bound + RANDOM.nextInt(bound);
		int holdFrames = (chance == 0) ? 0 : (chance < 10) ? value : -value;
		return holdFrames;
	}
	
}
