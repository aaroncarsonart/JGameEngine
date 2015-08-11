package game.item;

import exception.TooManyItemsException;

/**
 * An Item Stack is literally a stack of a single kind of Item.  It associates
 * a given Item with a quantity, and the quantity cannot exceed the Count.
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public class ItemSlot
{	
	private final Item item;
	private int quantity;
	private final int maximumCapacity;
	
	/**
	 * Create a new empty ItemStack for the given item, with a default capacity.
	 * @param item The item to stack.
	 */
	public ItemSlot(Item item){
		this(item, 0, item.stack);
	}
	
	/**
	 * Create a new ItemStack for the given item and of given size, with a 
	 * capacity equivalent 
	 * @param item The item to stack.
	 * @param amount How many items to stack.
	 */
	public ItemSlot(Item item, int amount){
		this(item, amount, item.stack);
	}
	
	/**
	 * Create a new ItemStack for the given item and of given size.
	 * @param item The item to stack.
	 * @param amount How many items to stack.
	 */
	public ItemSlot(Item item, int amount, int capacity){
		this.quantity = amount;
		this.item = item;
		this.maximumCapacity = capacity;
		if (!hasCapacity()) throw new TooManyItemsException(item); 
	}
	
	/**
	 * Check if there is room for at least one more item.
	 * @return true, if there is room.
	 */
	public boolean hasCapacity(){
		return quantity < maximumCapacity;
	}

	/**
	 * Get the ItemStack's maximum capacity.
	 * @return the capacity, determined by the Item's stack value.
	 */
	public int getMaximumCapacity(){
		return maximumCapacity;
	}

	/**
	 * Get the ItemStack's maximum capacity.
	 * @return the capacity, determined by the Item's stack value.
	 */
	public int getRemainingCapacity(){
		return maximumCapacity - quantity;
	}

	
	/**
	 * Check if there is room for the given amount of more items.
	 * @param amount The amount to check.
	 * @return True, if there is room.
	 */
	public boolean hasCapacityFor( int amount){
		return quantity + amount < getMaximumCapacity();
	}
	
	/**
	 * Check if this itemStack is empty.
	 * @return True, if there is less than one item in this ItemStack.
	 */
	public boolean isEmpty(){
		return quantity < 1;
	}
	
	/**
	 * Check if the given ItemStack is full.
	 * @return True, if it is full.
	 */
	public boolean isFull(){
		return quantity >= maximumCapacity;
	}
	

	/**
	 * Get how many items are in the stack.
	 * @return The quantity.
	 */
	public int getQuantity(){
		return quantity;
	}
	
	/**
	 * Get the Item this Stack holds.
	 * @return The item.
	 */
	public Item getItem(){
		return item;
	}
	
	/**
	 * Check if this ItemSlot will hold the given item.
	 * @param item The item to check.
	 * @return True, if the given item is the same kind as this ItemSlot's Item.
	 */
	public boolean holds(Item item){
		return this.item.equals(item);
	}
	
	/**
	 * Add one item to the stack.
	 * @return True, if the item could be added.
	 */
	public boolean add(){
		if (isFull()) {
			return false;
		}
		else {
			quantity ++;
			return true;
		}
	}

	/**
	 * Adds the given amount to this ItemSlot until full, and returns a value
	 * if any are remaining.
	 * @param amount The amount to add.
	 * @return zero if all items were added, or a number if some were left.
	 */
	public int addUntilFull(int amount){
		if (amount < 1){
			throw new IllegalArgumentException("Must add at least 1 Item.");
		}
		
		// see if any items will remain.
		int remaining = Math.min((quantity + amount) - maximumCapacity, 0);
		
		// if nothing remains, there was room.
		if (remaining == 0) quantity += amount;
		// otherwise, maximum capacity
		else quantity = maximumCapacity;
		return remaining;
	}

	
	/**
	 * Remove one item to the stack.
	 * @return True, if the item could be removed.
	 */
	public boolean remove(){
		if (isEmpty()) {
			return false;
		}
		else {
			quantity --;
			return true;
		}
	}

	/**
	 * Remove the given quantity of items from the stack.
	 * @param amount The amount to remove.
	 * @return True, if the item could be removed.
	 */
	public boolean remove(int amount){
		if (quantity - amount < 1) {
			return false;
		}
		else {
			quantity --;
			return true;
		}
	}
}
