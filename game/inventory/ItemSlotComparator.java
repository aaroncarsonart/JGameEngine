package game.inventory;

import java.util.Comparator;

/**
 * Compare two ItemSlots, based on their contents. They are ordered
 * alphabetically based on the contained Items. Slots with the same item are
 * ordered by greater quantity to lesser quantities.
 * 
 * @author Aaron Carson
 * @version Aug 7, 2015
 */
public class ItemSlotComparator implements Comparator<ItemSlot>
{
	@Override
	public int compare(ItemSlot s1, ItemSlot s2) {
		// handle nulls
		if (s1 == null && s2 == null) return 0;
		else if (s1 == null) return 1;
		else if (s2 == null) return -1;
		
		// next, handle stacks of the same item.
		else if (s1.holds(s2.getItem())) {
			return s2.getQuantity() - s1.getQuantity();
		}
		// next, handle stacks of different kinds.
		else return s1.getItem().compareTo(s2.getItem());
	}
	
}
