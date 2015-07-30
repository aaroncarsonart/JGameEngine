package game.graphics.sprite;

import game.engine.Command;
import game.engine.Game;
import game.graphics.Images;
import game.graphics.SpriteSheet;
import game.map.Quad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

import utility.Direction;
import utility.Position;

/**
 * A WalkingSprite represents a walking animation used by a player or enemy. All
 * WalkingSprites use 4x4 SpriteSheets to cycle through the poses.
 * 
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public class WalkingSprite extends Sprite
{
	/** Path to the image file for a male warrior walk-cycle. */
	public static final String	MALE_WARRIOR	= "/res/images/male_warrior_spritesheet.png";
	
	/** Path to the image file for a female warrior walk-cycle. */
	public static final String	FEMALE_WARRIOR	= "/res/images/female_warrior_spritesheet.png";
	public static final int		DEFAULT_WIDTH	= 16;
	public static final int		DEFAULT_HEIGHT	= 16;
	public static final int		DEFAULT_FRAMERATE = 8;
	
	public static final double	WALK_SPEED		= 1.0;
	public static final double	RUN_SPEED		= 2.5;
	
	private double				speed;
	private double				walkSpeed;
	private double				runSpeed;
	private boolean				running;
	
	private int					currentFrame;
	private int					updatesPerFrame;
	private int					updateCounter;
	private int					animationLength;
	
	public static final double	ACCELERATION	= 0.05;
	
	private double				leftSpeed;
	private double				rightSpeed;
	private double				upSpeed;
	private double				downSpeed;
	
	private Stack<Direction>	directions;
	private Direction			lastDirection;
	
	/**
	 * Create a new WalkingSprite from the given path with a hitbox that matches
	 * the sprite using a DEFAULT_FRAMERATE.
	 * 
	 * @param path The path of image to use for the backing SpriteSheet.
	 * @param start The starting position for the sprite.
	 */
	public WalkingSprite(String path, Position start) {
		super();
		BufferedImage src = Images.loadImageFrom(path);
		this.spriteSheet = new SpriteSheet(src, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.width = spriteSheet.getTileWidth();
		this.height = spriteSheet.getTileWidth();
		this.offset = new Position(0, 0);
		this.hitbox = new Quad(start.x(), start.y(), width / 2.0, height / 2.0);
		this.collides = true;
		initialize(DEFAULT_FRAMERATE);
	}
	
	/**
	 * Create a new WalkingSprite.
	 * 
	 * @param src The BufferedImage source file.
	 * @param hitbox The hitbox used for this Sprite.
	 * @param xOff The x offset of the Sprite image from the hitbox.
	 * @param yOff The y offset of the Sprite image from the hitbox.
	 * @param frameRate The frame rate of the walking animation.
	 */
	public WalkingSprite(SpriteSheet src, Quad hitbox, int xOff, int yOff,
			int frameRate) {
		super(src, hitbox, xOff, yOff);
		initialize(frameRate);
	}
	
	private void initialize(int frameRate){
		this.walkSpeed = WALK_SPEED;
		this.runSpeed = RUN_SPEED;
		this.speed = walkSpeed;
		this.running = false;
		this.currentFrame = 0;
		// for instance, if frameRate is "4" then wait is 15 frames.
		this.updatesPerFrame = Game.FRAME_RATE / frameRate;
		this.animationLength = spriteSheet.getWidth();
		//System.out.println("waitFrames: " + updatesPerFrame);
		this.updateCounter = 0;
		this.directions = new Stack<Direction>();// 
		this.lastDirection = Direction.DOWN;
	}
	
	@Override
	public void update() {
		
		// ****************************************************
		// check if the animation frame needs to be updated.
		// ****************************************************
		updateCounter += running ? 2 : 1;
		if (updateCounter >= updatesPerFrame) {
			
			// advance to the next frame, from between 0 to animationLength - 1
			currentFrame = (currentFrame + 1) % animationLength;
			updateCounter = 0;
		}
		
		// ****************************************************
		// calculate the new dx and dy.
		// ****************************************************
		double dx = 0;
		double dy = 0;
		speed = running ? runSpeed : walkSpeed;
		
		for (Direction d : directions) {
			switch (d) {
			case UP:
				dy -= speed;
				break;
			case DOWN:
				dy += speed;
				break;
			case LEFT:
				dx -= speed;
				break;
			case RIGHT:
				dx += speed;
				break;
			default:
				break;
			}
		}
		// move the player.
		hitbox.x += dx;
		hitbox.y += dy;
		
	}
	
	/**
	 * get the last direction
	 * @return
	 */
	public Direction getLastDirection(){
		return directions.isEmpty() ? lastDirection : directions.peek();
	}
	
	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		int row;
		
		// ****************************************************
		// 1. determine which row of SpriteSheet to use.
		// ****************************************************
		switch (getLastDirection()) {
		case UP:
			row = 0;
			break;
		case DOWN:
			row = 1;
			break;
		case LEFT:
			row = 2;
			break;
		case RIGHT:
			row = 3;
			break;
		default:
			row = 1;
			break;
		}
		
		// ****************************************************
		// draw the current animation frame.
		// ****************************************************
		int tileIndex = currentFrame + row * animationLength;
		//System.out.println("tileIndex: " + tileIndex);
		//System.out.println("currentFrame: " + currentFrame);
		//System.out.println("tileIndex: " + tileIndex);
		int startX = xOffset + offset.x() - width + (int) hitbox.getRightX();
		int startY = yOffset + offset.y() - height + (int) hitbox.getBottomY();
		spriteSheet.render(g, tileIndex, startX, startY);
	}
	
	/**
	 * Set if the entity will move quickly.
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Set the running speed for this Entity.
	 * 
	 * @param newSpeed The new runSpeed.
	 */
	public void setRunSpeed(double newSpeed) {
		this.runSpeed = newSpeed;
		if (running) speed = runSpeed;
	}
	
	/**
	 * Set the walking speed for this Entity.
	 * 
	 * @param newSpeed The new walkSpeed.
	 */
	public void setWalkSpeed(double newSpeed) {
		this.walkSpeed = newSpeed;
		if (running) speed = runSpeed;
	}
	
	/**
	 * The Entity stops moving, and returns to a resting animation.
	 */
	public void stop() {
		currentFrame = 1;
	}
	
	/**
	 * Set the current direction of the sprite's movement.
	 * 
	 * @param d The new direction.
	 */
	public void addDirection(Direction d) {
		directions.push(d);
	}
	
	public void removeDirection(Direction d) {
		if(directions.remove(d) && directions.isEmpty()){
			lastDirection = d;
			System.out.printf("last direction: %s\n", d);
		}
	}
	
	/**
	 * Convenience method to update this WalkingSprite's directions based on
	 * use input.
	 */
	public void updateDirections(){
		updateDirectionsFor(Command.UP);
		updateDirectionsFor(Command.DOWN);
		updateDirectionsFor(Command.LEFT);
		updateDirectionsFor(Command.RIGHT);
	}
	
	/**
	 * Update this WalkingSprite's directions based on the state of the given
	 * Command.
	 * @param command the Command to check.
	 */
	public void updateDirectionsFor(Command command){
		Direction d = Command.getDirectionFor(command);
			// add the related direction, if the key was just pressed.
		if (!command.isConsumed()){
			addDirection(d);
			//System.out.printf("added %s: %b\n", d, result);
			command.consume();
		}
		
		// otherwise, remove the command if the key is released.
		else if(!command.isPressed()){
			removeDirection(d);
			//System.out.printf("removed %s: %b\n", d, result);
		}
	}
	
	/**
	 * Set the direction stack used by this Entity (helpful for sharing data
	 * structures).
	 * 
	 * @param directions The direction stack to use.
	 */
	public void setDirections(Stack<Direction> directions) {
		this.directions = directions;
	}
	
	/**
	 * Get the directions of this WalkingSprite.
	 * @return A Stack of Directions.
	 */
	public Stack<Direction> getDirections() {
		return directions;
	}
}
