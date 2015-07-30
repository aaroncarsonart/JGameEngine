package game.map;

import game.graphics.sprite.Sprite;

import java.util.Comparator;


/**
 * Compare two Sprites based on their vertical position.
 * @author Aaron Carson
 * @version Jul 26, 2015
 */
public class VerticalSpriteComparator implements Comparator<Sprite>
{

	@Override
	public int compare(Sprite s1, Sprite s2) {
		// TODO Auto-generated method stub
		return (int)(s1.hitbox.y - s2.hitbox.y);
	}	
	
}
