package game.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * An {@code Inventory} is an arbitrary collection of {@link Item items},
 * organized as an array of Slots. Each Slot references an Item via an itemId
 * and stores the amount of that item there.
 * 
 * @author Aaron Carson
 * @version Jun 12, 2015
 */
public class SlotInventory implements Inventory<Item>
{
	private ItemSlot[]	slots;
	
	/**
	 * Create a new SlotInventory of the given size.
	 * 
	 * @param size The size of the SlotInventory.
	 */
	public SlotInventory(int size) {
		slots = new ItemSlot[size];
	}
	
	/**
	 * Create a new SlotInventory of default size of 10.
	 */
	public SlotInventory() {
		slots = new ItemSlot[10];
	}
	
	@Override
	public int getCapacity() {
		return slots.length;
	}
	
	@Override
	public boolean contains(Item item) {
		return contains(item, 1);
	}
	
	@Override
	public boolean contains(Item item, int amount) {
		int count = 0;
		for (int i = 0; i < slots.length; i++) {
			
			// if a match is found, increment the count.
			if (!isEmptyAt(i) && slots[i].holds(item)) {
				count += slots[i].getQuantity();
				
				// check if enough were found.
				if (count >= amount) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int count(Item item) {
		int count = 0;
		for (int i = 0; i < slots.length; i++) {
			if (!isEmptyAt(i) && slots[i].holds(item)) {
				
				// if item is found, count how many are in the slot.
				count += slots[i].getQuantity();
			}
		}
		return count;
	}
	
	@Override
	public int countAll() {
		int count = 0;
		for (int i = 0; i < slots.length; i++) {
			if (!isEmptyAt(i)) count += slots[i].getQuantity();
		}
		return count;
	}
	
	@Override
	public int getEmptySlotsFor(Item item) {
		int emptySlots = 0;
		for (int i = 0; i < slots.length; i++) {
			// case 1: an empty slot.
			if (isEmptyAt(i)) {
				emptySlots += item.stack;
			}
			// case 2: a partially full slot of the item.
			else if (slots[i].holds(item)) {
				emptySlots += slots[i].getRemainingCapacity();
			}
			// case 3: the slot is of another item.
		}
		return emptySlots;
	}
	
	@Override
	public boolean isFull() {
		for (int i = 0; i < slots.length; i++) {
			
			// if any slot is empty, or not full ...
			if (slots[i] == null || !slots[i].isFull()) return false;
		}
		// if every slot is not empty, then it is
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return !isFull();
	}
	
	@Override
	public boolean canHold(Item item) {
		return getEmptySlotsFor(item) > 0;
	}
	
	/**
	 * Adds the given item, if there is room, to the first ItemSlot with room.
	 */
	@Override
	public boolean add(Item item) {
		
		// 1. Try to add to itemStacks that have room, then add to empty slots.
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null && slots[i].holds(item) && !slots[i].isFull()) {
				slots[i].add();
				return true;
			}
		}
		// 2. Add any remaining items to empty slots.
		for (int i = 0; i < slots.length; i++) {
			if (isEmptyAt(i)) {
				slots[i] = new ItemSlot(item);
				slots[i].add();
				return true;
			}
		}
		
		// unable to add the single item, return false.
		return false;
		
	}
	
	/**
	 * First looks ahead to verify there is space in the Inventory, then it adds
	 * to matching item stacks, then adds to empty slots. Runtime is ~ O(3n).
	 */
	@Override
	public boolean add(Item item, int amount) {
		
		// 1. verify space for items.
		if (getEmptySlotsFor(item) < amount) {
			return false;
		}
		
		// 2. Add to itemStacks that have room, then add to empty slots.
		for (int i = 0; i < slots.length && amount > 0; i++) {
			if (slots[i] != null && slots[i].holds(item)) {
				amount = slots[i].addUntilFull(amount);
			}
		}
		// 3. Add any remaining items to empty slots.
		for (int i = 0; i < slots.length && amount > 0; i++) {
			if (isEmptyAt(i)) {
				slots[i] = new ItemSlot(item);
				amount = slots[i].addUntilFull(amount);
			}
		}
		
		// all items must be added by this point, return true.
		return true;
	}
	
	@Override
	public boolean addAll(Collection<Item> items) {
		boolean changed = false;
		for (Item item : items) {
			changed |= add(item);
		}
		return changed;
	}
	
	@Override
	public boolean addAll(Inventory<Item> inventory) {
		boolean changed = false;
		for (int i = 0; i < inventory.getCapacity(); i++) {
			if (!inventory.isEmptyAt(i)) {
				Item item = inventory.get(i);
				int amount = inventory.getQuantity(i);
				changed |= add(item, amount);
			}
		}
		return changed;
	}
	
	@Override
	public Item get(int index) {
		if (isEmptyAt(index)) return null;
		else return slots[index].getItem();
	}
	
	@Override
	public int getQuantity(int index) {
		if (isEmptyAt(index)) return 0;
		else return slots[index].getQuantity();
	}
	
	@Override
	public boolean isEmptyAt(int index) {
		return slots[index] == null || slots[index].isEmpty();
	}
	
	@Override
	public boolean swap(int index1, int index2) {
		ItemSlot slot1 = slots[index1];
		slots[index1] = slots[index2];
		slots[index2] = slot1;
		return true;
	}
	
	@Override
	public boolean remove(Item item) {
		// int count = 0;
		for (int i = 0; i < slots.length; i++) {
			
			// if a match is found, remove the item.
			if (!isEmptyAt(i) && slots[i].holds(item)) {
				boolean removed = slots[i].remove();
				if (removed) return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean withinBounds(int index) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Item remove(int index) {
		if (isEmptyAt(index)) return null;
		slots[index].remove();
		return slots[index].getItem();
	}
	
	@Override
	public void sort() {
		
		// a list of items that have already been ordered.
		for (int i = 0; i < slots.length; i++) {
			
			// skip if the slot empty.
			if (isEmptyAt(i)) continue;
			ItemSlot top = slots[i];
			Item item = top.getItem();
			
			/*
			 * step from the top and bottom, adding each ItemSlot of the correct
			 * item from the bottom to the ItemSlot at the top, until the
			 * pointers meet and the top ItemSlots are full.
			 */
			for (int j = slots.length - 1; j > i; j--) {
				
				// skip this
				if (isEmptyAt(j)) continue;
				ItemSlot bottom = slots[j];
				
				
				// add the items in the bottom slot to the top slot till full.
				if (bottom.holds(item)) {
					
					int bottomQuantity = bottom.getQuantity();
					int topCapacity = top.getRemainingCapacity();
					bottom.remove(Math.min(bottomQuantity, topCapacity));
					top.addUntilFull(bottomQuantity);

					// remove slot if empty.
					if (bottom.isEmpty()) slots[j] = null;
					
					// step to next slot if the top is filled.
					if(top.isFull()) break;
				}				
					//for (int k = bottom.getQuantity(); k > 0 && top.hasCapacity(); k--){
						//bottom.remove();
						//slot.add();
						//slot.re
					//}	
			}
		}
		
		// 2. Then, sort the array.
		Arrays.sort(slots, new ItemSlotComparator());
		
	}
	
	@Override
	public void clear() {
		slots = new ItemSlot[slots.length];
	}
	
	@Override
	public String toString() {
		return getContents();
	}
}
