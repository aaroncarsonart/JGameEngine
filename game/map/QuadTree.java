package game.map;

import game.graphics.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

import utility.Quad;

/**
 * QuadTree for finding and organizing sprites? I guess, we'll see.
 * 
 * @author Aaron Carson
 * @version Jul 27, 2015
 */
public class QuadTree
{
	/**
	 * Inner helper Node class.
	 * 
	 * @author Aaron Carson
	 * @version Jul 27, 2015
	 */
	private class Node
	{
		Sprite	sprite;
		Node	topLeft, topRight, bottomLeft, bottomRight;
		
		/**
		 * Create a new Node.
		 * 
		 * @param x The x coordinate.
		 * @param y The y coordinate.
		 * @param value The value stored here.
		 */
		Node(Sprite sprite) {
			this.sprite = sprite;
		}
	}
	
	private Node	root;
	
	/**
	 * Insert a Sprite into the QuadTree.
	 * @param sprite The sprite to insert.
	 */
	public void insert(Sprite sprite) {
		root = insert(root, sprite);
	}
	
	/**
	 * Helper recursive method.
	 * @param n The current node.
	 * @param s The sprite to add.
	 * @return The current node.
	 */
	private Node insert(Node n, Sprite s) {
		if (n == null) return new Node(s);
		boolean lessX = less(s.hitbox.x, n.sprite.hitbox.x);
		boolean lessY = less(s.hitbox.y, n.sprite.hitbox.y);
		if (lessX && lessY) n.bottomLeft = insert(n.bottomLeft, s);
		if (!lessX && lessY) n.bottomRight = insert(n.bottomRight, s);
		if (lessX && !lessY) n.topLeft = insert(n.topLeft, s);
		if (!lessX && !lessY) n.topRight = insert(n.topRight, s);
		return n;
	}
	
	/**
	 * Check if i1 is less than i2.
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	private boolean less(double i1, double i2) {
		return i1 < i2;
	}
	
	public void getSprites(Quad quad){
		List<Sprite> results = new ArrayList<Sprite>();
		
		
	}
	
	private void getSprites(Node n, Quad quad, List<Sprite> result){
		if (n == null) return;
		if(quad.collides(n.sprite.hitbox)){
			result.add(n.sprite);
		}
		double xMin = quad.getLeftX();
		double xMax = quad.getRightX();
		double yMin = quad.getTopY();
		double yMax = quad.getBottomY();
		
		if (less(xMin, n.sprite.hitbox.x) && less(yMin, n.sprite.hitbox.y)) getSprites(n.bottomLeft, quad, result);
		if (less(xMin, n.sprite.hitbox.x) && !less(yMax, n.sprite.hitbox.y)) getSprites(n.topLeft, quad, result);
		if (less(xMax, n.sprite.hitbox.x) && less(yMin, n.sprite.hitbox.y)) getSprites(n.bottomRight, quad, result);
		if (less(xMax, n.sprite.hitbox.x) && !less(yMax, n.sprite.hitbox.y)) getSprites(n.topRight, quad, result);
		
	}
	
}
