package game.map;

/**
 * A Quad is an axis-aligned bounding box, represented by a center point and
 * The half distance to the left and to the right.
 * <p>
 * For consistency and practical Terms, Quads values should be measured and
 * recorded on a pixel scale.  The position is a litera position, not related 
 * directly to any tile sizes.
 * @author Aaron Carson
 * @version Jun 30, 2015
 */
public final class Quad
{	
	/** The center x coordinate position.*/
	public double x;
	/** The center y coordinate position.*/
	public double y;	
	/** The half-width of the Quad.*/
	public double halfWidth;
	/** The half-height of the Quad.*/
	public double halfHeight;
	
	/**
	 * Make a Quad.
	 * @param x The center x position.
	 * @param y The center y Position.
	 * @param halfWidth The distance to the left and right edges.
	 * @param halfHeight The distance to the top and bottom edges.
	 */
	public Quad(double x, double y, double halfWidth, double halfHeight){
		this.x = x;
		this.y = y;
		this.halfWidth = halfWidth;
		this.halfHeight = halfHeight;
	}
	
	/**
	 * Check if this collides with the given Quad, and shift it back the 
	 * minimum distance if so.
	 * @param quad The quad to compare to.
	 * @return True, if this quad was adjusted.
	 */
	public final boolean fixIfCollides(Quad quad) {
		
		// the minimum distances the two Quads can be from each other.
		double xMin = this.halfWidth + quad.halfWidth;
		double yMin = this.halfHeight + quad.halfHeight;
		
		// if positive, quad is to the right; if negative, it is to the left.
		double xDist = quad.x - this.x;
		// if positive, quad is below; if negative, it is above.
		double yDist = quad.y - this.y;
		
		// a collision occurs!  resolve below
		double xAbs = Math.abs(xDist);
		double yAbs = Math.abs(yDist);
		if(xAbs < xMin && yAbs < yMin){			
			// adjust the greater distance first. 
			if (xAbs > yAbs){
				// adjust based on relative position.
				if (this.x < quad.x){
					this.x = quad.x - quad.halfWidth - this.halfWidth;
					//System.out.println("collides!");
				}
				else{
					this.x = quad.x + quad.halfWidth + this.halfWidth;
					//System.out.println("collides!");
				}				
			}
			else{
				// adjust based on relative position.
				if (this.y < quad.y){
					this.y = quad.y - quad.halfHeight - this.halfHeight;
					//System.out.println("collides!");
				}
				else{
					this.y = quad.y + quad.halfHeight + this.halfHeight;
					//System.out.println("collides!");
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the rectangle is off screen of the bounds of a map of given
	 * width and height.
	 * @param width The width of the map to check.
	 * @param height The height of the map to check.
	 * @return True fi the screen is off screen.
	 */
	public boolean offScreen(int width, int height){
		return halfWidth  > x || x >= width  - halfWidth || 
			   halfHeight > y || y >= height - halfHeight; 
	}
	
	/**
	 * Get the full width of this Quad.
	 */
	public double getWidth(){
		return halfWidth + halfWidth;
	}
	/**
	 * Get the full height of this Quad.
	 */
	public double getHeight(){
		return halfHeight + halfHeight;
	}
	
	public double getTopY(){
		return y - halfHeight;
	}

	public double getBottomY(){
		return y + halfHeight;
	}
	
	public double getLeftX(){
		return x - halfWidth;
	}
	
	public double getRightX(){
		return x + halfWidth;
	}
	
	/**
	 * Check if the two Quads collide.
	 * @param quad The Quad to check against.
	 * @return True, if they collide.
	 */
	public boolean collides(Quad quad){
		return Math.abs(this.x - quad.x) < this.halfWidth + quad.halfWidth &&
				Math.abs(this.y - quad.y) < this.halfHeight + quad.halfHeight; 
	}
}
