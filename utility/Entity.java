package utility;

import engine.Command;
import engine.Game;
import graphics.Sprite;

import java.awt.Graphics2D;
import java.util.Stack;

/**
 * An Entity is anything that inhabits a space and tries to move. An entity has
 * a hit box (known as a quad) that tracks its position, and it has a set of
 * rules defined that help it move.
 * 
 * @author Aaron Carson
 * @version Jul 18, 2015
 */
public class Entity extends Quad
{
	public static final double	WALK_SPEED		= 0.5;
	public static final double	RUN_SPEED		= 1.0;
	
	private Sprite				sprite;
	private double				speed;
	private boolean				running;
	private double				walkSpeed;
	private double				runSpeed;
	
	private int					step;
	private int					waitFrames;
	private int					frame;
	
	public static final double	ACCELERATION	= 0.05;
	
	private double				leftSpeed;
	private double				rightSpeed;
	private double				upSpeed;
	private double				downSpeed;
	
	private Stack<Direction>	directions;
	
	private byte				moveType;
	
	public static final byte	CONSTANT		= 0;
	public static final byte	ACCELERATED		= 1;
	
	public Entity(double x, double y, double halfWidth, double halfHeight,
			Sprite sprite, int frameRate) {
		this(x, y, halfWidth, halfHeight, sprite, frameRate, 0, 0);
	}
	
	/**
	 * Create a new Entity.
	 * 
	 * @param x The starting center x coordinate.
	 * @param y The starting center y coordinate.
	 * @param halfWidth The "half width" of the Entity.
	 * @param halfHeight The "half height" of the Entity.
	 * @param sprite The Sprite to be used by this Entity.
	 * @param frameRate The frame rate at which the animation updates..
	 */
	public Entity(double x, double y, double halfWidth, double halfHeight,
			Sprite sprite, int frameRate, int xOffset, int yOffset) {
		super(x, y, halfWidth, halfHeight);
		this.sprite = sprite;
		this.walkSpeed = WALK_SPEED;
		this.runSpeed = RUN_SPEED;
		this.speed = walkSpeed;
		this.running = false;
		this.step = 0;
		// for instance, if frameRate is "4" then wait is 15 frames.
		this.waitFrames = Game.FRAME_RATE / frameRate;
		System.out.println("waitFrames: " + waitFrames);
		this.frame = 0;
		this.directions = new Stack<Direction>();// = Direction.DOWN;
		moveType = CONSTANT;
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
	 * Draw this Entity to the given graphics context.
	 * 
	 * @param g The Graphics2D context to draw to.
	 */
	public void render(Graphics2D g, int dx1, int dy1, int dx2, int dy2) {
		int x, y;
		
		// 1. determine y coordinate of spriteSheet
		switch (directions.isEmpty() ? Direction.DOWN : directions.peek()) {
		case UP:
			y = 0;
			break;
		case DOWN:
			y = 1;
			break;
		case LEFT:
			y = 2;
			break;
		case RIGHT:
			y = 3;
			break;
		default:
			y = 1;
			break;
		}
		x = step;
		
		int tileSize = sprite.getTileSize();
		int sx1 = x * tileSize;
		int sx2 = x * tileSize + tileSize;
		int sy1 = y * tileSize;
		int sy2 = y * tileSize + tileSize;
		g.drawImage(sprite.getSpriteSheet(), dx1, dy1, dx2, dy2, sx1, sy1, sx2,
				sy2, null);
		
	}
	
	/**
	 * The Entity stops moving, and returns to a resting animation.
	 */
	public void stop() {
		step = 1;
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
		directions.remove(d);
	}
	
	public void updateConstant() {
		// advance the step after waitFrames have updated.
		frame += running ? 2 : 1;
		if (frame >= waitFrames) {
			// step cycles through 0 to 3
			step = (step + 1) % 4;
			frame = 0;
		}
		
		// calculate the new dx and dy.
		double dx = 0;
		double dy = 0;
		if (running) speed = runSpeed;
		else speed = walkSpeed;
		
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
		x += dx;
		y += dy;
	}
	
	public void updateAccelerated() {
		// advance the step after waitFrames have updated.
				frame += running ? 2 : 1;
				if (frame >= waitFrames) {
					// step cycles through 0 to 3
					step = (step + 1) % 4;
					frame = 0;
				}
				
				// calculate the new dx and dy.
				if (running) speed = runSpeed;
				else speed = walkSpeed;
				
				if(Command.UP.isPressed() && upSpeed < speed) upSpeed += ACCELERATION;
				else if(upSpeed > 0){
					upSpeed -= ACCELERATION;
					if (Math.abs(upSpeed) < 0.2) upSpeed = 0;
				}
				if(Command.DOWN.isPressed() && downSpeed < speed) downSpeed += ACCELERATION;
				else if(downSpeed > 0){
					downSpeed -= ACCELERATION;
					if (Math.abs(downSpeed) < 0.2) downSpeed = 0;
				}
				if(Command.LEFT.isPressed() && leftSpeed < speed) leftSpeed += ACCELERATION;
				else if(leftSpeed > 0){
					leftSpeed -= ACCELERATION;
					if (Math.abs(leftSpeed) < 0.2) leftSpeed = 0;
				}
				if(Command.RIGHT.isPressed() && rightSpeed < speed) rightSpeed += ACCELERATION;
				else if(rightSpeed > 0){
					rightSpeed -= ACCELERATION;
					if (Math.abs(rightSpeed) < 0.2) rightSpeed = 0;
				}
				
				y = y + downSpeed - upSpeed;
				x = x + rightSpeed - leftSpeed;
				
				/*
				for (Direction d : directions) {
					switch (d) {
					case UP:
						ySpeed -= ACCELERATION;
						break;
					case DOWN:
						ySpeed += ACCELERATION;
						break;
					case LEFT:
						xSpeed -= ACCELERATION;
						break;
					case RIGHT:
						xSpeed += ACCELERATION;
						break;
					default:
						break;
					}
				}
				// move the player.
				x += dx;
				y += dy;
				*/
	}
	
	/**
	 * Update the entity's position.
	 */
	public void update() {
		if (moveType == CONSTANT) updateConstant();
		else updateAccelerated();
	}
	
	/**
	 * Set the direction stack used by this Entity (helpful for sharing data
	 * structures).
	 * 
	 * @param directions The direction stack to use.
	 */
	public void setDirectionStack(Stack<Direction> directions) {
		this.directions = directions;
	}
	
}
