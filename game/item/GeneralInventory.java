package game.item;

import java.util.ArrayList;

/**
 * An {@code Inventory} is an arbitrary collection of {@link Item items}, 
 * organized as an array of Slots.  Each Slot references an Item via an itemId
 * and stores the amount of that item there.
 * @author Aaron Carson
 * @version Jun 12, 2015
 */
public abstract class GeneralInventory<E extends Item>
{	
	private ItemSlot[] slots;
	private int occupiedSlots;
	
	/**
	 * Create a new Inventory of the specified capacity.
	 * @param capacity The total capacity of this Inventory (how many slots).
	 */
	@SuppressWarnings("unchecked")
	public GeneralInventory(int capacity){
		this.slots = new ItemSlot[capacity];
		occupiedSlots = 0;
	}
	
	/**
	 * Get how many {@link Slot slots} this {@link GeneralInventory} has.
	 * @return the capacity of this {@code Inventory}, in number of available 
	 *         slots.
	 */
	public int getMaximumCapacity(){
		return slots.length;
	}
	
	/**
	 * Get the {@link Slot slot} at the specified index of this 
	 * {@link GeneralInventory inventory}.
	 * @param index The index of the {@code Slot} to get.
	 * @return The {@code Slot} at the specified {@code index}.
	 */
	public ItemSlot getSlot(int index){
		if(withinBounds(index)) return slots[index];
		else return null;
	}
	
	/**
	 * Check if the given index is within bounds of the inventory.
	 * @param index The index to check
	 * @return True, if within bounds of the slots array.
	 */
	public boolean withinBounds(int index){
		return 0 <= index  & index < getMaximumCapacity();
	}
	
	/**
	 * Swap the slots at the given indices.
	 * @param index1 The first Slot. 
	 * @param index2 The second Slot.
	 * @return True, if the swap was successful, else false.
	 */
	public boolean swapSlots(int index1, int index2){
		if (withinBounds(index1) && withinBounds(index2)){
			ItemSlot slot1 = slots[index1];
			slots[index1] = slots[index2];
			slots[index2] = slot1;			
			return true;
		}
		else{
			return false;
		}		
	}
	
	/**
	 * Check if this Inventory is empty.
	 * @return
	 */
	public boolean isEmpty(){
		return occupiedSlots < 1;
	}
	
	public boolean contains(Item item){
		if (isEmpty()) return false;
		for(int i = 0; i < slots.length; i++){
			ItemSlot slot = slots[i];
			if (slot != null && slot.holds(item)){
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Add the given the Item to the Inventory.
	 * @param item
	 * @return
	 */
	public boolean add(Item item){
		
		for(int i = 0; i < slots.length; i++){
			ItemSlot slot = slots[i];
			// ***********************************
			// Case 1: An Item exists in the slot.
			// ***********************************
			if (slot != null){
				if (slot.holds(item) && slot.hasCapacity()){
					slot.add();
					return true;
				}
			}
			// ***********************************
			// Case 2: The slot is empty.
			// ***********************************
			else{
				slots[i] = new ItemSlot(item, 1);
			}
		}
		// out of room.
		return false;
	}

	/**
	 * Add the given the number of items  to the Inventory.
	 * @param item
	 * @return
	 */
	public boolean add(Item item, int amount){
		for(int i = 0; i < slots.length && amount > 0; i++){
			ItemSlot slot = slots[i];
			// ***********************************
			// Case 1: an item exists in the slot.
			// ***********************************
			if (slot != null){
				
			    // if it is the same item, fill the slot to capacity.
				if (slot.holds(item)){
					amount = slot.addUntilFull(amount);
				}
			}
			
			// ***********************************
			// Case 2: no slot exists: add one.
			// ***********************************
			else {
				slots[i] = new ItemSlot(item);
				//slots[i] = createNewSlot(item);
			}
		}
		
		return false;
	}
	
	public abstract ItemSlot createNewSlot(Item item);
}
