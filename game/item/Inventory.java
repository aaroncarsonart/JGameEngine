package game.item;

import java.util.Collection;

/**
 * This is the base interface for storing items. The specific implementations
 * are up to the implementing classes. (For instance, more complex behavior can
 * be implemented by allowing more than one item to occupy each slot).
 * 
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public interface Inventory<E extends Item>
{
	/**
	 * Get the total number of item slots in the inventory.
	 * 
	 * @return The maximum capacity.
	 */
	int getCapacity();
	
	/**
	 * Check if this Inventory contains the given Item.
	 * 
	 * @param item The Item to check.
	 * @return True, if it contains the Item.
	 */
	boolean contains(E item);
	
	/**
	 * Check if this Inventory contains at least the given amount of an Item.
	 * 
	 * @param item The Item to check.
	 * @param amount The amount of the Item required.
	 * @return True, if it contains the given amount of the Item.
	 * @throws IllegalArgumentException If count < 0
	 */
	boolean contains(E item, int amount);
	
	/**
	 * Count how many of the given Item is contained in this Inventory.
	 * 
	 * @param item The Item to count.
	 * @return
	 */
	int count(E item);
	
	/**
	 * Count all Items in this Inventory.
	 * 
	 * @return The number of Items in the inventory.
	 */
	int countAll();
	
	/**
	 * Count how many of the given Item can currently be placed into the
	 * Inventory. (If this amount were added, it would cause the Inventory to
	 * become full).
	 * 
	 * @param item The Item to count available slots for.
	 * @return
	 */
	int getEmptySlotsFor(E item);
	
	/**
	 * Check if The Inventory is full.
	 * 
	 * @return True, if full.
	 */
	boolean isFull();
	
	/**
	 * Check if the Inventory is empty.
	 * 
	 * @return True, if empty.
	 */
	boolean isEmpty();
	
	/**
	 * Check if the given Item is a valid item to be stored in this Inventory.
	 * 
	 * @param item The item to check.
	 * @return True, if the item is of a valid type to be placed into the
	 *         Inventory.
	 */
	boolean canHold(Item item);
	
	/**
	 * Add one of the given Item to the list.
	 * 
	 * @param item The item to add.
	 * @return True, if the item was added.
	 */
	boolean add(Item item);
	
	/**
	 * Add the specified amount of the given Item to the Inventory.
	 * 
	 * @param item The Item to add.
	 * @param amount The amount to add.
	 * @return True, if the Inventory was modified.
	 * @throws IllegalArgumentException If amount < 0
	 */
	boolean add(Item item, int amount);
	
	/**
	 * Add all items from the specified Collection to this Inventory, in the
	 * order returned by the Collection's iterator, if there is room.
	 * 
	 * @param items The items to add.
	 * @return True, if the collection was modified.
	 */
	boolean addAll(Collection<Item> items);
	
	/**
	 * Add any Items that fit Inventory to this Inventory
	 * 
	 * @param inventory
	 * @return True, if this Inventory was modified.
	 */
	boolean addAll(Inventory<Item> inventory);
	
	/**
	 * Get the Item stored at the specified slot index of this Inventory.
	 * 
	 * @param index The Index to check at.
	 * @return An Item, or null if the slot is empty.
	 */
	E get(int index);
	
	/**
	 * Get the quantity of the item that is stored at the specified slot index
	 * of this Inventory.
	 * 
	 * @param index The Index to check at.
	 * @return The quantity of the item stored at the index.
	 */
	int getQuantity(int index);
	
	/**
	 * Check if the given slot is empty.
	 * 
	 * @param index The Index to check at.
	 * @return True, if empty.
	 */
	boolean isEmptyAt(int index);
	
	/**
	 * Swap the slots at the given indices.
	 * 
	 * @param index1 The first Slot.
	 * @param index2 The second Slot.
	 * @return True, if the swap was successful, else false.
	 */
	boolean swap(int index1, int index2);
	
	/**
	 * Remove one copy of the given Item from the Inventory, if it is present.
	 * 
	 * @param item The item to remove.
	 * @return True, if successful.
	 */
	boolean remove(Item item);
	
	/**
	 * Check if the given index is within bounds.
	 * 
	 * @param index The index to check
	 * @return True, if the index is within bounds.
	 */
	boolean withinBounds(int index);
	
	/**
	 * Remove an item stored at the given index.
	 * 
	 * @param index The index to remove the item from.
	 * @return The Item if it was successful, or null if not.
	 */
	E remove(int index);
	
	/**
	 * Sort the Items in this Inventory.
	 */
	void sort();
	
	/**
	 * Remove all Items from this Inventory.
	 */
	void clear();
	
	/**
	 * Get a String listing the contents of this inventory.
	 * 
	 * @return
	 */
	default String getContents() {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------------\n");
		sb.append("    Inventory\n");
		sb.append("--------------------------\n");
		int capacity = getCapacity();
		for (int i = 0; i < capacity; i++) {
			sb.append(String.format("%2d: ", i));
			if (!isEmptyAt(i)) {
				sb.append(String.format("%-8s x%-2d", get(i).name,
						getQuantity(i)));
			}
			else {
				sb.append('-');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	/**
	 * Get A String array describing the contents of each inventory slot, with
	 * the name and quantity.
	 * 
	 * @return An array of Strings.
	 */
	default String[] getSlotDescriptions() {
		int capacity = getCapacity();
		String[] descriptions = new String[capacity];
		for (int i = 0; i < capacity; i++) {
			descriptions[i] = getSlotDesciption(i);
		}
		return descriptions;
	}
	
	
	static final String EMPTY = "-         ";

	/**
	 * Get a String describing the contents of the given inventory slot.
	 * @param index The index of the item.
	 * @return A string describing the contents.
	 */
	default String getSlotDesciption(int index){
		if (isEmptyAt(index)) return EMPTY;
		else return (getQuantity(index) == 1 ? get(index).name
				: getQuantity(index) + " " + get(index).getPluralName());
	}
	
	/**
	 * Test a given Inventory of items by seeing output of methods.
	 * 
	 * @param inventory
	 */
	public static void test(Inventory<Item> inventory) {
		System.out.printf("test ListInventory:\n-------------------\n");
		System.out.printf("capacity: %d\n", inventory.getCapacity());
		System.out.printf("countAll: %d\n", inventory.countAll());
		System.out.println(inventory);
		System.out.printf("isEmpty(): %b\n", inventory.isEmpty());
		//System.out.printf("isEmpty(%d): %b\n", -1, inventory.isEmptyAt(-1));
		System.out.printf("isEmpty(%d): %b\n", 0, inventory.isEmptyAt(0));
		System.out.printf("isEmpty(%d): %b\n", 1, inventory.isEmptyAt(1));
		System.out.printf("isEmpty(%d): %b\n", 2, inventory.isEmptyAt(2));
		System.out.printf("isEmpty(%d): %b\n", 9, inventory.isEmptyAt(9));
		//System.out.printf("isEmpty(%d): %b\n", 10, inventory.isEmptyAt(10));
		
		Item item1 = new Item("Blue Potion");
		Item item2 = new Item("Bread");
		Item item3 = new Item("Water");
		Item item4 = new Item("Red Potion");
		Item item5 = new Item("Meat");
		
		System.out.printf("Created new: %s\n", item1);
		System.out.printf("Created new: %s\n", item2);
		System.out.printf("Created new: %s\n", item3);
		System.out.printf("Created new: %s\n", item4);
		System.out.printf("Created new: %s\n", item5);
		System.out.printf("count( %-12s): %d\n", item1.name,
				inventory.count(item1));
		System.out.printf("count( %-12s): %d\n", item2.name,
				inventory.count(item2));
		System.out.printf("count( %-12s): %d\n", item3.name,
				inventory.count(item3));
		System.out.printf("count( %-12s): %d\n", item4.name,
				inventory.count(item4));
		System.out.printf("count( %-12s): %d\n", item5.name,
				inventory.count(item5));
		inventory.add(item1);
		inventory.add(item2, 3);
		inventory.add(item2);
		inventory.add(item5, 3);
		inventory.add(item4, 0);
		
		inventory.add(item1, 0);
		inventory.add(item2, 1);
		inventory.add(item3, 2);
		inventory.add(item4, 3);
		inventory.add(item5, 4);
		inventory.add(item1, 5);
		System.out.printf("count( %-12s): %d\n", item1.name,
				inventory.count(item1));
		System.out.printf("count( %-12s): %d\n", item2.name,
				inventory.count(item2));
		System.out.printf("count( %-12s): %d\n", item3.name,
				inventory.count(item3));
		System.out.printf("count( %-12s): %d\n", item4.name,
				inventory.count(item4));
		System.out.printf("count( %-12s): %d\n", item5.name,
				inventory.count(item5));
		System.out.println(inventory);
		System.out.printf("countAll: %d\n", inventory.countAll());
		System.out.printf("isEmpty(): %b\n", inventory.isEmpty());
		//System.out.printf("isEmpty(%d): %b\n", -1, inventory.isEmptyAt(-1));
		System.out.printf("isEmpty(%d): %b\n", 0, inventory.isEmptyAt(0));
		System.out.printf("isEmpty(%d): %b\n", 1, inventory.isEmptyAt(1));
		System.out.printf("isEmpty(%d): %b\n", 2, inventory.isEmptyAt(2));
		System.out.printf("isEmpty(%d): %b\n", 9, inventory.isEmptyAt(9));
		//System.out.printf("isEmpty(%d): %b\n", 10, inventory.isEmptyAt(10));
		int swap1 = 0, swap2 = 2;
		System.out.printf("\nswap(%d, %d)\n", swap1, swap2);
		inventory.swap(swap1, swap2);
		System.out.println(inventory);
		System.out.println();
		System.out.println("Sort");
		inventory.sort();
		System.out.println(inventory);
		System.out.printf("removed: %s\n", inventory.remove(9));
		System.out.printf("removed: %s\n", inventory.remove(9));
		System.out.printf("removed: %s\n", inventory.remove(0));
		System.out.printf("removed: %s\n", inventory.remove(3));
		System.out.printf("removed: %s\n", inventory.remove(item1));
		System.out.printf("removed: %s\n", inventory.remove(item2));
		System.out.printf("capacity: %d\n", inventory.getCapacity());
		System.out.printf("countAll: %d\n", inventory.countAll());
		System.out.println(inventory);
		System.out.printf("count( %-12s): %d\n", item1.name,
				inventory.count(item1));
		System.out.printf("count( %-12s): %d\n", item2.name,
				inventory.count(item2));
		System.out.printf("count( %-12s): %d\n", item3.name,
				inventory.count(item3));
		System.out.printf("count( %-12s): %d\n", item4.name,
				inventory.count(item4));
		System.out.printf("count( %-12s): %d\n", item5.name,
				inventory.count(item5));
		
		System.out.printf("withinBounds(%2d ): %b\n", -1,
				inventory.withinBounds(-1));
		System.out.printf("withinBounds(%2d ): %b\n", 0,
				inventory.withinBounds(0));
		System.out.printf("withinBounds(%2d ): %b\n", 1,
				inventory.withinBounds(1));
		System.out.printf("withinBounds(%2d ): %b\n", 5,
				inventory.withinBounds(5));
		System.out.printf("withinBounds(%2d ): %b\n", 9,
				inventory.withinBounds(9));
		System.out.printf("withinBounds(%2d ): %b\n", 10,
				inventory.withinBounds(10));
		
		System.out.println();
		System.out.println("clear");
		inventory.clear();
		System.out.printf("capacity: %d\n", inventory.getCapacity());
		System.out.printf("countAll: %d\n", inventory.countAll());
		
	}
	
	/**
	 * Test the inventory.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//test(new ListInventory());
		test(new SlotInventory());
	}
	
}
