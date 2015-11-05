package game.inventory;

import game.item.Currency;
import game.item.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * CoinPurses
 * 
 * @author Aaron Carson
 * @version Aug 11, 2015
 */
public class CoinPurse implements Inventory<Currency>
{
	private int			capacity;
	
	private Map<Currency, Integer> coins;
	
	/**
	 * Create a new CoinPurse of the Given Capacity.
	 * 
	 * @param capacity
	 */
	public CoinPurse(int capacity) {
		this.capacity = capacity;
		this.coins = new HashMap<Currency, Integer>();
		this.coins.put(Currency.COPPER, 0);
	}
	
	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return capacity;
	}
	
	@Override
	public boolean contains(Currency item) {
		return contains(item, 1);
	}
	
	@Override
	public boolean contains(Currency item, int amount) {
		//for (int i = 0; i < size; i++) {
			//if (currency[i].equals(item)) return count[i] >= amount;
		//}
		return false;
	}
	
	@Override
	public int count(Currency item) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getEmptySlotsFor(Currency item) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canHold(Item item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean add(Item item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean add(Item item, int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean addAll(Collection<Item> items) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean addAll(Inventory<Item> inventory) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Currency get(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getQuantity(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isEmptyAt(int index) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean swap(int index1, int index2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean remove(Item item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean withinBounds(int index) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Currency remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void sort() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
}
