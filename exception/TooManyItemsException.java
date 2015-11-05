package exception;

import game.inventory.ItemSlot;
import game.item.Item;

/**
 * Used to represent when too many items are added to an Inventory Slot.
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public class TooManyItemsException extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;	
	
	/**
	 * Create a new Exception.
	 * @param item The item that caused the exception.
	 */
	public TooManyItemsException(Item i){
		super("The item " + i.name + " has max stack of " + i.stack);
	}

	/**
	 * Create a new Exception.
	 * @param itemStack The ItemStack that caused the exception.
	 */
	public TooManyItemsException(ItemSlot i){
		super("The Stack of " + i.getItem().name + " has max stack of " + i.getMaximumCapacity());
	}

}
