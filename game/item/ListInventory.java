package game.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implements Inventory using a java.util.List.
 * 
 * @author Aaron Carson
 * @version Jul 23, 2015
 */
public class ListInventory implements Inventory<Item>
{
	public static final int	DEFAULT_SIZE	= 10;
	private List<Item>		items;
	private int				capacity;
	
	/**
	 * Create a new ListInventory.
	 */
	public ListInventory() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Create a new ListInventory.
	 */
	public ListInventory(int capacity) {
		items = new ArrayList<>(capacity);
		this.capacity = capacity;
	}
	
	@Override
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public int getQuantity(int index) {
		if (!withinBounds(index)) return -1;
		else if (index < items.size()) return 1;
		else return 0;
	}
	
	@Override
	public boolean contains(Item item) {
		return items.contains(item);
	}
	
	@Override
	public boolean contains(Item item, int amount) {
		return amount <= count(item);
	}
	
	@Override
	public int count(Item item) {
		int count = 0;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(item)) count++;
		}
		return count;
	}
	
	@Override
	public int countAll() {
		return items.size();
	}
	
	@Override
	public int getEmptySlotsFor(Item item) {
		return items.size() - capacity;
	}
	
	@Override
	public boolean isFull() {
		return items.size() >= capacity;
	}
	
	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	@Override
	public boolean canHold(Item item) {
		return true;
	}
	
	@Override
	public boolean add(Item item) {
		if (isFull()) return false;
		else return items.add(item);
	}
	
	@Override
	public boolean add(Item item, int amount) {
		int remaining = amount;
		for (int i = 0; i < capacity && remaining > 0; i++) {
			if (items.size() < capacity) {
				items.add(item);
				remaining--;
			}
		}
		// if there are less items remaining, then the Inventory was modified.
		return remaining < amount;
		
	}
	
	@Override
	public boolean addAll(Collection<Item> items) {
		boolean modified = false;
		for (Item item : items) {
			modified = add(item) || modified;
		}
		return modified;
	}
	
	@Override
	public boolean addAll(Inventory<Item> inventory) {
		return false;
	}
	
	@Override
	public Item get(int index) {
		if (0 <= index && index < items.size()) return items.get(index);
		else return null;
	}
	
	@Override
	public boolean withinBounds(int index) {
		return 0 <= index && index < capacity;
	}
	
	@Override
	public boolean isEmptyAt(int index) {
		return withinBounds(index) && index >= items.size();
	}
	
	@Override
	public boolean swap(int index1, int index2) {
		if (withinBounds(index1) && withinBounds(index2)) {
			Item item1 = items.get(index1);
			items.set(index1, items.get(index2));
			items.set(index2, item1);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean remove(Item item) {
		return items.remove(item);
	}
	
	@Override
	public Item remove(int index) {
		if (0 <= index && index < items.size()) return items.remove(index);
		else return null;
	}
	
	@Override
	public void sort() {
		Collections.sort(items);
	}
	
	@Override
	public void clear() {
		items.clear();
	}
	
	@Override
	public String toString() {
		return getContents();
	}
}
