package utility;

/**
 * A Quad is an axis-aligned bounding box, represented by a center point and
 * The half distance to the left and to the right.
 * @author Aaron Carson
 * @version Jun 30, 2015
 */
public final class Quad
{	
	/** The top-left x coordinate position.*/
	public double x;
	/** The top-left y coordinate position.*/
	public double y;	
	/** The width of the Quad.*/
	public double halfWidth;
	/** The height of the Quad.*/
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
				}
				else{
					this.x = quad.x + quad.halfWidth + this.halfWidth;
				}				
			}
			else{
				// adjust based on relative position.
				if (this.y < quad.y){
					this.y = quad.y - quad.halfHeight - this.halfHeight;
				}
				else{
					this.y = quad.y + quad.halfHeight + this.halfHeight;
				}
			}
			return true;
		}
		return false;
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
}
